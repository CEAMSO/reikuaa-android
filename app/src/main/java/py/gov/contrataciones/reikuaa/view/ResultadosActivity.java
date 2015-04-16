package py.gov.contrataciones.reikuaa.view;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.List;

import py.gov.contrataciones.reikuaa.R;
import py.gov.contrataciones.reikuaa.adapters.MainDrawerAdapter;
import py.gov.contrataciones.reikuaa.listeners.EndlessRecyclerOnScrollListener;
import py.gov.contrataciones.reikuaa.presenters.ListaLicitacionPresenterImpl;
import py.gov.contrataciones.reikuaa.presenters.ResultadosPresenter;
import py.gov.contrataciones.reikuaa.presenters.ResultadosPresenterImpl;
import py.gov.contrataciones.reikuaa.util.view.DividerItemDecoration;

/**
 * Vista con los resultados de búsqueda. Se muestra un listado con la misma estructura
 * de ListaLicitacionesActivity, y al seleccionar uno de los resultados, se muestra la
 * última convocatoria asociada (si es que existe) en un LicitacionActivity nuevo.
 */
public class ResultadosActivity extends ActionBarActivity implements ResultadosView {
    /*
     * Constantes con los valores para identificar los campos por los cuales se pueden
     * identificar los parámetros de búsqueda
     */
    public static final String RESULTADOS_ID_NOMBRE = "py.gov.contrataciones.residnombre";
    public static final String RESULTADOS_CATEGORIA_ID = "py.gov.contrataciones.rescategoria";
    public static final String RESULTADOS_MODALIDAD_ID = "py.gov.contrataciones.restipoproc";
    public static final String RESULTADOS_FECHADESDE = "py.gov.contrataciones.restfechadesde";
    public static final String RESULTADOS_FECHAHASTA = "py.gov.contrataciones.restfechahasta";
    public static final String RESULTADOS_CODIGOCONTRATACION = "py.gov.contrataciones.restcodcontratacion";
    public static final String RESULTADOS_ETAPA = "py.gov.contrataciones.restetapa";
    public static final String RESULTADOS_PROD = "py.gov.contrataciones.restproducto";
    public static final String RESULTADOS_CONVOCANTE = "py.gov.contrataciones.conv" ;

    private Toolbar toolbar;
    private RecyclerView mListaLicView;
    private RecyclerView.Adapter mListaLicViewAdapter;
    private LinearLayoutManager mListaLicViewLayoutManager;
    private RelativeLayout mError;
    private LinearLayout mProgress;
    private ResultadosPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);

        presenter = new ResultadosPresenterImpl(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mListaLicView = (RecyclerView) findViewById(R.id.ListaResRecycerView);
        if(mListaLicViewLayoutManager == null) {
            mListaLicViewLayoutManager = new LinearLayoutManager(this);
        }
        mListaLicView.setLayoutManager(mListaLicViewLayoutManager);
        mListaLicView.addItemDecoration(new DividerItemDecoration(this, null));
        final Bundle params = getIntent().getExtras();
        presenter.obtenerResultados(params, 1);
        mListaLicView.setOnScrollListener(new EndlessRecyclerOnScrollListener(mListaLicViewLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                presenter.obtenerResultados(params, current_page);
                Log.i("ListaLicActivity", "Cargando más elementos.");
            }
        });
    }

    @Override
    public void llenarListaLicitacion(List<String> titulos, List<String> cuerpo, List<Integer> iconos, List<String> ids) {
        if(mListaLicViewAdapter==null) {
            mListaLicViewAdapter = new MainDrawerAdapter(titulos, iconos, cuerpo, ids, false);
            mListaLicView.setAdapter(mListaLicViewAdapter);
        }
    }

    @Override
    public void showProgressBar() {
        if(mProgress == null )
            mProgress = (LinearLayout) findViewById(R.id.resProgress);
        mProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        if(mProgress == null)
            mProgress = (LinearLayout) findViewById(R.id.resProgress);
        mProgress.setVisibility(View.GONE);
    }

    @Override
    public void showError() {
        if( mError==null )
            mError = (RelativeLayout) findViewById(R.id.resultadosErrorMessage);
        mError.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideError() {
        if( mError==null )
            mError = (RelativeLayout) findViewById(R.id.resultadosErrorMessage);
        mError.setVisibility(View.GONE);
    }

    @Override
    public void showContent() {
        mListaLicView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideContent() {
        mListaLicView.setVisibility(View.GONE);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showAddingItem(int size) {
        mListaLicViewAdapter.notifyItemInserted(size);
    }

    @Override
    public void showDeletingItem(int size) {
        mListaLicViewAdapter.notifyItemRemoved(size);
    }

    @Override
    public void hideError404() {
        RelativeLayout m404 = (RelativeLayout) findViewById(R.id.error404Resultados);
        m404.setVisibility(View.GONE);
    }

    @Override
    public void showError404() {
        RelativeLayout m404 = (RelativeLayout) findViewById(R.id.error404Resultados);
        m404.setVisibility(View.VISIBLE);
    }

}
