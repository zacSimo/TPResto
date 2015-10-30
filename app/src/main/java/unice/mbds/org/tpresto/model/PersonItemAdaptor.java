package unice.mbds.org.tpresto.model;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import unice.mbds.org.tpresto.R;

/**
 * Created by Zac on 30/10/2015.
 */
public class PersonItemAdaptor extends BaseAdapter {
    private Context context;
    public List<Person> person_list;

    public PersonItemAdaptor(Context context, List<Person> person) {
        this.context = context;
        this.person_list = person_list;
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
        //viewHolder.supression.setImageResource("supression");
        String connexion ="";
        if(person.isStatus()) connexion = "connecté";
        else connexion="Non connecté";
        viewHolder.connexion.setText("connexion");
        viewHolder.button.setText("button");
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