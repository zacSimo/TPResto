package unice.mbds.org.tpresto;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Field;

import unice.mbds.org.tpresto.model.Product;

public class DetailProduitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_produit);
        TextView type = (TextView) findViewById(R.id.menu_dessert_detail);
        TextView description = (TextView) findViewById(R.id.menu_description_detail);
        ImageView imageView = (ImageView) findViewById(R.id.imageMenu_detail);
        TextView price = (TextView) findViewById(R.id.menu_price_detail);
        TextView discount = (TextView) findViewById(R.id.menu_discount_detail);
        TextView calories =( TextView) findViewById(R.id.menu_calories_detail);
        TextView name =( TextView) findViewById(R.id.menu_name_detail);
        TextView createdAt =( TextView) findViewById(R.id.date_creation_produit);
        TextView dateMaj =( TextView) findViewById(R.id.date_maj_produit);
        Button retour = (Button) findViewById(R.id.retour_detail);

        Bundle extra = getIntent().getBundleExtra("extra");
        Product product = (Product) extra.getSerializable("detailProduit");
        type.setText(product.getType());
        description.setText(product.getDescription());

        final Field[] declaredFields = (R.drawable.class).getDeclaredFields();
        for (Field field : declaredFields) {
            final int drawableId;
            try {
                if(field.getName().equals(product.getPicture())){
                    drawableId = field.getInt(R.drawable.class);
                    imageView.setImageDrawable(getDrawable(drawableId));
                    break;
                }

            } catch (Exception e) {
                continue;
            }

        }
        price.setText("Prix : "+product.getPrice());
        discount.setText("Discount : "+product.getDiscount()+"%");
        calories.setText("Calorie : "+product.getCalories());
        name.setText(product.getName());
        createdAt.setText("Date Creation : "+product.getCreatedAt());
        dateMaj.setText("Date M.A.J : "+product.getUpdatedAt());

        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
