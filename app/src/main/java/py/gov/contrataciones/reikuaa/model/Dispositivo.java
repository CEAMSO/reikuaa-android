package py.gov.contrataciones.reikuaa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import py.gov.contrataciones.reikuaa.parametros.Parametro;

/**
 * Clase que representa el telefono a ser registrado para consultas a la API del proxy
 * Created by gaby.lorely on 09/03/2015.
 */
public class Dispositivo {

    @SerializedName("mail")
    @Expose
    private String mail;

    @SerializedName("registrationId")
    @Expose
    private String registrationId;

    @SerializedName("tipoDispositivo")
    @Expose
    private String tipoDispositivo;

    public Dispositivo(){
        super();
        this.tipoDispositivo = Parametro.TIPO_DISPOSITIVO;
    }

    public void setRegistrationId(String codigo) {
        this.registrationId = codigo;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public String getMail() {
        return mail;
    }

    public String getTipoDispositivo() {
        return tipoDispositivo;
    }

    public void setTipoDispositivo(String tipoDispositivo) {
        this.tipoDispositivo = tipoDispositivo;
    }



}
