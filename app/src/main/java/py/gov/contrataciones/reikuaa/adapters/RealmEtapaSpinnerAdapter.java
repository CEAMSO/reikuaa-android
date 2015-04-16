package py.gov.contrataciones.reikuaa.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;
import py.gov.contrataciones.reikuaa.R;
import py.gov.contrataciones.reikuaa.model.Entidad;
import py.gov.contrataciones.reikuaa.model.Estado;
import py.gov.contrataciones.reikuaa.model.Etapa;
import py.gov.contrataciones.reikuaa.model.TipoProcedimiento;

public class RealmEtapaSpinnerAdapter extends RealmBaseAdapter<Etapa> implements ListAdapter {
    private static class MyViewHolder {
        TextView nombreEstado;
    }

    public RealmEtapaSpinnerAdapter(Context context, int resId, RealmResults<Etapa> realmResults,
                                    boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        MyViewHolder viewHolder;

        if(convertView==null) {
            convertView = inflater.inflate(R.layout.basic_spinner_item_row, parent, false);
            viewHolder = new MyViewHolder();
            viewHolder.nombreEstado = (TextView) convertView.findViewById(R.id.text1);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (MyViewHolder) convertView.getTag();
        }

        Etapa item = realmResults.get(position);
        viewHolder.nombreEstado.setText(item.getNombre());
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
        viewHolder.nombreEstado.setSingleLine();
        viewHolder.nombreEstado.setEllipsize(TextUtils.TruncateAt.END);

        return mView;
    }

    public RealmResults<Etapa> getRealmResults() {
        return realmResults;
    }
}