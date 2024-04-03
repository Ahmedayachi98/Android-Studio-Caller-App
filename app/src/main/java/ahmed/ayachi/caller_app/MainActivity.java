package ahmed.ayachi.caller_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //Declaration des composants
    EditText name_auth, pass_auth;
    private Button btn_qte, btn_auth;

    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Recuperation des composantes
        name_auth = findViewById(R.id.name_auth);
        pass_auth = findViewById(R.id.edmp_auth);
        btn_auth = findViewById(R.id.btn_auth);
        btn_qte = findViewById(R.id.btn_qit);
        checkBox = findViewById(R.id.check_box);

        // Récupération de l'état de la case à cocher à partir des préférences partagées
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isChecked = preferences.getBoolean("isChecked", false);
        checkBox.setChecked(isChecked);

        if (isChecked) {
            Intent i = new Intent(MainActivity.this, Accueil.class);
            startActivity(i);
            finish(); // Finish the MainActivity so that it's not added to the back stack
        }

        //ecoutteur d'action
        btn_qte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_auth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nom = name_auth.getText().toString();
                String pass = pass_auth.getText().toString();
                if (nom.equalsIgnoreCase("admin") && pass.equals("0000")) {
                    // Sauvegarder l'état de la case à cocher
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("isChecked", checkBox.isChecked());
                    editor.apply();

                    Intent i = new Intent(MainActivity.this, Accueil.class);
                    startActivity(i);
                } else {
                    Toast.makeText(MainActivity.this, "Valeur non valid", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
