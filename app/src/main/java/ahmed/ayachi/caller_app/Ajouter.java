package ahmed.ayachi.caller_app;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Ajouter extends AppCompatActivity {

    //Declaration
    EditText ednom_ajout,edprenom_ajout,edphone_ajout;
     Button btnsave, btnback;

    ProfilManager manager = new ProfilManager(Ajouter.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter);

        ednom_ajout= findViewById(R.id.ednom_ajout);
        edprenom_ajout= findViewById(R.id.edprenom_ajout);
        edphone_ajout= findViewById(R.id.ednumero_ajout);
        btnsave= findViewById(R.id.btnsave_add);
        btnback = findViewById(R.id.btnback_add);

        //event
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nom= ednom_ajout.getText().toString();
                String prenom =edprenom_ajout.getText().toString();
                String numero =edphone_ajout.getText().toString();


                //Open database
                manager.ouvrir();

                //Call ajout

                long result = manager.ajout(nom,prenom,numero);
                if (result !=1){
                    Toast.makeText(Ajouter.this, "Profile add sucessfully", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(Ajouter.this, "Failed to add profile", Toast.LENGTH_SHORT).show();
                }

                //Close data base
                manager.fermer();

            }
        });

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}