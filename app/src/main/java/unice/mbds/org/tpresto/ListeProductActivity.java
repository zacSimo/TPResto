package unice.mbds.org.tpresto;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import unice.mbds.org.tpresto.database.ProduitdbHelper;
import unice.mbds.org.tpresto.model.Product;
import unice.mbds.org.tpresto.model.ProductItemAdaptor;

public class ListeProductActivity extends AppCompatActivity {
    protected List<Product> produits = new ArrayList<>();
    protected List<Product> products = new ArrayList<Product>();
    protected ProduitdbHelper produitDbHelper = new ProduitdbHelper(this);

    ListView lst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_product);
        this.produitDbHelper = new ProduitdbHelper(this);
        deleteDatabase(produitDbHelper.getDatabaseName());

        int i =0;
        lst = (ListView)findViewById(R.id.listViewProducts);
        new MyTaskReceiveProduct().execute();
        //produits = produitDbHelper.getAllProducts();

        System.out.print("DEBUG ONCREATE LISTEPRODUIT, SIZE LISTE PRODUIT : "+produits.size());
        final ProductItemAdaptor adapter = new ProductItemAdaptor(ListeProductActivity.this, produits);
        lst.setAdapter(adapter);

        Button menuCommande = (Button) findViewById(R.id.contenu_commande);
        menuCommande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListeProductActivity.this,ElementsCommandeActivity.class);
                startActivity(intent);
            }
        });
    }

    class MyTaskReceiveProduct extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... voids) {
            StringBuilder builder = new StringBuilder();
            String url = "http://92.243.14.22:1337/product/";
            HttpClient client = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);

            try {

                HttpResponse response = client.execute(httpGet);
                StatusLine statusLine = response.getStatusLine();
                int statusCode = statusLine.getStatusCode();
                if (statusCode == 200) {
                    HttpEntity entity = response.getEntity();
                    InputStream content = entity.getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        builder.append(line);
                    }
                } else {
                    Log.e(ListeUserActivity.class.toString(), "Failed to download JSONObject");
                }
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return builder.toString();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            showProgressDialog(true);
        }

        @Override
        protected void onPostExecute(String theResponse) {
            super.onPostExecute(theResponse);
            showProgressDialog(false);
            final ListView lst = (ListView)findViewById(R.id.listViewProducts);
            Random r = new Random();
            int m = 0,k=0,l=0,n=0;
            try {
                JSONArray array = new JSONArray(theResponse);
                for(int j = 0; j<array.length(); j++){
                    try{
                        JSONObject ob = array.getJSONObject(j);
                        Product p = new Product();
                        if (ob.has("name"))  p.setName(ob.getString("name")); //System.out.print("\nDEBUG NAME "+p.getName());
                        if (ob.has("description")) p.setDescription(ob.getString("description")); //System.out.print("\nDEBUG DESC"+p.getDescription());
                        if (ob.has("price")) p.setPrice(ob.getString("price"));//System.out.print("\nDEBUG PRICE "+p.getPrice());
                        if (ob.has("calories")) p.setCalories(ob.getString("calories"));//System.out.print("\nDEBUG CAT "+p.getCalories());
                        if (ob.has("type")) p.setType(ob.getString("type"));//System.out.print("\nDEBUG TYPE "+p.getType());
                        if (ob.has("picture")) p.setPicture(ob.getString("picture"));//System.out.print("\nDEBUG PIC "+p.getPicture());
                        if (ob.has("discount")) p.setDiscount(ob.getString("discount"));//System.out.print("\nDEBUG  "+p.getDiscount());
                        if (ob.has("createdAt")) p.setCreatedAt(ob.getString("createdAt"));//System.out.print("\nDEBUG CREAT "+p.getCreatedAt());
                        if (ob.has("updatedAt")) p.setUpdatedAt(ob.getString("updatedAt"));//System.out.print("\nDEBUG UPDATED "+p.getUpdatedAt());
                        if (ob.has("id")) p.setId(ob.getString("id"));//System.out.print("\nDEBUG ID " + p.getId());

                        //assignation des images pour plat, dessert et entree

                        if (p.getType().contains("Plat")){
                            p.setPicture("plat_"+(m%51));
                            m++;
                        }
                        else if (p.getType().contains("Entrée")){
                            p.setPicture("entree_"+(k%50));
                            k++;
                        }
                        else if (p.getType().contains("Dessert")){
                            p.setPicture("dessert_"+(l%50));
                            l++;
                        }
                        else if (p.getType().contains("Appéritif")) {
                            p.setPicture("apperitif_"+(n%35));
                            n++;
                        }
                        products.add(p);
                        produitDbHelper.insertProduct(p);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ProductItemAdaptor adapter = new ProductItemAdaptor(ListeProductActivity.this, products);
            lst.setAdapter(adapter);
        }

        ProgressDialog progressDialog = null;

        public void showProgressDialog(boolean isVisible) {
            if (isVisible) {
                if (progressDialog == null) {
                    progressDialog = new ProgressDialog(ListeProductActivity.this);
                    progressDialog.setMessage(ListeProductActivity.this.getResources().getString(R.string.please_wait));
                    progressDialog.setCancelable(false);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            progressDialog = null;
                        }
                    });
                    progressDialog.show();
                }
            } else {
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
            }
        }




    }



}