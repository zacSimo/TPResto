package unice.mbds.org.tpresto;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

import unice.mbds.org.tpresto.database.ProduitdbHelper;
import unice.mbds.org.tpresto.model.Commande;
import unice.mbds.org.tpresto.model.CommandeWS;
import unice.mbds.org.tpresto.model.CommandeWSItemsAdaptor;
import unice.mbds.org.tpresto.model.Product;

public class GetCommandeWSActivity extends AppCompatActivity {
    protected List<CommandeWS> listCmdWS = new ArrayList<>();
    protected List<Product> products = new ArrayList<Product>();

    protected ProduitdbHelper produitDbHelper = new ProduitdbHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_commande_ws);

        new MyTaskReceiveProduct().execute();

    }

    class MyTaskReceiveProduct extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... voids) {
            StringBuilder builder = new StringBuilder();
            String url = "http://92.243.14.22:1337/menu/";
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
                    Log.e(GetCommandeWSActivity.class.toString(), "Failed to download JSONObject");
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
            final ListView lst = (ListView)findViewById(R.id.listViewCommandeWS);
            CommandeWS cmdF;
            try {
                JSONArray array = new JSONArray(theResponse);
                for(int i = 0; i<array.length(); i++){
                    try{
                        JSONObject ob = array.getJSONObject(i);
                        JSONArray jsonArray;
                        cmdF = new CommandeWS();
                        if (ob.has("price"))  cmdF.setPrice(ob.getString("price"));
                        if (ob.has("discount")) cmdF.setDiscount(ob.getString("discount"));
                        if (ob.has("server")){
                            JSONObject job = ob.getJSONObject("server");
                            if(job.has("id")) cmdF.getServer().setId(job.getString("id"));
                        }
                        if (ob.has("cooker")) {
                            JSONObject job = ob.getJSONObject("cooker");
                            if(job.has("id")) cmdF.getCooker().setId(job.getString("id"));
                        }
                        if (ob.has("items")){
                            jsonArray = ob.getJSONArray("items");
                            for (int k=0; k < jsonArray.length(); k++) {
                                JSONObject object = jsonArray.getJSONObject(k);
                                if (object.has("id")){
                                 cmdF.getItems().add(k, new Commande(object.getString("id")));
                                }
                            }
                        }
                        listCmdWS.add(cmdF);
                        //String result = "Price : "+cmdF.getPrice()+"\ndiscount : "+cmdF.getDiscount();

                    }catch (Exception e){
                        e.printStackTrace();
                    }


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            CommandeWSItemsAdaptor adaptor = new CommandeWSItemsAdaptor(GetCommandeWSActivity.this,listCmdWS);
            lst.setAdapter(adaptor);
        }

        ProgressDialog progressDialog = null;

        public void showProgressDialog(boolean isVisible) {
            if (isVisible) {
                if (progressDialog == null) {
                    progressDialog = new ProgressDialog(GetCommandeWSActivity.this);
                    progressDialog.setMessage(GetCommandeWSActivity.this.getResources().getString(R.string.please_wait));
                    progressDialog.setCancelable(false);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {progressDialog = null;
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
