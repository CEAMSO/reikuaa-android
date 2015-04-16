package py.gov.contrataciones.reikuaa.presenters;

/**
 * Created by willtallpear on 04/03/15.
 */
public interface AdjudicacionesPresenter {
    /**
     * Obtiene las adjudicaciones con los proveedores adjudicados para la convocatoria específicada.
     *
     * @param id id de la convocatoria de la que se desea obtener las adjudicaciones relacionadas.
     */
    public void obtenerAdjudicaciones(String id);
}
