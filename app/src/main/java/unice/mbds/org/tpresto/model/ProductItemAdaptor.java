package unice.mbds.org.tpresto.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import unice.mbds.org.tpresto.R;
import unice.mbds.org.tpresto.utils.BitmapUtils;

/**
 * Created by Zac on 12/12/2015.
 */
public class ProductItemAdaptor extends ArrayAdapter<Product> {

    private Context context;
    public List<Product> products;
    View.OnClickListener listener;
    private SparseBooleanArray mSelectedItemsIds;
    private LayoutInflater inflater;

    public ProductItemAdaptor(Context context, int resourceId,List<Product> products) {
        super(context,resourceId,products);//, View.OnClickListener listener
        mSelectedItemsIds = new SparseBooleanArray();
        this.context = context;
        this.products = products;
        this.inflater = LayoutInflater.from(context);
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

        Product produit = products.get(position);
        viewHolder.menu_name.setText(produit.getName());
        viewHolder.menu_description.setText(produit.getDescription());
        try {
            image = BitmapUtils.loadBitmap(produit.getPicture());
        }catch (Exception conn){
            //image = g(R.drawable.images);
        }
        viewHolder.menu_image.setImageBitmap(image);
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

                Toast.makeText(context, "Le produit a été ajouté Halala.", Toast.LENGTH_LONG).show();

            }
        });


        return v;
    }


    public void toggleSelection(int position){
        selectView(position, !mSelectedItemsIds.get(position));
    }

    public void removeSelection(){
        mSelectedItemsIds = new SparseBooleanArray();
        notifyDataSetChanged();
    }
    private void selectView(int position, boolean b) {
        if (b){
            mSelectedItemsIds.put(position,b);
        }else{
            mSelectedItemsIds.delete(position);
        }
        notifyDataSetChanged();
    }

    public SparseBooleanArray getSelectedItemIds(){
        return mSelectedItemsIds;
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
