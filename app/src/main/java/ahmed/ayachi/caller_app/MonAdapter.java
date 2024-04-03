package ahmed.ayachi.caller_app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MonAdapter extends BaseAdapter {
    ArrayList<Profil> data;
    Context con;

    public MonAdapter(Context con ,ArrayList<Profil> data) {
        this.data = data;
        this.con = con;
    }

    @Override
    public int getCount() { // retourne Nbr des views
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        //Creation d'un view
        LayoutInflater inf =LayoutInflater.from(con);
        View v =inf.inflate(R.layout.activity_profil,null); //Convertir activity_profile on object v

        //Recuperation des sous View /Holders
        TextView tvnom=v.findViewById(R.id.tvnom_profil);
        TextView tvprenom=v.findViewById(R.id.tvprenom_profil);
        TextView tvnumero=v.findViewById(R.id.tvnumero_profil);

        ImageView imgdelete=v.findViewById(R.id.imageViewdelete_profil);
        ImageView imgedit=v.findViewById(R.id.imageViewedit_profil);
        ImageView imgcall=v.findViewById(R.id.imageViewcall_profil);

        // Affectation des holders
        Profil p=data.get(position);
        tvnom.setText(""+p.nom);
        tvprenom.setText(""+p.prenom);
        tvnumero.setText(""+p.numero);

        // event
        imgdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // afficher une boite de dialog
                AlertDialog.Builder alert=new AlertDialog.Builder(con);
                alert.setTitle("Confirmation");
                alert.setMessage("confirmer la suppression?");

                alert.setPositiveButton("supprimer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ProfilManager manager = new ProfilManager(con);

                        // supprimer de la base
                        manager.ouvrir();
                        Profil profilToDelete = data.get(position);
                        int deletedRows = manager.supprimer(profilToDelete.nom);
                        manager.fermer();

                        if (deletedRows > 0) {
                            // Si la suppression a réussi, mettre à jour l'affichage
                            data.remove(position);
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
                alert.setNeutralButton("Exit",null);
                alert.show();
            }
        });

        imgcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:"+p.numero));
                con.startActivity(i);
            }
        });


        return v;
    }
}
