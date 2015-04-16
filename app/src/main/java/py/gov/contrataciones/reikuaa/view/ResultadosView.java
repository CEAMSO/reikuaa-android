package py.gov.contrataciones.reikuaa.view;

import android.content.Context;

import java.util.List;

/**
 * Created by willtallpear on 23/03/15.
 */
public interface ResultadosView {
    /**
     * Llena la lista de licitaciones con los resultados obtenidos del buscador.
     *
     * @param titulos Lista de títulos a mostrar
     * @param cuerpo Lista de texto a mostrar debajo del título
     * @param iconos Lista con los íconos identificatorios de la categoría a la que corresponde cada licitación
     * @param ids Lista de ids de cada licitación
     */
    public void llenarListaLicitacion(List<String> titulos, List<String> cuerpo, List<Integer> iconos, List<String> ids);
    /**
     * Muestra el contenido principal de la actividad
     */
    public void showContent();
    /**
     * Oculta el mensaje de error en la pantalla
     */
    public void hideError();
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
     * Oculta el contenido principal de la actividad
     */
    public void hideContent();
    /**
     * Obtiene el Context sobre el cual se ejecuta la LicitacionView actual.
     * @see android.content.Context
     * @return el context del view
     */
    public Context getContext();
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
     * ocultar el error 404
     */
    public void hideError404();

    /**
     * mostrar el error 404
     */
    public void showError404();
}
