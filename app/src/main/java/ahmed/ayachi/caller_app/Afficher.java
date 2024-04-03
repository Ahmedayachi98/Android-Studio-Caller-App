package ahmed.ayachi.caller_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class Afficher extends AppCompatActivity {


    ArrayList <Profil> data = new ArrayList<Profil>();
    EditText rech ;
    RecyclerView rv_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afficher);

        rv_list = findViewById(R.id.rv_list);
        rech =findViewById(R.id.rechercher);

        ProfilManager manager = new ProfilManager(Afficher.this);
        manager.ouvrir();
        data = manager.getAllProfil();
        manager.fermer();

        //Affichage des donnes
        //MonAdapter ad = new MonAdapter(Afficher.this ,data) ;  Pour list View*****

        MyRecyclerProfilAdapter ad = new MyRecyclerProfilAdapter(Afficher.this,data);
        rv_list.setAdapter(ad);


        /*LinearLayoutManager layoutManager =new LinearLayoutManager(Afficher.this ,
                                                            LinearLayoutManager.VERTICAL,
                                                                true);
                rv_list.setLayoutManager(layoutManager);*/

        GridLayoutManager layoutManager =new GridLayoutManager(Afficher.this,1
                                                            ,GridLayoutManager.VERTICAL,false);

        rv_list.setLayoutManager(layoutManager);

        // bare de recherche
        rech.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String searchText = charSequence.toString().toLowerCase().trim();
                ad.filter(searchText); // Filtrez les données en fonction du texte de recherche
            }


            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}