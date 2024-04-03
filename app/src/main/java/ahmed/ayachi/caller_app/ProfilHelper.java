package ahmed.ayachi.caller_app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ProfilHelper extends SQLiteOpenHelper {

    public static final String table_profil="Profils";
    public static final String col_non="Nom";
    public static final String col_prenom="Prenom";
    public static final String col_numero="Numerp";

    String requete=" create table "+table_profil+" ("+col_non+" Text not null,"+
            col_prenom+" Text not null,"+col_numero+" Text not null)";
    public ProfilHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //lors de l'ouverture de la base de premier fois
        db.execSQL(requete);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        //modification de la version
        db.execSQL(" drop table"+table_profil);
        onCreate(db);
    }
}
