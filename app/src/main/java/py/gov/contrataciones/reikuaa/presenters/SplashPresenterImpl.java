package py.gov.contrataciones.reikuaa.presenters;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Base64;
import android.util.Log;

import py.gov.contrataciones.reikuaa.model.Dispositivo;
import py.gov.contrataciones.reikuaa.network.NetworkAccess;
import py.gov.contrataciones.reikuaa.parametros.Parametro;
import py.gov.contrataciones.reikuaa.services.ProxyAPIService;
import py.gov.contrataciones.reikuaa.util.AuthorizationAccess;
import py.gov.contrataciones.reikuaa.view.ListaLicitacionesActivity;
import py.gov.contrataciones.reikuaa.view.SplashView;
import retrofit.client.Response;

/**
 * Created by willtallpear on 10/04/15.
 */
public class SplashPresenterImpl implements SplashPresenter {
    public static final String PROPERTY_REG_ID = "registration_id";
    public static final String PROPERTY_APP_VERSION = "appVersion";
    public static final String PROPERTY_EMAIL = "user_email";
    private SplashView mView;

    public SplashPresenterImpl(SplashView mView) {
        this.mView = mView;
    }

    @Override
    public void registrarDispositivo(Context context, String regId) {
        //Envío de código al Backend
        Log.i("ListaLic", "REGISTRANDO NUEVO USUARIO: " + regId);
        NetworkAccess na = NetworkAccess.getInstance(context);
        ProxyAPIService ps = na.getProxyService();

        Dispositivo t = new Dispositivo();
        t.setMail(AuthorizationAccess.getMail(context));
        t.setRegistrationId(regId);

        String authorization = AuthorizationAccess.getMail(context) + ":" + Parametro.CODIGO_APLICACION;
        authorization = "Basic " + Base64.encodeToString(authorization.getBytes(), Base64.NO_WRAP);
        Response r = ps.addUsuario(authorization, t);

        // Persist the registration ID - no need to register again.
        storeRegistrationId(context, regId, t.getMail());
    }

    @Override
    public void actualizarRegistrationId(Context context,String regId) {
        Log.i("ListaLic", "LLAMADA A ACTUALIZAR REGISTRATION ID con regId: " + regId);

        NetworkAccess na = NetworkAccess.getInstance(context);
        ProxyAPIService ps = na.getProxyService();

        Dispositivo t = new Dispositivo();
        t.setMail(AuthorizationAccess.getMail(context));
        t.setRegistrationId(regId);

        String authorization = AuthorizationAccess.getMail(context) + ":" + AuthorizationAccess.getRegistrationId(context);
        Log.i("ListaLic", "regId viejo: " + AuthorizationAccess.getRegistrationId(context));
        authorization = "Basic " + Base64.encodeToString(authorization.getBytes(), Base64.NO_WRAP);
        Log.i("ListaLic", "Authorization to send: " + authorization);
        Response r = ps.updateUsuario(authorization, t);

        storeRegistrationId(context, regId, t.getMail());
    }

    @Override
    public boolean checkPlayServices() {
        return mView.checkPlayServices();
    }

    @Override
    public String getRegistrationId(Context context) {

        String registrationId = AuthorizationAccess.getRegistrationId(context);
        Log.i("ListaLicitacionPresente", "Registration id: " + registrationId);
        if(registrationId.isEmpty()) {
            Log.i("ListaLicPresenter", "No se ha encontrado Registration ID.");
            mView.actualizarRegistrationId(false);
            return "";
        }

        if(cambioRegistrationId(context)){
            mView.actualizarRegistrationId(true);
            return "";
        }else {
            mView.actualizarRegistrationId(false);
            return registrationId;
        }
    }

    /**
     * Controla que se haya cambiado el registration id del dispositivo
     * @param context
     * @return
     */
    @Override
    public boolean cambioRegistrationId(Context context){
        boolean cambio = false;
        final SharedPreferences prefs = getGCMPreferences(context);
        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        Log.i("ListaLicPresenter", "registeredVersion = " + registeredVersion);
        Log.i("ListaLicPresenter", "currentVersion = " + currentVersion);
        if (registeredVersion != currentVersion) {
            cambio= true;
        }
        return cambio;
    }

    private SharedPreferences getGCMPreferences(Context context) {
        return context.getSharedPreferences(ListaLicitacionesActivity.class.getSimpleName(),
                Context.MODE_PRIVATE);
    }

    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    @Override
    public void registerInBackground() {
        mView.registerInBackground();
    }

    @Override
    public void storeRegistrationId(Context context, String regId, String email) {
        final SharedPreferences prefs = getGCMPreferences(context);
        int appVersion = getAppVersion(context);
        Log.i("ListaLicitacionAct", "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_REG_ID, regId);
        editor.putString(PROPERTY_EMAIL, email);
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.commit();
        AuthorizationAccess.setRegistrationId(regId);
    }
}
