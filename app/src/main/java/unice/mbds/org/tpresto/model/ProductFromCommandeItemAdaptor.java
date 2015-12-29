package unice.mbds.org.tpresto.model;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.List;

import unice.mbds.org.tpresto.R;
import unice.mbds.org.tpresto.database.OrderDbHelper;

import static java.lang.Integer.parseInt;

/**
 * Created by Zac on 23/12/2015.
 */
public class ProductFromCommandeItemAdaptor extends BaseAdapter {
    private Context context;
    public List<Product> commande_list;
    protected String typeProduit;
    protected OrderDbHelper orderDbHelper;

    public ProductFromCommandeItemAdaptor(Context context, List<Product> commande_list, String typeProduit) {
        this.context = context;
        this.commande_list = commande_list;
        this.typeProduit = typeProduit;
        orderDbHelper = new OrderDbHelper(context);
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
        //Bitmap image = null;
        ProduitViewHolder viewHolder = null;
        if(v==null){
            v = View.inflate(context, R.layout.layout_liste_product_from_commande, null);
            viewHolder = new ProduitViewHolder();

            v.setTag(viewHolder);// un Tag à travers lequel on recupere la reference ds 1 autre view
        }
        else{
            viewHolder = (ProduitViewHolder) v.getTag();
        }

        final Product product = commande_list.get(position);
        if ((product.getType().equals(typeProduit))){
            viewHolder.nom_produit = (TextView) v.findViewById(R.id.nom_produit);
            viewHolder.imageProduit = (ImageView)v.findViewById(R.id.imageTypeProduit);
            viewHolder.ajout_typeProduit = (Button)v.findViewById(R.id.ajout_typeProduit);
            viewHolder.nom_produit.setText(product.getName());
            final Field[] declaredFields = (R.drawable.class).getDeclaredFields();
            for (Field field : declaredFields) {
                final int drawableId;
                try {
                    if(field.getName().equals(product.getPicture())){
                        drawableId = field.getInt(R.drawable.class);
                        viewHolder.imageProduit.setImageDrawable(context.getDrawable(drawableId));
                        break;
                    }

                } catch (Exception e) {
                    continue;
                }

            }

            viewHolder.ajout_typeProduit.setText("ajouter");
            viewHolder.ajout_typeProduit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Order data, order;
                    int quantite = 0;
                    if ((data = orderDbHelper.getOrderDb(product.getId())) != null) {
                        quantite = parseInt(data.getQuantite());
                        quantite++;
                        data.setQuantite(Integer.toString(quantite));
                        orderDbHelper.updateQuantite(data);
                        Toast.makeText(context, "Ordre incrementé Quantité :" + data.getQuantite(), Toast.LENGTH_LONG).show();
                    } else {
                        order = new Order(product.getName(), product.getDescription(), product.getPrice(), product.getCalories(),
                                product.getType(), product.getDiscount(), product.getPicture(), product.getDessert(), product.getCreatedAt(),
                                product.getUpdatedAt(), product.getId(), "1");
                        orderDbHelper.insertOrder(order);
                        Toast.makeText(context, "Ordre Ajouté :" + order.getQuantite(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }else {

        }
        return v;
    }


    class ProduitViewHolder {
        TextView nom_produit;
        ImageView imageProduit;
        Button ajout_typeProduit;

    }


}
