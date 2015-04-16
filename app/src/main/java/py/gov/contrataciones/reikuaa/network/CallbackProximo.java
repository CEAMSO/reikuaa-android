package py.gov.contrataciones.reikuaa.network;

/**
 * Created by willtallpear on 06/03/15.
 */
public interface CallbackProximo {
    /**
     * Se define qué realizar una vez se ha obtenido el accessToken
     * @param accessToken token de acceso generado
     */
    public void proximaEjecucion(String accessToken);

    /**
     * Se define qué hacer en caso de error
     */
    public void onError();
}
