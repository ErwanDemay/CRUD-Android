package com.example.gestionvisiteurs.controleur;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gestionvisiteurs.R;
import com.example.gestionvisiteurs.modele.VisiteurDAO;
import com.example.gestionvisiteurs.modele.Visiteur;

import java.util.List;

public class AjoutActivity extends AppCompatActivity {
    private VisiteurDAO visiteurDAO;
    private EditText edtIdentifiant, edtNom, edtPrenom, edtLogin, edtPassword, edtAdresseRue, edtCodePostal, edtVille, edtDateEmbauche;
    private Button btnAjouter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ajout);

        // Initialisation du DAO
        visiteurDAO = new VisiteurDAO(this);

        // Initialisation des champs
        edtIdentifiant = findViewById(R.id.edtIdentifiant);
        edtNom = findViewById(R.id.edtNom);
        edtPrenom = findViewById(R.id.edtPrenom);
        edtLogin = findViewById(R.id.edtLogin);
        edtPassword = findViewById(R.id.edtPassword);
        edtAdresseRue = findViewById(R.id.edtAdresseRue);
        edtCodePostal = findViewById(R.id.edtCodePostal);
        edtVille = findViewById(R.id.edtVille);
        edtDateEmbauche = findViewById(R.id.edtDateEmbauche);
        btnAjouter = findViewById(R.id.btnValiderForm);

        // Ajout du listener sur le bouton
        btnAjouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validerAjout();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void validerAjout() {
        try {
            String id = (edtIdentifiant.getText().toString());
            String nom = edtNom.getText().toString();
            String prenom = edtPrenom.getText().toString();
            String login = edtLogin.getText().toString();
            String mdp = edtPassword.getText().toString();
            String adresse = edtAdresseRue.getText().toString();
            String cp = edtCodePostal.getText().toString();
            String ville = edtVille.getText().toString();
            String dateEmbauche = edtDateEmbauche.getText().toString(); // La date reste en String

            Visiteur visiteur = new Visiteur(id, nom, prenom, login, mdp, adresse, cp, ville, dateEmbauche);

            // Utilisation de l'objet visiteurDAO
            visiteurDAO.addVisiteur(visiteur);

            Toast.makeText(this, "Visiteur ajoute avec succès !", Toast.LENGTH_SHORT).show();
            Log.d("DB_DEBUG", "Visiteur ajouté : " + visiteur.getNom() + ", " + visiteur.getPrenom());

            List<Visiteur> visiteurs = visiteurDAO.getAllVisiteurs();
            for (Visiteur v : visiteurs) {
                Log.d("DB_CHECK", "Visiteur ID: " + v.getIdentifiant() + ", Nom: " + v.getNom());
            }



        } catch (NumberFormatException e) {
            Toast.makeText(this, "Erreur : L'ID doit être un nombre valide.", Toast.LENGTH_SHORT).show();
        }
    }
}
