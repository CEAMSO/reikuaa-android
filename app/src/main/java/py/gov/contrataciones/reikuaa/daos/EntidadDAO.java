package py.gov.contrataciones.reikuaa.daos;

import android.content.Context;
import android.util.Log;

import io.realm.Realm;
import io.realm.RealmQuery;
import py.gov.contrataciones.reikuaa.model.Entidad;
import py.gov.contrataciones.reikuaa.model.Suscripcion;

/**
 * Manejador de acceso a Datos para Entidades
 * Created by gaby.lorely on 13/04/2015.
 */
public class EntidadDAO {

    public static boolean existeConvocante(Context context, String codigo) {
        try {
            Realm realm = Realm.getInstance(context);
            RealmQuery<Entidad> query = realm.where(Entidad.class);

            Integer codigoInt = Integer.parseInt(codigo);
            if (codigoInt != null) {
                query.equalTo("codigo", codigoInt);
                return query.findFirst() != null;
            } else
                return false;
        }
        catch (Exception e)
        {
            Log.i("EntidadDAP", e.getMessage());
            return false;
        }
    }
}
