package unice.mbds.org.tpresto;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Character.isDigit;

public class RegisterActivity extends AppCompatActivity {
    private RadioGroup radioGroup;
    private RadioButton rbm,rbf;
    private Button seConnecter;
    private EditText etNom;
    private EditText etPreNom;
    private EditText tel;
    private EditText email;
    private EditText usr;
    private EditText pwd1;
    private EditText pwd2;


    public static boolean checkTelephoneNumber(View v,String tel){
        if(tel.length()==10) {
            for (int i = 0; i < tel.length(); i++) {
                if (!isDigit(tel.charAt(i))) {
                    alertDialog(v, "Telephone Number", "Numero à 10 chiffres sans caractères");
                    return false;
                }
            }
            return true;
        }
        else {
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

    public static void alertDialog(View v,String titre, String contenu){
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
        rbf = (RadioButton)findViewById(R.id.radioButton2);
        rbm = (RadioButton) findViewById(R.id.radioButton);

        seConnecter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(v.getContext(),LoginActivity.class);
                if((etNom.getText().toString()!= "") &&
                        (etPreNom.getText().toString()!= "") &&
                        (tel.getText().toString()!= "") &&
                        (email.getText().toString()!= "") &&
                        (pwd1.getText().toString()!= "") &&
                        (pwd2.getText().toString()!= "")){

                    intent.putExtra("Nom",etNom.getText().toString());
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

                }
                else
                alertDialog(v, "Champs vides","Veuillez vérifier les champs vides");
            }
        });
    };
    }

//    class MyTask extends AsyncTask<Void,Void,Void> {
//        protected Exception exception = null;
//        protected String ResultString = "";
//        @Override
//        protected String doInBackground(Void... voids) {
//            URL url;
//            HttpURLConnection urlConnection = null;
//            try {
//                url = new URL(" http://92.243.14.22/person/");
//                urlConnection = (HttpURLConnection) url.openConnection();
//                urlConnection.setRequestMethod("POST");
//                // InputStream in = new BufferedInputStream(urlConnection.getInputStream());
//                // String stream_url = IOUtils.toString(in, "UTF-8");
//                //urlConnection.disconnect();
//
//                //return stream_url;
//
//                // HttpClient client = new DefaultHttpClient();
//                // HttpPost post = new HttpPost(url);
//
//                // add header
//                urlConnection.setRequestProperty("Content-Type", "application/json");
//                // post.setHeader("Content-Type", "application/json");
//                JSONObject obj = new JSONObject();
//                obj.put("prenom", "Amosse");
//                obj.put("nom", "Edouard");
//                obj.put("sexe", "M");
//                obj.put("email", "eamosse@gmail.com");
//                obj.put("tel", "0123456789");
//                obj.put("password", "1234");
//
//                urlConnection.connect();
//                // create data output stream
//                DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
//                // write to the output stream from the string
//                wr.writeBytes(obj.toString());
//                wr.flush();
//                wr.close();
//
//                //HttpResponse response = client.execute(post);
//                InputStream input = urlConnection.getInputStream();
//                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
//                StringBuilder result = new StringBuilder();
//                String line;
//
//                while ((line = reader.readLine()) != null) {
//                    result.append(line);
//                }
//                Log.d("doInBackground(Resp)", result.toString());
//                // response = new JSONObject(result.toString());
//
//                ResultString = result.toString();
//                System.out.println(result.toString());
//                return ResultString;
//            } catch (Exception e) {
//                urlConnection.disconnect();
//            }
//            return ResultString;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//            showProgressDialog(true);
//        }
//
//        @Override
//        protected void onPostExecute(String theResponse) {
//            super.onPostExecute(aVoid);
//            super.onPostExecute(aVoid);
//            showProgressDialog(false);
//            Toast.makeText(this, R.string.inscription_ok, Toast.LENGTH_LONG).show();
//        }
//
//        ProgressDialog progressDialog = null;
//        public void showProgressDialog(boolean isVisible) {
//            if (isVisible) {
//                if(progressDialog==null) {
//                    progressDialog = new ProgressDialog(this);
//                    progressDialog.setMessage(this.getResources().getString(R.string.please_wait));
//                    progressDialog.setCancelable(false);
//                    progressDialog.setIndeterminate(true);
//                    progressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//                        @Override
//                        public void onDismiss(DialogInterface dialog) {
//                            progressDialog = null;
//                        }
//                    });
//                    progressDialog.show();
//                }
//            }
//            else {
//                if(progressDialog!=null) {
//                    progressDialog.dismiss();
//                }
//            }
//        }

//    }


