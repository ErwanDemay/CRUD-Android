package com.example.gestionvisiteurs.controleur;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.view.View.OnClickListener;


import com.example.gestionvisiteurs.R;

public class  PropositionActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_proposition);


        Button buttonValider= (Button) findViewById(R.id.btnValider);
        Button buttonConsulter= (Button) findViewById(R.id.btnConsulter);
        Button buttonModifier = findViewById(R.id.btnModifier);

        //ClickListener pour ouvrir la vue activity_ajout au clic du bouton
        buttonValider.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                startActivity(new Intent(PropositionActivity.this, AjoutActivity.class));
            }
        });


        //ClickListener pour ouvrir la vue activity_consult au clic du bouton
        buttonConsulter.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view){
                Log.d("PropositionActivity", "Bouton Consulter cliqué !");
                startActivity(new Intent(PropositionActivity.this,ConsultActivity.class));
            }
        });

        buttonModifier.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                //Log.d("PropositionActivity", "Bouton Modfier cliqué !");
                startActivity(new Intent(PropositionActivity.this,ModifierActivity.class));
            }
        });



        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


}
}