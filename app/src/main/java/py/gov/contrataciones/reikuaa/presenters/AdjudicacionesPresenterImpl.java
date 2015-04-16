package py.gov.contrataciones.reikuaa.presenters;

import py.gov.contrataciones.reikuaa.network.CallbackProximo;
import py.gov.contrataciones.reikuaa.services.ContratacionesAPIService;
import py.gov.contrataciones.reikuaa.network.NetworkAccess;
import android.util.Log;

import py.gov.contrataciones.reikuaa.model.JSONResponseContrataciones;
import py.gov.contrataciones.reikuaa.model.List;
import py.gov.contrataciones.reikuaa.view.AdjudicacionesView;
import py.gov.contrataciones.reikuaa.presenters.AdjudicacionesPresenter;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by willtallpear on 04/03/15.
 */
public class AdjudicacionesPresenterImpl implements AdjudicacionesPresenter {
    private AdjudicacionesView mView;
    private NetworkAccess na;
    private ContratacionesAPIService service;
    private String accessToken;
    private java.util.List<List> proveedores;
    public AdjudicacionesPresenterImpl(AdjudicacionesView mView) {
        this.mView = mView;
    }

    @Override
    public void obtenerAdjudicaciones(String url) {
        Log.i("Success", "En obtenerAdjudicaciones" );
        mView.hideContent();
        mView.hideError404();
        mView.showProgressBar();
        na = NetworkAccess.getInstance(mView.getContext());
        service = na.getService();

        final String urlAux = url;
        na.getAccessToken(new CallbackProximo() {
            @Override
            public void proximaEjecucion(String accessToken) {
                Log.i("Success", "En próximaEjecucion con accessToken: " + accessToken);
                final String aToken = accessToken;
                service.getAdjudicaciones("Bearer " + accessToken, urlAux, new Callback<JSONResponseContrataciones>() {
                    @Override
                    public void success(JSONResponseContrataciones jsonResponseContrataciones, Response response) {
                        Log.i("Success", "Adjudicaciones obtenidas correctamente.");
                        java.util.List<List> adjudicaciones = jsonResponseContrataciones.getGraph().get(0).getAdjudicacion().getList();
                        for(List adjudicacion : adjudicaciones) {
                            Log.i("Success", "En adjudicacion: " + adjudicacion.getId());
                            service.getProveedoresAdjudicados("Bearer " + aToken, adjudicacion.getId(), new Callback<JSONResponseContrataciones>() {
                                @Override
                                public void success(JSONResponseContrataciones JSONResponseContrataciones, Response response) {
                                    Log.i("success", "Adjudicacion obtenida correctamente");
                                    java.util.List<List> prov = JSONResponseContrataciones.getGraph().get(0).getContrato().getList();
                                    if(proveedores==null)
                                        proveedores = prov;
                                    else {
                                        proveedores.addAll(prov);
                                    }
                                    Log.i("Success", "Adjudicación obtenida correctamente " + proveedores.toString());
                                    mView.llenarListaAdjudicaciones(proveedores);
                                    mView.showContent();
                                    mView.hideProgressBar();
                                    mView.hideError404();
                                }

                                @Override
                                public void failure(RetrofitError error) {
                                    Log.i("error", "Error: " + error.getMessage() + " " + error.getUrl());


                                }
                            });
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        if (error.getKind().equals(RetrofitError.Kind.HTTP) && error.getResponse().getStatus() == 404) {
                            Log.i("AdjudicacionesPres", "404 en " + error.getUrl());
                            mView.showError404();
                            mView.hideProgressBar();
                            mView.hideContent();
                        }
                        Log.e("failure", "Error: " + error.getMessage());
                    }
                });
            }

            @Override
            public void onError() {
                Log.e("error", "Error.");
                mView.showError404();
                mView.hideProgressBar();
                mView.hideContent();
            }
        });

    }
}
