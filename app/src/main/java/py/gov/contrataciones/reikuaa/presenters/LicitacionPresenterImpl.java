package py.gov.contrataciones.reikuaa.presenters;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import py.gov.contrataciones.reikuaa.model.Graph;
import py.gov.contrataciones.reikuaa.model.JSONResponseContrataciones;
import py.gov.contrataciones.reikuaa.network.CallbackProximo;
import py.gov.contrataciones.reikuaa.services.ContratacionesAPIService;
import py.gov.contrataciones.reikuaa.network.NetworkAccess;
import py.gov.contrataciones.reikuaa.services.ProxyAPIService;
import py.gov.contrataciones.reikuaa.util.AuthorizationAccess;
import py.gov.contrataciones.reikuaa.daos.LicitacionDAO;
import py.gov.contrataciones.reikuaa.view.LicitacionView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by willtallpear on 23/02/15.
 */
public class LicitacionPresenterImpl implements LicitacionPresenter {
    private final String TAG = "py.com.codium.reikuaa";
    private String accessToken = "";
    private  ContratacionesAPIService service;
    private ProxyAPIService proxyAPIService;
    private LicitacionView mView;
    private NetworkAccess na;
    private Graph currentLicitacion;
    public static final String STATE_LICITACION = "py.com.codium.reikuaa.currentLicitacion";

    public LicitacionPresenterImpl(LicitacionView l) {
        mView = l;
    }

    private void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }


    @Override
    public void cargarLicitacion(final String id) {
        mView.hideContent();
        mView.hideError();
        mView.showProgressBar();
        na = NetworkAccess.getInstance(mView.getContext());
        service = na.getService();
        na.getAccessToken(new CallbackProximo() {
            @Override
            public void proximaEjecucion(String accessToken)  {
                licitacionCallback(accessToken, id);
            }

            @Override
            public void onError() {
                mView.showError();
                mView.hideProgressBar();
                mView.hideContent();
            }
        });

    }

    @Override
    public void setLicitacion(Graph graph) {
        this.currentLicitacion = graph;
    }


    /**
     * Realiza la llamada al proxy para enviar una suscripción a la convocatoria
     * @param planificacion_id
     */
    @Override
    public void suscribir(String planificacion_id, Context context) {
        Log.i(TAG, "Suscripcion a " + planificacion_id);
        String authorization = AuthorizationAccess.getMail() + ":" + AuthorizationAccess.getRegistrationId(context);
        authorization = "Basic " + Base64.encodeToString(authorization.getBytes(),  Base64.NO_WRAP);
        na = NetworkAccess.getInstance(mView.getContext());
        proxyAPIService = na.getProxyService();
        Response r = proxyAPIService.suscribir(authorization, planificacion_id.trim());


    }

    @Override
    public void eliminarSuscripcion(String planificacion_id, Context context) {
        Log.i(TAG, "Eliminar Suscripcion a " + planificacion_id);
        String authorization = AuthorizationAccess.getMail() + ":" + AuthorizationAccess.getRegistrationId(context);
        authorization = "Basic " + Base64.encodeToString(authorization.getBytes(),  Base64.NO_WRAP);
        na = NetworkAccess.getInstance(mView.getContext());
        proxyAPIService = na.getProxyService();

        Response r = proxyAPIService.desuscribir(authorization, planificacion_id.trim());
        LicitacionDAO.borrarSuscripcion(context, planificacion_id.trim());
    }

    @Override
    public void almacenarSuscripcion(Context context) {
        Log.i(TAG, "Almacenando suscripción en base de datos");
        LicitacionDAO.persistir(context, currentLicitacion);
    }

    @Override
    public Graph getLicitacion() {
        return currentLicitacion;
    }

    private void licitacionCallback(String accessToken, String id)  {

        Log.i(TAG, "Se va a mandar request con accessToken: " + accessToken);
        service.getConvocatorias("Bearer " + accessToken, id, new Callback<JSONResponseContrataciones>() {
            @Override
            public void success(JSONResponseContrataciones JSONResponseContrataciones, Response response) {
                Log.i(TAG, "convocatorias obtenidas correctamente.");

                //Actualmente tomamos la primera convocatoria obtenida, ya que esa es la activa según las especificaciones del buscador.
                final String idC = JSONResponseContrataciones.getGraph().get(0).getConvocatoria().getList().get(0).getId();
                //hacemos una nueva llamada para obtener los detalles específicos de la convocatoria
                na.getAccessToken(new CallbackProximo() {
                    @Override
                    public void proximaEjecucion(String accessToken) {
                        service.getConvocatoria(accessToken, idC, new Callback<py.gov.contrataciones.reikuaa.model.JSONResponseContrataciones>() {
                            @Override
                            public void success(JSONResponseContrataciones jsonResponseContrataciones, Response response) {
                                //hacemos el seteo de licitación al presenter a través del view para cubrir el caso
                                //en que la instancia se pierde por recreación de la actividad (ej.: rotación de pantalla)
                                setLicitacion(jsonResponseContrataciones.getGraph().get(0));
                                mView.loadConvocatoria(jsonResponseContrataciones.getGraph().get(0));
                                mView.showContent();
                                mView.hideProgressBar();
                                mView.hideError();
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                Log.i(TAG, "Error al obtener convocatorias: " + error.getMessage() + " / " + error.getUrl());
                                if(error.getKind().equals(RetrofitError.Kind.NETWORK)) {
                                    mView.showError();
                                    mView.hideProgressBar();
                                    mView.hideContent();
                                }
                                else if(error.getKind().equals(RetrofitError.Kind.HTTP) && error.getResponse().getStatus()==404) {
                                    mView.showError404();
                                    mView.hideProgressBar();
                                    mView.hideContent();
                                }
                            }
                        });
                    }

                    @Override
                    public void onError() {

                    }
                });

            }

            @Override
            public void failure(RetrofitError error) {
                Log.i(TAG, "Error al obtener convocatorias: " + error.getMessage() + " / " + error.getUrl());
                if(error.getKind().equals(RetrofitError.Kind.NETWORK)) {
                    mView.showError();
                    mView.hideProgressBar();
                    mView.hideContent();
                }
                else if(error.getKind().equals(RetrofitError.Kind.HTTP) && error.getResponse().getStatus()==404) {
                    mView.showError404();
                    mView.hideProgressBar();
                    mView.hideContent();
                }
            }
        });
    }


}
