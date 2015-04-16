package py.gov.contrataciones.reikuaa.network;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;

import java.io.File;
import java.io.IOException;

import io.realm.RealmObject;
import py.gov.contrataciones.reikuaa.interceptors.ProxyInterceptor;
import py.gov.contrataciones.reikuaa.model.AccessToken;
import py.gov.contrataciones.reikuaa.parametros.Parametro;
import py.gov.contrataciones.reikuaa.services.ContratacionesAPIService;
import py.gov.contrataciones.reikuaa.services.ProxyAPIService;
import py.gov.contrataciones.reikuaa.util.AuthorizationAccess;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

/**
 * Clase que maneja el acceso a la red por parte de la aplicación.
 *
 * Created by willtallpear on 06/03/15.
 */
public class NetworkAccess {
    private final String TAG = "py.com.codium.reikuaa";

    private String accessToken = "";
    private ContratacionesAPIService service;
    private ProxyAPIService proxyService;
    private static NetworkAccess nInstance;
    private Context context;


    private NetworkAccess(Context context) {
        this.context = context;
        inicializarAPI();
    }

    /**
     * Setea el valor del access token almacenado al especificado.
     * @param text acess token a almacenar
     */
    private void setAccessToken(String text) {
        accessToken = text;
    }

    /**
     * Obtiene el access token de la API del servidor del proxy.
     * @param callback CallbackProximo que es llamado una vez que se obtiene (o se falla al obtener) respuesta del servidor
     */
    public void getAccessToken(final CallbackProximo callback) {
        Log.i("NetworkAccess", "Generar Access Token");
        if(accessToken.isEmpty()) {
            String authorization =  AuthorizationAccess.getMail(context) +":" + AuthorizationAccess.getRegistrationId(context);
            Log.i("NetworkAccess", "Authorization a mandar compuesto por: " + authorization);

            authorization = "Basic " + Base64.encodeToString(authorization.getBytes(), Base64.NO_WRAP);
            Log.i("NetworkAccess", "En Base64: " + authorization);
            proxyService.getAccessToken(authorization, new Callback<AccessToken>() {

                @Override
                public void success(AccessToken accessToken, Response response) {
                    Log.i(TAG, "accessToken generado: " + accessToken.getAccessToken());
                    setAccessToken(accessToken.getAccessToken());
                    callback.proximaEjecucion(accessToken.getAccessToken());
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.i(TAG, "Error Retrofit: " + error.getMessage() + " Tipo: " + error.getKind().name());
                    if (error.getKind().equals(RetrofitError.Kind.NETWORK)) {
                        callback.onError();
                    }
                    else {
                        accessToken = "";
                    }
                }
            });
        }
        else {
            Log.i("NetworkAccess", "Existe accessToken mandado: " + accessToken);
            callback.proximaEjecucion(accessToken);
        }
    }


    //Maneja la obtención de un nuevo access token cuando uno anterior vence de forma síncrona
    //utilizada por el ProxyInterceptor
    public AccessToken refreshAccessToken() {
        String authorization =  AuthorizationAccess.getMail(context) +":" + AuthorizationAccess.getRegistrationId(context);
        authorization = "Basic " + Base64.encodeToString(authorization.getBytes(), Base64.NO_WRAP);
        AccessToken at = proxyService.refreshAccessToken(authorization);
        setAccessToken(at.getAccessToken());
        return at;
    }

    public static NetworkAccess getInstance(Context context) {
        if(nInstance==null) {
            nInstance = new NetworkAccess(context);
        }
        return nInstance;
    }

    public  ContratacionesAPIService getService() {
        return this.service;
    }

    public ProxyAPIService getProxyService(){ return this.proxyService; }

    private boolean inicializarAPI() {
        if(service==null) {
            Log.i("TAG", "inicializando API de Contrataciones");

            //Configuramos para que GSON soporte clases heredadas de RealmObject
            Gson gson = new GsonBuilder()
                    .setExclusionStrategies(new ExclusionStrategy() {
                        @Override
                        public boolean shouldSkipField(FieldAttributes f) {
                            return f.getDeclaringClass().equals(RealmObject.class);
                        }

                        @Override
                        public boolean shouldSkipClass(Class<?> clazz) {
                            return false;
                        }
                    })
                    .create();

            //Configuramos el cache
            File httpCacheDirectory = new File(context.getCacheDir(), "responses");

            Cache httpResponseCache = null;
            try {
                httpResponseCache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);
            } catch (IOException e) {
                Log.e("Retrofit", "no se pudo crear cache http", e);
            }

            //configuramos el Interceptor que va a añadir una cabecera de Cache al Response
            final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
                @Override public com.squareup.okhttp.Response intercept(Chain chain) throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                            .header("Cache-Control", Parametro.CACHE_CONTROL)
                            .build();
                }
            };

            OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient.setCache(httpResponseCache);
            okHttpClient.interceptors().add(new ProxyInterceptor()); //Este interceptor es clave para regenerar tokens vencidos
            okHttpClient.networkInterceptors().add(REWRITE_CACHE_CONTROL_INTERCEPTOR);

            //Inicializamos el adaptador para la API de Contrataciones
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(Parametro.CONTRATACIONES_URL)
                    .setClient(new OkClient(okHttpClient))
                    .setConverter(new GsonConverter(gson))
                    .build();

            service = restAdapter.create(ContratacionesAPIService.class);

            OkHttpClient okHttpClientProxy = new OkHttpClient();

            Log.i("NetworkAccess", "Inicializando API de Proxy");
            //Inicializamos el adapter para el proxy de la APP
            RestAdapter restAdapterProxy = new RestAdapter.Builder()
                    .setEndpoint(Parametro.PROXY_URL)
                    .setClient(new OkClient(okHttpClientProxy))
                    .setConverter(new GsonConverter(gson))
                    .build();

            proxyService = restAdapterProxy.create(ProxyAPIService.class);

        }

        return true;
    }

}
