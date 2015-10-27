package unice.mbds.org.tpresto;

import android.app.AlertDialog;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import unice.mbds.org.tpresto.model.Person;

import static java.lang.Character.isDigit;

public class RegisterActivity extends AppCompatActivity {
    private RadioGroup radioGroup;
    private RadioButton rbm, rbf;
    private Button seConnecter;
    private EditText etNom;
    private EditText etPreNom;
    private EditText tel;
    private EditText email;
    private EditText usr;
    private EditText pwd1;
    private EditText pwd2;
    protected Person person;

    public static boolean checkTelephoneNumber(View v, String tel) {
        if (tel.length() == 10) {
            for (int i = 0; i < tel.length(); i++) {
                if (!isDigit(tel.charAt(i))) {
                    alertDialog(v, "Telephone Number", "Numero à 10 chiffres sans caractères");
                    return false;
                }
            }
            return true;
        } else {
            alertDialog(v, "Telephone Number", "Numero à 10 chiffres sans caractères");
            return false;
        }

    }

    public static boolean isValidEmailAddress(String emailAddress) {
        String emailRegEx;
        Pattern pattern;
        // Regex for a valid email address
        emailRegEx = "^[A-Za-z0-9._%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,4}$";
        // Compare the regex with the email address
        pattern = Pattern.compile(emailRegEx);
        Matcher matcher = pattern.matcher(emailAddress);
        if (!matcher.find()) {
            return false;
        }
        return true;
    }

    public static void alertDialog(View v, String titre, String contenu) {
        AlertDialog show = new AlertDialog.Builder(v.getContext())
                .setTitle(titre)
                .setMessage(contenu)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
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

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        etNom = (EditText) findViewById(R.id.editText);
        etPreNom = (EditText) findViewById(R.id.editText2);
        tel = (EditText) findViewById(R.id.editText3);
        email = (EditText) findViewById(R.id.editText4);
        pwd1 = (EditText) findViewById(R.id.editText5);
        pwd2 = (EditText) findViewById(R.id.editText6);
        seConnecter = (Button) findViewById(R.id.button4);
        rbf = (RadioButton) findViewById(R.id.radioButton2);
        rbm = (RadioButton) findViewById(R.id.radioButton);

        seConnecter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(v.getContext(), LoginActivity.class);
                if ((etNom.getText().toString() != "") &&
                        (etPreNom.getText().toString() != "") &&
                        (tel.getText().toString() != "") &&
                        (email.getText().toString() != "") &&
                        (pwd1.getText().toString() != "") &&
                        (pwd2.getText().toString() != "")) {
                    String sexe = "";
                    if(rbm.isChecked()) sexe = "M";
                    else if(rbf.isChecked()) sexe = "F";
                    person = new Person(etNom.getText().toString(),etPreNom.getText().toString(),sexe,tel.getText().toString(),
                            email.getText().toString(),pwd1.getText().toString(),"Zac&Soufiane" );
                    new MyTask().execute();
                    /*intent.putExtra("Nom",etNom.getText().toString());
                    intent.putExtra("Prenom",etPreNom.getText().toString());
                    intent.putExtra("Telephone", tel.getText().toString());
                    intent.putExtra("email", email.getText().toString());
                    intent.putExtra("Password", pwd1.getText().toString());
                    if(checkTelephoneNumber(v, tel.getText().toString()))
                       if(!isValidEmailAddress(email.getText().toString()))
                           alertDialog(v,"Email Address","Adresse Email Non valide");
                       else if(!pwd1.getText().toString().equals(pwd2.getText().toString()))
                                 alertDialog(v, "Password", "Password différentes");
                            else  if(rbf.isChecked()){
                                            intent.putExtra("Sexe","Feminin");
//                                            if(v.getId()==R.id.register){
//                                               //Valider votre formulaire
//                                               new MyTask().execute();
//                                            }
                                               startActivity(intent);
                                    }
                                  else  if(rbm.isChecked()) {
                                            intent.putExtra("Sexe","Masculin");
//                                               if(v.getId()==R.id.register){
//                                                   //Valider votre formulaire
//                                                   new MyTask().execute();
//                                               }
                                            startActivity(intent);
                                        }
                                       else alertDialog(v, "Sexe","Choisir le sexe");
                    */

                } else
                    alertDialog(v, "Champs vides", "Veuillez vérifier les champs vides");
            }
        });
    }

    class MyTask extends AsyncTask<String, String, String> {
        protected Exception exception = null;
        protected String ResultString = "";

        @Override
        protected String doInBackground(String... voids) {
            String url = "http://92.243.14.22/person/";
            HttpClient client = null;
            try {
                client = new DefaultHttpClient();
                HttpPost post = new HttpPost(url);

                // add header

                post.setHeader("Content-Type", "application/json");
                JSONObject obj = new JSONObject();
                obj.put("prenom", person.getPrenom());
                obj.put("nom", person.getNom());
                obj.put("sexe", person.getSexe());
                obj.put("email", person.getEmail());
                obj.put("tel", person.getTelephone());
                obj.put("password", person.getPassword());
                obj.put("createdBy", person.getCreatedBy());

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

    }
}


