package py.gov.contrataciones.reikuaa.presenters;

import android.os.Bundle;

import java.util.Map;

/**
 * Created by willtallpear on 23/03/15.
 */
public interface ResultadosPresenter {
    /**
     * Obtiene los resultados de búsqueda tomando como paráemtros de búsqueda los
     * traídos de la vista de búsqueda.
     * @param parametros Bundle con los parámetros por los cuales realizar la búsqueda
     * @param currentPage página de resultados que se desea mostrar. La primera llamada se hará con 1, la segunda con 2, etc.
     */
    public void obtenerResultados(Bundle parametros, int currentPage);

}
