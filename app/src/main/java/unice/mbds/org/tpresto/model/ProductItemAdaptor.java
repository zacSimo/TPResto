package unice.mbds.org.tpresto.model;

import android.content.Context;
import android.graphics.Bitmap;
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
import unice.mbds.org.tpresto.database.ProduitdbHelper;

import static java.lang.Integer.parseInt;

/**
 * Created by Zac on 12/12/2015.
 */
public class ProductItemAdaptor extends BaseAdapter {
    OrderDbHelper orderDbHelper;
    ProduitdbHelper produitdbHelper;
    private Context context;
    protected List<Product> products;
    protected Order order;
    protected int quantite;
    public ProductItemAdaptor(Context context,List<Product> products) {
        this.context = context;
        this.products = products;
        this.orderDbHelper = new OrderDbHelper(context);
        context.deleteDatabase(orderDbHelper.getDatabaseName());
//        this.produitdbHelper = new ProduitdbHelper(context);
//        context.deleteDatabase(produitdbHelper.getDatabaseName());
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Product getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        Bitmap image = null;
        Bitmap bitmap = null;
        quantite = 0;
        ProductViewHolder viewHolder = null;
        if (v==null){
            v = View.inflate(context, R.layout.layout_list_product,null);
            viewHolder = new ProductViewHolder();
            viewHolder.menu_name = (TextView) v.findViewById(R.id.menu_name);
            viewHolder.menu_image = (ImageView) v.findViewById(R.id.imageMenu);
            viewHolder.menu_description = (TextView) v.findViewById(R.id.menu_description);
            viewHolder.menu_price = (TextView)v.findViewById(R.id.menu_price);
            viewHolder.menu_dessert = (TextView) v.findViewById(R.id.menu_dessert);
            viewHolder.menu_calories = (TextView) v.findViewById(R.id.menu_calories);
            viewHolder.menu_discount = (TextView)v.findViewById(R.id.menu_discount);
            viewHolder.ajouter = (Button) v.findViewById(R.id.ajouter);


            v.setTag(viewHolder);
        }
        else{
            viewHolder = (ProductViewHolder) v.getTag();
        }

        final Product produit = products.get(position);
        viewHolder.menu_name.setText(produit.getName());
        viewHolder.menu_description.setText(produit.getDescription());

        int i = 1;

        final Field[] declaredFields = (R.drawable.class).getDeclaredFields();
        for (Field field : declaredFields) {
            final int drawableId;
            try {
                if(field.getName().equals(produit.getPicture())){
                    drawableId = field.getInt(R.drawable.class);
                    viewHolder.menu_image.setImageDrawable(context.getDrawable(drawableId));
                    //Toast.makeText(context, "nom foto in R.drawable : "+field.getName(), Toast.LENGTH_LONG).show();
                    break;
                }

                // DEBUG : Toast.makeText(context, "nom foto in R.drawable : "+field.getName(), Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                continue;
            }

        }
       // viewHolder.menu_image.setImageDrawable(context.getDrawable(R.drawable.dessert_0));
        viewHolder.menu_price.setText("Prix : " + new Double(produit.getPrice()).toString());
        viewHolder.menu_dessert.setText(produit.getType());
        viewHolder.menu_dessert.setTextColor(v.getResources().getColor(R.color.text_menu));
        viewHolder.menu_calories.setText("Calories : " + new Double(produit.getCalories()).toString());
        viewHolder.menu_discount.setText("Discount " + new Double(produit.getDiscount()).toString());
        viewHolder.ajouter.setText("Ajouter");
        viewHolder.ajouter.setTag(produit.getId());
        //viewHolder.ajouter.setOnClickListener(listener);

        viewHolder.ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //order = produit;
                Order data;
                if((data=orderDbHelper.getOrderDb(produit.getId()))!=null) {
                    quantite = parseInt(data.getQuantite());
                    quantite++;
                    data.setQuantite(Integer.toString(quantite));
                    orderDbHelper.updateQuantite(data);
                    System.out.println("\nCONTENU ORDER TROUVER IN DB Name: " + data.getName());
                    System.out.println("\nCONTENU ORDER TROUVER IN DB Description: " + data.getDescription());
                    System.out.println("\nCONTENU ORDER TROUVER IN DB Price: " + data.getPrice());
                    System.out.println("\nCONTENU ORDER TROUVER IN DB Picture: " + data.getPicture());
                    System.out.println("\nCONTENU ORDER TROUVER IN DB Discount: " + data.getDiscount());
                    System.out.println("\nCONTENU ORDER TROUVER IN DB Type: " + data.getType());
                    System.out.println("\nCONTENU ORDER TROUVER IN DB Id: " + data.getId());
                    System.out.println("\nCONTENU ORDER TROUVER IN DB Calorie: " + data.getCalories());
                    System.out.println("\nCONTENU ORDER TROUVER IN DB Quantité: " + data.getQuantite());
                    Toast.makeText(context, "Ordre incrementé Quantité :"+data.getQuantite(), Toast.LENGTH_LONG).show();
                }
                else{
                    order = new Order(produit.getName(),produit.getDescription(),produit.getPrice(),produit.getCalories(),
                            produit.getType(),produit.getDiscount(),produit.getPicture(),produit.getDessert(),produit.getCreatedAt(),
                            produit.getUpdatedAt(),produit.getId(),"1");
                    orderDbHelper.insertOrder(order);
                    Toast.makeText(context, "Ordre Ajouté :"+order.getQuantite(), Toast.LENGTH_LONG).show();
                }

            }
        });


        return v;
    }

    class ProductViewHolder {
        TextView menu_name;
        ImageView menu_image;
        TextView menu_description;
        TextView menu_price;
        TextView menu_calories;
        TextView menu_discount;
        TextView menu_dessert;
        Button ajouter;
    }


}
