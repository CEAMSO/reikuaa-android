package py.gov.contrataciones.reikuaa.presenters;

import android.content.Context;

/**
 * Presenter para el menú principal, Lista de Licitaciones.
 * Created by willtallpear on 25/02/15.
 *
 * @author Guillermo Peralta
 */
public interface ListaLicitacionPresenter {
    /**
     * Muestra el navigation drawer (Menú lateral principal)
     */
    public void iniciarInterfaz();


    public void cargarUltimasLicitaciones(int currentPage);


}
