package unice.mbds.org.tpresto;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Bundle bundle = getIntent().getExtras();
        String nom = bundle.getString("Nom");
        String prenom = bundle.getString("Prenom");
        String welcome = "BIENVENUE";
        nom = welcome +"\n"+nom +"   "+prenom;
        TextView textView = new TextView(this);

        textView.setTextSize(70);
        textView.setText(nom);
        textView.setTextColor(0xFF19BCD5);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textView.setTop(80);
        setContentView(textView);


    }
}
