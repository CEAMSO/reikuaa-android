package py.gov.contrataciones.reikuaa.adapters;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.h6ah4i.android.widget.advrecyclerview.swipeable.RecyclerViewSwipeManager;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.SwipeableItemAdapter;

import java.util.List;

import py.gov.contrataciones.reikuaa.R;
import py.gov.contrataciones.reikuaa.parametros.Parametro;
import py.gov.contrataciones.reikuaa.view.ConfiguracionActivity;
import py.gov.contrataciones.reikuaa.view.LicitacionActivity;
import py.gov.contrataciones.reikuaa.view.ListaLicitacionesActivity;
import py.gov.contrataciones.reikuaa.view.MisLicitacionesActivity;

/**
 * Adapter para el RecyclerView utilizado para el navigation drawer principal (menú principal) y el
 * listado de licitaciones.
 *
 * Created by willtallpear on 18/02/15.
 */
public class MainDrawerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements SwipeableItemAdapter<MainDrawerAdapter.ViewHolder> {
    /*
     * Definición del tipo de item presente.
     * TYPE_ITEM: item del navigation drawer
     * TYPE_LICITACION:item del listado de licitaciones
     * TYPE_LOADING: item que muestra un progress bar de carga cuando se intenta traer nuevo resultados al lsitado de licitaciones
     * TYPE_HEADER: item especial del encabezado del navigation drawer que muestra el logo con el texto del DNCP
     */
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_LICITACION = 1;
    private static final int TYPE_LOADING = 2;
    private static final int TYPE_HEADER = 3;

    private int viewType;
    private List<String> mNavTitles; // String Array to store the passed titles Value from the activity
    private List<String> mNavDesc;
    private List<String> mIds;
    private List<Integer> mIcons; // Int Array to store the passed icons resource value from the activity
    //Indica si la vista que lo invoca viene de MisLicitacionesActivity
    private Boolean parentViewMisLicitaciones=false;
    /**
     *
     * @return lista de ids de las licitaciones mostradas en el listado.
     */
    public List<String> getmIds() {
        return mIds;
    }

    /**
     * Setea la lista de ids de las licitaciones mostradas en el listado.
     * @param mIds lista de ids
     */
    public void setmIds(List<String> mIds) {
        this.mIds = mIds;
    }


    @Override
    public int onGetSwipeReactionType(ViewHolder viewHolder, int x, int y) {
        return 0;
    }

    @Override
    public void onSetSwipeBackground(ViewHolder viewHolder, int type) {
        int bgRes = 0;
        switch(type) {
            case RecyclerViewSwipeManager.DRAWABLE_SWIPE_NEUTRAL_BACKGROUND:
                bgRes = R.drawable.bg_swipe_item_neutral;
                break;
            case RecyclerViewSwipeManager.DRAWABLE_SWIPE_LEFT_BACKGROUND:
                bgRes = R.drawable.bg_swipe_item_left;
                break;
        }
        viewHolder.itemView.setBackgroundResource(bgRes);
    }

    @Override
    public int onSwipeItem(ViewHolder viewHolder, int result) {
        Log.d("MainDrawerAdapter", "onSwipeItem(result = " + result + ")");

        switch(result) {
            //swipe left -- suscribirse
            case RecyclerViewSwipeManager.RESULT_SWIPED_LEFT:
                return RecyclerViewSwipeManager.AFTER_SWIPE_REACTION_MOVE_TO_SWIPED_DIRECTION;
            //en otro caso, no hacer nada
            case RecyclerViewSwipeManager.RESULT_CANCELED:
            default:
                return RecyclerViewSwipeManager.AFTER_SWIPE_REACTION_DEFAULT;
        }
    }

    @Override
    public void onPerformAfterSwipeReaction(ViewHolder viewHolder, int result, int reaction) {
        Log.d("MainDrawerAdapter", "onPerformAfterSwipeReaction(result = " + result + ", reaction = " + reaction + ")");

        final int position = viewHolder.getPosition();

        if( reaction == RecyclerViewSwipeManager.AFTER_SWIPE_REACTION_MOVE_TO_SWIPED_DIRECTION) {
            Log.d("MainDrawerAdapter", "Aquí va la acción a realizar");
        }
    }

