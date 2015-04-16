package py.gov.contrataciones.reikuaa.view;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmResults;
import py.gov.contrataciones.reikuaa.R;
import py.gov.contrataciones.reikuaa.adapters.RealmCategoriaSpinnerAdapter;
import py.gov.contrataciones.reikuaa.adapters.RealmEtapaSpinnerAdapter;
import py.gov.contrataciones.reikuaa.adapters.RealmModalidadSpinnerAdapter;
import py.gov.contrataciones.reikuaa.model.Categoria;
import py.gov.contrataciones.reikuaa.model.Entidad;
import py.gov.contrataciones.reikuaa.model.Etapa;
import py.gov.contrataciones.reikuaa.model.ItemNivel4;
import py.gov.contrataciones.reikuaa.model.TipoProcedimiento;
import py.gov.contrataciones.reikuaa.presenters.BusquedaPresenter;
import py.gov.contrataciones.reikuaa.presenters.BusquedaPresenterImpl;
import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.fourmob.datetimepicker.date.DatePickerDialog.OnDateSetListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Muestra un formulario con campos con opciones posibles para realizar búsquedas
 */
public class BusquedaActivity extends ActionBarActivity implements BusquedaView, OnDateSetListener {
    private Toolbar toolbar;
    private Spinner mCategoriaSpinner;
    private EditText mIdNombre, mCodContratacion;
    private AutoCompleteTextView mProducto, mEntidad;
    private BusquedaPresenter presenter;
    private TextView mEditDesde, mEditHasta;

    /*
     * Constantes para identificar si el campo de calendario corresponde a la fecha inicial (FECHA_DESDE)
     * o a la fecha final (FECHA_HASTA) a especificar
     */
    public final static int FECHA_DESDE = 1;
    public final static int FECHA_HASTA = 2;

    /*
     * Constantes para identificar los spinners existentes en el formulario de búsqueda.
     */
    public static final int CAMPO_ID_CATEGORIA = 3;
    public static final int CAMPO_MODALIDAD = 4;
    public static final int CAMPO_ETAPA = 5;

    private int catId;
    private String modCod;
    private String etapaCod;
    private int conCod;
    private String codProd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        presenter = new BusquedaPresenterImpl(this);
        presenter.obtenerCategorias();
        presenter.obtenerModalidades();
        presenter.obtenerConvocantes();
        presenter.obtenerEtapas();
        presenter.obtenerProductos();
        presenter.agregarCalendario();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_busqueda, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



