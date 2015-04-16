package py.gov.contrataciones.reikuaa.model;

import com.google.gson.annotations.Expose;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Etapa extends RealmObject {

    @Expose
    private String nombre;
    @Expose
    @PrimaryKey
    private String codigo;

    public Etapa() { }

    public Etapa(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
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

}