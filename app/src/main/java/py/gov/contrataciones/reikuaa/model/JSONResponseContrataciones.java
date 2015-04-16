package py.gov.contrataciones.reikuaa.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Clase base que maneja el formato de los responses JSON de la API de DNCP
 */
public class JSONResponseContrataciones {

    @SerializedName("@context")
    @Expose
    private String Context;
    @SerializedName("@graph")
    @Expose
    private List<py.gov.contrataciones.reikuaa.model.Graph> Graph = new ArrayList<py.gov.contrataciones.reikuaa.model.Graph>();

    /**
     *
     * @return
     * The Context
     */
    public String getContext() {
        return Context;
    }

    /**
     *
     * @param Context
     * The @context
     */
    public void setContext(String Context) {
        this.Context = Context;
    }

    /**
     *
     * @return
     * The Graph
     */
    public List<py.gov.contrataciones.reikuaa.model.Graph> getGraph() {
        return Graph;
    }

    /**
     *
     * @param Graph
     * The @graph
     */
    public void setGraph(List<py.gov.contrataciones.reikuaa.model.Graph> Graph) {
        this.Graph = Graph;
    }

}