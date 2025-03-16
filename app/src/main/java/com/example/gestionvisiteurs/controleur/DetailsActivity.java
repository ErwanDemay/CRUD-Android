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
        Button btnSupprimer = findViewById(R.id.btnSupprimer);

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
            //Log.d("BTN_CLICK", "Bouton Valider cliqué");

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


            boolean success = visiteurDAO.updateVisiteur(visiteurModif);

            if (success) {
                Toast.makeText(DetailsActivity.this, "Modifications enregistrées", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(DetailsActivity.this, "Échec de la modification", Toast.LENGTH_SHORT).show();
            }
        });

        // Action du bouton supprimer
        btnSupprimer.setOnClickListener(v ->{
            Log.d("BTN_CLICK", "Bouton Supprimer cliqué");

            //Get visitor ID
            String id = edtIdentifiant.getText().toString();

            AlertDialog.Builder builder = new AlertDialog.Builder(DetailsActivity.this);

            //Dialogue alert
            builder.setTitle("Confirmation");
            builder.setMessage("Êtes-vous sûr de vouloir supprimer ce visiteur ? ");

            //Reponse
            builder.setPositiveButton("Oui", (dialog,which) -> {
                //Create DAO and delete the visitor
                VisiteurDAO visiteurDAO = new VisiteurDAO(DetailsActivity.this);
                boolean success = visiteurDAO.deleteVisiteur(id);

                if(success) {
                    Toast.makeText(DetailsActivity.this, "Visiteur supprimé avec succès", Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(DetailsActivity.this, "Echec de la suppression", Toast.LENGTH_SHORT).show();
                }
            });

            builder.setNegativeButton("Non", (dialog,which) -> {

                dialog.dismiss();
            });

            AlertDialog dialog = builder.create();
            dialog.show();








        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
