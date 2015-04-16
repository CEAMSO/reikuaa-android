package py.gov.contrataciones.reikuaa.presenters;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import py.gov.contrataciones.reikuaa.daos.EntidadDAO;
import py.gov.contrataciones.reikuaa.model.TipoEvento;
import py.gov.contrataciones.reikuaa.network.NetworkAccess;
import py.gov.contrataciones.reikuaa.parametros.Parametro;
import py.gov.contrataciones.reikuaa.services.ContratacionesAPIService;
import py.gov.contrataciones.reikuaa.services.ProxyAPIService;
import py.gov.contrataciones.reikuaa.util.AuthorizationAccess;
import py.gov.contrataciones.reikuaa.view.ConfiguracionActivity;
import py.gov.contrataciones.reikuaa.view.ConfiguracionView;
import retrofit.client.Response;

/**
 * Created by alejandro on 07/04/15.
 */
public class ConfiguracionPresenterImpl implements ConfiguracionPresenter {

    public static final String TIPO_CONVOCANTE = "convocante";
    public static final String TIPO_PROVEEDOR = "proveedor";
    public static final String TIPO_CIUDADANO = "ciudadano";
    public static final String PROPERTY_CODIGO_UOC = "codigo_convocante";
    public static final String PROPERTY_RUC_PROVEEDOR = "ruc_proveedor";

    public static final String PROPERTY_CONF_CAMBIO_ESTADO = "conf_cambio";
    public static final String PROPERTY_CONF_ADENDAS = "conf_adendas";
    public static final String PROPERTY_CONF_SUBASTAS = "conf_subastas";
    public static final String PROPERTY_CONF_PRORROGAS = "conf_prorrogas";
    public static final String PROPERTY_CONF_ACLARACIONES = "conf_aclaraciones";
    public static final String PROPERTY_CONF_AVISOS = "conf_avisos";

    private ContratacionesAPIService service;
    private ProxyAPIService proxyAPIService;
    private ConfiguracionView mView;
    private NetworkAccess na;

    public ConfiguracionPresenterImpl(ConfiguracionView l) {
        mView = l;
    }

//    @Override
//    public void setearConvocanteoProveedor(Context context, String tipo, String codigo) {
//        final SharedPreferences prefs = getGCMPreferences(context);
//        SharedPreferences.Editor editor = prefs.edit();
//        editor.putString(PROPERTY_CODIGO_UOC, "");
//        editor.putString(PROPERTY_RUC_PROVEEDOR, "");
//        if (tipo.equals(TIPO_CONVOCANTE)) {
//            if (EntidadDAO.existeConvocante(context, codigo))
//            {
//                editor.putString(PROPERTY_CODIGO_UOC, codigo);
//                Log.i("ConfPresenterImpl", "UOC Guardada con codigo: " + codigo);
//            }
//            else
//            {
//                Log.i("ConfPresenterImpl", "UOC no existe. Codigo: " + codigo);
//            }
//        }
//        else if (tipo.equals(TIPO_PROVEEDOR)) {
//            editor.putString(PROPERTY_RUC_PROVEEDOR, codigo);
//            Log.i("ConfPresenterImpl", "Proveedor guardado con RUC: " + codigo);
//        }
//        editor.commit();
//    }

