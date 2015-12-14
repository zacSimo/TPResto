package unice.mbds.org.tpresto;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import unice.mbds.org.tpresto.database.ProduitdbHelper;
import unice.mbds.org.tpresto.model.Product;
import unice.mbds.org.tpresto.model.ProductItemAdaptor;

public class ListeProductActivity extends AppCompatActivity {
    protected List<Product> produits = new ArrayList<>();
    protected List<Product> selectedProduct = new ArrayList<>();
    protected List<Product> products = new ArrayList<Product>();
    protected ProduitdbHelper produitDbHelper = new ProduitdbHelper(this);

    ListView lst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_product);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lst = (ListView)findViewById(R.id.listViewProducts);

        produits = produitDbHelper.getAllProducts();
        final ProductItemAdaptor adapter = new ProductItemAdaptor(ListeProductActivity.this, R.layout.layout_list_product,produits);

        lst.setAdapter(adapter);
        lst.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        lst.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                final int checkedCount = lst.getCheckedItemCount();
                mode.setTitle(checkedCount + " Selected");
                adapter.toggleSelection(position);

            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.liste_produits, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_choisi:
                        SparseBooleanArray selected = adapter.getSelectedItemIds();
                        Intent intent = new Intent(ListeProductActivity.this, ElementsCommandeActivity.class);
                        for(int i=0;i<selected.size();i++){
                            if (selected.valueAt(i)){
                                Product selectedItem = adapter.getItem(selected.keyAt(i));
                                intent.putExtra("produits", (Serializable) selectedItem );
                            }
                          }
                        startActivity(intent);
                        mode.finish();
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                    adapter.removeSelection();
            }
        });


    }





}