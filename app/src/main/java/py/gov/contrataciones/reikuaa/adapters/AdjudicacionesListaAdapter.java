package py.gov.contrataciones.reikuaa.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

import py.gov.contrataciones.reikuaa.R;
import py.gov.contrataciones.reikuaa.model.List;
import py.gov.contrataciones.reikuaa.view.LicitacionActivity;

/**
 * Adapter para el listado de proveedores adjudicados que se muestra en AdjudicacionesActivity
 * Created by willtallpear on 18/02/15.
 */

public class AdjudicacionesListaAdapter extends RecyclerView.Adapter<AdjudicacionesListaAdapter.ViewHolder> {

    private java.util.List<List> adjudicaciones;
    private int viewType;

    /**
     * ViewHolder para cada item de la lista, que representa a cada proveedor adjudicado.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        int holderId;
        TextView proveedorTextView;
        TextView rucTextView;
        TextView montoTextView;
        TextView monedaTextView;

        public ViewHolder(View itemView, int viewType) {
            super(itemView);
            proveedorTextView = (TextView) itemView.findViewById(R.id.proveedor);
            rucTextView = (TextView) itemView.findViewById(R.id.ruc);
            montoTextView = (TextView) itemView.findViewById(R.id.montoAdjudicado);
            monedaTextView = (TextView) itemView.findViewById(R.id.moneda);
        }

        @Override
        public void onClick(View v) {
            //acción al realizar en el clic
        }

    }

    public AdjudicacionesListaAdapter(java.util.List<List> adjudicaciones) { // MyAdapter Constructor with titles and icons parameter
        //here we assign those passed values to the values we declared here in adapter
        this.adjudicaciones = adjudicaciones;
    }


    //Below first we ovverride the method onCreateViewHolder which is called when the ViewHolder is
    //Created, In this method we inflate the drawer_item_row.xmlrow.xml layout if the viewType is Type_ITEM
    @Override
    public AdjudicacionesListaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_adjudicacion_item,parent,false);
            ViewHolder vhItem = new ViewHolder(v, viewType); //Creating ViewHolder and passing the object of type view
            return vhItem;
    }

    //Next we override a method which is called when the item in a row is needed to be displayed, here the int position
    // Tells us item at which position is being constructed to be displayed and the holder id of the holder object tell us
    // which view type is being created
    @Override
    public void onBindViewHolder(AdjudicacionesListaAdapter.ViewHolder holder, int position) {

            holder.proveedorTextView.setText(adjudicaciones.get(position).getProveedor().getRazonSocial());
            holder.rucTextView.setText(adjudicaciones.get(position).getProveedor().getRuc());
            long monto = adjudicaciones.get(position).getMontoAdjudicado();
            DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.getDefault());
            otherSymbols.setGroupingSeparator('.');
            otherSymbols.setDecimalSeparator(',');
            DecimalFormat df = new DecimalFormat("#,###", otherSymbols);
            holder.montoTextView.setText(df.format(monto));
            holder.monedaTextView.setText(adjudicaciones.get(position).getMoneda().getNombre());
    }

    // This method returns the number of items present in the list
    @Override
    public int getItemCount() {
        return adjudicaciones.size();
    }

    @Override
    public int getItemViewType(int position) {
        return viewType;
    }
}
