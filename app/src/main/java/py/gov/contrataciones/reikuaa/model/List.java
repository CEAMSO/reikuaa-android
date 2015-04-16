package py.gov.contrataciones.reikuaa.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class List {

    @SerializedName("tipo_procedimiento")
    @Expose
    private TipoProcedimiento tipoProcedimiento;
    @SerializedName("sistema_adjudicacion")
    @Expose
    private SistemaAdjudicacion sistemaAdjudicacion;
    @Expose
    private Categoria categoria;
    @Expose
    private Etapa etapa;
    @SerializedName("planificacion_id")
    @Expose
    private String planificacionId;
    @SerializedName("id_llamado")
    @Expose
    private Integer idLlamado;
    @SerializedName("nombre_licitacion")
    @Expose
    private String nombreLicitacion;
    @Expose
    private String convocante;
    @SerializedName("fecha_entrega_oferta")
    @Expose
    private String fechaEntregaOferta;
    @SerializedName("fecha_publicacion")
    @Expose
    private String fechaPublicacion;
    @Expose
    private String convocatorias;
    @Expose
    private String adjudicaciones;
    @Expose
    private Moneda moneda;
    @Expose
    private Estado estado;
    @Expose
    private String id;
    @SerializedName("codigo_contratacion")
    @Expose
    private String codigoContratacion;
    @SerializedName("monto_adjudicado")
    @Expose
    private Integer montoAdjudicado;
    @Expose
    private Proveedor proveedor;
    @SerializedName("monto_total_adjudicado")
    @Expose
    private Integer montoTotalAdjudicado;

    /**
     *
     * @return
     * The tipoProcedimiento
     */
    public TipoProcedimiento getTipoProcedimiento() {
        return tipoProcedimiento;
    }

    /**
     *
     * @param tipoProcedimiento
     * The tipo_procedimiento
     */
    public void setTipoProcedimiento(TipoProcedimiento tipoProcedimiento) {
        this.tipoProcedimiento = tipoProcedimiento;
    }

    /**
     *
     * @return
     * The sistemaAdjudicacion
     */
    public SistemaAdjudicacion getSistemaAdjudicacion() {
        return sistemaAdjudicacion;
    }

    /**
     *
     * @param sistemaAdjudicacion
     * The sistema_adjudicacion
     */
    public void setSistemaAdjudicacion(SistemaAdjudicacion sistemaAdjudicacion) {
        this.sistemaAdjudicacion = sistemaAdjudicacion;
    }
    /**
     *
     * @return
     * The categoria
     */
    public Categoria getCategoria() {
        return categoria;
    }

    /**
     *
     * @param categoria
     * The categoria
     */
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    /**
     *
     * @return
     * The etapa
     */
    public Etapa getEtapa() {
        return etapa;
    }

    /**
     *
     * @param etapa
     * The etapa
     */
    public void setEtapa(Etapa etapa) {
        this.etapa = etapa;
    }

    /**
     *
     * @return
     * The planificacionId
     */
    public String getPlanificacionId() {
        return planificacionId;
    }

    /**
     *
     * @param planificacionId
     * The planificacion_id
     */
    public void setPlanificacionId(String planificacionId) {
        this.planificacionId = planificacionId;
    }

    /**
     *
     * @return
     * The idLlamado
     */
    public Integer getIdLlamado() {
        return idLlamado;
    }

    /**
     *
     * @param idLlamado
     * The id_llamado
     */
    public void setIdLlamado(Integer idLlamado) {
        this.idLlamado = idLlamado;
    }

    /**
     *
     * @return
     * The nombreLicitacion
     */
    public String getNombreLicitacion() {
        return nombreLicitacion;
    }

    /**
     *
     * @param nombreLicitacion
     * The nombre_licitacion
     */
    public void setNombreLicitacion(String nombreLicitacion) {
        this.nombreLicitacion = nombreLicitacion;
    }

    /**
     *
     * @return
     * The convocante
     */
    public String getConvocante() {
        return convocante;
    }

    /**
     *
     * @param convocante
     * The convocante
     */
    public void setConvocante(String convocante) {
        this.convocante = convocante;
    }

    /**
     *
     * @return
     * The fechaEntregaOferta
     */
    public String getFechaEntregaOferta() {
        return fechaEntregaOferta;
    }

    /**
     *
     * @param fechaEntregaOferta
     * The fecha_entrega_oferta
     */
    public void setFechaEntregaOferta(String fechaEntregaOferta) {
        this.fechaEntregaOferta = fechaEntregaOferta;
    }

    /**
     *
     * @return
     * The convocatorias
     */
    public String getConvocatorias() {
        return convocatorias;
    }

    /**
     *
     * @param convocatorias
     * The convocatorias
     */
    public void setConvocatorias(String convocatorias) {
        this.convocatorias = convocatorias;
    }

    /**
     *
     * @return
     * The adjudicaciones
     */
    public String getAdjudicaciones() {
        return adjudicaciones;
    }

    /**
     *
     * @param adjudicaciones
     * The adjudicaciones
     */
    public void setAdjudicaciones(String adjudicaciones) {
        this.adjudicaciones = adjudicaciones;
    }

    /**
     *
     * @return
     * The moneda
     */
    public Moneda getMoneda() {
        return moneda;
    }

    /**
     *
     * @param moneda
     * The moneda
     */
    public void setMoneda(Moneda moneda) {
        this.moneda = moneda;
    }

    /**
     *
     * @return
     * The estado
     */
    public Estado getEstado() {
        return estado;
    }

    /**
     *
     * @param estado
     * The estado
     */
    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The codigoContratacion
     */
    public String getCodigoContratacion() {
        return codigoContratacion;
    }

    /**
     *
     * @param codigoContratacion
     * The codigo_contratacion
     */
    public void setCodigoContratacion(String codigoContratacion) {
        this.codigoContratacion = codigoContratacion;
    }

    /**
     *
     * @return
     * The montoAdjudicado
     */
    public Integer getMontoAdjudicado() {
        return montoAdjudicado;
    }

    /**
     *
     * @param montoAdjudicado
     * The monto_adjudicado
     */
    public void setMontoAdjudicado(Integer montoAdjudicado) {
        this.montoAdjudicado = montoAdjudicado;
    }

    /**
     *
     * @return
     * The proveedor
     */
    public Proveedor getProveedor() {
        return proveedor;
    }

    /**
     *
     * @param proveedor
     * The proveedor
     */
    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    /**
     *
     * @return
     * The fechaPublicacion
     */
    public String getFechaPublicacion() {
        return fechaPublicacion;
    }

    /**
     *
     * @param fechaPublicacion
     * The fecha_publicacion
     */
    public void setFechaPublicacion(String fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    /**
     *
     * @return
     * The montoTotalAdjudicado
     */
    public Integer getMontoTotalAdjudicado() {
        return montoTotalAdjudicado;
    }

    /**
     *
     * @param montoTotalAdjudicado
     * The monto_total_adjudicado
     */
    public void setMontoTotalAdjudicado(Integer montoTotalAdjudicado) {
        this.montoTotalAdjudicado = montoTotalAdjudicado;
    }
}