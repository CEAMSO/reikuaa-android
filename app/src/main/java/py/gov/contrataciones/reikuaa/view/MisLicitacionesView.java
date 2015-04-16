package py.gov.contrataciones.reikuaa.view;

import android.content.Context;

import java.util.List;

/**
 * Created by willtallpear on 09/04/15.
 */
public interface MisLicitacionesView {
    public Context getContext();

    public void llenarListaSuscripciones(List<String> titulos, List<String> cuerpo, List<Integer> iconos, List<String> ids);

    public void showEmpty();
}
