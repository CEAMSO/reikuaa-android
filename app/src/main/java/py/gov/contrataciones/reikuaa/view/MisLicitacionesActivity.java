package py.gov.contrataciones.reikuaa.view;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.List;

import py.gov.contrataciones.reikuaa.R;
import py.gov.contrataciones.reikuaa.adapters.MainDrawerAdapter;
import py.gov.contrataciones.reikuaa.presenters.MisLicitacionesPresenter;
import py.gov.contrataciones.reikuaa.presenters.MisLicitacionesPresenterImpl;
import py.gov.contrataciones.reikuaa.util.view.DividerItemDecoration;

public class MisLicitacionesActivity extends ActionBarActivity implements MisLicitacionesView {

    private Toolbar toolbar;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mRecyclerViewAdapter;
    private LinearLayoutManager mRecyclerViewLayoutManager;
    private RelativeLayout mError;
    private MisLicitacionesPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_licitaciones);

        presenter = new MisLicitacionesPresenterImpl(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRecyclerView = (RecyclerView) findViewById(R.id.MisSuscripcionesRecycerView);
        if(mRecyclerViewLayoutManager == null) {
            mRecyclerViewLayoutManager = new LinearLayoutManager(this);
        }
        mRecyclerView.setLayoutManager(mRecyclerViewLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, null));
        presenter.obtenerSuscripciones();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void llenarListaSuscripciones(List<String> titulos, List<String> cuerpo, List<Integer> iconos, List<String> ids) {
        if(mRecyclerViewAdapter==null) {
            mRecyclerViewAdapter = new MainDrawerAdapter(titulos, iconos, cuerpo, ids, true);
            mRecyclerView.setAdapter(mRecyclerViewAdapter);
        }
    }

    @Override
    public void showEmpty() {
        mError = (RelativeLayout) findViewById(R.id.errorsinlicitaciones);
        mError.setVisibility(View.VISIBLE);
    }
}
