package unice.mbds.org.tpresto.model;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

import unice.mbds.org.tpresto.R;

/**
 * Created by Zac on 30/10/2015.
 */
public class PersonItemAdaptor extends BaseAdapter {
    private Context context;
    public List<Person> person_list;
    View.OnClickListener listener;

    public PersonItemAdaptor(Context context, List<Person> persons, View.OnClickListener listener) {
        this.context = context;
        this.person_list = persons;
        this.listener =listener;
    }


    @Override
    public int getCount() {
        return person_list.size();
    }

    @Override
    public Object getItem(int arg0) {
        return person_list.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        View v = convertView;

        PersonViewHolder viewHolder = null;
        if(v==null){
            v = View.inflate(context, R.layout.layout_list_users, null);
            viewHolder = new PersonViewHolder();
            viewHolder.nom_prenom= (TextView)v.findViewById(R.id.NomPrenom);
            viewHolder.supression= (ImageView)v.findViewById(R.id.supression);
            viewHolder.connexion= (TextView)v.findViewById(R.id.connexion);
            viewHolder.button= (TextView)v.findViewById(R.id.button);
            v.setTag(viewHolder);
        }
        else{
            viewHolder = (PersonViewHolder) v.getTag();
        }
        Person person = person_list.get(position);
        String prenomNom = person.getPrenom()+" "+person.getNom();
        viewHolder.nom_prenom.setText(prenomNom);

        String connexion ="";

        if(person.isStatus()) {
            connexion = "connecté";
            viewHolder.button.setTextColor(Color.GREEN);
        }
        else {
            connexion="Non connecté";
            viewHolder.button.setTextColor(Color.RED);
        }
        viewHolder.connexion.setText(connexion);
        viewHolder.button.setText("button");
        viewHolder.supression.setTag(person.getId());

        //logique de suppression
        viewHolder.supression.setOnClickListener( listener);
        return v;
    }


    class PersonViewHolder{
        TextView nom_prenom;
        ImageView supression;
        TextView button;
        TextView connexion;


        public TextView getNom_prenom() {
            return nom_prenom;
        }

        public void setNom_prenom(TextView nom_prenom) {
            this.nom_prenom = nom_prenom;
        }

        public ImageView getSupression() {
            return supression;
        }

        public void setSupression(ImageView supression) {
            this.supression = supression;
        }

        public TextView getButton() {
            return button;
        }

        public void setButton(TextView button) {
            this.button = button;
        }

        public TextView getConnexion() {
            return connexion;
        }

        public void setConnexion(TextView connexion) {
            this.connexion = connexion;
        }

    }

}