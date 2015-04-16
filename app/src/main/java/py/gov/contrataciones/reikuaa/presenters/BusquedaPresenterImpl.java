package py.gov.contrataciones.reikuaa.presenters;

import android.content.ClipData;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import io.realm.Realm;
import io.realm.RealmResults;
import py.gov.contrataciones.reikuaa.model.Categoria;
import py.gov.contrataciones.reikuaa.model.Entidad;
import py.gov.contrataciones.reikuaa.model.Estado;
import py.gov.contrataciones.reikuaa.model.Etapa;
import py.gov.contrataciones.reikuaa.model.ItemNivel4;
import py.gov.contrataciones.reikuaa.model.TipoProcedimiento;
import py.gov.contrataciones.reikuaa.view.BusquedaActivity;
import py.gov.contrataciones.reikuaa.view.BusquedaView;

/**
 * Created by willtallpear on 26/02/15.
 */

public class BusquedaPresenterImpl implements BusquedaPresenter {
    private BusquedaView mView;

    public BusquedaPresenterImpl(BusquedaView mView) {
        this.mView = mView;
    }

    @Override
    public void obtenerCategorias() {
        Realm realm = mView.getBD();
        RealmResults<Categoria> results = realm.allObjectsSorted(Categoria.class, "nombre", true);
        mView.llenarCategoriaSpinner(results);
    }

    @Override
    public void obtenerModalidades() {
        Realm realm = mView.getBD();
        RealmResults<TipoProcedimiento> results = realm.allObjectsSorted(TipoProcedimiento.class, "nombre", true);
        mView.llenarModalidadesSpinner(results);
    }

    @Override
    public void obtenerConvocantes() {
        Realm realm = mView.getBD();
        RealmResults<Entidad> results = realm.allObjectsSorted(Entidad.class, "nombre", true);
        mView.llenarConvocantesText(results);
    }

    @Override
    public void obtenerEtapas() {
        Realm realm = mView.getBD();
        RealmResults<Etapa> results = realm.allObjectsSorted(Etapa.class, "nombre", true);
        mView.llenarEtapasSpinner(results);
    }

    @Override
    public void obtenerProductos() {
        Realm realm = mView.getBD();
        RealmResults<ItemNivel4> results = realm.allObjectsSorted(ItemNivel4.class, "descripcion", true);
        mView.llenarProductosText(results);
    }

    @Override
    public void mostarCalendario(int tipo) {
        mView.mostrarCalendario(tipo);
    }

    @Override
    public void agregarCalendario() {
        mView.addCalendario();
    }

    public static class CategoriaSpinnerListener implements AdapterView.OnItemSelectedListener {
        private BusquedaView mView;

        public CategoriaSpinnerListener(BusquedaView mView) {
            this.mView = mView;
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Categoria cat = (Categoria) parent.getAdapter().getItem(position);
            mView.setCampo(BusquedaActivity.CAMPO_ID_CATEGORIA, cat.getId());
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    public static class ModalidadSpinnerListener implements AdapterView.OnItemSelectedListener {
        private BusquedaView mView;

        public ModalidadSpinnerListener(BusquedaView mView) {
            this.mView = mView;
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            TipoProcedimiento mod = (TipoProcedimiento) parent.getAdapter().getItem(position);
            mView.setCampo(BusquedaActivity.CAMPO_MODALIDAD, mod.getCodigo());
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    public static class EtapaSpinnerListener implements AdapterView.OnItemSelectedListener {
        private BusquedaView mView;

        public EtapaSpinnerListener(BusquedaView mView) {
            this.mView = mView;
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Etapa etapa = (Etapa) parent.getAdapter().getItem(position);
            mView.setCampo(BusquedaActivity.CAMPO_ETAPA, etapa.getCodigo());

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

}

