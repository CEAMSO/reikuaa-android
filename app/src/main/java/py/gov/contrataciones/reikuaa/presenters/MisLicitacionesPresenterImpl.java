package py.gov.contrataciones.reikuaa.presenters;

import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import io.realm.RealmResults;
import py.gov.contrataciones.reikuaa.R;
import py.gov.contrataciones.reikuaa.model.Categoria;
import py.gov.contrataciones.reikuaa.model.Suscripcion;
import py.gov.contrataciones.reikuaa.daos.LicitacionDAO;
import py.gov.contrataciones.reikuaa.view.MisLicitacionesView;

/**
 * Created by willtallpear on 09/04/15.
 */
public class MisLicitacionesPresenterImpl implements MisLicitacionesPresenter {
    private MisLicitacionesView mView;
    private java.util.List<String> titulos;
    private java.util.List<String> cuerpo;
    private java.util.List<String> ids;
    private java.util.List<Integer> iconos;

    public MisLicitacionesPresenterImpl(MisLicitacionesView mView) {
        this.mView = mView;
    }

    @Override
    public void obtenerSuscripciones() {
        RealmResults<Suscripcion> suscripciones = LicitacionDAO.obtenerSuscripciones(mView.getContext());
        if (suscripciones.isEmpty()) {
            mView.showEmpty();
        } else {
            if (titulos == null) titulos = new ArrayList<>();
            if (cuerpo == null) cuerpo = new ArrayList<>();
            if (ids == null) ids = new ArrayList<>();
            if (iconos == null) iconos = new ArrayList<>();
            for (Suscripcion s : suscripciones) {
                titulos.add(s.getNombre());
                String cuerpoStr = "Convocante: " + s.getConvocante() + "\n\n" +
                        "Tipo de Procedimiento: " + s.getTipoProcedimiento();
                cuerpo.add(cuerpoStr);
                ids.add(s.getPlanificacionId());

                Categoria c = new Categoria(s.getCategoriaId(), s.getCategoriaCodigo(), s.getCategoriaNombre());
                iconos.add(c.getIcono());
            }
            mView.llenarListaSuscripciones(titulos, cuerpo, iconos, ids);
        }
    }

}
