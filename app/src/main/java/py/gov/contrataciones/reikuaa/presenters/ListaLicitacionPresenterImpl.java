package py.gov.contrataciones.reikuaa.presenters;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Base64;
import android.util.Log;

import org.apache.http.auth.AUTH;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import py.gov.contrataciones.reikuaa.R;
import py.gov.contrataciones.reikuaa.model.Categoria;
import py.gov.contrataciones.reikuaa.model.Dispositivo;
import py.gov.contrataciones.reikuaa.model.JSONResponseContrataciones;
import py.gov.contrataciones.reikuaa.model.List;
import py.gov.contrataciones.reikuaa.model.Pagination;
import py.gov.contrataciones.reikuaa.network.CallbackProximo;
import py.gov.contrataciones.reikuaa.network.NetworkAccess;
import py.gov.contrataciones.reikuaa.parametros.Parametro;
import py.gov.contrataciones.reikuaa.services.ContratacionesAPIService;
import py.gov.contrataciones.reikuaa.services.ProxyAPIService;
import py.gov.contrataciones.reikuaa.util.AuthorizationAccess;
import py.gov.contrataciones.reikuaa.view.ConfiguracionActivity;
import py.gov.contrataciones.reikuaa.view.ListaLicitacionView;
import py.gov.contrataciones.reikuaa.view.ListaLicitacionesActivity;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by willtallpear on 25/02/15.
 */
public class ListaLicitacionPresenterImpl implements ListaLicitacionPresenter {


    private ListaLicitacionView mView;
    private String TITLES[] = {"Todas las licitaciones", "Mis suscripciones", "Histórico de Precios",
             "Visitar portal web", "Configuración",
            "Ayuda"};
    private Integer ICONS[] = {R.drawable.ic_todas, R.drawable.ic_mias, R.drawable.ic_historico,
             R.drawable.ic_web, R.drawable.ic_config,
            R.drawable.ic_ayuda};


    private java.util.List<String> titulos;
    private java.util.List<String> cuerpo;
    private java.util.List<String> ids;
    private java.util.List<Integer> iconos;
    private ContratacionesAPIService service;
    private NetworkAccess na;
    private int cantResultados = 1;
    public static final String PROPERTY_REG_ID = "registration_id";
    public static final String PROPERTY_APP_VERSION = "appVersion";
    public static final String PROPERTY_EMAIL = "user_email";


    public ListaLicitacionPresenterImpl(ListaLicitacionView mView) {
        this.mView = mView;
    }

    @Override
    public void cargarUltimasLicitaciones(final int currentPage) {
        Log.i("ListaLicitacionPres", "En cargarUltimasLicitaciones()");

        if(currentPage==1) { //sólo en la primera carga ocultar la lista
            mView.hideContent();
            mView.hideError();
            mView.showProgressBar();
        }
        else { //mostramos item de carga
            titulos.add(null);
            mView.showAddingItem(titulos.size());
        }

        na = NetworkAccess.getInstance(mView.getContext());
        service = na.getService();

        na.getAccessToken(new CallbackProximo() {
            @Override
            public void proximaEjecucion(String accessToken) {
                Log.i("ListaLicitacionPres", "En proximaEjecucion()");
                llamadaLicitaciones(accessToken, currentPage);
            }

            @Override
            public void onError() {
                Log.e("ListaLicitacionPres", "Ocurrió un error");
                mView.showError();
                mView.hideProgressBar();
                mView.hideContent();
            }
        });
    }

