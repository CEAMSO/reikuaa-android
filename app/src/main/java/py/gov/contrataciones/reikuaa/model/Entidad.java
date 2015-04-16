package py.gov.contrataciones.reikuaa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by willtallpear on 02/03/15.
 */
public class Entidad extends RealmObject {
    @Expose
    @PrimaryKey
    private int codigo;
    @Expose
    private String nombre;
    @Expose
    @SerializedName("nivel_entidad")
    private String nivelEntidad;
    @Expose
    @SerializedName("tipo_entidad")
    private String tipoEntidad;

    public Entidad() { super(); }

    public Entidad(int codigo, String nombre, String nivelEntidad, String tipoEntidad) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.nivelEntidad = nivelEntidad;
        this.tipoEntidad = tipoEntidad;
    }


    public int getCodigo() { return codigo; }
    public void setCodigo(int codigo) { this.codigo = codigo;}

    public void setNombre(String nombre) { this.nombre = nombre;}
    public String getNombre() { return nombre; }

    public String getTipoEntidad() {
        return tipoEntidad;
    }

    public void setTipoEntidad(String tipoEntidad) {
        this.tipoEntidad = tipoEntidad;
    }

    public String getNivelEntidad() {
        return nivelEntidad;
    }

    public void setNivelEntidad(String nivelEntidad) {
        this.nivelEntidad = nivelEntidad;
    }
}
