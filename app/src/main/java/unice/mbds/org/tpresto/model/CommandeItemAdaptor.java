package unice.mbds.org.tpresto.model;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

import unice.mbds.org.tpresto.DetailProduitActivity;
import unice.mbds.org.tpresto.ElementsCommandeActivity;
import unice.mbds.org.tpresto.ListeProductFromCommandeActivity;
import unice.mbds.org.tpresto.R;
import unice.mbds.org.tpresto.database.OrderDbHelper;
import unice.mbds.org.tpresto.database.ProduitdbHelper;

import static java.lang.Integer.parseInt;

/**
 * Created by Zac on 13/12/2015.
 */
public class CommandeItemAdaptor extends BaseAdapter {
    private Context context;
    public List<Order> commande_list;
    OrderDbHelper orderDbHelper;
    ProduitdbHelper produitdbHelper;

    public CommandeItemAdaptor(Context context, List<Order> commandes) {
        this.context = context;
        this.commande_list = commandes;
        this.orderDbHelper = new OrderDbHelper(context);
        this.produitdbHelper = new ProduitdbHelper(context);
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
            viewHolder.quantite = (TextView) v.findViewById(R.id.quantite);
            v.setTag(viewHolder);// un Tag à travers lequel on recupere la reference ds 1 autre view
        }
        else{
            viewHolder = (CommandeViewHolder) v.getTag();
        }
        final Order commande = commande_list.get(position);

        viewHolder.type_choix.setText(commande.getType());
        viewHolder.quantite.setText("Qté : "+commande.getQuantite());
        viewHolder.liste_type.setText("Voir la liste des " + commande.getType());
        viewHolder.menu_name.setText(commande.getName());

        final Field[] declaredFields = (R.drawable.class).getDeclaredFields();
        for (Field field : declaredFields) {
            final int drawableId;
            try {
                if(field.getName().equals(commande.getPicture())){
                    drawableId = field.getInt(R.drawable.class);
                    viewHolder.imageType.setImageDrawable(context.getDrawable(drawableId));
                    break;
                }

            } catch (Exception e) {
                continue;
            }

        }
        //logique de suppression sur le webservice mais faudra rafraichir la liste
        viewHolder.liste_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context,ListeProductFromCommandeActivity.class);
                Bundle extra = new Bundle();
                extra.putSerializable("typeProduit", commande.getType());
                intent.putExtra("extra",extra);
                context.startActivity(intent);
            }
        });
        viewHolder.voir_produit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailProduitActivity.class);
                Bundle extra = new Bundle();
                extra.putSerializable("detailProduit", (Serializable) commande);
                intent.putExtra("extra", extra);
                context.startActivity(intent);
            }
        });
        viewHolder.supprimer_produit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder adb=new AlertDialog.Builder(context);
                adb.setTitle("Supprimer?");
                adb.setMessage("Etes-vous sure de vouloir supprimer? ");
                adb.setNegativeButton("Cancel", null);
                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Integer q = parseInt(commande.getQuantite());
                        if (q > 1) {
                            q--;
                            commande.setQuantite(q.toString());
                            orderDbHelper.updateOrder(commande);
                        } else {
                            if (orderDbHelper.deleteOrder(commande.getId())) {
                                Toast.makeText(context, "" + commande.getType() + ", " + commande.getName() + " Supprimé avec SUCCES", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(context, "" + commande.getType() + ", " + commande.getName() + " ECHEC SUPPRESSION", Toast.LENGTH_LONG).show();
                            }
                        }
                        Intent intent = new Intent(context, ElementsCommandeActivity.class);
                        context.startActivity(intent);
                    }
                });
                adb.show();

            }
        });

        return v;
    }


    class CommandeViewHolder{
        TextView type_choix;
        TextView quantite;
        ImageView imageType;
        Button liste_type;
        TextView menu_name;
        Button voir_produit;
        Button supprimer_produit;


    }


}
