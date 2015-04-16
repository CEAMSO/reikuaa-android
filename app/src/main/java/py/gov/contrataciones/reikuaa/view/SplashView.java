package py.gov.contrataciones.reikuaa.view;

import android.content.Context;

/**
 * Created by willtallpear on 10/04/15.
 */
public interface SplashView {
    /**
     * Comprueba que Google Play Services esté presente en el dispositivo, ya
     * que es un requisito de la aplicación.
     *
     * @return true si Google Play Services está presente
     */
    public boolean checkPlayServices();

    /**
     * Comprueba que el dispositivo cuente con un registration Id y en caso de que no
     * lo haga crea uno.
     */
    public void registerInBackground();

    /**
     * Obtiene el Context sobre el cual se ejecuta la ListaLicitacionView actual.
     * @see android.content.Context
     * @return el context del view
     */
    public Context getContext();

    /**
     * True si es necesario actualizar el registration id.
     * False si no
     * @param actualizar
     */
    public void actualizarRegistrationId(Boolean actualizar);

    public void irListaLicitaciones();
}
