package py.gov.contrataciones.reikuaa.presenters;

import io.realm.Realm;

/**
 * Created by willtallpear on 26/02/15.
 */
public interface BusquedaPresenter {
    /**
     * obtiene las categorías presentes en la base de datos
     */
    public void obtenerCategorias();
    /**
     * obtiene los tipos de procedimiento presentes en la base de datos
     */
    public void obtenerModalidades();
    /**
     * obtiene los convocantes presentes en la base de datos
     */
    public void obtenerConvocantes();
    /**
     * obtiene las etapas presentes en la base de datos
     */
    public void obtenerEtapas();
    /**
     * obtiene los productos presentes en la base de datos
     */
    public void obtenerProductos();
    /**
     * muestra de forma de pop-up un datepicker para seleccionar
     * fecha
     */
    public void mostarCalendario(int tipo);

    /**
     * agrega un calendario a la interfaz de búsqueda
     */
    public void agregarCalendario();
}
