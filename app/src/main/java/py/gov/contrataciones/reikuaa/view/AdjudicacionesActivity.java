package py.gov.contrataciones.reikuaa.view;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import net.i2p.android.ext.floatingactionbutton.FloatingActionsMenu;

import py.gov.contrataciones.reikuaa.R;
import py.gov.contrataciones.reikuaa.adapters.AdjudicacionesListaAdapter;
import py.gov.contrataciones.reikuaa.model.List;
import py.gov.contrataciones.reikuaa.presenters.AdjudicacionesPresenter;
import py.gov.contrataciones.reikuaa.presenters.AdjudicacionesPresenterImpl;

/**
 * Muestra los detalles de las adjudicaciones relacionadas a una convocatoria en específico.
 */
public class AdjudicacionesActivity extends ActionBarActivity implements py.gov.contrataciones.reikuaa.view.AdjudicacionesView {
    private AdjudicacionesPresenter presenter;
    private Toolbar toolbar;

    private RecyclerView mListaView;
    private RecyclerView.Adapter mListaViewAdapter;
    private RecyclerView.LayoutManager mListaViewLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adjudicaciones);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mListaView = (RecyclerView) findViewById(R.id.cuerpoAdjudicacion);
        if(mListaViewLayoutManager==null) {
            mListaViewLayoutManager = new LinearLayoutManager(this);
        }
        mListaView.setLayoutManager(mListaViewLayoutManager);
        String urlAdjudicaciones = getIntent().getStringExtra(LicitacionActivity.URL_ADJUDICACIONES);
        Log.i("success", "URL obtenida: " + urlAdjudicaciones);
        presenter = new AdjudicacionesPresenterImpl(this);
        presenter.obtenerAdjudicaciones(urlAdjudicaciones);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_adjudicaciones, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void llenarListaAdjudicaciones(java.util.List<List> adjudicaciones) {
        Log.i("AdjudicacionesActivity", "en llenarListaAdjudicaciones");
        if(mListaViewAdapter==null) {
            mListaViewAdapter = new AdjudicacionesListaAdapter(adjudicaciones);
            mListaView.setAdapter(mListaViewAdapter);
        }
        else {
            mListaViewAdapter.notifyDataSetChanged();
        }
    }
    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showProgressBar() {
        LinearLayout mProgress = (LinearLayout) findViewById(R.id.AdjProgress);
        mProgress.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideProgressBar() {
        LinearLayout mProgress = (LinearLayout) findViewById(R.id.AdjProgress);
        mProgress.setVisibility(View.GONE);
    }

    @Override
    public void showError404() {
        RelativeLayout m404 = (RelativeLayout) findViewById(R.id.error404Adjudicacion);
        m404.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideError404() {
        RelativeLayout m404 = (RelativeLayout) findViewById(R.id.error404Adjudicacion);
        m404.setVisibility(View.GONE);
    }

    @Override
    public void showContent() {

        RecyclerView mContent = (RecyclerView) findViewById(R.id.cuerpoAdjudicacion);
        mContent.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideContent() {
        RecyclerView mContent = (RecyclerView) findViewById(R.id.cuerpoAdjudicacion);
        mContent.setVisibility(View.GONE);
    }
}
