package py.gov.contrataciones.reikuaa.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;
import py.gov.contrataciones.reikuaa.R;
import py.gov.contrataciones.reikuaa.model.Categoria;

/**
 * Adapter para el spinner de Categorías, en él, se muestran las descripciones de las categorías
 * junto a sus iconos.
 */
public class RealmCategoriaSpinnerAdapter extends RealmBaseAdapter<Categoria> implements SpinnerAdapter {
    private static class MyViewHolder {
        TextView nombreCategoria;
        ImageView iconoCategoria;
    }

    public RealmCategoriaSpinnerAdapter(Context context, int resId, RealmResults<Categoria> realmResults,
                                        boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        MyViewHolder viewHolder;

        if(convertView==null) {
            convertView = inflater.inflate(R.layout.spinner_item_row, parent, false);
            viewHolder = new MyViewHolder();
            viewHolder.nombreCategoria = (TextView) convertView.findViewById(R.id.text1);
            viewHolder.iconoCategoria = (ImageView) convertView.findViewById(R.id.imgCategoriaRow);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (MyViewHolder) convertView.getTag();
        }

        Categoria item = realmResults.get(position);
        viewHolder.nombreCategoria.setText(item.getNombre());
        viewHolder.iconoCategoria.setImageResource(item.getIcono());
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
        viewHolder.nombreCategoria.setSingleLine();
        viewHolder.nombreCategoria.setEllipsize(TextUtils.TruncateAt.END);
        return mView;
    }

    public RealmResults<Categoria> getRealmResults() {
        return realmResults;
    }

    @Override
    public int getCount() {
        int count = super.getCount();
        return count > 0 ? count-1 : count;
    }
}