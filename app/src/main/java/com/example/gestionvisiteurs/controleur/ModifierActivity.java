package com.example.gestionvisiteurs.controleur;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gestionvisiteurs.R;
import com.example.gestionvisiteurs.modele.Visiteur;
import com.example.gestionvisiteurs.modele.VisiteurDAO;

import java.util.List;

public class ModifierActivity extends AppCompatActivity {

    private Spinner spinnerVisiteurs;
    private VisiteurDAO visiteurDAO;
    private List<Visiteur> listeVisiteurs;
    private EditText edtNom, edtPrenom, edtLogin, edtMotDePasse, edtAdresse, edtCodePostal, edtVille, edtDateEmbauche;
    private Button btnValider;
    private Visiteur visiteurSelectionne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_modifier);

        // Initialisation des vues
        spinnerVisiteurs = findViewById(R.id.spinnerVisiteurs);
        edtNom = findViewById(R.id.edtNom);
        edtPrenom = findViewById(R.id.edtPrenom);
        edtLogin = findViewById(R.id.edtLogin);
        edtMotDePasse = findViewById(R.id.edtPassword);
        edtAdresse = findViewById(R.id.edtAdresseRue);
        edtCodePostal = findViewById(R.id.edtCodePostal);
        edtVille = findViewById(R.id.edtVille);
        edtDateEmbauche = findViewById(R.id.edtDateEmbauche);
        btnValider = findViewById(R.id.btnValider);

        // Initialisation du DAO et récupération des visiteurs
        visiteurDAO = new VisiteurDAO(this);
        listeVisiteurs = visiteurDAO.getAllVisiteurs();

        // Vérification que la liste de visiteurs n'est pas vide
        if (listeVisiteurs.isEmpty()) {
            Toast.makeText(this, "Aucun visiteur trouvé dans la base de données", Toast.LENGTH_LONG).show();
            return;
        }

        // Remplissage du Spinner avec les noms des visiteurs
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                getNomsVisiteurs()
        );
        spinnerVisiteurs.setAdapter(adapter);

        // Gestion de la sélection d'un visiteur dans le Spinner
        spinnerVisiteurs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                visiteurSelectionne = listeVisiteurs.get(position);
                afficherInfosVisiteur(visiteurSelectionne);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Listener du bouton valider
        btnValider.setOnClickListener(v -> updateVisiteur());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    /**
     * Récupère les noms des visiteurs pour afficher dans le Spinner.
     */
    private String[] getNomsVisiteurs() {
        String[] noms = new String[listeVisiteurs.size()];
        for (int i = 0; i < listeVisiteurs.size(); i++) {
            noms[i] = listeVisiteurs.get(i).getNom() + " " + listeVisiteurs.get(i).getPrenom();
        }
        return noms;
    }

    /**
     * Remplit les champs de texte avec les informations du visiteur sélectionné.
     */
    private void afficherInfosVisiteur(Visiteur visiteur) {
        edtNom.setText(visiteur.getNom());
        edtPrenom.setText(visiteur.getPrenom());
        edtLogin.setText(visiteur.getLogin());
        edtMotDePasse.setText(visiteur.getMotDePasse());
        edtAdresse.setText(visiteur.getAdresse());
        edtCodePostal.setText(visiteur.getCodePostal());
        edtVille.setText(visiteur.getVille());
        edtDateEmbauche.setText(visiteur.getDateEmbauche());
    }

    /**
     * Met à jour les informations du visiteur sélectionné dans la base de données.
     */
    private void updateVisiteur() {
        if (visiteurSelectionne == null) {
            Toast.makeText(this, "Veuillez sélectionner un visiteur", Toast.LENGTH_SHORT).show();
            return;
        }

        String newNom = edtNom.getText().toString().trim();
        String newPrenom = edtPrenom.getText().toString().trim();
        String newLogin = edtLogin.getText().toString().trim();
        String newMdp = edtMotDePasse.getText().toString().trim();
        String newAdresse = edtAdresse.getText().toString().trim();
        String newCodePostal = edtCodePostal.getText().toString().trim();
        String newVille = edtVille.getText().toString().trim();
        String newDateEmbauche = edtDateEmbauche.getText().toString().trim();

        // Vérification des champs obligatoires
        if (newNom.isEmpty() || newPrenom.isEmpty() || newLogin.isEmpty() || newMdp.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs obligatoires", Toast.LENGTH_SHORT).show();
            return;
        }

        // Mise à jour des données du visiteur
        visiteurSelectionne.setNom(newNom);
        visiteurSelectionne.setPrenom(newPrenom);
        visiteurSelectionne.setLogin(newLogin);
        visiteurSelectionne.setMotDePasse(newMdp);
        visiteurSelectionne.setAdresse(newAdresse);
        visiteurSelectionne.setCodePostal(newCodePostal);
        visiteurSelectionne.setVille(newVille);
        visiteurSelectionne.setDateEmbauche(newDateEmbauche);

        // Mise à jour dans la base de données
        boolean success = visiteurDAO.updateVisiteur(visiteurSelectionne);

        if (success) {
            Toast.makeText(this, "Modifications enregistrées avec succès", Toast.LENGTH_SHORT).show();
            finish(); // Ferme l'activité après modification réussie
        } else {
            Toast.makeText(this, "Échec de la mise à jour", Toast.LENGTH_SHORT).show();
        }
    }
}
