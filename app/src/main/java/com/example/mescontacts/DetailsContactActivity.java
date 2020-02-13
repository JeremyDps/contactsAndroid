package com.example.mescontacts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailsContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_contact);

        ImageView appel = findViewById(R.id.appel);
        final TextView num_data = findViewById(R.id.number_data);

        ImageView email = findViewById(R.id.mail);
        TextView mail_data = findViewById(R.id.mail_data);

        appel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("DEBUG", num_data.getText().toString());
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + num_data.getText().toString()));
                startActivity(callIntent);
            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("DEBUG", num_data.getText().toString());
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + num_data.getText().toString()));
                startActivity(callIntent);
            }
        });

        Intent intent = getIntent();

        if(intent != null) {
            if(intent.hasExtra("nom")) {
                String nom = intent.getStringExtra("nom");
                TextView nom_data = findViewById(R.id.name_data);
                nom_data.setText(nom);
            }
            if(intent.hasExtra("prenom")) {
                TextView prenom_data = findViewById(R.id.surname_data);
                String prenom = intent.getStringExtra("prenom");
                prenom_data.setText(prenom);
            }
            if(intent.hasExtra("numero")) {
                TextView numero_data = findViewById(R.id.number_data);
                String numero = intent.getStringExtra("numero");
                numero_data.setText(numero);
            }
            if(intent.hasExtra("mail")) {
                TextView mail_data = findViewById(R.id.mail_data);
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
