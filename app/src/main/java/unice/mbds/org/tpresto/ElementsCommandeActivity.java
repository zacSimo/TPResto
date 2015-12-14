package unice.mbds.org.tpresto;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

import unice.mbds.org.tpresto.model.Product;

public class ElementsCommandeActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_list_commandes);
        ListView lst = (ListView)findViewById(R.id.listViewCommandes);
        Intent intent = getIntent();
        ArrayList<Product> list =
                (ArrayList<Product>)intent.getSerializableExtra("produits");
        System.out.print("\nLISTE PRODUITS RECUS : "+list.toString());
//        CommandeItemAdaptor adaptor = new CommandeItemAdaptor(this,list);
//        lst.setAdapter(adaptor);

    }
}
