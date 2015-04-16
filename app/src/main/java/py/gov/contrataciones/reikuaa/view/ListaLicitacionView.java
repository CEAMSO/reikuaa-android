package py.gov.contrataciones.reikuaa.view;

import android.content.Context;

import java.util.List;

/**
 * Created by willtallpear on 25/02/15.
 */
public interface ListaLicitacionView {
    /**
     * Inicializa el drawer de navegación (menú principal de la aplicación) con los
     * valores proveídos en las listas.
     * @param titulares
     * @param icons
     */
    public void iniciarDrawer(List<String> titulares, List<Integer> icons);

    /**
     * Agrega licitaciones al listado de licitaciones mostrados en pantalla
     * @param titulares
     * @param cuerpo
     * @param iconos
     * @param ids
     */
    public void llenarListaLicitacion(List<String> titulares, List<String> cuerpo, List<Integer> iconos, List<String> ids);

    /**
     * Muestra una barra de progreso en la pantalla
     */
    public void showProgressBar();

    /**
     * Oculta la barra de progreso en la pantalla
     */
    public void hideProgressBar();

    /**
     * Actualiza la lista de licitaciones para que aparezca el progress bar
     * que indica que se están trayendo más licitaciones del backend.
     * @param size tamaño nuevo de la lista
     */
    public void showAddingItem(int size);

    /**
     * Actualiza la lista de licitaciones para que desaparezca el progress bar
     * que indica que se están trayendo más licitaciones del backend.
     * @param size tamaño nuevo de la lista
     */
    public void showDeletingItem(int size);

    /**
     * Muestra un mensaje de error en la pantalla
     */
    public void showError();

    public void showError404();

    public void showError500();

    /**
     * Oculta el mensaje de error de la pantalla.
     */
    public void hideError();

    /**
     * Muestra el contenido principal de la actividad
     */
    public void showContent();

    /**
     * Oculta  el contenido principal de la actividad
     */
    public void hideContent();

    public Context getContext();
}
