package unice.mbds.org.tpresto.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import unice.mbds.org.tpresto.R;
import unice.mbds.org.tpresto.utils.BitmapUtils;

/**
 * Created by Zac on 13/12/2015.
 */
public class CommandeItemAdaptor extends BaseAdapter {
    private Context context;
    public List<Product> commande_list;
    View.OnClickListener listener;

    public CommandeItemAdaptor(Context context, List<Product> commandes) {
        this.context = context;
        this.commande_list = commandes;
        this.listener =listener;
    }


    @Override
    public int getCount() {
        return commande_list.size();
    }

    @Override
    public Object getItem(int arg0) {
        return commande_list.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        View v = convertView;
        Bitmap image = null;
        CommandeViewHolder viewHolder = null;
        if(v==null){
            v = View.inflate(context, R.layout.layout_list_commandes, null);
            viewHolder = new CommandeViewHolder();
            viewHolder.type_choix= (TextView)v.findViewById(R.id.type_choix);
            viewHolder.imageType= (ImageView)v.findViewById(R.id.imageType);
            viewHolder.liste_type= (Button)v.findViewById(R.id.liste_type);
            viewHolder.menu_name= (TextView)v.findViewById(R.id.menu_name_s);
            viewHolder.voir_produit= (Button)v.findViewById(R.id.voir_produit);
            viewHolder.supprimer_produit= (Button)v.findViewById(R.id.supprimer_produit);
            v.setTag(viewHolder);// un Tag à travers lequel on recupere la reference ds 1 autre view
        }
        else{
            viewHolder = (CommandeViewHolder) v.getTag();
        }
        Product commande = commande_list.get(position);

        viewHolder.type_choix.setText(commande.getType());
        viewHolder.liste_type.setText("Voir la liste des "+commande.getType());
        viewHolder.menu_name.setText(commande.getName());
        try {
            image = BitmapUtils.loadBitmap(commande.getPicture());
        }catch (Exception conn){
            //image = g(R.drawable.images);
        }
        viewHolder.imageType.setImageBitmap(image);
        String connexion ="";


        //logique de suppression sur le webservice mais faudra rafraichir la liste
        viewHolder.liste_type.setOnClickListener( listener);
        viewHolder.voir_produit.setOnClickListener( listener);
        viewHolder.supprimer_produit.setOnClickListener( listener);
        return v;
    }


    class CommandeViewHolder{
        TextView type_choix;
        ImageView imageType;
        Button liste_type;
        TextView menu_name;
        Button voir_produit;
        Button supprimer_produit;


    }


}
