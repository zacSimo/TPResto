package unice.mbds.org.tpresto;

import android.app.AlertDialog;
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
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import unice.mbds.org.tpresto.database.OrderDbHelper;
import unice.mbds.org.tpresto.model.CommandeWS;
import unice.mbds.org.tpresto.model.CommandeItemAdaptor;
import unice.mbds.org.tpresto.model.Order;

import static java.lang.Integer.parseInt;

public class ElementsCommandeActivity extends AppCompatActivity  {
    OrderDbHelper orderDbHelper = new OrderDbHelper(this);
    List<Order> orders = new ArrayList<Order>();
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elements_commande);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        Button annuler = (Button) findViewById(R.id.annuler_commande);
        Button envoyer = (Button) findViewById(R.id.envoyer_commande);

        listView = (ListView)findViewById(R.id.listViewCommandes);
        orders = orderDbHelper.getAllOrders();

        final CommandeItemAdaptor adapter = new CommandeItemAdaptor(ElementsCommandeActivity.this,orders);
        listView.setAdapter(adapter);
        annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder adb = new AlertDialog.Builder(ElementsCommandeActivity.this);
                adb.setTitle("Annuler?");
                adb.setMessage("Etes-vous sure de vouloir annuler la commande? ");
                adb.setNegativeButton("Cancel", null);
                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        deleteDatabase(orderDbHelper.getDatabaseName());
                        Intent intent = new Intent(ElementsCommandeActivity.this, ListeProductActivity.class);
                        startActivity(intent);
                    }
                });
                adb.show();
            }
        });

        envoyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder adb = new AlertDialog.Builder(ElementsCommandeActivity.this);
                adb.setTitle("Envoyer?");
                adb.setMessage("Etes-vous sure de vouloir envoyer la commande? ");
                adb.setNegativeButton("Cancel", null);
                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        new RegisterCommandeTask().execute();
                    }
                });
                adb.show();
            }
        });
    }

    class RegisterCommandeTask extends AsyncTask<String, String, String> {
        protected Exception exception = null;
        protected String ResultString = "";

        @Override
        protected String doInBackground(String... voids) {
            Random r = new Random();
            HashMap<String,Integer> itemsMap = new HashMap<>();
            String url = "http://92.243.14.22:1337/menu/";
            HttpClient client = null;
            try {
                client = new DefaultHttpClient();
                HttpPost post = new HttpPost(url);
                // add header
                post.setHeader("Content-Type", "application/json");
                CommandeWS cmndF = new CommandeWS();
                JSONObject obj = new JSONObject();
                Integer priceTot = 0, discountTot = 0;
                JSONArray jsonArr = new JSONArray();
                JSONObject cmdObj;
                int it = 0;
                for (Order order:orders){
                    for(int i=0;i<parseInt(order.getQuantite());i++){
                        cmdObj = new JSONObject();
                        priceTot += parseInt(order.getPrice());
                        discountTot += parseInt(order.getDiscount());
                        cmdObj.put("id",order.getId());
                        System.out.print("\nDEBUG ID : "+order.getId() );
                        jsonArr.put(it,cmdObj);
                        it++;
                    }
                }
                discountTot = (discountTot/orders.size());
                obj.put("price", priceTot.toString());
                obj.put("discount", discountTot);
                JSONObject jsonAdd = new JSONObject();
                jsonAdd.put("id",LoginActivity.person.getId());
                obj.put("server", jsonAdd);
                jsonAdd = new JSONObject();
                jsonAdd.put("id",r.nextInt(999999));
                obj.put("cooker", jsonAdd);
                obj.put("items", jsonArr);

                StringEntity entity = new StringEntity(obj.toString());
                post.setEntity(entity);
                HttpResponse response = client.execute(post);
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(response.getEntity().getContent()));

                StringBuffer result = new StringBuffer();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                Log.d("doInBackground(Resp)", result.toString());

                ResultString = result.toString();
                System.out.println(result.toString());
                return ResultString;
            } catch (Exception e) {

            }
            return ResultString;
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
            Toast.makeText(ElementsCommandeActivity.this, R.string.envoi_commande_ok, Toast.LENGTH_LONG).show();
            deleteDatabase(orderDbHelper.getDatabaseName());
            startActivity(new Intent(ElementsCommandeActivity.this, ListeProductActivity.class));
        }

        ProgressDialog progressDialog = null;

        public void showProgressDialog(boolean isVisible) {
            if (isVisible) {
                if (progressDialog == null) {
                    progressDialog = new ProgressDialog(ElementsCommandeActivity.this);
                    progressDialog.setMessage(ElementsCommandeActivity.this.getResources().getString(R.string.please_wait));
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
