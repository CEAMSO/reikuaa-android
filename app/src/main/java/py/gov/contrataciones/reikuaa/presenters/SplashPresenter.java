package py.gov.contrataciones.reikuaa.presenters;

import android.content.Context;

/**
 * Created by willtallpear on 10/04/15.
 */
public interface SplashPresenter {
    /**
     * Verifica que el dispositivo cuente con Google Play Services
     *
     * @return true si el dispositivo cuenta con Google Play Services
     */
    public boolean checkPlayServices();

    /**
     * Obtiene el Registration Id del dispositivo actual
     * @param context
     * @return
     */
    public String getRegistrationId(Context context);

    /**
     * Verifica que hay ocurrido un cambio en el registration id
     * @param context
     * @return
     */
    boolean cambioRegistrationId(Context context);

    /**
     * Comprueba que el dispositivo cuente con un registration Id y en caso de que no
     * lo haga crea uno.
     */
    public void registerInBackground();

    /**
     * Almacena el Registration Id y el E-Mail del dispositivo dentro de la configuración
     * de la aplicación.
     * @param context Context asociado al ListaLicitacionView del ListaLicitacionPresenter actual
     * @param regId Registration ID a almacenar
     * @param email Dirección de E-Mail a almacenar
     */
    public void storeRegistrationId(Context context, String regId, String email);

    /**
     * Registra un nuevo dispositivo en el proxy. Actualiza los datos del registration id
     * si ha cambiado. No hace nada si el usuario ya esta registrado.
     * @param context
     * @param regId
     */
    void registrarDispositivo(Context context, String regId);

    /**
     * Actualiza datos del Registration ID del dispositivo
     * @param regId
     */
    void actualizarRegistrationId(Context context,String regId);
}
