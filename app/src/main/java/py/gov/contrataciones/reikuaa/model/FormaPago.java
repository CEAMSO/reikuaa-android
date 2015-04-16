package py.gov.contrataciones.reikuaa.model;

import com.google.gson.annotations.Expose;

public class FormaPago {

    @Expose
    private String codigo;
    @Expose
    private String nombre;

    /**
     *
     * @return
     * The codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     *
     * @param codigo
     * The codigo
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     *
     * @return
     * The nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     *
     * @param nombre
     * The nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}