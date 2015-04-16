package py.gov.contrataciones.reikuaa.view;

import android.content.Context;
import android.view.View;

import py.gov.contrataciones.reikuaa.model.Graph;
import py.gov.contrataciones.reikuaa.model.JSONResponseContrataciones;

/**
 * Created by willtallpear on 24/02/15.
 */
public interface LicitacionView {
    /**
     * Cargar los datos de la convocatoria especificada en los campos correspondientes
     * dentro de la vista.
     *
     * @param c Graph con los datos de la convocatoria que se desea mostrar
     */
    public void loadConvocatoria(Graph c);
    /**
     * Muestra una barra de progreso en la pantalla
     */
    public void showProgressBar();
    /**
     * Oculta la barra de progreso en la pantalla
     */
    public void hideProgressBar();
    /**
     * Muestra un mensaje de error en la pantalla
     */
    public void showError();
    /**
     * Oculta el mensaje de error en la pantalla
     */
    public void hideError();
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
    /**
     * Obtiene el Context sobre el cual se ejecuta la LicitacionView actual.
     * @see android.content.Context
     * @return el context del view
     */
    public Context getContext();
}
