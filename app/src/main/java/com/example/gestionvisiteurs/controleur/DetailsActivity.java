package com.example.gestionvisiteurs.controleur;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gestionvisiteurs.R;
import com.example.gestionvisiteurs.modele.Visiteur;
import com.example.gestionvisiteurs.modele.VisiteurDAO;

public class DetailsActivity extends AppCompatActivity {

    private EditText edtIdentifiant, edtNom, edtPrenom, edtLogin, edtPassword, edtAdresseRue, edtCodePostal, edtVille, edtDateEmbauche;
    private Button btnValider, btnSupprimer;
    private VisiteurDAO visiteurDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_details);

        // Initialisation des vues
        edtIdentifiant = findViewById(R.id.edtIdentifiant);
        edtNom = findViewById(R.id.edtNom);
        edtPrenom = findViewById(R.id.edtPrenom);
        edtLogin = findViewById(R.id.edtLogin);
        edtPassword = findViewById(R.id.edtPassword);
        edtAdresseRue = findViewById(R.id.edtAdresseRue);
        edtCodePostal = findViewById(R.id.edtCodePostal);
        edtVille = findViewById(R.id.edtVille);
        edtDateEmbauche = findViewById(R.id.edtDateEmbauche);
        btnValider = findViewById(R.id.btnValider);
        btnSupprimer = findViewById(R.id.btnSupprimer);

        visiteurDAO = new VisiteurDAO(this);

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
        btnValider.setOnClickListener(v -> updateVisiteur());

        // Action du bouton supprimer
        btnSupprimer.setOnClickListener(v -> deleteVisiteur());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void updateVisiteur() {
        String identifiant = edtIdentifiant.getText().toString();
        String newNom = edtNom.getText().toString();
        String newPrenom = edtPrenom.getText().toString();
        String newLogin = edtLogin.getText().toString();
        String newMdp = edtPassword.getText().toString();
        String newAdresse = edtAdresseRue.getText().toString();
        String newCodePostal = edtCodePostal.getText().toString();
        String newVille = edtVille.getText().toString();
        String newDateEmbauche = edtDateEmbauche.getText().toString();

        // Vérification des champs obligatoires
        if (newNom.isEmpty() || newPrenom.isEmpty() || newLogin.isEmpty() || newMdp.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs obligatoires", Toast.LENGTH_SHORT).show();
            return;
        }

        // Création de l'objet Visiteur avec les nouvelles valeurs
        Visiteur visiteurModif = new Visiteur(identifiant, newNom, newPrenom, newLogin, newMdp, newAdresse, newCodePostal, newVille, newDateEmbauche);

        // Mise à jour en base de données
        boolean success = visiteurDAO.updateVisiteur(visiteurModif);

        // Affichage du résultat
        if (success) {
            Toast.makeText(this, "Modifications enregistrées", Toast.LENGTH_SHORT).show();
            finish(); // Ferme l'activité après la modification
        } else {
            Toast.makeText(this, "Échec de la modification", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteVisiteur() {
        Log.d("BTN_CLICK", "Bouton Supprimer cliqué");

        String id = edtIdentifiant.getText().toString();

        AlertDialog.Builder builder = new AlertDialog.Builder(DetailsActivity.this);

        builder.setTitle("Confirmation");
        builder.setMessage("Êtes-vous sûr de vouloir supprimer ce visiteur ? ");

        builder.setPositiveButton("Oui", (dialog, which) -> {
            boolean success = visiteurDAO.deleteVisiteur(id);

            if (success) {
                Toast.makeText(DetailsActivity.this, "Visiteur supprimé avec succès", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(DetailsActivity.this, "Échec de la suppression", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Non", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
