package py.gov.contrataciones.reikuaa.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import net.i2p.android.ext.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;
import java.util.Map;

import py.gov.contrataciones.reikuaa.R;
import py.gov.contrataciones.reikuaa.presenters.ConfiguracionPresenter;
import py.gov.contrataciones.reikuaa.presenters.ConfiguracionPresenterImpl;
import retrofit.RetrofitError;

public class ConfiguracionActivity extends ActionBarActivity implements py.gov.contrataciones.reikuaa.view.ConfiguracionView {

    private Toolbar toolbar;
    private ConfiguracionPresenter presenter;
    EditText rucText;
    EditText codigoText;
    RadioButton radioButtonProveedor;
    RadioButton radioButtonConvocante;
    RadioButton radioButtonCiudadano;
    CheckBox checkBoxCambioEstado;
    CheckBox checkBoxAdendas;
    CheckBox checkBoxSubastas;
    CheckBox checkBoxProrrogas;
    CheckBox checkBoxAclaraciones;
    CheckBox checkBoxAvisos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);
        //incialización de toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        presenter = new ConfiguracionPresenterImpl(this);
        rucText = (EditText) findViewById(R.id.editTextRUC);
        codigoText = (EditText) findViewById(R.id.editTextCodigo);
        radioButtonProveedor = (RadioButton) findViewById(R.id.radioButtonProveedor);
        radioButtonConvocante = (RadioButton) findViewById(R.id.radioButtonConvocante);
        radioButtonCiudadano = (RadioButton) findViewById(R.id.radioButtonCiudadano);
        checkBoxCambioEstado = (CheckBox) findViewById(R.id.checkBoxCambioEstado);
        checkBoxAdendas = (CheckBox) findViewById(R.id.checkBoxAdendas);
        checkBoxSubastas = (CheckBox) findViewById(R.id.checkBoxSubastas);
        checkBoxProrrogas = (CheckBox) findViewById(R.id.checkBoxProrrogas);
        checkBoxAclaraciones = (CheckBox) findViewById(R.id.checkBoxAclaraciones);
        checkBoxAvisos = (CheckBox) findViewById(R.id.checkBoxAvisos);

        setEstado();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        final Context context = getContext();

        SharedPreferences prefs = this.getSharedPreferences(ConfiguracionActivity.class.getSimpleName(), Context.MODE_PRIVATE);

        switch(keyCode){
            case KeyEvent.KEYCODE_BACK: // Al apretar el botón de atrás
                final Map<String, Boolean> configuracion = new HashMap<>();
                configuracion.put(ConfiguracionPresenterImpl.PROPERTY_CONF_CAMBIO_ESTADO, checkBoxCambioEstado.isChecked());
                configuracion.put(ConfiguracionPresenterImpl.PROPERTY_CONF_ADENDAS, checkBoxAdendas.isChecked());
                configuracion.put(ConfiguracionPresenterImpl.PROPERTY_CONF_SUBASTAS, checkBoxSubastas.isChecked());
                configuracion.put(ConfiguracionPresenterImpl.PROPERTY_CONF_PRORROGAS, checkBoxProrrogas.isChecked());
                configuracion.put(ConfiguracionPresenterImpl.PROPERTY_CONF_ACLARACIONES, checkBoxAclaraciones.isChecked());
                configuracion.put(ConfiguracionPresenterImpl.PROPERTY_CONF_AVISOS, checkBoxAvisos.isChecked());

                Toast toast = Toast.makeText(context, "Ciudadano guardado", Toast.LENGTH_SHORT);

                if (radioButtonConvocante.isChecked())
                {
                    if (presenter.setearConvocante(context, codigoText.getText().toString()))
                        toast = Toast.makeText(context, "UOC guardada. Codigo: " + codigoText.getText(), Toast.LENGTH_SHORT);
                    else
                        toast = Toast.makeText(context, "UOC inválida. Codigo: " + codigoText.getText(), Toast.LENGTH_SHORT);
                }
                else if (radioButtonProveedor.isChecked())
                {
                    if (presenter.setearProveedor(context, rucText.getText().toString()))
                        toast = Toast.makeText(context, "Proveedor guardado. Ruc: " + rucText.getText(), Toast.LENGTH_SHORT);
                    else
                        toast = Toast.makeText(context, "Proveedor inválido. Ruc: " + rucText.getText(), Toast.LENGTH_SHORT);
                }
                else {
                    if (presenter.setearCiudadano(context))
                        toast = Toast.makeText(context, "Ciudadano guardado", Toast.LENGTH_SHORT);
                }
                toast.show();

                new AsyncTask<Void,Void,Boolean>() {
                    Exception error;
                    @Override
                    protected Boolean doInBackground(Void... params) {
                        try {
                            int response = presenter.suscribir(context, configuracion);
                            Log.i("RESPONSE", "RESPONSE: " + response);
                            if (response == 200)
                            {
                                presenter.setearConfiguracion(context, configuracion);
                                return true;
                            } else {
                                return false;
                            }
                        }
                        catch(final RetrofitError err) {
                            //error de respuesta de servidor
                            Log.i("ConfPresenter", "Error: " + err.getMessage());
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(getContext(), "Suscripcion no guardada. " +  err.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                            error = err;
                            return false;
                        }
                        catch (final Exception e)
                        {
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(getContext(), "Suscripcion no guardada. " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                            return false;
                        }
                    }

                    @Override
                    protected void onPostExecute(Boolean result) {
                        //toastLoading.cancel();
                        if(result) {
                            CharSequence text = "Suscripción guardada.";
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                            Intent intent = new Intent(context, ListaLicitacionesActivity.class);
                            context.startActivity(intent);
                        }
                        else {
                            if(error!=null) {
                                CharSequence text = "Suscripción no guardada.";
                                int duration = Toast.LENGTH_SHORT;
                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();
                                String msg = "Error (producido en onPostExecute()):" + error.getMessage();
                                Log.e("CONFIGURACION ACTIVITY", msg);
                            }
                        }
                    }
                }.execute(null, null, null);
        }
        return super.onKeyDown(keyCode, event);
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        actualizarVisibilidad(view);
    }

    public void actualizarVisibilidad(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        rucText.setVisibility(View.INVISIBLE);
        codigoText.setVisibility(View.INVISIBLE);
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioButtonProveedor:
                if (checked)
                    rucText.setVisibility(View.VISIBLE);
                break;
            case R.id.radioButtonConvocante:
                if (checked)
                    codigoText.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void setEstado() {
        SharedPreferences prefs = this.getSharedPreferences(ConfiguracionActivity.class.getSimpleName(), Context.MODE_PRIVATE);
        if(!(prefs.getString(ConfiguracionPresenterImpl.PROPERTY_CODIGO_UOC,"").isEmpty())) {
            radioButtonConvocante.setChecked(true);
            codigoText.setVisibility(View.VISIBLE);
            codigoText.setText(prefs.getString(ConfiguracionPresenterImpl.PROPERTY_CODIGO_UOC, ""));
            actualizarVisibilidad(radioButtonConvocante);
        }
        else if(!(prefs.getString(ConfiguracionPresenterImpl.PROPERTY_RUC_PROVEEDOR,"").isEmpty())) {
            radioButtonProveedor.setChecked(true);
            rucText.setVisibility(View.VISIBLE);
            rucText.setText(prefs.getString(ConfiguracionPresenterImpl.PROPERTY_RUC_PROVEEDOR, ""));
            actualizarVisibilidad(radioButtonProveedor);
        }
        else {
            radioButtonCiudadano.setChecked(true);
            actualizarVisibilidad(radioButtonCiudadano);
        }

        checkBoxCambioEstado.setChecked(prefs.getBoolean(ConfiguracionPresenterImpl.PROPERTY_CONF_CAMBIO_ESTADO, false));
        checkBoxAdendas.setChecked(prefs.getBoolean(ConfiguracionPresenterImpl.PROPERTY_CONF_ADENDAS, false));
        checkBoxSubastas.setChecked(prefs.getBoolean(ConfiguracionPresenterImpl.PROPERTY_CONF_SUBASTAS, false));
        checkBoxProrrogas.setChecked(prefs.getBoolean(ConfiguracionPresenterImpl.PROPERTY_CONF_PRORROGAS, false));
        checkBoxAclaraciones.setChecked(prefs.getBoolean(ConfiguracionPresenterImpl.PROPERTY_CONF_ACLARACIONES, false));
        checkBoxAvisos.setChecked(prefs.getBoolean(ConfiguracionPresenterImpl.PROPERTY_CONF_AVISOS, false));
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void hideError() {

    }

    @Override
    public void showError404() {

    }

    @Override
    public void hideError404() {

    }

    @Override
    public void showContent() {

    }

    @Override
    public void hideContent() {

    }

    @Override
    public Context getContext() {
        return this;
    }
}