        return super.onOptionsItemSelected(item);
    }

    public void llenarCategoriaSpinner(RealmResults<Categoria> resultados) {
        mCategoriaSpinner = (Spinner) findViewById(R.id.categoriaSpinner);
        RealmCategoriaSpinnerAdapter adapter = new
                RealmCategoriaSpinnerAdapter(this, R.id.categoriaSpinner, resultados, true);
        mCategoriaSpinner.setAdapter(adapter);
        mCategoriaSpinner.setOnItemSelectedListener(new BusquedaPresenterImpl.CategoriaSpinnerListener(this));
    }


    @Override
    public Realm getBD() {
        return Realm.getInstance(this);
    }

    @Override
    public void mostrarCalendario(int tipo) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog mPicker = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        Log.i("Busqueda", "Se debería mostrar el calendario");
        mPicker.setYearRange(1990, 2036);
        mPicker.show(getSupportFragmentManager(), String.valueOf(tipo));
    }

    @Override
    public void llenarModalidadesSpinner(RealmResults<TipoProcedimiento> results) {
        Spinner mModalidadesSpinner = (Spinner) findViewById(R.id.modalidadSpinner);
        RealmModalidadSpinnerAdapter adapter = new RealmModalidadSpinnerAdapter(this, R.id.modalidadSpinner,
                results, true);
        mModalidadesSpinner.setAdapter(adapter);
        mModalidadesSpinner.setOnItemSelectedListener(new BusquedaPresenterImpl.ModalidadSpinnerListener(this));
    }

    @Override
    public void llenarEtapasSpinner(RealmResults<Etapa> results) {
        Spinner mEtapasSpinner = (Spinner) findViewById(R.id.estadoSpinner);
        RealmEtapaSpinnerAdapter adapter = new RealmEtapaSpinnerAdapter(this, R.id.estadoSpinner,
                results, true);
        mEtapasSpinner.setAdapter(adapter);
        mEtapasSpinner.setOnItemSelectedListener(new BusquedaPresenterImpl.EtapaSpinnerListener(this));
    }

    @Override
    public void llenarConvocantesText(final RealmResults<Entidad> results) {
        //Como actualmente Realm no soporta implementar la interfaz Filter dentro de un RealmBaseAdapter
        //Usaremos para el AutoCompleteTextView un ArrayAdapter, creando previamente una lista con
        //los datos traídos del RealmResults
        List<String> lista = new ArrayList<>();
        for(int i =0; i < results.size(); i++) {
            lista.add(results.get(i).getNombre());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.autocomplete_basic_item_row,
                R.id.item, lista);
        final AutoCompleteTextView mConvocantesTextView = (AutoCompleteTextView) findViewById(R.id.convocanteText);
        mConvocantesTextView.setThreshold(3);
        final List<String> listaAux = lista;
        mConvocantesTextView.setAdapter(adapter);
        mConvocantesTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LinearLayout layout = (LinearLayout) view;
                TextView textView = (TextView) layout.findViewById(R.id.item);
                int pos = listaAux.indexOf((textView.getText()).toString());
                Entidad seleccionado = results.get(pos);
                conCod = seleccionado.getCodigo();
            }
        });
    }

    @Override
    public void llenarProductosText(final RealmResults<ItemNivel4> results) {
        //Como actualmente Realm no soporta implementar la interfaz Filter dentro de un RealmBaseAdapter
        //Usaremos para el AutoCompleteTextView un ArrayAdapter, creando previamente una lista con
        //los datos traídos del RealmResults
        List<String> lista = new ArrayList<>();
        for(int i =0; i < results.size(); i++) {
            lista.add(results.get(i).getDescripcion());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.autocomplete_basic_item_row,
                R.id.item, lista);
        AutoCompleteTextView mProductosTextView = (AutoCompleteTextView) findViewById(R.id.productoText);
        mProductosTextView.setThreshold(3);
        mProductosTextView.setAdapter(adapter);
        final List<String> listaAux = lista;
        mProductosTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LinearLayout layout = (LinearLayout) view;
                TextView textView = (TextView) layout.findViewById(R.id.item);
                int pos = listaAux.indexOf((textView.getText()).toString());
                ItemNivel4 seleccion = results.get(pos);
                codProd = seleccion.getCodigo();
            }
        });
    }

    @Override
    public void addCalendario() {
        //incializamos los campos de texto
        mEditDesde = (TextView) findViewById(R.id.editTextDesde);
        mEditHasta = (TextView) findViewById(R.id.editTextHasta);
        mEditDesde.setInputType(InputType.TYPE_NULL);
        mEditHasta.setInputType(InputType.TYPE_NULL);

        mEditDesde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Busqueda", "Se invoca al clic del edit Desde");
                presenter.mostarCalendario(BusquedaActivity.FECHA_DESDE);
            }
        });
        /*
        mEditDesde.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    Log.i("Busqueda", "Se invoca al focus del edit Desde");
                    presenter.mostarCalendario(BusquedaActivity.FECHA_DESDE);
                }
            }
        }); */

        mEditHasta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.mostarCalendario(BusquedaActivity.FECHA_HASTA);
                Log.i("Busqueda", "Se invoca al clic del edit Hasta");
            }
        });

        /*
        mEditHasta.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    Log.i("Busqueda", "Se invoca al focus del edit Hasta");
                    presenter.mostarCalendario(BusquedaActivity.FECHA_HASTA);
                }
            }
        }); */
    }

    @Override
    public void setCampo(int campo, Object valor) {
        switch(campo) {
            case(BusquedaActivity.CAMPO_ID_CATEGORIA):
                catId = (int) valor;
                break;
            case(BusquedaActivity.CAMPO_ETAPA):
                etapaCod = (String) valor;
                break;
            case(BusquedaActivity.CAMPO_MODALIDAD):
                modCod = (String) valor;
                break;
        }
    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
        TextView mTextView = null;
        month++;

        if(Integer.parseInt(datePickerDialog.getTag())==BusquedaActivity.FECHA_DESDE) {
           mTextView = (TextView) findViewById(R.id.editTextDesde);
        }
        if(Integer.parseInt(datePickerDialog.getTag())==BusquedaActivity.FECHA_HASTA) {
           mTextView = (TextView) findViewById(R.id.editTextHasta);
        }

        String cadena = year + "-" + month + "-" + day;
        mTextView.setText(cadena);
    }

    public void buscar(View view) {
        mIdNombre = (EditText) findViewById(R.id.idNombreEdit);
        mCodContratacion = (EditText) findViewById(R.id.editContratacionText);
        mProducto = (AutoCompleteTextView) findViewById(R.id.productoText);
        mEntidad = (AutoCompleteTextView) findViewById(R.id.convocanteText);
        Intent intent = new Intent(this, ResultadosActivity.class);
        if(catId!=42) {
            intent.putExtra(ResultadosActivity.RESULTADOS_CATEGORIA_ID, catId);
            Log.i("A enviar", "catId: " + catId);
        }
        if(!mIdNombre.getText().toString().equals("")) {
            intent.putExtra(ResultadosActivity.RESULTADOS_ID_NOMBRE, mIdNombre.getText().toString());
            Log.i("A enviar", "Id-Nombre: " + mIdNombre.getText());
        }
        if(!mEditDesde.getText().toString().equals("(Fecha inicial)")) {
            intent.putExtra(ResultadosActivity.RESULTADOS_FECHADESDE, mEditDesde.getText().toString());
            Log.i("A enviar", "Fecha desde: " + mEditDesde.getText());
        }
        if(!mEditHasta.getText().toString().equals("(Fecha final)")) {
            intent.putExtra(ResultadosActivity.RESULTADOS_FECHAHASTA, mEditHasta.getText().toString());
            Log.i("A enviar", "Fecha hasta: " + mEditHasta.getText());
        }
        if(!modCod.equals("TOD")) {
            intent.putExtra(ResultadosActivity.RESULTADOS_MODALIDAD_ID, modCod);
            Log.i("A enviar", "ModCod: " + modCod);
        }
        if(!etapaCod.equals("TOD")) {
            intent.putExtra(ResultadosActivity.RESULTADOS_ETAPA, etapaCod);
            Log.i("A enviar", "EtapaCod: " + etapaCod);
        }
        if(!mProducto.getText().toString().isEmpty() && codProd!=null) {
            intent.putExtra(ResultadosActivity.RESULTADOS_PROD, codProd);
            Log.i("A enviar", "codProd: " + codProd);
        }
        if(!mEntidad.getText().toString().isEmpty() && conCod!=0) {
            intent.putExtra(ResultadosActivity.RESULTADOS_CONVOCANTE, conCod);
            Log.i("A enviar", "conCod: " + conCod);
        }
        if(!mCodContratacion.getText().toString().equals("")) {
            intent.putExtra(ResultadosActivity.RESULTADOS_CODIGOCONTRATACION, mCodContratacion.getText().toString());
            Log.i("A enviar", "Codgo Contratacion: " + mCodContratacion.getText().toString());
        }
        startActivity(intent);
    }
}
