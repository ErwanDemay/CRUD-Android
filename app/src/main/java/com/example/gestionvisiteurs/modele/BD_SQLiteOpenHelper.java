package com.example.gestionvisiteurs.modele;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BD_SQLiteOpenHelper extends SQLiteOpenHelper {
    private String visiteur = "CREATE TABLE IF NOT EXISTS visiteur (\n" +
            "  id text PRIMARY KEY NOT NULL,\n" +
            "  nom text DEFAULT NULL,\n" +
            "  prenom text DEFAULT NULL,\n" +
            "  login text DEFAULT NULL,\n" +
            "  mdp text DEFAULT NULL,\n" +
            "  adresse text DEFAULT NULL,\n" +
            "  cp text DEFAULT NULL,\n" +
            "  ville text DEFAULT NULL,\n" +
            "  dateEmbauche text DEFAULT NULL); ";

    public BD_SQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }



    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(visiteur);
        db.execSQL("INSERT INTO visiteur (id, nom, prenom, login, mdp, adresse, cp, ville, dateEmbauche) VALUES\n" +
                "('a131', 'Villechalane', 'Louis', 'aribiA', 'aaaa', '8 rue des textmes', '46000', 'Cahors', '2005-12-21'),\n" +
                "('a17', 'Andre', 'David', 'dandre', 'oppg5', '1 rue Petit', '46200', 'Lalbenque', '1998-11-23'),\n" +
                "('a55', 'Bedos', 'Christian', 'cbedos', 'gmhxd', '1 rue Peranud', '46250', 'Montcuq', '1995-01-12'),\n" +
                "('a93', 'Tusseau', 'Louis', 'ltusseau', 'ktp3s', '22 rue des Ternes', '46123', 'Gramat', '2000-05-01'),\n" +
                "('b13', 'Bentot', 'Pascal', 'pbentot', 'doyw1', '11 allée des Cerises', '46512', 'Bessines', '1992-07-09'),\n" +
                "('b16', 'Bioret', 'Luc', 'lbioret', 'hrjfs', '1 Avenue gambetta', '46000', 'Cahors', '1998-05-11'),\n" +
                "('b19', 'Bunisset', 'Francis', 'fbunisset', '4vbnd', '10 rue des Perles', '93100', 'Montreuil', '1987-10-21'),\n" +
                "('b25', 'Bunisset', 'Denise', 'dbunisset', 's1y1r', '23 rue Manin', '75019', 'paris', '2010-12-05');");


        Log.d("log","base de test cree");
    }
}
