package py.gov.contrataciones.reikuaa.model;
import com.google.gson.annotations.Expose;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import py.gov.contrataciones.reikuaa.R;

public class Categoria extends RealmObject {
    @PrimaryKey
    @Expose
    private int id;
    @Expose
    private int codigo;
    @Expose
    private String nombre;
    private int icono;

    @Ignore
    private static int[] iconos_codigo = {R.drawable.ic_cat1, R.drawable.ic_cat2, R.drawable.ic_cat3,
            R.drawable.ic_cat4, R.drawable.ic_cat5, R.drawable.ic_cat6, R.drawable.ic_cat7,
            R.drawable.ic_cat8, R.drawable.ic_cat9, R.drawable.ic_cat10, R.drawable.ic_cat11,
            R.drawable.ic_cat12, R.drawable.ic_cat13, R.drawable.ic_food, R.drawable.ic_cat15,
            R.drawable.ic_cat16, R.drawable.ic_cat17, R.drawable.ic_cat18, R.drawable.ic_cat19,
            R.drawable.ic_cat20, R.drawable.ic_cat21, R.drawable.ic_cat22, R.drawable.ic_cat23,
            R.drawable.ic_cat24, R.drawable.ic_cat25, R.drawable.ic_todas_cat
    };
    /**
     *
     * @return
     * The id
     */
    public Categoria() { super(); };

    public Categoria(int id, int codigo, String nombre) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.icono = Categoria.iconos_codigo[codigo-1];
    }

    public Categoria(int id, int codigo, String nombre, int icono) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.icono = icono;
    }

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
    public int getCodigo() {
        return codigo;
    }

    /**
     *
     * @param codigo
     * The codigo
     */
    public void setCodigo(int codigo) {
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

    public int getIcono() {
        return icono;
    }

    public void setIcono(int icono) {
        this.icono = icono;
    }
}