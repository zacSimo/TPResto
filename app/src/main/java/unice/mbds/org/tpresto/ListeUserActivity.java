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
import android.widget.ListView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
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

import unice.mbds.org.tpresto.model.Person;
import unice.mbds.org.tpresto.model.PersonItemAdaptor;

public class ListeUserActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_user);


        new MyTaskReceive().execute();

    }

    @Override
    public void onClick(View v) {
        new DeleteAccountTask().execute(v.getTag().toString());
    }

    class MyTaskReceive extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... voids) {
            StringBuilder builder = new StringBuilder();
            String url = "http://92.243.14.22:1337/person/";
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
            ListView lst = (ListView)findViewById(R.id.listView);
            List<Person> persons = new ArrayList<>();
            try {
                JSONArray array = new JSONArray(theResponse);
                for(int i = 0; i<array.length(); i++){
                    try{
                    JSONObject ob = array.getJSONObject(i);
                    Person p = new Person();
                        if (ob.has("sexe"))  p.setSexe(ob.getString("sexe"));
                        if (ob.has("email")) p.setEmail(ob.getString("email"));
                        if (ob.has("password")) p.setPassword(ob.getString("password"));
                        if (ob.has("createdBy")) p.setPassword(ob.getString("createdBy"));
                        if (ob.has("prenom")) p.setPrenom(ob.getString("prenom"));
                        if (ob.has("nom")) p.setNom(ob.getString("nom"));
                        if (ob.has("telephone")) p.setTelephone(ob.getString("telephone"));
                        if (ob.has("connected")) p.setStatus(ob.getBoolean("connected"));
                        if (ob.has("id")) p.setId(ob.getString("id"));
                    //if (p.getNom().contains("sim"))
                      persons.add(p);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            PersonItemAdaptor adapter = new PersonItemAdaptor(ListeUserActivity.this, persons, new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    AlertDialog.Builder adb=new AlertDialog.Builder(ListeUserActivity.this);
                    adb.setTitle("Supprimer?");
                    adb.setMessage("Etes-vous sure de vouloir supprimer? ");
                    adb.setNegativeButton("Cancel", null);
                    adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            new DeleteAccountTask().execute(v.getTag().toString());
                            Intent intent = getIntent();
                            finish(); //update de la page
                            startActivity(intent);
                        }});
                    adb.show();

               }
            });
                lst.setAdapter(adapter);
        }

        ProgressDialog progressDialog = null;

        public void showProgressDialog(boolean isVisible) {
            if (isVisible) {
                if (progressDialog == null) {
                    progressDialog = new ProgressDialog(ListeUserActivity.this);
                    progressDialog.setMessage(ListeUserActivity.this.getResources().getString(R.string.please_wait));
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


    public class DeleteAccountTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String url = "http://92.243.14.22:1337/person/"+params[0];
            StringBuilder builder = new StringBuilder();
            HttpClient client = new DefaultHttpClient();
            HttpDelete httpDelete = new HttpDelete(url);
            try {
                HttpResponse response = client.execute(httpDelete);
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
    }
}


