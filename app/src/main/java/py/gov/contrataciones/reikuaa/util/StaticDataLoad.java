package py.gov.contrataciones.reikuaa.util;

import android.content.ClipData;
import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import py.gov.contrataciones.reikuaa.model.Categoria;
import py.gov.contrataciones.reikuaa.model.Entidad;
import py.gov.contrataciones.reikuaa.model.Estado;
import py.gov.contrataciones.reikuaa.model.Etapa;
import py.gov.contrataciones.reikuaa.model.ItemNivel4;
import py.gov.contrataciones.reikuaa.model.ItemNivel5;
import py.gov.contrataciones.reikuaa.model.TipoProcedimiento;

/**
 * Carga los datos contenidos en los assets a la BD local de la app
 *
 * Created by willtallpear on 25/02/15.
 */
public final class StaticDataLoad {
    private static StaticDataLoad instance;
    private  Realm realm;
    private  Context context;

    private StaticDataLoad(Context context) {
        realm = Realm.getInstance(context);
        this.context = context;
    }

    public static StaticDataLoad loadStatic(Context context) {
        //Realizamos la carga de los datos estáticos a la base de datos Realm
        instance = new StaticDataLoad(context);
        return instance;
    }

    public void cargarTodos() {
        cargarCategorias();
        cargarEstados();
        cargarEtapas();
        cargarModalidades();
        cargarItems();
        cargarEntidades();
    }


    public void cargarCategorias() {
        //traemos datos actualmente presentes en la base de datos
        RealmQuery<Categoria> query = realm.where(Categoria.class);
        long results = query.count();

        if(results==0) {
            List<Categoria> categorias = new ArrayList<Categoria>();
            try {
                InputStream catRaw = context.getAssets().open("cat.csv");
                CSVReader reader = new CSVReader(new InputStreamReader(catRaw));
                String[] linea;
                int index = 0;
                while ((linea = reader.readNext()) != null) {
                    if (index != 0) { //ignoramos las cabeceras
                        Categoria categoria = new Categoria(Integer.parseInt(linea[0]), Integer.parseInt(linea[1]), linea[2]);
                        categorias.add(categoria);
                    }
                    index++;
                }
                reader.close();
                catRaw.close();


                realm.beginTransaction();
                realm.copyToRealmOrUpdate(categorias);
                realm.commitTransaction();

            } catch (IOException e) {
                Log.e("bd", "Error: " + e.getMessage());
            }
        }
    }

    public void cargarModalidades() {
        //traemos datos actualmente presentes en la base de datos
        RealmQuery<TipoProcedimiento> query = realm.where(TipoProcedimiento.class);
        long results = query.count();

        if(results==0) {
            List<TipoProcedimiento> tipos = new ArrayList<>();
            try {
                InputStream catRaw = context.getAssets().open("mod.csv");
                CSVReader reader = new CSVReader(new InputStreamReader(catRaw));
                String[] linea;
                int index = 0;
                while ((linea = reader.readNext()) != null) {
                    if (index != 0) { //ignoramos las cabeceras
                        TipoProcedimiento modalidad = new TipoProcedimiento(Integer.parseInt(linea[0]), linea[1], linea[2]);
                        tipos.add(modalidad);
                    }
                    index++;
                }
                reader.close();
                catRaw.close();

                realm.beginTransaction();
                realm.copyToRealmOrUpdate(tipos);
                realm.commitTransaction();

            } catch (IOException e) {
                Log.e("bd", "Error: " + e.getMessage());
            }
        }
    }

    public void cargarEstados() {
        //traemos datos actualmente presentes en la base de datos
        RealmQuery<Estado> query = realm.where(Estado.class);
        long results = query.count();

        if(results==0) {
            List<Estado> estados = new ArrayList<Estado>();
            try {
                InputStream catRaw = context.getAssets().open("est.csv");
                CSVReader reader = new CSVReader(new InputStreamReader(catRaw));
                String[] linea;
                int index = 0;
                while ((linea = reader.readNext()) != null) {
                    if (index != 0) { //ignoramos las cabeceras
                        Estado estado = new Estado(linea[0], linea[1]);
                        estados.add(estado);
                    }
                    index++;
                }
                reader.close();
                catRaw.close();


                realm.beginTransaction();
                realm.copyToRealmOrUpdate(estados);
                realm.commitTransaction();

            } catch (IOException e) {
                Log.e("bd", "Error: " + e.getMessage());
            }
        }
    }

    public void cargarEtapas() {
        //traemos datos actualmente presentes en la base de datos
        RealmQuery<Etapa> query = realm.where(Etapa.class);
        long results = query.count();

        if(results==0) {
            List<Etapa> etapas = new ArrayList<>();
            try {
                InputStream catRaw = context.getAssets().open("etapas.csv");
                CSVReader reader = new CSVReader(new InputStreamReader(catRaw));
                String[] linea;
                int index = 0;
                while ((linea = reader.readNext()) != null) {
                    if (index != 0) { //ignoramos las cabeceras
                        Etapa etapa = new Etapa(linea[0], linea[1]);
                        etapas.add(etapa);
                    }
                    index++;
                }
                reader.close();
                catRaw.close();

                realm.beginTransaction();
                realm.copyToRealmOrUpdate(etapas);
                realm.commitTransaction();

            } catch (IOException e) {
                Log.e("bd", "Error: " + e.getMessage());
            }
        }
    }

    public void cargarItems() {
        //traemos datos actualmente presentes en la base de datos
        RealmQuery<ItemNivel4> query1 = realm.where(ItemNivel4.class);
        long results1 = query1.count();

        RealmQuery<ItemNivel5> query2 = realm.where(ItemNivel5.class);
        long results2 = query2.count();

        if(results1==0 || results2==0) {
            List<ItemNivel4> items4 = new ArrayList<>();
            List<ItemNivel5> items5 = new ArrayList<>();
            try {
                InputStream catRaw = context.getAssets().open("itemsnivel4.csv");
                CSVReader reader = new CSVReader(new InputStreamReader(catRaw));
                String[] linea;
                int index = 0;
                while ((linea = reader.readNext()) != null) {
                    if (index != 0) { //ignoramos las cabeceras
                        ItemNivel4 item = new ItemNivel4(linea[0], linea[1]);
                        items4.add(item);
                    }
                    index++;
                }
                reader.close();
                catRaw.close();

                //mismo procedimiento para los items nivel 5
                catRaw = context.getAssets().open("itemsnivel5.csv");
                reader = new CSVReader(new InputStreamReader(catRaw));

                index = 0;
                while ((linea = reader.readNext()) != null) {
                    if (index != 0) { //ignoramos las cabeceras
                        ItemNivel5 item = new ItemNivel5(linea[0], linea[1]);
                        items5.add(item);
                    }
                    index++;
                }
                reader.close();
                catRaw.close();
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(items4);
                realm.copyToRealmOrUpdate(items5);
                realm.commitTransaction();
            } catch (IOException e) {
                Log.e("bd", "Error: " + e.getMessage());
            }
        }
    }

    public void cargarEntidades() {
        try {
            InputStream is = context.getAssets().open("uoc.json");
            realm.beginTransaction();
            realm.createOrUpdateAllFromJson(Entidad.class, is);
            realm.commitTransaction();
        } catch (IOException e) {
            Log.e("staticDataLoad", "Error: " + e.getMessage());
        }
    }
}
