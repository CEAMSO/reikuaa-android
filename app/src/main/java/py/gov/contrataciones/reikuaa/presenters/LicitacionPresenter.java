package py.gov.contrataciones.reikuaa.presenters;

import android.content.Context;
import android.os.Bundle;

import py.gov.contrataciones.reikuaa.model.Graph;
import py.gov.contrataciones.reikuaa.model.JSONResponseContrataciones;

/**
 * Created by willtallpear on 23/02/15.
 */
public interface LicitacionPresenter {
    /**
     * Carga a la instancia actual la convocatoria a través de la API Rest de la DNCP los datos de la convocatoria especificada en id.
     * @param id id de la convocatoria que se desea obtener
     */
    public void cargarLicitacion(String id);

    /**
     * Obtiene los datos de la convocatoria cargada en la instancia actual. Tener en cuenta
     * que este método no trae los datos por medio de Internet, si no que busca la convocatoria
     * asociada previamente a este LicitacionPresenter. Para asociar la convocatoria al LicitacionPresenter
     * hay que invocar al método cargarLicitacion().
     *
     * @return Graph con los datos de la convocatoria actual.
     */
    public Graph getLicitacion();

    /**
     * Establece la convocatoria a la cual estará asociado el LicitacionPresenter actual.
     * @param c el Graph de la convocatoria que se desea asociar
     */
    public void setLicitacion(Graph c);

    /**
     * Suscribe el dispositivo a un llamado. Guarda información
     * básica de la licitación en el dispositivo
     *
     * @param planificacion_id id de la planificación a la que se desea suscrbir
     * @param context Context del LicitacionView asociado a la instancia actual.
     */
    public void suscribir(String planificacion_id, Context context);

    /**
     * Elimina la suscripción  del dispositivo a un llamado. Elimina información
     * básica de la licitación en el dispositivo
     * @param planificacion_id
     */
    public void eliminarSuscripcion(String planificacion_id, Context context);

    public void almacenarSuscripcion(Context context);

}
