package com.example.gestionvisiteurs.controleur;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gestionvisiteurs.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // DOIT être en premier
        setContentView(R.layout.activity_main); // Ensuite, on définit le layout

       View mainLayout = findViewById(R.id.main);
        if (mainLayout != null) {
            EdgeToEdge.enable(this);
            ViewCompat.setOnApplyWindowInsetsListener(mainLayout, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }

        // Initialisation du bouton
        Button button = findViewById(R.id.btnValider);
        button.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, PropositionActivity.class)));
    }



}