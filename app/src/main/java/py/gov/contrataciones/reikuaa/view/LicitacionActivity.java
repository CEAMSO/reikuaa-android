package py.gov.contrataciones.reikuaa.view;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import net.i2p.android.ext.floatingactionbutton.FloatingActionButton;
import net.i2p.android.ext.floatingactionbutton.FloatingActionsMenu;

import java.net.MalformedURLException;
import java.net.URL;

import py.gov.contrataciones.reikuaa.R;
import py.gov.contrataciones.reikuaa.model.Categoria;
import py.gov.contrataciones.reikuaa.model.Graph;
import py.gov.contrataciones.reikuaa.presenters.LicitacionPresenter;
import py.gov.contrataciones.reikuaa.presenters.LicitacionPresenterImpl;
import py.gov.contrataciones.reikuaa.daos.LicitacionDAO;
import retrofit.RetrofitError;

/**
 * Activity que muestra los detalles de una convocatoria en específico, con la posibilidad de
 * suscribirse a la misma y ver las adjudicaciones asociadas a la misma (si existen).
 */
public class LicitacionActivity extends ActionBarActivity implements py.gov.contrataciones.reikuaa.view.LicitacionView {
    public static final String ID_PLANIFICACION = "py.gov.contrataciones.idplanificacion";
    public static final String ID_LLAMADO = "py.gov.contrataciones.idplanificacion";
    public static final String PARENT_MIS_LICITACIONES = "py.gov.contrataciones.misLicitaciones";
    private static final String CONVOCATORIA_CARGADA = "py.gov.contrataciones.convCargada";
    private Toolbar toolbar;
    String idLlamado;
    private LicitacionPresenter presenter;
    private String urlAdjudicaciones;
    private boolean convocatoriaCargada = false;
    private ImageView mIconoDetalle;
    private TextView mIdLlamado, mCategoria, mEntidad, mNivel, mModalidad, mEstado, mNombreContacto,
                    mCargoContacto, mTelefonoContacto, mMailContacto, mFechaTopeConsultas, mHoraConsultas,
                    mLugarConsultas, mFechaEntrega, mHoraEntrega, mLugarEntrega, mFechaApertura, mHoraApertura,
                        mLugarApertura, mNombreLicitacion, mFormaPago, mMoneda, mSistemaAdjudicacion, mTipoGarantiaOferta,
                    mObservaciones, mRestricciones;