    /**
     * ViewHolder para item especial que muestra el progress bar de items cargando
     */
    public class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        }
    }

    /**
     * ViewHolder que almacena una fila, puede tratarse de una licitación en caso del listado
     * de licitaciones, o una opción en caso del navigation drawer.
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        int holderId;
        TextView titleTextView;
        TextView cuerpoTextView;
        View mDragHandle;
        ImageView imageView;

        public ViewHolder(View itemView, int viewType) {
            super(itemView);

            if(viewType == TYPE_ITEM) { //item de navigation drawer
                titleTextView = (TextView) itemView.findViewById(R.id.rowText);
                imageView = (ImageView) itemView.findViewById(R.id.rowIcon);
                holderId = 0;
                itemView.setClickable(true);
                itemView.setOnClickListener(this);
            }
            if(viewType == TYPE_LICITACION) { //item de licitación
                titleTextView = (TextView) itemView.findViewById(R.id.tituloLicText);
                cuerpoTextView = (TextView) itemView.findViewById(R.id.cuerpoLicText);
                imageView = (ImageView) itemView.findViewById(R.id.iconoLic);
                mDragHandle = itemView.findViewById(R.id.drag_handle);
                itemView.setOnClickListener(this);
                mDragHandle.setVisibility(View.GONE);
                holderId = 1;

            }
        }

        @Override
        public void onClick(View v) {
            int pos = this.getPosition();

            if(viewType == TYPE_LICITACION) { //botones de listado de iems
                String id = getmIds().get(pos);
                Intent i = new Intent(v.getContext(), LicitacionActivity.class);
                i.putExtra(LicitacionActivity.ID_PLANIFICACION, id);
                if(parentViewMisLicitaciones) {
                    i.putExtra(LicitacionActivity.PARENT_MIS_LICITACIONES, parentViewMisLicitaciones.toString());
                }
                v.getContext().startActivity(i);
            }
            else { //botones de navigation drawer
                if(pos==1) { //todas las licitaciones
                    Intent intent = new Intent(v.getContext(), ListaLicitacionesActivity.class);

                    v.getContext().startActivity(intent);
                }
                if(pos==2) { //mis licitaciones
                    Intent intent = new Intent(v.getContext(), MisLicitacionesActivity.class);
                    v.getContext().startActivity(intent);
                }
                if(pos==4) { //visitar portal web
                    String url = Parametro.WEBSITE_DNCP_URL;
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    v.getContext().startActivity(intent);
                }
                if(pos==5) { //configuracion
                    Intent intent = new Intent(v.getContext(), ConfiguracionActivity.class);

                    v.getContext().startActivity(intent);
                }
            }
        }

    }

    public MainDrawerAdapter(List<String> titles, List<Integer> icons) { // MyAdapter Constructor with titles and icons parameter
        mNavTitles = titles;
        mIcons = icons;
        viewType = TYPE_ITEM;
        //here we assign those passed values to the values we declared here in adapter
    }

    public MainDrawerAdapter(List<String> titles, List<Integer> icons, List<String> cuerpo, List<String> id, Boolean misLicitaciones) {
        mNavTitles = titles;
        mNavDesc = cuerpo;
        mIcons = icons;
        mIds = id;
        viewType = TYPE_LICITACION;
        parentViewMisLicitaciones = misLicitaciones;
    }

    //Below first we ovverride the method onCreateViewHolder which is called when the ViewHolder is
    //Created, In this method we inflate the drawer_item_row.xmlrow.xml layout if the viewType is Type_ITEM
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_item_row,parent,false);
            ViewHolder vhItem = new ViewHolder(v, viewType); //Creating ViewHolder and passing the object of type view
            return vhItem;
        }
        if(viewType == TYPE_LICITACION) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.licitation_item_row, parent,false);
            ViewHolder vhItem = new ViewHolder(v, viewType); //Creating ViewHolder and passing the object of type view
            return vhItem;
        }
        if(viewType == TYPE_LOADING) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.progress_item, parent, false);
            ProgressViewHolder pvhItem = new ProgressViewHolder(v);
            return pvhItem;
        }
        if(viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.header,parent,false);
            ViewHolder vhHeader = new ViewHolder(v, viewType);
            return vhHeader;
        }
        return null;
    }
    //Next we override a method which is called when the item in a row is needed to be displayed, here the int position
    // Tells us item at which position is being constructed to be displayed and the holder id of the holder object tell us
    // which view type is being created
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ViewHolder) {
            if (((ViewHolder)holder).holderId == TYPE_ITEM && position!=0) {
                ((ViewHolder)holder).titleTextView.setText(mNavTitles.get(position-1));
                ((ViewHolder)holder).imageView.setImageResource(mIcons.get(position-1));
            }
            if (((ViewHolder)holder).holderId == TYPE_LICITACION) {
                ((ViewHolder)holder).titleTextView.setText(mNavTitles.get(position));
                ((ViewHolder)holder).cuerpoTextView.setText(mNavDesc.get(position));
                ((ViewHolder)holder).imageView.setImageResource(mIcons.get(position));
            }
        }
        else {
            ((ProgressViewHolder)holder).progressBar.setIndeterminate(true);
        }
    }

    // This method returns the number of items present in the list
    @Override
    public int getItemCount() {
        return mNavTitles.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(viewType==TYPE_ITEM) {
            if( isPositionHeader(position)) {
                return TYPE_HEADER;
            }
            else {
                return TYPE_ITEM;
            }
        }
        return mNavTitles.get(position)!=null ? viewType : TYPE_LOADING;
    }

    private boolean isPositionHeader(int position) {
        return position==0;
    }
}
