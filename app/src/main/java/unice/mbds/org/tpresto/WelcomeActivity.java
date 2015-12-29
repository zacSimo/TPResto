package unice.mbds.org.tpresto;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import unice.mbds.org.tpresto.database.ProduitdbHelper;

public class WelcomeActivity extends AppCompatActivity {
    ProduitdbHelper produitdbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

//        this.produitdbHelper = new ProduitdbHelper(this);
//        this.deleteDatabase(produitdbHelper.getDatabaseName());
        ImageButton imageButton = (ImageButton) findViewById(R.id.imageButtonProducts);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ListeProductActivity.class);
                startActivity(intent);
//                startActivity(new Intent(v.getContext(),ElementsCommandeActivity.class));

            }
        });
        Button button3 = (Button) findViewById(R.id.buttonListUsers);
        button3.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           Intent intent = new Intent(v.getContext(), ListeUserActivity.class);
                                           startActivity(intent);
                                       }
                                   }
        );
        Button button4 = (Button) findViewById(R.id.buttonMenuTpresto);
        button4.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           Intent intent = new Intent(v.getContext(), ListeProductActivity.class);
                                           startActivity(intent);
                                       }
                                   }
        );
        Button listeCommades = (Button) findViewById(R.id.buttonListCommandes);
        listeCommades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), GetCommandeWSActivity.class);
                startActivity(intent);
            }
        });


    }
}
