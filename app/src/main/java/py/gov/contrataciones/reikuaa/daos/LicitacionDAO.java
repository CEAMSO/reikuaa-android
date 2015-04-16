package py.gov.contrataciones.reikuaa.daos;

import android.content.Context;
import android.util.Log;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import py.gov.contrataciones.reikuaa.model.Graph;
import py.gov.contrataciones.reikuaa.model.Suscripcion;

/**
 * Created by willtallpear on 09/04/15.
 */
public class LicitacionDAO {

    public static boolean estaSuscripto(Context context, String planificacionId) {
        Realm realm = Realm.getInstance(context);
        RealmQuery<Suscripcion> query = realm.where(Suscripcion.class);
        query.equalTo("planificacionId", planificacionId);
        return query.findFirst()!=null;
    }

    public static void persistir(Context context, Graph licitacion) {
        Realm realm = Realm.getInstance(context);
        realm.beginTransaction();
        Suscripcion s = realm.createObject(Suscripcion.class);
        s.setNombre(licitacion.getNombreLicitacion());
        s.setPlanificacionId(licitacion.getPlanificacionId());
        s.setConvocante(licitacion.getConvocante());
        s.setTipoProcedimiento(licitacion.getTipoProcedimiento().getNombre());
        s.setCategoriaNombre(licitacion.getCategoria().getNombre());
        s.setCategoriaId(licitacion.getCategoria().getId());
        s.setCategoriaCodigo(licitacion.getCategoria().getCodigo());
        realm.commitTransaction();
        Log.i("LicitacionDAO", "Se guardó la suscripción en el dispositivo.");
    }

    public static void borrarSuscripcion(Context context, String planificacionId) {
        Realm realm = Realm.getInstance(context);
        realm.beginTransaction();
        RealmResults<Suscripcion> results = realm.where(Suscripcion.class)
                .equalTo("planificacionId", planificacionId)
                .findAll();
        results.clear();
        realm.commitTransaction();
    }

    public static RealmResults<Suscripcion> obtenerSuscripciones(Context context) {
        Realm realm = Realm.getInstance(context);
        RealmResults<Suscripcion> results = realm.allObjects(Suscripcion.class);
        return results;
    }
}
