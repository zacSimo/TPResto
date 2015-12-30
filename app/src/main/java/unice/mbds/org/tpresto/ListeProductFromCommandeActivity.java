package unice.mbds.org.tpresto;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import unice.mbds.org.tpresto.database.OrderDbHelper;
import unice.mbds.org.tpresto.database.ProduitdbHelper;
import unice.mbds.org.tpresto.model.ProductFromCommandeItemAdaptor;
import unice.mbds.org.tpresto.model.Product;

public class ListeProductFromCommandeActivity extends AppCompatActivity {

    protected OrderDbHelper orderDbHelper = new OrderDbHelper(this);
    protected ProduitdbHelper produitdbHelper = new ProduitdbHelper(this);
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_product_from_commande);
        List<Product> products = produitdbHelper.getAllProducts();
        listView = (ListView) findViewById(R.id.listViewDessert);
        Bundle extra = getIntent().getBundleExtra("extra");
        String typeProduit = (String) extra.getSerializable("typeProduit");
        TextView typeProd = (TextView) findViewById(R.id.typeProd);
        typeProd.setText("    Liste des "+typeProduit+"s    ");
        final ProductFromCommandeItemAdaptor adaptor = new ProductFromCommandeItemAdaptor(ListeProductFromCommandeActivity.this,products,typeProduit);
        listView.setAdapter(adaptor);
        Button retour = (Button) findViewById(R.id.retour_listViewCommande);
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListeProductFromCommandeActivity.this,ElementsCommandeActivity.class);
                startActivity(intent);
            }
        });

    }
}