    @Override
    public boolean setearConvocante(Context context, String codigo) {
        final SharedPreferences prefs = getGCMPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_CODIGO_UOC, "");
        editor.putString(PROPERTY_RUC_PROVEEDOR, "");
        if (EntidadDAO.existeConvocante(context, codigo))
        {
            editor.putString(PROPERTY_CODIGO_UOC, codigo);
            Log.i("ConfPresenterImpl", "UOC Guardada con codigo: " + codigo);
            editor.commit();
            return true;
        }
        else
        {
            Log.i("ConfPresenterImpl", "UOC no existe. Codigo: " + codigo);
            editor.commit();
            return false;
        }
    }

    @Override
    public boolean setearProveedor(Context context, String ruc) {
        final SharedPreferences prefs = getGCMPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_CODIGO_UOC, "");
        editor.putString(PROPERTY_RUC_PROVEEDOR, "");
        editor.putString(PROPERTY_RUC_PROVEEDOR, ruc);
        Log.i("ConfPresenterImpl", "Proveedor guardado con RUC: " + ruc);
        editor.commit();
        return true;
    }

    @Override
    public boolean setearCiudadano(Context context) {
        final SharedPreferences prefs = getGCMPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_CODIGO_UOC, "");
        editor.putString(PROPERTY_RUC_PROVEEDOR, "");
        Log.i("ConfPresenterImpl", "Ciudadano guardado");
        editor.commit();
        return true;
    }

    @Override
    public void setearConfiguracion(Context context, Map<String, Boolean> configuracion) {
        final SharedPreferences prefs = getGCMPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();

        if (configuracion.get(PROPERTY_CONF_CAMBIO_ESTADO))
            editor.putBoolean(PROPERTY_CONF_CAMBIO_ESTADO, true);
        else
            editor.putBoolean(PROPERTY_CONF_CAMBIO_ESTADO, false);

        if (configuracion.get(PROPERTY_CONF_ADENDAS))
            editor.putBoolean(PROPERTY_CONF_ADENDAS, true);
        else
            editor.putBoolean(PROPERTY_CONF_ADENDAS, false);

        if (configuracion.get(PROPERTY_CONF_SUBASTAS))
            editor.putBoolean(PROPERTY_CONF_SUBASTAS, true);
        else
            editor.putBoolean(PROPERTY_CONF_SUBASTAS, false);

        if (configuracion.get(PROPERTY_CONF_PRORROGAS))
            editor.putBoolean(PROPERTY_CONF_PRORROGAS, true);
        else
            editor.putBoolean(PROPERTY_CONF_PRORROGAS, false);

        if (configuracion.get(PROPERTY_CONF_ACLARACIONES))
            editor.putBoolean(PROPERTY_CONF_ACLARACIONES, true);
        else
            editor.putBoolean(PROPERTY_CONF_ACLARACIONES, false);

        if (configuracion.get(PROPERTY_CONF_AVISOS))
            editor.putBoolean(PROPERTY_CONF_AVISOS, true);
        else
            editor.putBoolean(PROPERTY_CONF_AVISOS, false);

        editor.commit();
    }

    /**
     * Realiza la llamada al proxy para enviar una lista de Tipos de Eventos a suscribir
     * @param configuracion
     */

    @Override
    public int suscribir(Context context, Map<String, Boolean> configuracion) {
        //Log.i(TAG, "Suscripcion a " + planificacion_id);
        List<TipoEvento> tiposEventos = new ArrayList<TipoEvento>();

        TipoEvento tipoEvento;
        if (configuracion.get(PROPERTY_CONF_CAMBIO_ESTADO)) {
            tipoEvento = new TipoEvento();
            tipoEvento.setId(Parametro.CONF_CAMBIO_ESTADO_ID);
            tiposEventos.add(tipoEvento);
        }

        if (configuracion.get(PROPERTY_CONF_ADENDAS)) {
            tipoEvento = new TipoEvento();
            tipoEvento.setId(Parametro.CONF_ADENDAS_ID);
            tiposEventos.add(tipoEvento);
        }

        if (configuracion.get(PROPERTY_CONF_SUBASTAS)) {
            tipoEvento = new TipoEvento();
            tipoEvento.setId(Parametro.CONF_SUBASTAS_ID);
            tiposEventos.add(tipoEvento);
        }

        if (configuracion.get(PROPERTY_CONF_PRORROGAS)) {
            tipoEvento = new TipoEvento();
            tipoEvento.setId(Parametro.CONF_PRORROGAS_ID);
            tiposEventos.add(tipoEvento);
        }

        if (configuracion.get(PROPERTY_CONF_ACLARACIONES)) {
            tipoEvento = new TipoEvento();
            tipoEvento.setId(Parametro.CONF_ACLARACIONES_ID);
            tiposEventos.add(tipoEvento);
        }

        if (configuracion.get(PROPERTY_CONF_AVISOS)) {
            tipoEvento = new TipoEvento();
            tipoEvento.setId(Parametro.CONF_AVISOS_ID);
            tiposEventos.add(tipoEvento);
        }

        String authorization = AuthorizationAccess.getMail() + ":" + AuthorizationAccess.getRegistrationId(context);
        authorization = "Basic " + Base64.encodeToString(authorization.getBytes(), Base64.NO_WRAP);
        na = NetworkAccess.getInstance(mView.getContext());
        proxyAPIService = na.getProxyService();
        Response r = proxyAPIService.suscribirEventos(authorization, tiposEventos);
        return r.getStatus();
    }

    private SharedPreferences getGCMPreferences(Context context) {
        return context.getSharedPreferences(ConfiguracionActivity.class.getSimpleName(),
                Context.MODE_PRIVATE);
    }
}
