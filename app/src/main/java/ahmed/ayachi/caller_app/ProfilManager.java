package ahmed.ayachi.caller_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class ProfilManager {
    SQLiteDatabase db = null;
    Context con;

    ProfilManager(Context con) {
        this.con = con;
    }

    public void ouvrir() {
        ProfilHelper helper = new ProfilHelper(con, "mabase.db", null, 1);
        db = helper.getWritableDatabase();
    }

    public long ajout(String nom, String pren, String num) {
        long a = 0;
        ContentValues values = new ContentValues();

        values.put(ProfilHelper.col_non, nom);
        values.put(ProfilHelper.col_prenom, pren);
        values.put(ProfilHelper.col_numero, num);

        a = db.insert(ProfilHelper.table_profil, null, values);
        return a;
    }

    public ArrayList<Profil> getAllProfil() {
        ArrayList<Profil> l = new ArrayList<Profil>();
        Cursor cr = db.query(ProfilHelper.table_profil,
                new String[]{ProfilHelper.col_non,
                        ProfilHelper.col_prenom,
                        ProfilHelper.col_numero,}, null, null, null, null, null);


        cr.moveToFirst(); //pointer le cursor a la premier element
        while (!cr.isAfterLast()) {
            String i1 = cr.getString(0); //indice commence par 0
            String i2 = cr.getString(1);
            String i3 = cr.getString(2);

            //inserer le champs dans la liste
            l.add(new Profil(i1, i2, i3));
            cr.moveToNext();

        }
        return l;
    }

    public int supprimer(String nom) {
        return db.delete(ProfilHelper.table_profil,
                ProfilHelper.col_non + " = ?",
                new String[]{nom});
    }

    public long modifier (String nom, String prenom , String numero)
    {
        long a = 0;
        ContentValues values = new ContentValues();

        values.put(ProfilHelper.col_non, nom);
        values.put(ProfilHelper.col_prenom, prenom);
        values.put(ProfilHelper.col_numero, numero);

        a = db.update(ProfilHelper.table_profil, values,
                ProfilHelper.col_numero + " = ?",
                new String[]{numero});
        return a;
    }


    public void fermer() {
        if (db != null && db.isOpen()) {
            db.close();
        }
    }
}
