package ahmed.ayachi.caller_app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyRecyclerProfilAdapter extends RecyclerView.Adapter<MyRecyclerProfilAdapter.MyViewHolder> {

    Context con;
    ArrayList<Profil> data;
    private ArrayList<Profil> originalDataList; // Ajoutez une liste pour stocker les données d'origine non filtrées


    public MyRecyclerProfilAdapter(Context context, ArrayList<Profil> data) {
        this.con = context;
        this.data = data;
        this.originalDataList = data;
    }

    @NonNull
    @Override
    public MyRecyclerProfilAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //Creation d'un viewHolder
        LayoutInflater inf = LayoutInflater.from(con);
        View v = inf.inflate(R.layout.activity_profil, null); //Convertir activity_profile on object v

        return new MyViewHolder(v); //cree constructor

    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerProfilAdapter.MyViewHolder holder, int position) {
        // Affectation des holders
        Profil p = data.get(position);
        holder.tvnom.setText("" + p.nom);
        holder.tvprenom.setText("" + p.prenom);
        holder.tvnumero.setText("" + p.numero);
    }

    @Override
    public int getItemCount() {
        //retourne le nbr total des elements
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        //declaration des holders
        TextView tvnom, tvprenom, tvnumero;
        ImageView imgdelete, imgedit, imgcall;

        public MyViewHolder(@NonNull View v) {
            super(v);

            //Recuperation des sous View /Holders
            tvnom = v.findViewById(R.id.tvnom_profil);
            tvprenom = v.findViewById(R.id.tvprenom_profil);
            tvnumero = v.findViewById(R.id.tvnumero_profil);
            tvnumero.setTextColor(Color.BLUE);

            imgdelete = v.findViewById(R.id.imageViewdelete_profil);
            imgedit = v.findViewById(R.id.imageViewedit_profil);
            imgcall = v.findViewById(R.id.imageViewcall_profil);

            // event
            imgdelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // afficher une boite de dialog
                    AlertDialog.Builder alert = new AlertDialog.Builder(con);
                    alert.setTitle("Confirmation");
                    alert.setMessage("confirmer la suppression?");

                    alert.setPositiveButton("supprimer", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            int indice = getAdapterPosition(); //revoie de position de element selectionés


                            ProfilManager manager = new ProfilManager(con);

                            // supprimer de la base
                            manager.ouvrir();
                            Profil profilToDelete = data.get(indice);
                            int deletedRows = manager.supprimer(profilToDelete.nom);
                            manager.fermer();

                            if (deletedRows > 0) {
                                // Si la suppression a réussi, mettre à jour l'affichage
                                data.remove(indice);
                                notifyDataSetChanged(); // Mettre à jour l'affichage de la liste
                                Toast.makeText(con, "Profil supprimé avec succès", Toast.LENGTH_SHORT).show();
                            } else {
                                // Si la suppression a échoué, afficher un message d'erreur
                                Toast.makeText(con, "Erreur lors de la suppression du profil", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(con, "Suppression annulée", Toast.LENGTH_SHORT).show();
                        }
                    });
                    alert.setNeutralButton("Exit", null);
                    alert.show();
                }
            });

            imgcall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int indice = getAdapterPosition(); //revoie de position de element selectionés
                    Profil p = data.get(indice);  //get profile de ce indice

                    Intent i = new Intent(Intent.ACTION_CALL);
                    i.setData(Uri.parse("tel:" + p.numero));
                    con.startActivity(i);
                }
            });

            imgedit.setOnClickListener(new View.OnClickListener() {
                AlertDialog dialog; //

                @Override
                public void onClick(View view) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(con);
                    alert.setTitle("Edition");
                    alert.setMessage("Modifier les informations");

                    //create dialog.xml in alertDialogue
                    LayoutInflater inf = LayoutInflater.from(con);  //Convert code xml in java
                    View vd= inf.inflate(R.layout.view_dialog,null);

                    EditText ednom =vd.findViewById(R.id.ednom_dialog);
                    EditText edprenom =vd.findViewById(R.id.edprenom_dialog);
                    EditText ednumero =vd.findViewById(R.id.ednumerp_dialog);
                    Button btnenregistrer = vd.findViewById(R.id.btnenregistrer_dialog);
                    Button btnannuler = vd.findViewById(R.id.btnannuler_dialog);

                    //GET DATA IN edit file
                    int indice =getAdapterPosition();
                    Profil p =data.get(indice);

                    ednom.setText(p.nom);
                    edprenom.setText(p.prenom);
                    ednumero.setText(p.numero);

                    //Actionn de deux donnéé

                    btnenregistrer.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String newNom =ednom.getText().toString();
                            String newPrenom =edprenom.getText().toString();
                            String newNumero =ednumero.getText().toString();

                            p.setNom(newNom);
                            p.setPrenom(newPrenom);
                            p.setNumero(newNumero);

                            ProfilManager manager = new ProfilManager(con);

                            manager.ouvrir();
                            long result = manager.modifier(newNom, newPrenom, newNumero);
                            if (result != -1) {
                                // Update data in the adapter

                                AlertDialog.Builder alert = new AlertDialog.Builder(con);
                                alert.setTitle("Succès");
                                alert.setMessage("Modification réussie");
                                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //data.set(indice, p);
                                        notifyDataSetChanged();// rafraichir le view
                                        dialog.dismiss();
                                    }
                                });
                                alert.show();
                            } else {
                                // Profile modification failed
                                // Handle this case appropriately, such as displaying an error message
                                AlertDialog.Builder alert = new AlertDialog.Builder(con);
                                alert.setTitle("Erreur");
                                alert.setMessage("Erreur de modification");
                                alert.setPositiveButton("OK", null);
                                alert.show();

                                Toast.makeText(con, "Failed to modify profile", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    btnannuler.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                    alert.setView(vd);

                    dialog = alert.create();
                    dialog.show();
                }
            });
        }




    }
    public void filter(String name) {
        data.clear(); // Effacez les données existantes de l'adaptateur
        if (name.isEmpty()) {
            data.addAll(originalDataList);
        } else {
            String searchName = name.toLowerCase().trim();
            for (Profil profil : originalDataList) {
                // Si le nom du profil contient le texte de recherche, ajoutez-le à la liste filtrée
                if (profil.getNom().toLowerCase().contains(searchName)) {
                    data.add(profil);
                }
            }
        }
        notifyDataSetChanged(); // Notifiez à l'adaptateur que les données ont changé, pour rafraîchir l'affichage
    }
}

