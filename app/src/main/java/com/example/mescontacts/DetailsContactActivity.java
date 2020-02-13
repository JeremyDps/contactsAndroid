package com.example.mescontacts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DetailsContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_contact);

        Intent intent = getIntent();

        if(intent != null) {
            if(intent.hasExtra("nom")) {
                String nom = intent.getStringExtra("nom");
                TextView nom_data = findViewById(R.id.name_data);
                nom_data.setText(nom);
            }
            if(intent.hasExtra("prenom")) {
                TextView prenom_data = findViewById(R.id.firstname_data);
                String prenom = intent.getStringExtra("prenom");
                prenom_data.setText(prenom);
            }
            if(intent.hasExtra("numero")) {
                TextView numero_data = findViewById(R.id.phone_number_data);
                String numero = intent.getStringExtra("numero");
                numero_data.setText(numero);
            }
            if(intent.hasExtra("mail")) {
                TextView mail_data = findViewById(R.id.email_data);
                String mail = intent.getStringExtra("mail");
                mail_data.setText(mail);
            }
            if(intent.hasExtra("adresse")) {
                TextView adresse_data = findViewById(R.id.adress_data);
                String adresse = intent.getStringExtra("adresse");
                adresse_data.setText(adresse);
            }
        }
    }
}
