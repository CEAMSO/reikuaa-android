package py.gov.contrataciones.reikuaa.parametros;

/**
 * Created by gaby.lorely on 09/03/2015.
 */
public class Parametro {

    public static final String FLAG_CARGANDO = "4623";
    public static String CONTRATACIONES_URL = "https://www.contrataciones.gov.py:443";
    public static String WEBSITE_DNCP_URL = "https://www.contrataciones.gov.py/";
    public static String PROXY_URL = "http://107.170.22.46:9000/";
//    public static String PROXY_URL = "http://192.168.0.12:9000";
    public static String CACHE_CONTROL = "max-age=900";
    public static String CODIGO_APLICACION = "reikuaa-dncp";
    public static String EMAIL_DEFAULT = "reikuaa-dncp@dncp.gov.py";
    public static String SENDER_ID = "454119427660";
    public static String LIMIT_PARAM_FETCH_LICITACIONES = "20"; //cant. de items a traer para mostrar en la lista inicial
    public static String ETAPA_CONVOCATADA = "CONV";
    public static String TIPO_DISPOSITIVO = "ANDROID";

    public static int CONF_CAMBIO_ESTADO_ID = 1;
    public static int CONF_ADENDAS_ID = 2;
    public static int CONF_SUBASTAS_ID = 3;
    public static int CONF_PRORROGAS_ID = 4;
    public static int CONF_ACLARACIONES_ID = 5;
    public static int CONF_AVISOS_ID = 6;


    //Para que no se pueda instanciar desde afuera
    private Parametro() {}
}
