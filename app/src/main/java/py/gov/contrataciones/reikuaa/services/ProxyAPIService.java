package py.gov.contrataciones.reikuaa.services;

import java.util.List;

import py.gov.contrataciones.reikuaa.model.AccessToken;
import py.gov.contrataciones.reikuaa.model.Dispositivo;
import py.gov.contrataciones.reikuaa.model.TipoEvento;
import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

/**
 * Define los servicios que pueden ser utilizados por el Proxy
 * Created by gaby.lorely on 09/03/2015.
 */
public interface ProxyAPIService {


    @GET("/token")
    public void getAccessToken(@Header("Authorization") String MailCodigo, Callback<AccessToken> callback);

    @GET("/token")
    public AccessToken refreshAccessToken(@Header("Authorization") String MailCodigo);

    @Headers({
            "Accept: application/vnd.github.v3.full+json",
            "Content-Type: application/json; charset=UTF-8"
    })
    @POST("/usuario")
    public Response addUsuario(@Header("Authorization") String MailCodigoAplicacion, @Body Dispositivo dispositivo);

    @PUT("/usuario")
    public Response updateUsuario(@Header("Authorization") String MailCodigoAplicacion, @Body Dispositivo dispositivo);

    @POST("/licitaciones/{planificacionId}/suscripcion ")
    public Response suscribir(@Header("Authorization")String MailRegistrationId, @Path("planificacionId") String id);

    @DELETE("/licitaciones/{planificacionId}/suscripcion ")
    public Response desuscribir(@Header("Authorization")String MailRegistrationId, @Path("planificacionId") String id);

    @POST("/eventos/suscripcion ")
    public Response suscribirEventos(@Header("Authorization")String MailRegistrationId, @Body List<TipoEvento> tipoEventos);

    @GET("/usuario")
    Response getUsuario(String authorization, Callback<Dispositivo> callback);
}
