package py.gov.contrataciones.reikuaa.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by willtallpear on 27/02/15.
 */
public class ItemNivel4 extends RealmObject {
    @PrimaryKey
    private String codigo;
    private String descripcion;

    public ItemNivel4() {

    }

    public ItemNivel4(String codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