    public static final String CURRENT_LICITACION = "py.gov.contrataciones.reikuaa.current_licitacion";
    public static final String URL_ADJUDICACIONES = "py.gov.contrataciones.reikuaa.url_adjudicaciones";
    boolean estaSuscripto = false;
    boolean parentMisSuscripciones = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_licitacion);

            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            presenter = new LicitacionPresenterImpl(this);
        inicializarReferencias();
        if(savedInstanceState==null) {
            idLlamado = getIntent().getStringExtra(ID_PLANIFICACION);
        }
        else {
            urlAdjudicaciones = savedInstanceState.getString(URL_ADJUDICACIONES);
            idLlamado = savedInstanceState.getString(ID_PLANIFICACION);
            convocatoriaCargada = savedInstanceState.getBoolean(CONVOCATORIA_CARGADA);
        }
        //if(!convocatoriaCargada)
            presenter.cargarLicitacion(idLlamado);

        FloatingActionButton suscribirseButton = (FloatingActionButton) findViewById(R.id.suscribirse);
        // Default button, if need set it in xml via background="@drawable/default"
        //comprobación de si está suscripto
        estaSuscripto = LicitacionDAO.estaSuscripto(this, idLlamado);
        if(estaSuscripto) {
            suscribirseButton.setIcon(R.drawable.ic_fab_star_checked);
            suscribirseButton.setTitle(getResources().getString(R.string.desuscribirse));
        }else{
            suscribirseButton.setIcon(R.drawable.ic_fab_star);
        }
        suscribirseButton.setOnClickListener(mTogglePlayButton);

    }

    private void inicializarReferencias() {
        mIconoDetalle = (ImageView) findViewById(R.id.iconoCatDetalle);
        mIdLlamado = (TextView) findViewById(R.id.idLlamado);
        mNombreLicitacion = (TextView) findViewById(R.id.nombreLicitacion);
        mCategoria = (TextView) findViewById(R.id.categoria);
        mEntidad = (TextView) findViewById(R.id.entidad);
        mModalidad = (TextView) findViewById(R.id.modalidad);
        mNivel = (TextView) findViewById(R.id.nivelEntidad);
        mEstado = (TextView) findViewById(R.id.estado);
        mNombreContacto = (TextView) findViewById(R.id.nombreContacto);
        mCargoContacto = (TextView) findViewById(R.id.cargo);
        mTelefonoContacto = (TextView) findViewById(R.id.telefono);
        mMailContacto = (TextView) findViewById(R.id.email);
        mFechaTopeConsultas = (TextView) findViewById(R.id.fechaTopeConsulta);
        //mHoraConsultas = (TextView) findViewById(R.id.horaConsulta);
        mLugarConsultas = (TextView) findViewById(R.id.lugarConsulta);
        mFechaEntrega = (TextView) findViewById(R.id.fechaEntrega);
        //mHoraEntrega = (TextView) findViewById(R.id.horaEntrega);
        mLugarEntrega = (TextView) findViewById(R.id.lugarEntrega);
        mFechaApertura = (TextView) findViewById(R.id.fechaApertura);
        //mHoraApertura = (TextView) findViewById(R.id.horaApertura);
        mLugarApertura = (TextView) findViewById(R.id.lugarApertura);
        mFormaPago = (TextView) findViewById(R.id.formaPago);
        mMoneda = (TextView) findViewById(R.id.moneda);
        mSistemaAdjudicacion = (TextView) findViewById(R.id.sistemaAdjudicacion);
        mTipoGarantiaOferta = (TextView) findViewById(R.id.tipoGarantiaOferta);
        mObservaciones = (TextView) findViewById(R.id.observaciones);
        mRestricciones = (TextView) findViewById(R.id.restricciones);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_licitacion, menu);
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        Log.i("LicitacionActivity", "en onSaveInstanceState()");
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(URL_ADJUDICACIONES, urlAdjudicaciones);
        savedInstanceState.putString(ID_PLANIFICACION, idLlamado);
        savedInstanceState.putBoolean(CONVOCATORIA_CARGADA, convocatoriaCargada);
    }

    @Override
    public void onBackPressed() {
        if(getIntent().getStringExtra(PARENT_MIS_LICITACIONES) != null) {
            Intent intent = new Intent(this, MisLicitacionesActivity.class);
            startActivity(intent);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (item.getItemId()) {
            case android.R.id.home:
                if(getIntent().getStringExtra(PARENT_MIS_LICITACIONES)!= null) {
                    Intent i = new Intent(this.getContext(), MisLicitacionesActivity.class);
                    i.putExtra(LicitacionActivity.ID_PLANIFICACION, id);
                    this.getContext().startActivity(i);
                }else{
                    Intent i = new Intent(this.getContext(), ListaLicitacionesActivity.class);
                    i.putExtra(LicitacionActivity.ID_PLANIFICACION, id);
                    this.getContext().startActivity(i);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void loadConvocatoria(Graph datos) {
        convocatoriaCargada = true;
        Log.i("LicitacionActivity", "Cargando convocatoria");
        urlAdjudicaciones = datos.getAdjudicaciones();

        Categoria cat = new Categoria(datos.getCategoria().getId(), datos.getCategoria().getCodigo(), datos.getCategoria().getNombre());
        mIconoDetalle.setImageResource(cat.getIcono());
        mNombreLicitacion.setText(datos.getNombreLicitacion());
        mIdLlamado.setText(datos.getIdLlamado().toString());
        mCategoria.setText(datos.getCategoria().getNombre());
        mEntidad.setText(datos.getConvocante());
        mNivel.setText("-");
        mModalidad.setText(datos.getTipoProcedimiento().getNombre());
        mEstado.setText(datos.getEstado().getNombre());
        mNombreContacto.setText(datos.getNombreContacto());
        mCargoContacto.setText(datos.getCargoContacto());
        mTelefonoContacto.setText(datos.getTelefonoContacto());
        mMailContacto.setText(datos.getEmailContacto());
        mFechaTopeConsultas.setText(datos.getFechaTopeConsulta());
        //mHoraConsultas.setText("-");
        mLugarConsultas.setText(datos.getLugarConsulta());
        mFechaEntrega.setText(datos.getFechaEntregaOferta());
        //mHoraEntrega.setText("-");
        mLugarEntrega.setText(datos.getLugarEntregaOferta());
        mFechaApertura.setText(datos.getFechaAperturaOferta());
        //mHoraApertura.setText("-");
        mLugarApertura.setText(datos.getLugarAperturaOferta());
        mFormaPago.setText(datos.getFormaPago().getNombre());
        mMoneda.setText(datos.getMoneda().getNombre());
        mSistemaAdjudicacion.setText(datos.getSistemaAdjudicacion().getNombre());
        mTipoGarantiaOferta.setText(datos.getTipoGarantiaOferta().getNombre());
        mObservaciones.setText(datos.getObservaciones());
        mRestricciones.setText(datos.getRestricciones());
    }

    @Override
    public void showProgressBar() {
        LinearLayout mProgress = (LinearLayout) findViewById(R.id.linlaHeaderProgress);
        mProgress.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideProgressBar() {
        LinearLayout mProgress = (LinearLayout) findViewById(R.id.linlaHeaderProgress);
        mProgress.setVisibility(View.GONE);
    }

    @Override
    public void showError() {
        RelativeLayout mError = (RelativeLayout) findViewById(R.id.errorMessage);
        mError.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideError() {
        RelativeLayout mError = (RelativeLayout) findViewById(R.id.errorMessage);
        mError.setVisibility(View.GONE);
    }

    @Override
    public void showError404() {
        RelativeLayout m404 = (RelativeLayout) findViewById(R.id.error404);
        m404.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideError404() {
        RelativeLayout m404 = (RelativeLayout) findViewById(R.id.error404);
        m404.setVisibility(View.GONE);
    }

    @Override
    public void showContent() {

        ScrollView mContent = (ScrollView) findViewById(R.id.cuerpoLicitacion);
        FloatingActionsMenu actionsMenu = (FloatingActionsMenu) findViewById(R.id.actionsMenu);
        actionsMenu.setVisibility(View.VISIBLE);
        mContent.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideContent() {
        ScrollView mContent = (ScrollView) findViewById(R.id.cuerpoLicitacion);
        FloatingActionsMenu actionsMenu = (FloatingActionsMenu) findViewById(R.id.actionsMenu);
        actionsMenu.setVisibility(View.GONE);
        mContent.setVisibility(View.GONE);
    }

    @Override
    public Context getContext() {
        return this;
    }

    public void tryAgainFetch(View view) {
        String idllamado = getIntent().getStringExtra(ID_PLANIFICACION);
        presenter.cargarLicitacion(idllamado);
    }

    public void irAdjudicacion(View view) {
        Intent intent = new Intent(this, AdjudicacionesActivity.class);
        URL url = null;
        Log.i("LicitacionActivity", "url: " + urlAdjudicaciones);
        try {
            url = new URL(urlAdjudicaciones);
        } catch (MalformedURLException e) {
            Log.e("LicitacionActivity", "MalformedURLException: " + e.getMessage());
        }

        String stringUrl = url.getPath();
        Log.i("LicitacionActivity", "url a mandar: " + stringUrl);
        intent.putExtra(URL_ADJUDICACIONES, stringUrl);
        startActivity(intent);

    }

    public void enviarSuscripcion(View view) {
        Log.i("LicitacionActivity", "LLAMADA  a EnviarSuscripcion()");
        CharSequence text = "Suscribiendose a licitación...";
        int duration = Toast.LENGTH_SHORT;
        final Toast toastLoading = Toast.makeText(view.getContext(), text, duration);
        toastLoading.show();
        final Context context = view.getContext();
        final View v = view;
        new AsyncTask<Void,Void,Boolean>() {
            Exception error;
            @Override
            protected Boolean doInBackground(Void... params) {
                try {
                    presenter.suscribir(idLlamado, context);
                    presenter.almacenarSuscripcion(context);
                }
                catch(RetrofitError err) {
                    //error de respuesta de servidor
                    Log.i("LicPresenter", "Error: " + err.getMessage());
                    error = err;
                    return false;
                }
                return true;
            }

            @Override
            protected void onPostExecute(Boolean result) {
                toastLoading.cancel();
                if(result) {
                    CharSequence text = "Suscripción guardada.";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    estaSuscripto = !estaSuscripto; // reverse
                    //se habilita botón de suscripción
                    ((FloatingActionButton) v.findViewById(R.id.suscribirse)).setTitle(context.getString(R.string.desuscribirse));
                    ((FloatingActionButton) v.findViewById(R.id.suscribirse)).setIcon(R.drawable.ic_fab_star_checked);
                    ((FloatingActionButton) v.findViewById(R.id.suscribirse)).setEnabled(true);
                    ((FloatingActionButton) v.findViewById(R.id.suscribirse)).forceLayout();
                }
                else {
                    if(error!=null) {
                        CharSequence text = "Suscripción no guardada. Vuelva a intentarlo.";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                        String msg = "Error (producido en onPostExecute()):" + error.getMessage();
                        Log.e("LICITACION ACTIVITY", msg);
                        ((FloatingActionButton) v.findViewById(R.id.suscribirse)).setEnabled(true);
                        ((FloatingActionButton) v.findViewById(R.id.suscribirse)).forceLayout();
                    }
                }
            }
        }.execute(null, null, null);


    }

    public void eliminarSuscripcion(View view) {
        Log.i("LicitacionActivity", "LLAMADA  a ELIMINARSuscripcion()");
        CharSequence text = "Cancelando suscripción a licitación...";
        int duration = Toast.LENGTH_SHORT;
        final Toast toastLoading = Toast.makeText(view.getContext(), text, duration);
        toastLoading.show();
        final Context context = view.getContext();
        final View v = view;
        new AsyncTask<Void, Void, Boolean>() {
            Exception error;

            @Override
            protected Boolean doInBackground(Void... params) {
                try {
                    presenter.eliminarSuscripcion(idLlamado, context);
                }
                catch(RetrofitError err) {
                    //error de respuesta de servidor
                    Log.i("LicPresenter", "Error: " + err.getMessage());
                    error = err;
                    return false;
                }
                return true;
            }

            @Override
            protected void onPostExecute(Boolean result) {
                toastLoading.cancel();
                if (result) {
                    CharSequence text = "Suscripción cancelada.";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    estaSuscripto = !estaSuscripto; // reverse
                    //se habilita el botón de suscripción
                    ((FloatingActionButton) v.findViewById(R.id.suscribirse)).setTitle(context.getString(R.string.suscribrise));
                    ((FloatingActionButton) v.findViewById(R.id.suscribirse)).setIcon(R.drawable.ic_fab_star);
                    ((FloatingActionButton) v.findViewById(R.id.suscribirse)).setEnabled(true);
                    ((FloatingActionButton) v.findViewById(R.id.suscribirse)).forceLayout();
                } else {
                    if (error != null) {
                        CharSequence text = "No se pudo cancelar la suscripción. Vuelva a intentarlo.";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                        String msg = "Error (producido en onPostExecute()):" + error.getMessage();
                        Log.e("LICITACION ACTIVITY", msg);
                        ((FloatingActionButton) v.findViewById(R.id.suscribirse)).setEnabled(true);
                        ((FloatingActionButton) v.findViewById(R.id.suscribirse)).forceLayout();
                    }
                }
            }
        }.execute(null, null, null);


    }

    //botón de suscripción a la licitación
    View.OnClickListener mTogglePlayButton = new View.OnClickListener(){

        @Override
        public void onClick(View v){
            // change your button background

            if(estaSuscripto){//Desuscribir
                ((FloatingActionButton) v.findViewById(R.id.suscribirse)).setEnabled(false);
                eliminarSuscripcion(v);
            }else{//Suscribir
                ((FloatingActionButton) v.findViewById(R.id.suscribirse)).setEnabled(false);
                enviarSuscripcion(v);
            }


        }

    };
}
