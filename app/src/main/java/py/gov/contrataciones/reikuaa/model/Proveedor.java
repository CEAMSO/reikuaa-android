package py.gov.contrataciones.reikuaa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Proveedor {

    @SerializedName("razon_social")
    @Expose
    private String razonSocial;
    @Expose
    private String ruc;

    /**
     *
     * @return
     * The razonSocial
     */
    public String getRazonSocial() {
        return razonSocial;
    }

    /**
     *
     * @param razonSocial
     * The razon_social
     */
    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    /**
     *
     * @return
     * The ruc
     */
    public String getRuc() {
        return ruc;
    }

    /**
     *
     * @param ruc
     * The ruc
     */
    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

}