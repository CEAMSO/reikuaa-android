package py.gov.contrataciones.reikuaa.model;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;


public class Adjudicacion {

    @Expose
    private java.util.List<py.gov.contrataciones.reikuaa.model.List> list = new ArrayList<>();

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

}