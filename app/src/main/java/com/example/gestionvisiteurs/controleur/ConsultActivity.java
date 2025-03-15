package com.example.gestionvisiteurs.controleur;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gestionvisiteurs.R;
import com.example.gestionvisiteurs.modele.Visiteur;
import com.example.gestionvisiteurs.modele.VisiteurDAO;

import java.util.List;

public class ConsultActivity extends AppCompatActivity {

    private ListView listeVisiteurs;
    private VisiteurDAO visiteurDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_consult);

        Button btnRetour = findViewById(R.id.btnRetour);
        btnRetour.setOnClickListener(v -> finish());

        // Initialisation correcte de la ListView
        listeVisiteurs = findViewById(R.id.listeVisiteurs);
        if (listeVisiteurs == null) {
            Log.e("ConsultActivity", "ERREUR : listeVisiteurs est null !");
        }

        visiteurDAO = new VisiteurDAO(this);

        // Gestion du clic sur un visiteur
        listeVisiteurs.setOnItemClickListener((parent, view, position, id) -> {
            Visiteur visiteurSelectionne = (Visiteur) parent.getItemAtPosition(position);

            Intent intent = new Intent(ConsultActivity.this, DetailsActivity.class);
            intent.putExtra("identifiant", visiteurSelectionne.getIdentifiant());
            intent.putExtra("nom", visiteurSelectionne.getNom());
            intent.putExtra("prenom", visiteurSelectionne.getPrenom());
            intent.putExtra("login", visiteurSelectionne.getLogin());
            intent.putExtra("adresse", visiteurSelectionne.getAdresse());
            intent.putExtra("codePostal", visiteurSelectionne.getCodePostal());
            intent.putExtra("ville", visiteurSelectionne.getVille());
            intent.putExtra("dateEmbauche", visiteurSelectionne.getDateEmbauche());

            startActivity(intent);
        });

        afficherVisiteurs();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("ConsultActivity", "Rechargement des visiteurs après modification...");
        afficherVisiteurs(); // Recharge la liste après modification
    }

    public void afficherVisiteurs() {
        List<Visiteur> visiteurs = visiteurDAO.recupVisiteur();

        if (listeVisiteurs != null) { // Sécuriser avant d'utiliser la ListView
            ArrayAdapter<Visiteur> visiteurArrayAdapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_list_item_1,
                    visiteurs
            );
            listeVisiteurs.setAdapter(visiteurArrayAdapter);
        } else {
            Log.e("ConsultActivity", "ERREUR : listeVisiteurs est toujours null !");
        }
    }
}