    /**
     * Hace una llamada a la API Rest de Contrataciones para obtener las licitaciones.
     * @param accessToken accessToken para la autenticación
     * @param currentPage página de resultados que se desea mostrar. La primera llamada se hará con 1, la segunda con 2, etc.
     */
    private void llamadaLicitaciones(String accessToken, final int currentPage) {
        Log.i("ListaLicitacionPres", "En llamadaLicitaciones()");
        int cant = Integer.valueOf(Parametro.LIMIT_PARAM_FETCH_LICITACIONES);
        Map<String,String> query = new HashMap<>();
        //comprobamos si se trata de una uoc o convocante para agregar parámetros al query
        SharedPreferences prefs = mView.getContext().getSharedPreferences(ConfiguracionActivity.class.getSimpleName(), Context.MODE_PRIVATE);
        if(!(prefs.getString(ConfiguracionPresenterImpl.PROPERTY_CODIGO_UOC,"").isEmpty())) {
            query.put("convocantes", prefs.getString(ConfiguracionPresenterImpl.PROPERTY_CODIGO_UOC, ""));
        }
        else if(!(prefs.getString(ConfiguracionPresenterImpl.PROPERTY_RUC_PROVEEDOR,"").isEmpty())) {
            //TODO Lógica para proveedores
        }
        Log.i("ListaLicitacionPres", "Codigo UOC: " + prefs.getString(ConfiguracionPresenterImpl.PROPERTY_CODIGO_UOC, ""));
        int offset = (currentPage-1)*cant;
        if(offset<cantResultados) { //si el offset es igual o mayor a la cantidad de resultados el server retornará un error 500
            query.put("offset", String.valueOf(offset));
            query.put("limit", Parametro.LIMIT_PARAM_FETCH_LICITACIONES);
            query.put("etapa", Parametro.ETAPA_CONVOCATADA);

            service.getLicitaciones("Bearer " + accessToken, query, new Callback<JSONResponseContrataciones>() {
                @Override
                public void success(JSONResponseContrataciones jsonResponseContrataciones, Response response) {
                    Log.i("ListaLicitacionPres", "Licitaciones obtenidas correctamente");
                    Log.i("ListaLicitacionPres", String.valueOf(response.getStatus()));
                    if (currentPage == 1) {
                        Pagination p = jsonResponseContrataciones.getGraph().get(0).getPagination();
                        if(p!=null)
                            cantResultados = p.getTotalItems();
                        else
                            cantResultados = 0;
                        Log.i("ListaLicPresenter", jsonResponseContrataciones.getGraph().get(0).getPagination()==null ? "null" : "not null");
                    }

                    java.util.List<List> resultados = jsonResponseContrataciones.getGraph().get(0).getList();
                    if (titulos == null) titulos = new ArrayList<>();
                    if (cuerpo == null) cuerpo = new ArrayList<>();
                    if (ids == null) ids = new ArrayList<>();
                    if (iconos == null) iconos = new ArrayList<>();

                    if (currentPage != 1) {
                        titulos.remove(titulos.size() - 1);
                        mView.showDeletingItem(titulos.size());
                    }
                    int i = 0;
                    for (List resultado : resultados) {

                        titulos.add(resultado.getNombreLicitacion());
                        String cuerpoStr = "Convocante: " + resultado.getConvocante() + "\n\n" + "Etapa: " +
                                resultado.getEtapa().getNombre() + "\n\n" + "Tipo de Procedimiento: " + resultado.getTipoProcedimiento().getNombre();
                        cuerpo.add(cuerpoStr);
                        ids.add(resultado.getPlanificacionId());
                        Categoria c = new Categoria(resultado.getCategoria().getId(), resultado.getCategoria().getCodigo(), resultado.getCategoria().getNombre());
                        iconos.add(c.getIcono());
                        i++;
                    }

                    mView.llenarListaLicitacion(titulos, cuerpo, iconos, ids);
                    if (currentPage == 1) {
                        mView.showContent();
                        mView.hideError();
                        mView.hideProgressBar();
                    }
                    Log.i("ListaLicitacionPres", "Licitaciones mostradas correctamente");
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.e("ListaLicitacionPresent", "Error: " + error.getMessage());
                    if (error.getKind().equals(RetrofitError.Kind.NETWORK)) {
                        mView.showError();
                        mView.hideProgressBar();
                        mView.hideContent();
                    }
                    if (error.getKind().equals(RetrofitError.Kind.HTTP)) {
                        if(error.getResponse().getStatus()==500) {
                            mView.showError500();
                            mView.hideProgressBar();
                            mView.hideContent();
                        }
                        else {
                            mView.showError404();
                            mView.hideProgressBar();
                            mView.hideContent();
                        }
                    }
                }
            });
        }
        else {
            if (currentPage != 1) {
                titulos.remove(titulos.size() - 1);
                mView.showDeletingItem(titulos.size());
            }
        }
    }

    @Override
    public void iniciarInterfaz() {
        java.util.List<String> titulos = Arrays.asList(TITLES);
        java.util.List<Integer> icons = Arrays.asList(ICONS);
        mView.iniciarDrawer(titulos, icons);
    }

}
