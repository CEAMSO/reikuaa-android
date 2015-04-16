package py.gov.contrataciones.reikuaa.services;

import java.util.Map;

import py.gov.contrataciones.reikuaa.model.AccessToken;
import py.gov.contrataciones.reikuaa.model.JSONResponseContrataciones;
import retrofit.Callback;
import retrofit.http.EncodedPath;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.QueryMap;

/**
 * Service (para la librería Retrofit) de manejo de la API de DNCP
 *
 * Created by willtallpear on 23/02/15.
 */
public interface ContratacionesAPIService {


    @GET("/datos/doc/planificaciones/{id}/convocatorias")
    public void getConvocatorias(@Header("Authorization") String accessToken, @Path("id") String id, Callback<JSONResponseContrataciones> callback);

    @GET("/datos/doc/convocatorias/{id}")
    public void getConvocatoria(@Header("Authorization") String accessToken, @Path("id") String id, Callback<JSONResponseContrataciones> callback);

    @GET("/{url}")
    public void getAdjudicaciones(@Header("Authorization") String accessToken, @Path(value="url", encode=false) String url, Callback<JSONResponseContrataciones> callback);

    @GET("/datos/doc/adjudicaciones/{id}/proveedores")
    public void getProveedoresAdjudicados(@Header("Authorization") String accessToken, @Path("id") String id, Callback<JSONResponseContrataciones> callback);

    @GET("/datos/doc/buscadores/licitaciones")
    public void getLicitaciones(@Header("Authorization") String accessToken, @QueryMap Map<String, String> parametros, Callback<JSONResponseContrataciones> callback);
}
