package py.gov.contrataciones.reikuaa.view;


import android.content.Context;

import py.gov.contrataciones.reikuaa.model.List;

/**
 * Created by willtallpear on 04/03/15.
 */
public interface AdjudicacionesView {
    /**
     * Llena un listado que se muestra en pantalla con los datos de las adjudicaciones
     * obtenidas.
     *
     * @param adjudicaciones lista con las adjudicaciones que se recorren para obtener por cada adjudicación,
     *                       los proveedores adjudicados con sus respectivos montos.
     */
    public void llenarListaAdjudicaciones(java.util.List<List> adjudicaciones);
    /**
     * Obtiene el Context sobre el cual se ejecuta la AdjudicacionesView actual.
     * @see android.content.Context
     * @return el context del view
     */
    public Context getContext();
    /**
     * Muestra una barra de progreso en la pantalla
     */
    public void showProgressBar();
    /**
     * Oculta la barra de progreso en la pantalla
     */
    public void hideProgressBar();
    /**
     * Muestra un mensaje de error 404 (no encontrado) en la pantalla
     */
    public void showError404();
    /**
     * Oculta el mensaje de error 404 (no encontrado) en la pantalla
     */
    public void hideError404();
    /**
     * Muestra el contenido principal de la actividad
     */
    public void showContent();
    /**
     * Oculta el contenido principal de la actividad
     */
    public void hideContent();
}
