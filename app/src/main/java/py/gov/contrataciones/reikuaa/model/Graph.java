package py.gov.contrataciones.reikuaa.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Graph {
    @Expose
    private java.util.List<py.gov.contrataciones.reikuaa.model.List> list = new ArrayList<py.gov.contrataciones.reikuaa.model.List>();
    @Expose
    private Pagination pagination;
    @Expose
    private Categoria categoria;
    @SerializedName("tipo_procedimiento")
    @Expose
    private TipoProcedimiento tipoProcedimiento;
    @Expose
    private Estado estado;
    @SerializedName("tipo_garantia_oferta")
    @Expose
    private TipoGarantiaOferta tipoGarantiaOferta;
    @SerializedName("sistema_adjudicacion")
    @Expose
    private SistemaAdjudicacion sistemaAdjudicacion;
    @SerializedName("forma_pago")
    @Expose
    private FormaPago formaPago;
    @Expose
    private Moneda moneda;
    @Expose
    private Convocatoria convocatoria;
    @Expose
    private String id;
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
    @SerializedName("metodo_seleccion")
    @Expose
    private String metodoSeleccion;
    @SerializedName("fecha_publicacion")
    @Expose
    private String fechaPublicacion;
    @SerializedName("fuente_financiamiento")
    @Expose
    private String fuenteFinanciamiento;
    @SerializedName("plazo_entrega_adquisicion")
    @Expose
    private String plazoEntregaAdquisicion;
    @SerializedName("lugar_entrega_adquisicion")
    @Expose
    private String lugarEntregaAdquisicion;
    @SerializedName("vigencia_contrato")
    @Expose
    private String vigenciaContrato;
    @SerializedName("nombre_contacto")
    @Expose
    private String nombreContacto;
    @SerializedName("cargo_contacto")
    @Expose
    private String cargoContacto;
    @SerializedName("telefono_contacto")
    @Expose
    private String telefonoContacto;
    @SerializedName("email_contacto")
    @Expose
    private String emailContacto;
    @SerializedName("fecha_junta_aclaracion")
    @Expose
    private String fechaJuntaAclaracion;
    @SerializedName("lugar_junta_aclaracion")
    @Expose
    private String lugarJuntaAclaracion;
    @SerializedName("lugar_consulta")
    @Expose
    private String lugarConsulta;
    @SerializedName("fecha_tope_consulta")
    @Expose
    private String fechaTopeConsulta;
    @SerializedName("fecha_tope_respuesta")
    @Expose
    private String fechaTopeRespuesta;
    @SerializedName("lugar_entrega_oferta")
    @Expose
    private String lugarEntregaOferta;
    @SerializedName("fecha_entrega_oferta")
    @Expose
    private String fechaEntregaOferta;
    @SerializedName("lugar_apertura_oferta")
    @Expose
    private String lugarAperturaOferta;
    @SerializedName("fecha_apertura_oferta")
    @Expose
    private String fechaAperturaOferta;
    @SerializedName("fecha_inicio_propuesta")
    @Expose
    private String fechaInicioPropuesta;
    @SerializedName("fecha_cierre_propuesta")
    @Expose
    private String fechaCierrePropuesta;
    @SerializedName("fecha_etapa_competitiva")
    @Expose
    private String fechaEtapaCompetitiva;
    @Expose
    private String observaciones;
    @Expose
    private String restricciones;
    @Expose
    private List<String> etiquetas = new ArrayList<String>();
    @SerializedName("@id")
    @Expose
    private String Id;
    @SerializedName("@type")
    @Expose
    private String Type;
    @Expose
    private String formato;
    @Expose
    private String license;
    @Expose
    private String invitados;
    @Expose
    private String documentos;
    @Expose
    private String lotes;
    @Expose
    private String adjudicaciones;
    @Expose
    private Contrato contrato;
    @Expose
    private Adjudicacion adjudicacion;

    /**
     *
     * @return
     * The list
     */
    public java.util.List<py.gov.contrataciones.reikuaa.model.List> getList() {
        return list;
    }

    /**
     *
     * @param list
     * The list
     */
    public void setList(java.util.List<py.gov.contrataciones.reikuaa.model.List> list) {
        this.list = list;
    }

    /**
     *
     * @return
     * The pagination
     */
    public Pagination getPagination() {
        return pagination;
    }


    /**
     *
     * @return
     * The convocatoria
     */
    public Convocatoria getConvocatoria() {
        return convocatoria;
    }

    /**
     *
     * @param convocatoria
     * The convocatoria
     */
    public void setConvocatoria(Convocatoria convocatoria) {
        this.convocatoria = convocatoria;
    }


    /**
     *
     * @param pagination
     * The pagination
     */
    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
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
     * The tipoGarantiaOferta
     */
    public TipoGarantiaOferta getTipoGarantiaOferta() {
        return tipoGarantiaOferta;
    }

    /**
     *
     * @param tipoGarantiaOferta
     * The tipo_garantia_oferta
     */
    public void setTipoGarantiaOferta(TipoGarantiaOferta tipoGarantiaOferta) {
        this.tipoGarantiaOferta = tipoGarantiaOferta;
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
     * The formaPago
     */
    public FormaPago getFormaPago() {
        return formaPago;
    }

    /**
     *
     * @param formaPago
     * The forma_pago
     */
    public void setFormaPago(FormaPago formaPago) {
        this.formaPago = formaPago;
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
     * The metodoSeleccion
     */
    public String getMetodoSeleccion() {
        return metodoSeleccion;
    }

    /**
     *
     * @param metodoSeleccion
     * The metodo_seleccion
     */
    public void setMetodoSeleccion(String metodoSeleccion) {
        this.metodoSeleccion = metodoSeleccion;
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
     * The fuenteFinanciamiento
     */
    public String getFuenteFinanciamiento() {
        return fuenteFinanciamiento;
    }

    /**
     *
     * @param fuenteFinanciamiento
     * The fuente_financiamiento
     */
    public void setFuenteFinanciamiento(String fuenteFinanciamiento) {
        this.fuenteFinanciamiento = fuenteFinanciamiento;
    }

    /**
     *
     * @return
     * The plazoEntregaAdquisicion
     */
    public String getPlazoEntregaAdquisicion() {
        return plazoEntregaAdquisicion;
    }

    /**
     *
     * @param plazoEntregaAdquisicion
     * The plazo_entrega_adquisicion
     */
    public void setPlazoEntregaAdquisicion(String plazoEntregaAdquisicion) {
        this.plazoEntregaAdquisicion = plazoEntregaAdquisicion;
    }

    /**
     *
     * @return
     * The lugarEntregaAdquisicion
     */
    public String getLugarEntregaAdquisicion() {
        return lugarEntregaAdquisicion;
    }

    /**
     *
     * @param lugarEntregaAdquisicion
     * The lugar_entrega_adquisicion
     */
    public void setLugarEntregaAdquisicion(String lugarEntregaAdquisicion) {
        this.lugarEntregaAdquisicion = lugarEntregaAdquisicion;
    }

    /**
     *
     * @return
     * The vigenciaContrato
     */
    public String getVigenciaContrato() {
        return vigenciaContrato;
    }

    /**
     *
     * @param vigenciaContrato
     * The vigencia_contrato
     */
    public void setVigenciaContrato(String vigenciaContrato) {
        this.vigenciaContrato = vigenciaContrato;
    }

    /**
     *
     * @return
     * The nombreContacto
     */
    public String getNombreContacto() {
        return nombreContacto;
    }

    /**
     *
     * @param nombreContacto
     * The nombre_contacto
     */
    public void setNombreContacto(String nombreContacto) {
        this.nombreContacto = nombreContacto;
    }

    /**
     *
     * @return
     * The cargoContacto
     */
    public String getCargoContacto() {
        return cargoContacto;
    }

    /**
     *
     * @param cargoContacto
     * The cargo_contacto
     */
    public void setCargoContacto(String cargoContacto) {
        this.cargoContacto = cargoContacto;
    }

    /**
     *
     * @return
     * The telefonoContacto
     */
    public String getTelefonoContacto() {
        return telefonoContacto;
    }

    /**
     *
     * @param telefonoContacto
     * The telefono_contacto
     */
    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }

    /**
     *
     * @return
     * The emailContacto
     */
    public String getEmailContacto() {
        return emailContacto;
    }

    /**
     *
     * @param emailContacto
     * The email_contacto
     */
    public void setEmailContacto(String emailContacto) {
        this.emailContacto = emailContacto;
    }

    /**
     *
     * @return
     * The fechaJuntaAclaracion
     */
    public String getFechaJuntaAclaracion() {
        return fechaJuntaAclaracion;
    }

    /**
     *
     * @param fechaJuntaAclaracion
     * The fecha_junta_aclaracion
     */
    public void setFechaJuntaAclaracion(String fechaJuntaAclaracion) {
        this.fechaJuntaAclaracion = fechaJuntaAclaracion;
    }

    /**
     *
     * @return
     * The lugarJuntaAclaracion
     */
    public String getLugarJuntaAclaracion() {
        return lugarJuntaAclaracion;
    }

    /**
     *
     * @param lugarJuntaAclaracion
     * The lugar_junta_aclaracion
     */
    public void setLugarJuntaAclaracion(String lugarJuntaAclaracion) {
        this.lugarJuntaAclaracion = lugarJuntaAclaracion;
    }

    /**
     *
     * @return
     * The lugarConsulta
     */
    public String getLugarConsulta() {
        return lugarConsulta;
    }

    /**
     *
     * @param lugarConsulta
     * The lugar_consulta
     */
    public void setLugarConsulta(String lugarConsulta) {
        this.lugarConsulta = lugarConsulta;
    }

    /**
     *
     * @return
     * The fechaTopeConsulta
     */
    public String getFechaTopeConsulta() {
        return fechaTopeConsulta;
    }

    /**
     *
     * @param fechaTopeConsulta
     * The fecha_tope_consulta
     */
    public void setFechaTopeConsulta(String fechaTopeConsulta) {
        this.fechaTopeConsulta = fechaTopeConsulta;
    }

    /**
     *
     * @return
     * The fechaTopeRespuesta
     */
    public String getFechaTopeRespuesta() {
        return fechaTopeRespuesta;
    }

    /**
     *
     * @param fechaTopeRespuesta
     * The fecha_tope_respuesta
     */
    public void setFechaTopeRespuesta(String fechaTopeRespuesta) {
        this.fechaTopeRespuesta = fechaTopeRespuesta;
    }

    /**
     *
     * @return
     * The lugarEntregaOferta
     */
    public String getLugarEntregaOferta() {
        return lugarEntregaOferta;
    }

    /**
     *
     * @param lugarEntregaOferta
     * The lugar_entrega_oferta
     */
    public void setLugarEntregaOferta(String lugarEntregaOferta) {
        this.lugarEntregaOferta = lugarEntregaOferta;
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
     * The lugarAperturaOferta
     */
    public String getLugarAperturaOferta() {
        return lugarAperturaOferta;
    }

    /**
     *
     * @param lugarAperturaOferta
     * The lugar_apertura_oferta
     */
    public void setLugarAperturaOferta(String lugarAperturaOferta) {
        this.lugarAperturaOferta = lugarAperturaOferta;
    }

    /**
     *
     * @return
     * The fechaAperturaOferta
     */
    public String getFechaAperturaOferta() {
        return fechaAperturaOferta;
    }

    /**
     *
     * @param fechaAperturaOferta
     * The fecha_apertura_oferta
     */
    public void setFechaAperturaOferta(String fechaAperturaOferta) {
        this.fechaAperturaOferta = fechaAperturaOferta;
    }

    /**
     *
     * @return
     * The fechaInicioPropuesta
     */
    public String getFechaInicioPropuesta() {
        return fechaInicioPropuesta;
    }

    /**
     *
     * @param fechaInicioPropuesta
     * The fecha_inicio_propuesta
     */
    public void setFechaInicioPropuesta(String fechaInicioPropuesta) {
        this.fechaInicioPropuesta = fechaInicioPropuesta;
    }

    /**
     *
     * @return
     * The fechaCierrePropuesta
     */
    public String getFechaCierrePropuesta() {
        return fechaCierrePropuesta;
    }

    /**
     *
     * @param fechaCierrePropuesta
     * The fecha_cierre_propuesta
     */
    public void setFechaCierrePropuesta(String fechaCierrePropuesta) {
        this.fechaCierrePropuesta = fechaCierrePropuesta;
    }

    /**
     *
     * @return
     * The fechaEtapaCompetitiva
     */
    public String getFechaEtapaCompetitiva() {
        return fechaEtapaCompetitiva;
    }

    /**
     *
     * @param fechaEtapaCompetitiva
     * The fecha_etapa_competitiva
     */
    public void setFechaEtapaCompetitiva(String fechaEtapaCompetitiva) {
        this.fechaEtapaCompetitiva = fechaEtapaCompetitiva;
    }

    /**
     *
     * @return
     * The observaciones
     */
    public String getObservaciones() {
        return observaciones;
    }

    /**
     *
     * @param observaciones
     * The observaciones
     */
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    /**
     *
     * @return
     * The restricciones
     */
    public String getRestricciones() {
        return restricciones;
    }

    /**
     *
     * @param restricciones
     * The restricciones
     */
    public void setRestricciones(String restricciones) {
        this.restricciones = restricciones;
    }

    /**
     *
     * @return
     * The etiquetas
     */
    public List<String> getEtiquetas() {
        return etiquetas;
    }

    /**
     *
     * @param etiquetas
     * The etiquetas
     */
    public void setEtiquetas(List<String> etiquetas) {
        this.etiquetas = etiquetas;
    }

    /**
     *
     * @return
     * The Id
     */
    public String getId() {
        return Id;
    }

    /**
     *
     * @param Id
     * The @id
     */
    public void setId(String Id) {
        this.Id = Id;
    }

    /**
     *
     * @return
     * The Type
     */
    public String getType() {
        return Type;
    }

    /**
     *
     * @param Type
     * The @type
     */
    public void setType(String Type) {
        this.Type = Type;
    }

    /**
     *
     * @return
     * The formato
     */
    public String getFormato() {
        return formato;
    }

    /**
     *
     * @param formato
     * The formato
     */
    public void setFormato(String formato) {
        this.formato = formato;
    }

    /**
     *
     * @return
     * The license
     */
    public String getLicense() {
        return license;
    }

    /**
     *
     * @param license
     * The license
     */
    public void setLicense(String license) {
        this.license = license;
    }

    /**
     *
     * @return
     * The invitados
     */
    public String getInvitados() {
        return invitados;
    }

    /**
     *
     * @param invitados
     * The invitados
     */
    public void setInvitados(String invitados) {
        this.invitados = invitados;
    }

    /**
     *
     * @return
     * The documentos
     */
    public String getDocumentos() {
        return documentos;
    }

    /**
     *
     * @param documentos
     * The documentos
     */
    public void setDocumentos(String documentos) {
        this.documentos = documentos;
    }

    /**
     *
     * @return
     * The lotes
     */
    public String getLotes() {
        return lotes;
    }

    /**
     *
     * @param lotes
     * The lotes
     */
    public void setLotes(String lotes) {
        this.lotes = lotes;
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
     * The contrato
     */
    public Contrato getContrato() {
        return contrato;
    }

    /**
     *
     * @param contrato
     * The contrato
     */
    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }

    /**
     *
     * @return
     * The adjudicacion
     */
    public Adjudicacion getAdjudicacion() {
        return adjudicacion;
    }

    /**
     *
     * @param adjudicacion
     * The adjudicacion
     */
    public void setAdjudicacion(Adjudicacion adjudicacion) {
        this.adjudicacion = adjudicacion;
    }

}