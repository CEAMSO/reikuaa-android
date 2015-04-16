package py.gov.contrataciones.reikuaa.view;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.h6ah4i.android.widget.advrecyclerview.animator.GeneralItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.animator.SwipeDismissItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.RecyclerViewSwipeManager;
import com.h6ah4i.android.widget.advrecyclerview.touchguard.RecyclerViewTouchActionGuardManager;

import java.io.IOException;
import java.util.List;

import py.gov.contrataciones.reikuaa.adapters.MainDrawerAdapter;
import py.gov.contrataciones.reikuaa.R;
import py.gov.contrataciones.reikuaa.listeners.EndlessRecyclerOnScrollListener;
import py.gov.contrataciones.reikuaa.parametros.Parametro;
import py.gov.contrataciones.reikuaa.presenters.ListaLicitacionPresenter;
import py.gov.contrataciones.reikuaa.presenters.ListaLicitacionPresenterImpl;
import py.gov.contrataciones.reikuaa.util.AuthorizationAccess;
import py.gov.contrataciones.reikuaa.util.StaticDataLoad;
import py.gov.contrataciones.reikuaa.util.view.DividerItemDecoration;

/**
 * Actividad principal de la aplicación, muestra un listado de las licitaciones existentes junto al
 * menú principal de la aplicación (navigation drawer).
 */
public class ListaLicitacionesActivity extends ActionBarActivity implements ListaLicitacionView {



    private Toolbar toolbar;
    private RecyclerView mDrawerRecyclerView;
    private RecyclerView.Adapter mDrawerRecyclerViewAdapter;
    private RecyclerView.LayoutManager mDrawerRecyclerViewLayoutManager;
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private RecyclerView mListaLicView;
    private RecyclerView.Adapter mListaLicViewAdapter;
    private RecyclerView.Adapter mWrappedAdapter;
    private RecyclerViewSwipeManager mRecyclerViewSwipeManager;
    private RecyclerViewTouchActionGuardManager mRecyclerViewTouchActionGuardManager;
    private LinearLayoutManager mListaLicViewLayoutManager;
    private RelativeLayout mError;
    private LinearLayout mProgress;
    private ListaLicitacionPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_licitaciones);
        presenter = new ListaLicitacionPresenterImpl(this);
        //incialización de toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //referencias a RecyclerViews
        mDrawerRecyclerView = (RecyclerView) findViewById(R.id.DrawerRecyclerView);
        mDrawerRecyclerView.setHasFixedSize(true);
        mDrawerRecyclerViewLayoutManager = new LinearLayoutManager(this);
        mDrawerRecyclerView.setLayoutManager(mDrawerRecyclerViewLayoutManager);

        mListaLicView = (RecyclerView) findViewById(R.id.ListaLicRecycerView);
        if(mListaLicViewLayoutManager == null) {
            mListaLicViewLayoutManager = new LinearLayoutManager(this);
        }
        mListaLicView.setLayoutManager(mListaLicViewLayoutManager);
        mListaLicView.addItemDecoration(new DividerItemDecoration(this, null));
        mRecyclerViewTouchActionGuardManager = new RecyclerViewTouchActionGuardManager();
        mRecyclerViewTouchActionGuardManager.setInterceptVerticalScrollingWhileAnimationRunning(true);
        mRecyclerViewTouchActionGuardManager.setEnabled(true);
        mRecyclerViewSwipeManager = new RecyclerViewSwipeManager();
        presenter.cargarUltimasLicitaciones(1);

        //implementación del endless scroll
        mListaLicView.setOnScrollListener(new EndlessRecyclerOnScrollListener(mListaLicViewLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                presenter.cargarUltimasLicitaciones(current_page);
                Log.i("ListaLicActivity", "Cargando más elementos.");
            }
        });
        presenter.iniciarInterfaz();
    }

    @Override
    public Context getContext() {

        return this;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lista_licitaciones, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if( id == R.id.action_search) {
            Intent intent = new Intent(this, BusquedaActivity.class);
            startActivity(intent);
        }
        if (id == R.id.action_notifications) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void iniciarDrawer(List<String> TITLES, List<Integer> ICONS) {

        mDrawerRecyclerViewAdapter = new MainDrawerAdapter(TITLES, ICONS);

        mDrawerRecyclerView.setAdapter(mDrawerRecyclerViewAdapter);

        mDrawer = (DrawerLayout) findViewById(R.id.DrawerLayout);
        mDrawerToggle = new ActionBarDrawerToggle(this,mDrawer,toolbar,R.string.openDrawer,
                R.string.closeDrawer) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // code here will execute once the drawer is opened
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                //code here will execute once the drawer is closed
            }
        };

        mDrawer.setDrawerListener(mDrawerToggle);
        /*
        mDrawerRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                mDrawer.closeDrawers();
                return true;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }
        });
        */
        mDrawerToggle.syncState();
    }

    public void llenarListaLicitacion(List<String> titulares, List<String> cuerpo, List<Integer> iconos, List<String> ids)
    {
        if(mListaLicViewAdapter==null) {
            mListaLicViewAdapter = new MainDrawerAdapter(titulares, iconos, cuerpo, ids, false);
            mWrappedAdapter = mRecyclerViewSwipeManager.createWrappedAdapter(mListaLicViewAdapter);
            final GeneralItemAnimator animator = new SwipeDismissItemAnimator();

            mListaLicView.setAdapter(mWrappedAdapter);
            mListaLicView.setItemAnimator(animator);
            mRecyclerViewTouchActionGuardManager.attachRecyclerView(mListaLicView);
            mRecyclerViewSwipeManager.attachRecyclerView(mListaLicView);
        }
        else
            mListaLicViewAdapter.notifyDataSetChanged();

    }

    @Override
    public void showProgressBar() {
        if(mProgress == null )
            mProgress = (LinearLayout) findViewById(R.id.listaliclinlaHeaderProgress);
        mProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        if(mProgress == null)
            mProgress = (LinearLayout) findViewById(R.id.listaliclinlaHeaderProgress);
        mProgress.setVisibility(View.GONE);
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
    public void showError() {
        if( mError==null )
            mError = (RelativeLayout) findViewById(R.id.listalicitacioneserrorMessage);
        mError.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError404() {
        mError = (RelativeLayout) findViewById(R.id.error404Resultados);
        mError.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError500() {
        mError = (RelativeLayout) findViewById(R.id.error500licitaciones);
        mError.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideError() {
        if( mError==null )
            mError = (RelativeLayout) findViewById(R.id.listalicitacioneserrorMessage);
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

}
