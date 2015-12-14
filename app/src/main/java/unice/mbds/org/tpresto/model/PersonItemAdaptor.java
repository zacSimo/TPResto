package unice.mbds.org.tpresto.model;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import static unice.mbds.org.tpresto.R.drawable;
import static unice.mbds.org.tpresto.R.id;
import static unice.mbds.org.tpresto.R.layout;

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
            v = View.inflate(context, layout.layout_list_users, null);
            viewHolder = new PersonViewHolder();
            viewHolder.nom_prenom= (TextView)v.findViewById(id.NomPrenom);
            viewHolder.supression= (ImageView)v.findViewById(id.supression);
            viewHolder.connexion= (TextView)v.findViewById(id.connexion);
            viewHolder.button= (TextView)v.findViewById(id.button);
            v.setTag(viewHolder);// un Tag à travers lequel on recupere la reference ds 1 autre view
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
            Drawable  image = context.getResources().getDrawable(drawable.img_rond_green);
            image.setBounds(0,0,60,60);
            viewHolder.button.setCompoundDrawables(null,image,null,null);
            //viewHolder.button.setTextColor(Color.GREEN);
        }
        else {
            connexion="Non connecté";
            Drawable  image = context.getResources().getDrawable(drawable.img_rond_red);
            image.setBounds(0,0,60,60);
            viewHolder.button.setCompoundDrawables(null, image, null, null);
           // viewHolder.button.setTextColor(Color.RED);
        }
        viewHolder.connexion.setText(connexion);
        viewHolder.button.setText("Buzzer");
        viewHolder.supression.setTag(person.getId());

        //logique de suppression sur le webservice mais faudra rafraichir la liste
        viewHolder.supression.setOnClickListener( listener);
        return v;
    }


    class PersonViewHolder{
        TextView nom_prenom;
        ImageView supression;
        TextView button;
        TextView connexion;


    }

}