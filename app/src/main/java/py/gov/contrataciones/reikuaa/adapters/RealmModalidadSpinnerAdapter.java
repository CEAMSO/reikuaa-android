package py.gov.contrataciones.reikuaa.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;
import py.gov.contrataciones.reikuaa.R;
import py.gov.contrataciones.reikuaa.model.Categoria;
import py.gov.contrataciones.reikuaa.model.TipoProcedimiento;

/**
 * Adapter para el spinner de tipos de procedimieno.
 */
public class RealmModalidadSpinnerAdapter extends RealmBaseAdapter<TipoProcedimiento> implements SpinnerAdapter {
    private static class MyViewHolder {
        TextView nombreModalidad;
    }

    public RealmModalidadSpinnerAdapter(Context context, int resId, RealmResults<TipoProcedimiento> realmResults,
                                        boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        MyViewHolder viewHolder;

        if(convertView==null) {
            convertView = inflater.inflate(R.layout.basic_spinner_item_row, parent, false);
            viewHolder = new MyViewHolder();
            viewHolder.nombreModalidad = (TextView) convertView.findViewById(R.id.text1);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (MyViewHolder) convertView.getTag();
        }

        TipoProcedimiento item = realmResults.get(position);
        viewHolder.nombreModalidad.setText(item.getNombre());
        return convertView;
    }
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View mView = getCustomView(position, convertView, parent);
        MyViewHolder viewHolder = (MyViewHolder) mView.getTag();
        return mView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View mView = getCustomView(position, convertView, parent);
        MyViewHolder viewHolder = (MyViewHolder) mView.getTag();
        viewHolder.nombreModalidad.setSingleLine();
        viewHolder.nombreModalidad.setEllipsize(TextUtils.TruncateAt.END);

        return mView;
    }

    public RealmResults<TipoProcedimiento> getRealmResults() {
        return realmResults;
    }
}