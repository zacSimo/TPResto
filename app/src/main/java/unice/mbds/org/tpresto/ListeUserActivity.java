package unice.mbds.org.tpresto;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import unice.mbds.org.tpresto.model.Person;

public class ListeUserActivity extends AppCompatActivity {
    private Person person;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


    }

    class MyTaskReceive extends AsyncTask<String, String, String> {
        protected Exception exception = null;
        protected String ResultString = "";

        @Override
        protected String doInBackground(String... voids) {
            String url = "http://92.243.14.22/person/";
            HttpClient client = null;
            try {
                String readJSON = getJSON(url);
                try{
                    JSONObject jsonObject = new JSONObject(readJSON);
                    Log.i(ListeUserActivity.class.getName(), jsonObject.getString("date"));
                } catch(Exception e){e.printStackTrace();}
                finally{System.out.println("Success");}

                get.setHeader("Content-Type", "application/json");
                JSONObject obj = new JSONObject();
                obj.put("prenom", person.getPrenom());
                obj.put("nom", person.getNom());
                obj.put("sexe", person.getSexe());
                obj.put("email", person.getEmail());
                obj.put("tel", person.getTelephone());
                obj.put("password", person.getPassword());
                obj.put("createdBy", person.getCreatedBy());

                StringEntity entity = new StringEntity(obj.toString());


                get.getEntity(entity);
                HttpResponse response = client.execute(post);
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(response.getEntity().getContent()));

                StringBuffer result = new StringBuffer();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                Log.d("doInBackground(Resp)", result.toString());
                // response = new JSONObject(result.toString());

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
            Toast.makeText(RegisterActivity.this, R.string.inscription_ok, Toast.LENGTH_LONG).show();
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        }

        ProgressDialog progressDialog = null;

        public void showProgressDialog(boolean isVisible) {
            if (isVisible) {
                if (progressDialog == null) {
                    progressDialog = new ProgressDialog(RegisterActivity.this);
                    progressDialog.setMessage(RegisterActivity.this.getResources().getString(R.string.please_wait));
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

        public String getJSON(String address){
            StringBuilder builder = new StringBuilder();
            HttpClient client = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(address);
            try{
                HttpResponse response = client.execute(httpGet);
                StatusLine statusLine = response.getStatusLine();
                int statusCode = statusLine.getStatusCode();
                if(statusCode == 200){
                    HttpEntity entity = response.getEntity();
                    InputStream content = entity.getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                    String line;
                    while((line = reader.readLine()) != null){
                        builder.append(line);
                    }
                } else {
                    Log.e(ListeUserActivity.class.toString(),"Failed on JSON object");
                }
            }catch(ClientProtocolException e){
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            }
            return builder.toString();
        }

    }

}
