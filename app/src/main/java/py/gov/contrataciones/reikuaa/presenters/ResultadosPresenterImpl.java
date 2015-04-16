package py.gov.contrataciones.reikuaa.presenters;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import py.gov.contrataciones.reikuaa.model.Categoria;
import py.gov.contrataciones.reikuaa.model.JSONResponseContrataciones;
import py.gov.contrataciones.reikuaa.model.List;
import py.gov.contrataciones.reikuaa.model.Pagination;
import py.gov.contrataciones.reikuaa.network.CallbackProximo;
import py.gov.contrataciones.reikuaa.network.NetworkAccess;
import py.gov.contrataciones.reikuaa.parametros.Parametro;
import py.gov.contrataciones.reikuaa.services.ContratacionesAPIService;
import py.gov.contrataciones.reikuaa.view.ResultadosActivity;
import py.gov.contrataciones.reikuaa.view.ResultadosView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by willtallpear on 23/03/15.
 */
public class ResultadosPresenterImpl implements ResultadosPresenter {

    private ResultadosView mView;
    private NetworkAccess na;
    private ContratacionesAPIService service;
    private Bundle parametros;
    private java.util.List<String> titulos;
    private java.util.List<String> cuerpo;
    private java.util.List<String> ids;
    private java.util.List<Integer> iconos;
    private int cantResultados = 1; //cantidad de items devueltos, default: 1

    public ResultadosPresenterImpl(ResultadosView mView) {
        this.mView = mView;
    }
    @Override
    public void obtenerResultados(Bundle parametros, final int currentPage) {
        if(currentPage==1) { //sólo en la primera carga ocultar la lista
            mView.hideContent();
            mView.hideError();
            mView.hideError404();
            mView.showProgressBar();
        }
        else { //mostramos item de carga
            titulos.add(null);
            mView.showAddingItem(titulos.size());
        }

        this.parametros = parametros;

        na = NetworkAccess.getInstance(mView.getContext());
        service = na.getService();

        na.getAccessToken(new CallbackProximo() {
            @Override
            public void proximaEjecucion(String accessToken) {
                llamadaResultados(accessToken, currentPage);
            }

            @Override
            public void onError() {

            }
        });
    }

    /**
     * Hace una llamada a la API Rest de Contrataciones para obtener los resultados de búsqueda.
     * @param accessToken accessToken para la autenticación
     * @param currentPage página de resultados que se desea mostrar. La primera llamada se hará con 1, la segunda con 2, etc.
     */
    private void llamadaResultados(String accessToken, final int currentPage) {
        String idnombrellamado, fechaDesde, fechaHasta, etapaCod, modCod, prodCod, contCod;
        int catId, convCod;

        idnombrellamado = fechaDesde = fechaHasta = etapaCod = modCod = prodCod = contCod = null;
        catId = convCod = 0;

        int cant = Integer.valueOf(Parametro.LIMIT_PARAM_FETCH_LICITACIONES);
        Map<String, String> query = new HashMap<>();
        int offset = (currentPage-1)*cant;
        if(offset<cantResultados) { //si el offset es igual o mayor a la cantidad de resultados el server retornará un error 500
            query.put("offset", String.valueOf(offset));
            query.put("limit", Parametro.LIMIT_PARAM_FETCH_LICITACIONES);
            query.put("tipo_fecha", "ENT");

            if (parametros != null) {
                idnombrellamado = parametros.getString(ResultadosActivity.RESULTADOS_ID_NOMBRE);
                catId = parametros.getInt(ResultadosActivity.RESULTADOS_CATEGORIA_ID);
                fechaDesde = parametros.getString(ResultadosActivity.RESULTADOS_FECHADESDE);
                fechaHasta = parametros.getString(ResultadosActivity.RESULTADOS_FECHAHASTA);
                etapaCod = parametros.getString(ResultadosActivity.RESULTADOS_ETAPA);
                modCod = parametros.getString(ResultadosActivity.RESULTADOS_MODALIDAD_ID);
                prodCod = parametros.getString(ResultadosActivity.RESULTADOS_PROD);
                convCod = parametros.getInt(ResultadosActivity.RESULTADOS_CONVOCANTE);
                contCod = parametros.getString(ResultadosActivity.RESULTADOS_CODIGOCONTRATACION);
            }
            if (idnombrellamado != null) {
                query.put("nro_nombre_llamado", idnombrellamado);
                Log.i("ResultadosPresenter", "Agregado a query: nro_nombre_lamado= " + idnombrellamado);
            }
            if (catId != 0) {
                query.put("categorias", String.valueOf(catId));
                Log.i("ResultadosPresenter", "Agregado a query: categorias= " + catId);
            }
            if (fechaDesde != null) {
                query.put("fecha_desde", fechaDesde);
                Log.i("ResultadosPresenter", "Agregado a query: fecha_desde= " + fechaDesde);
            }
            if (fechaHasta != null) {
                query.put("fecha_hasta", fechaHasta);
                Log.i("ResultadosPresenter", "Agregado a query: fecha_hasta= " + fechaHasta);
            }
            if (contCod != null) {
                query.put("codigo_contratacion", contCod);
                Log.i("ResultadosPresenter", "Agregado a query: codigo_contratacion= " + contCod);
            }
            if (prodCod != null) {
                query.put("codigo_catalogo_n4", String.valueOf(prodCod));
                Log.i("ResultadosPresenter", "Agregado a query: codigo_catalogo_n4= " + prodCod);
            }
            if (etapaCod != null) {
                query.put("etapa", etapaCod);
                Log.i("ResultadosPresenter", "Agregado a query: etapa= " + etapaCod);
            }
            if (modCod != null) {
                query.put("tipos_procedimiento", modCod);
                Log.i("ResultadosPresenter", "Agregado a query: tipos_procedimiento= " + modCod);
            }
            if (convCod != 0) {
                query.put("convocantes", String.valueOf(convCod));
                Log.i("ResultadosPresenter", "Agregado a query: convocantes= " + String.valueOf(convCod));
            }

            service.getLicitaciones("Bearer " + accessToken, query, new Callback<JSONResponseContrataciones>() {
                @Override
                public void success(JSONResponseContrataciones jsonResponseContrataciones, Response response) {
                    Pagination p = jsonResponseContrataciones.getGraph().get(0).getPagination();
                    if(p!=null)
                        cantResultados = p.getTotalItems();
                    else
                        cantResultados = 0;

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
                        mView.hideError404();
                        mView.hideProgressBar();
                    }

                }

                @Override
                public void failure(RetrofitError error) {
                    Log.e("ListaLicitacionPresent", "Error: " + error.getMessage());
                    if (error.getKind().equals(RetrofitError.Kind.NETWORK)) {
                        mView.showError();
                        mView.hideProgressBar();
                        mView.hideContent();
                    }
                    if(error.getKind().equals(RetrofitError.Kind.HTTP) && error.getResponse().getStatus()==404) {
                        mView.showError404();
                        mView.hideProgressBar();
                        mView.hideContent();
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


}
