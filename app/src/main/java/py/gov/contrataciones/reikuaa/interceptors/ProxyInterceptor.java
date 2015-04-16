package py.gov.contrataciones.reikuaa.interceptors;

import android.util.Log;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import py.gov.contrataciones.reikuaa.model.AccessToken;
import py.gov.contrataciones.reikuaa.network.CallbackProximo;
import py.gov.contrataciones.reikuaa.network.NetworkAccess;

/**
 * Interceptor para el servicio de Proxy. Esta clase intercepta cada request antes de que se haga la llamada
 * Created by willtallpear on 12/03/15.
 */
public class ProxyInterceptor implements Interceptor {

    @Override
    public Response intercept(final Chain chain) throws IOException {
        Log.i("ProxyInterceptor", "Llamada a intercept()");
        final Request request = chain.request();

        Response response = chain.proceed(request);
        final Response newResponse;
        //Interceptamos si se produjo un error de autenticación de forma a volver a generar un access token

        if(response.code()==401) {
            NetworkAccess na = NetworkAccess.getInstance(null);
            AccessToken at = na.refreshAccessToken(); //llamada síncrona
            Log.i("ProxyInterceptor", "AccessToken obtenido: " + at.getAccessToken());
            Request newRequest = request.newBuilder().removeHeader("Authorization")
                    .addHeader("Authorization", "Bearer " + at.getAccessToken())
                    .build();
            Log.i("ProxyInterceptor", "Enviando request nuevo");
            return chain.proceed(newRequest);
        }
        Log.i("ProxyInterceptor", "Enviando response");
        return response;
    }
}
