package ahmed.ayachi.caller_app;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class Accueil extends AppCompatActivity {
    public static ArrayList<Profil> data=new ArrayList<Profil>();
    //declaration
    private Button btn_ajout,btn_aff ,btn_decon;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        //Reclamation des composantes
        btn_ajout=findViewById(R.id.btn_ajout);
        btn_aff=findViewById(R.id.btn_affich);
        btn_decon=findViewById(R.id.btn_decon);

        //event

        btn_ajout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Accueil.this ,Ajouter.class);
                startActivity(i);
            }
        });

        btn_aff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(
                        new Intent(Accueil.this,Afficher.class)
                );
            }
        });

        btn_decon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Clear the list of profiles
                data.clear();

                // Update the state of the checkbox in SharedPreferences
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Accueil.this);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("isChecked", false); // Set it to false when disconnected
                editor.apply();

                // Navigate to the login screen (MainActivity)
                Intent intent = new Intent(Accueil.this, MainActivity.class);
                // Add flags to clear the back stack and start the new activity as a new task
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                // Finish the current activity to prevent the user from navigating back
                finish();
            }
        });

    }
}