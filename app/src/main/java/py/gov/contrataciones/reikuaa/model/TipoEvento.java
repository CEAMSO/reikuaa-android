package py.gov.contrataciones.reikuaa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by alejandro on 07/04/15.
 */
public class TipoEvento {
    @SerializedName("id")
    @Expose
    public Integer id;

    @SerializedName("descripcion")
    @Expose
    public String descripcion;

    @SerializedName("template")
    @Expose
    public String template;

    public String getDescripcion() {
        return descripcion;
    }

    public Integer getId() {
        return id;
    }

    public String getTemplate() {
        return template;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

}
