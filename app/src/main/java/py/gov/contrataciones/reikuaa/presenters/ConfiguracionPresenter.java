package py.gov.contrataciones.reikuaa.presenters;

import android.content.Context;

import java.util.List;
import java.util.Map;

import py.gov.contrataciones.reikuaa.model.TipoEvento;

/**
 * Created by alejandro on 07/04/15.
 */
public interface ConfiguracionPresenter {

    /**
     * Setea en las preferencias el RUC si es un proveedor o un código si es Convocante.
     * @param tipo Si es proveedor o convocante
     * @param codigo RUC o codigo del proveedor o convocante
     */
//    public void setearConvocanteoProveedor(Context context, String tipo, String codigo);
    /**
     * Setea en las preferencias el codigo si existe la convocante.
     * @param codigo de la convocante
     */
    public boolean setearConvocante(Context context, String codigo);
    /**
     * Setea en las preferencias el ruc del proveedor.
     * @param ruc del proveedor
     */
    public boolean setearProveedor(Context context, String ruc);
    public boolean setearCiudadano(Context context);
    public void setearConfiguracion(Context context, Map<String, Boolean> configuracion);
    public int suscribir(Context context, Map<String, Boolean> configuracion );
}
