package py.gov.contrataciones.reikuaa.model;

import com.google.gson.annotations.Expose;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class TipoProcedimiento extends RealmObject {
    @PrimaryKey
    @Expose
    private int id;
    @Expose
    private String codigo;
    @Expose
    private String nombre;

    private String rangoPrecio;

    public TipoProcedimiento() { super(); };

    public TipoProcedimiento(int id, String codigo, String nombre) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public TipoProcedimiento(int id, String codigo, String nombre, String rangoPrecio) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.rangoPrecio = rangoPrecio;
    }
    /**
     *
     * @return
     * The id
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(int id) {
        this.id = id;
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

    public String getRangoPrecio() {
        return rangoPrecio;
    }

    public void setRangoPrecio(String rangoPrecio) {
        this.rangoPrecio = rangoPrecio;
    }
}