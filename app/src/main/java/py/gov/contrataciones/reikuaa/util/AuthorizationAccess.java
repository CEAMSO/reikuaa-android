package py.gov.contrataciones.reikuaa.util;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Patterns;

import java.util.regex.Pattern;

import py.gov.contrataciones.reikuaa.parametros.Parametro;
import py.gov.contrataciones.reikuaa.presenters.ListaLicitacionPresenterImpl;
import py.gov.contrataciones.reikuaa.view.ListaLicitacionesActivity;

/**
 * Clase que almacena información de acceso autorizado al access token resguardado por el servidor Proxy
 *
 * Created by gaby.lorely on 09/03/2015.
 */
public class AuthorizationAccess {

    private AuthorizationAccess() { };
    private static String mail = null;
    private static String regId = null;

    /**
     *
     * @return e-mail asociado al dispositivo
     */
    public static String getMail(){
        return mail;
    }

    /**
     * @return e-mail asociado al dispositivo
     */
    public static String getMail(Context context){
        if(mail==null) {
            final SharedPreferences prefs = getGCMPreferences(context);
            String currentEmail = prefs.getString(ListaLicitacionPresenterImpl.PROPERTY_EMAIL, "");
            if (currentEmail.isEmpty()) {
                String possibleEmail = null;
                Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
                Account[] accounts = AccountManager.get(context).getAccounts();
                for (Account account : accounts) {
                    if (emailPattern.matcher(account.name).matches()) {
                        possibleEmail = account.name;
                        break;
                    }
                }
                if (possibleEmail == null) {
                    possibleEmail = Parametro.EMAIL_DEFAULT;
                }
                mail = possibleEmail;
            }
            else
                mail = currentEmail;
        }
        return mail;
    }

    /**
     *
     * @param context
     * @return el registration Id asociado al dispositivo
     */
    public static String getRegistrationId(Context context) {
        if(regId == null){
            final SharedPreferences prefs = getGCMPreferences(context);
            String registrationId = prefs.getString(ListaLicitacionPresenterImpl.PROPERTY_REG_ID, "");
            if(registrationId.isEmpty()) {
                return "";
            }
            else {
                regId = registrationId;
            }

        }
            return regId;

    }

    /**
     * Obitnee una instancia del archivo de preferencias compartidas de la aplicación dentro del
     * dispositivo.
     * @param context
     * @return
     */
    private static SharedPreferences getGCMPreferences(Context context) {
        return context.getSharedPreferences(ListaLicitacionesActivity.class.getSimpleName(),
                Context.MODE_PRIVATE);
    }

    /**
     * Setea el registration id deseado
     * @param registrationId registration id a almacenar
     */
    public static void setRegistrationId(String registrationId) {
        regId = registrationId;
    }
}
