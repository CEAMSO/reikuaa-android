package py.gov.contrataciones.reikuaa.view;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

import py.gov.contrataciones.reikuaa.R;
import py.gov.contrataciones.reikuaa.parametros.Parametro;
import py.gov.contrataciones.reikuaa.presenters.SplashPresenter;
import py.gov.contrataciones.reikuaa.presenters.SplashPresenterImpl;
import py.gov.contrataciones.reikuaa.util.StaticDataLoad;
import retrofit.RetrofitError;

public class SplashActivity extends ActionBarActivity implements SplashView {
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private Boolean actualizarRegistrationId = false;
    private SplashPresenter presenter;
    private GoogleCloudMessaging gcm;
    private String regId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        presenter = new SplashPresenterImpl(this);

        //registracion en Google Play Services
        if(presenter.checkPlayServices()) {
            gcm = GoogleCloudMessaging.getInstance(this);
            regId = presenter.getRegistrationId(this);
            Log.i("ListaLicActivity", "Registration ID recuperado: " + regId);
            this.registerInBackground();
        }

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                StaticDataLoad.loadStatic(getContext()).cargarTodos();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                irListaLicitaciones();
            }
        }.execute();

    }

    @Override
    public boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if(resultCode != ConnectionResult.SUCCESS) {
            if(GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this, PLAY_SERVICES_RESOLUTION_REQUEST).show();
            }
            else {
                Log.i("ListaLicitacionesActivi", "El dispositivo no tiene soporte para Google Play Services");
                finish();
            }
            return false;
        }
        return true;
    }

    @Override
    public void registerInBackground() {
        Log.i("ListaLicActivity", "en registerInBackground()");
        final Context context = getApplicationContext();

        new AsyncTask<Void,Void,Boolean>() {
            Exception error;
            @Override
            protected Boolean doInBackground(Void... params) {
                int noofAttemptsAllowed = 5; //Cantidad de intentos permitidos
                int noofAttempts = 0;
                boolean stopFetching = false;
                if(regId.isEmpty()) {//Si no tiene reg Id obtener
                    while (!stopFetching) {
                        noofAttempts++;
                        if (gcm == null) {
                            gcm = GoogleCloudMessaging.getInstance(context);
                        }
                        try {
                            //dar un tiempo para que se registre correctamente el gcm
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        try {

                            regId = gcm.register(Parametro.SENDER_ID);

                        } catch (IOException ex) {
                            error = ex;
                        }
                        if (!regId.isEmpty() || noofAttempts > noofAttemptsAllowed) {
                            stopFetching = true;
                        }
                        if (!regId.isEmpty()) {
                            if (actualizarRegistrationId) {
                                presenter.actualizarRegistrationId(getContext(), regId);
                            } else {
                                presenter.registrarDispositivo(getContext(), regId);
                            }
                            error = null;
                            return true;
                        }
                    }
                }else{//Si ya tiene un reg id, comprobar que este registrado
                    try {
                        presenter.registrarDispositivo(getContext(), regId);
                    }
                    catch(RetrofitError err) {
                        error = err;
                        return false;
                    }
                }
                return true;
            }


            @Override
            protected void onPostExecute(Boolean result) {
                if(!result) {
                    if(error!=null) {
                        String msg = "Error (producido en onPostExecute()):" + error.getMessage();
                        Log.e("ListaLicitacionAct", msg);
                    }
                }
            }
        }.execute(null, null, null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.checkPlayServices();
    }

    @Override
    public Context getContext() {

        return this;
    }

    @Override
    public void actualizarRegistrationId(Boolean actualizar) {
        actualizarRegistrationId = actualizar;
    }

    @Override
    public void irListaLicitaciones() {
        //finalizar proceso
        Intent mainIntent = new Intent(this, ListaLicitacionesActivity.class);
        this.startActivity(mainIntent);
        this.finish();
    }

}
