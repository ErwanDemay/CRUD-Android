package com.example.gestionvisiteurs.controleur;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gestionvisiteurs.R;
import com.example.gestionvisiteurs.modele.Visiteur;
import com.example.gestionvisiteurs.modele.VisiteurDAO;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_details);

        EditText edtIdentifiant = findViewById(R.id.edtIdentifiant);
        EditText edtNom = findViewById(R.id.edtNom);
        EditText edtPrenom = findViewById(R.id.edtPrenom);
        EditText edtLogin = findViewById(R.id.edtLogin);
        EditText edtPassword = findViewById(R.id.edtPassword);
        EditText edtAdresseRue = findViewById(R.id.edtAdresseRue);
        EditText edtCodePostal = findViewById(R.id.edtCodePostal);
        EditText edtVille = findViewById(R.id.edtVille);
        EditText edtDateEmbauche = findViewById(R.id.edtDateEmbauche);

        Button btnValider = findViewById(R.id.btnValider);

        // Récupération des données envoyées par l'Intent
        Intent intent = getIntent();
        if (intent != null) {
            edtIdentifiant.setText(intent.getStringExtra("identifiant"));
            edtNom.setText(intent.getStringExtra("nom"));
            edtPrenom.setText(intent.getStringExtra("prenom"));
            edtLogin.setText(intent.getStringExtra("login"));
            edtPassword.setText(intent.getStringExtra("motDePasse"));
            edtAdresseRue.setText(intent.getStringExtra("adresse"));
            edtCodePostal.setText(intent.getStringExtra("codePostal"));
            edtVille.setText(intent.getStringExtra("ville"));
            edtDateEmbauche.setText(intent.getStringExtra("dateEmbauche"));
        }

        // Action du bouton valider
        btnValider.setOnClickListener(v -> {
            Log.d("BTN_CLICK", "Bouton Valider cliqué");

            String identifiant = (edtIdentifiant.getText().toString());
            String newNom = edtNom.getText().toString();
            String newPrenom = edtPrenom.getText().toString();
            String newLogin = edtLogin.getText().toString();
            String newMdp = edtPassword.getText().toString();
            String newAdresse = edtAdresseRue.getText().toString();
            String newCodePostal = edtCodePostal.getText().toString();
            String newVille = edtVille.getText().toString();
            String newDateEmbauche = edtDateEmbauche.getText().toString();

            Visiteur visiteurModif = new Visiteur(identifiant, newNom, newPrenom, newLogin, newMdp, newAdresse, newCodePostal, newVille, newDateEmbauche);

            VisiteurDAO visiteurDAO = new VisiteurDAO(DetailsActivity.this);

            Log.d("MODIF", "ID: " + identifiant);
            Log.d("MODIF", "Nom: " + newNom);
            Log.d("MODIF", "Prénom: " + newPrenom);
            Log.d("MODIF", "Login: " + newLogin);
            Log.d("MODIF", "MDP: " + newMdp);
            Log.d("MODIF", "Adresse: " + newAdresse);
            Log.d("MODIF", "Code Postal: " + newCodePostal);
            Log.d("MODIF", "Ville: " + newVille);
            Log.d("MODIF", "Date Embauche: " + newDateEmbauche);



            if (!visiteurDAO.idExiste(identifiant)) {
                Log.e("DEBUG_UPDATE", "Erreur: L'ID " + identifiant + " n'existe pas dans la base !");
                Toast.makeText(DetailsActivity.this, "Erreur: visiteur introuvable", Toast.LENGTH_SHORT).show();
                return;
            }

            Log.d("DEBUG_UPDATE", "ID envoyé à updateVisiteur: " + identifiant);
            boolean success = visiteurDAO.updateVisiteur(visiteurModif);

            if (success) {
                Log.d("DB_UPDATE", "Mise à jour réussie !");
            } else {
                Log.e("DB_UPDATE", "Échec de la mise à jour !");
            }


            if (success) {
                Toast.makeText(DetailsActivity.this, "Modifications enregistrées", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(DetailsActivity.this, "Échec de la modification", Toast.LENGTH_SHORT).show();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
