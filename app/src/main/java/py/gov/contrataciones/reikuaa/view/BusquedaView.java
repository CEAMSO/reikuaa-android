package py.gov.contrataciones.reikuaa.view;

import io.realm.Realm;
import io.realm.RealmResults;
import py.gov.contrataciones.reikuaa.model.Categoria;
import py.gov.contrataciones.reikuaa.model.Entidad;
import py.gov.contrataciones.reikuaa.model.Estado;
import py.gov.contrataciones.reikuaa.model.Etapa;
import py.gov.contrataciones.reikuaa.model.ItemNivel4;
import py.gov.contrataciones.reikuaa.model.TipoProcedimiento;

/**
 * Created by willtallpear on 26/02/15.
 */
public interface BusquedaView {
    /**
     * Llena el spinner de categorías con los valores pasados.
     * @param resultados colección de valores a mostrar.
     */
    public void llenarCategoriaSpinner(RealmResults<Categoria> resultados);

    /**
     * Obtiene una instancia de la base de datos
     * @return singleton Realm de acceso a datos.
     */
    public Realm getBD();

    /**
     * muestra un calendario en pantalla para la selección de fecha.
     * @param tipo FECHA_DESDE o FECHA_HASTA
     */
    public void mostrarCalendario(int tipo);

    /**
     * Llena el spinner de tipos de procedimiento con los valores pasados.
     *
     * @param results colección de valores a mostrar.
     */
    public void llenarModalidadesSpinner(RealmResults<TipoProcedimiento> results);

    /**
     * Llena el campo de texto de convocantes con los valores pasados en forma de sugerencias
     * de autocompletado.
     *
     * @param results colección de valores a mostrar.
     */
    public void llenarConvocantesText(RealmResults<Entidad> results);

    /**
     * Llena el spinner de etapas con los valores pasados.
     *
     * @param results colección de valores a mostrar.
     */
    public void llenarEtapasSpinner(RealmResults<Etapa> results);

    /**
     * Llena el campo de texto de productos con los valores pasados en forma de sugerencias
     * de autocompletado.
     *
     * @param results colección de valores a mostrar.
     */
    public void llenarProductosText(RealmResults<ItemNivel4> results);

    /**
     * agrega los calendarios para la selección de fechas
     */
    public void addCalendario();

    /**
     * Método que se encarga de setear en una variable local un valor específico.
     * Se usa en los listeners de los spinners para registrar la opción seleccionada por el
     * usuario.
     *
     *
     * @param campo CAMPO_ID_CATEGORIA, CAMPO_ETAPA, CAMPO_MODALIDAD
     * @param valor valor a almacenar
     */
    public void setCampo(int campo, Object valor);
}
