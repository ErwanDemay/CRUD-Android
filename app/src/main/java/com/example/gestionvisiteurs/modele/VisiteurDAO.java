package com.example.gestionvisiteurs.modele;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class VisiteurDAO{
    private final BD_SQLiteOpenHelper dbHelper;
    private static SQLiteDatabase database;

    private static List<Visiteur> listeVisiteurs;

    public VisiteurDAO(Context context){
        dbHelper = new BD_SQLiteOpenHelper(context,"visiteurs.db",null,1);
        database = dbHelper.getWritableDatabase();

        this.listeVisiteurs = new ArrayList<>();
    }

    public static void addVisiteur(Visiteur unVisiteur){

        ContentValues values = new ContentValues();

        values.put("id",unVisiteur.getIdentifiant());
        values.put("Nom",unVisiteur.getNom());
        values.put("Prenom",unVisiteur.getPrenom());
        values.put("login",unVisiteur.getLogin());
        values.put("mdp",unVisiteur.getMotDePasse());
        values.put("adresse",unVisiteur.getAdresse());
        values.put("cp",unVisiteur.getCodePostal());
        values.put("ville",unVisiteur.getVille());
        values.put("dateEmbauche", String.valueOf(unVisiteur.getDateEmbauche()));

        database.insert("visiteur",null,values);

        listeVisiteurs.add(unVisiteur);

        Log.d("Text", "Le visiteur a bien été ajouté. ");

    }

    public List<Visiteur> getAllVisiteurs() {
        List<Visiteur> visiteurs = new ArrayList<>();
        String query = "SELECT * FROM visiteur";
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Visiteur visiteur = new Visiteur(
                        cursor.getString(cursor.getColumnIndexOrThrow("id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("nom")),
                        cursor.getString(cursor.getColumnIndexOrThrow("prenom")),
                        cursor.getString(cursor.getColumnIndexOrThrow("login")),
                        cursor.getString(cursor.getColumnIndexOrThrow("mdp")),
                        cursor.getString(cursor.getColumnIndexOrThrow("adresse")),
                        cursor.getString(cursor.getColumnIndexOrThrow("cp")),
                        cursor.getString(cursor.getColumnIndexOrThrow("ville")),
                        cursor.getString(cursor.getColumnIndexOrThrow("dateEmbauche"))
                );
                visiteurs.add(visiteur);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return visiteurs;
    }



    public List<Visiteur> recupVisiteur()
    {
        List<Visiteur> visiteurs = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase(); // S'assurer d'ouvrir la base en lecture
        String query = "SELECT * FROM visiteur";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String identifiant = cursor.getString(cursor.getColumnIndexOrThrow("id"));
                String nom = cursor.getString(cursor.getColumnIndexOrThrow("nom"));
                String prenom = cursor.getString(cursor.getColumnIndexOrThrow("prenom"));
                String login = cursor.getString(cursor.getColumnIndexOrThrow("login"));
                String mdp = cursor.getString(cursor.getColumnIndexOrThrow("mdp"));
                String adresse = cursor.getString(cursor.getColumnIndexOrThrow("adresse"));
                String codePostal = cursor.getString(cursor.getColumnIndexOrThrow("cp"));
                String ville = cursor.getString(cursor.getColumnIndexOrThrow("ville"));
                String dateEmbauche = cursor.getString(cursor.getColumnIndexOrThrow("dateEmbauche"));

                Visiteur visiteur = new Visiteur(identifiant, nom, prenom, login, mdp, adresse, codePostal, ville, dateEmbauche);
                visiteurs.add(visiteur);
            } while (cursor.moveToNext());

            cursor.close();
        }
        return visiteurs;
    }

    public boolean updateVisiteur(Visiteur visiteur) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();


        values.put("nom", visiteur.getNom());
        values.put("prenom", visiteur.getPrenom());
        values.put("login", visiteur.getLogin());
        values.put("mdp", visiteur.getMotDePasse()); // Assure-toi que le nom de la colonne est correct
        values.put("adresse", visiteur.getAdresse());
        values.put("cp", visiteur.getCodePostal());
        values.put("ville", visiteur.getVille());
        values.put("dateEmbauche", visiteur.getDateEmbauche());

        // Mise à jour dans la base de données
        int rowsAffected = db.update("visiteur", values, "id = ?", new String[]{visiteur.getIdentifiant()});

        Log.d("DEBUG_UPDATE", "Lignes affectées par la mise à jour : " + rowsAffected);


        db.close(); // Toujours fermer la base de données après usage

        return rowsAffected > 0; // Retourne true si la mise à jour a réussi, sinon false
    }


    public boolean idExiste(String id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id FROM visiteur WHERE id = ?", new String[]{id});

        boolean existe = cursor.getCount() > 0;
        cursor.close();
        return existe;
    }


    public boolean deleteVisiteur(String id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rowsDeleted = db.delete("visiteur", "id = ?", new String[]{id});
        db.close();

        //Log.d("DB_DELETE","Deleted visitor with id : " + id + ", Rows affected : " + rowsDeleted);
        return rowsDeleted > 0;
    }

}


