package py.gov.contrataciones.reikuaa.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by willtallpear on 09/04/15.
 */
public class Suscripcion extends RealmObject {
    @PrimaryKey
    private String planificacionId;
    private String nombre;
    private String  convocante;
    private String tipoProcedimiento;
    private String categoriaNombre;
    private int categoriaId;
    private int categoriaCodigo;


    public String getPlanificacionId() {
        return planificacionId;
    }

    public void setPlanificacionId(String planificacionId) {
        this.planificacionId = planificacionId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getConvocante() {
        return convocante;
    }

    public void setConvocante(String convocante) {
        this.convocante = convocante;
    }

    public String getTipoProcedimiento() {
        return tipoProcedimiento;
    }

    public void setTipoProcedimiento(String tipoProcedimiento) {
        this.tipoProcedimiento = tipoProcedimiento;
    }

    public String getCategoriaNombre() {
        return categoriaNombre;
    }

    public void setCategoriaNombre(String categoriaNombre) {
        this.categoriaNombre = categoriaNombre;
    }

    public int getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(int categoriaId) {
        this.categoriaId = categoriaId;
    }

    public int getCategoriaCodigo() {
        return categoriaCodigo;
    }

    public void setCategoriaCodigo(int categoriaCodigo) {
        this.categoriaCodigo = categoriaCodigo;
    }
}
