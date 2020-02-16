package com.example.mescontacts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DetailsContactActivity extends AppCompatActivity {

    ImageView imageCall;
    ImageView imageEmail;
    ImageView imageMessage;
    ImageView imageAdress;

    TextView firstNameData;
    TextView lastNameData;
    TextView phoneNumberData;
    TextView emailData;
    TextView adressData;
    TextView favori;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_contact);

        imageCall = findViewById(R.id.image_call);
        imageEmail = findViewById(R.id.image_email);
        imageMessage = findViewById(R.id.image_message);
        imageAdress = findViewById(R.id.image_adress);

        lastNameData = findViewById(R.id.name_data);
        firstNameData = findViewById(R.id.lastname);
        emailData = findViewById(R.id.email_data);
        phoneNumberData = findViewById(R.id.phone_number_data);
        adressData = findViewById(R.id.adress_data);
        favori = findViewById(R.id.favori);

        Intent intent = getIntent();

        if(intent != null) {
            if(intent.hasExtra("nom")) {
                String nom = intent.getStringExtra("nom");
                lastNameData = findViewById(R.id.name_data);
                lastNameData.setText(nom);
            }
            if(intent.hasExtra("prenom")) {
                firstNameData = findViewById(R.id.lastname);
                String prenom = intent.getStringExtra("prenom");
                firstNameData.setText(prenom);
            }
            if(intent.hasExtra("numero")) {
                phoneNumberData = findViewById(R.id.phone_number_data);
                String numero = intent.getStringExtra("numero");
                phoneNumberData.setText(numero);
            }
            if(intent.hasExtra("mail")) {
                emailData = findViewById(R.id.email_data);
                String mail = intent.getStringExtra("mail");
               emailData.setText(mail);
            }
            if(intent.hasExtra("adresse")) {
                adressData = findViewById(R.id.adress_data);
                String adresse = intent.getStringExtra("adresse");
                adressData.setText(adresse);
            }
            if(intent.hasExtra("favori")) {
                String favoris = intent.getStringExtra("favori");
                favori.setText(favoris);
            }
        }

        imageCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("DEBUG", "appel en cours");
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + phoneNumberData.getText().toString()));
                startActivity(callIntent);
            }
        });

        imageMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("DEBUG", "envoi message");
                Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                smsIntent.setData(Uri.parse("smsto:" + phoneNumberData.getText().toString()));
                smsIntent.putExtra("sms_body"  , "");
                startActivity(smsIntent);
            }
        });

        imageEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("DEBUG", "envoi email");
                String[] TO = {emailData.getText().toString()};
                String[] CC = {""};
                Intent emailIntent = new Intent(Intent.ACTION_SEND);

                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                emailIntent.putExtra(Intent.EXTRA_CC, CC);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "");

                try {
                    startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                    finish();
                    Log.i("Finished sending email", "");
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(DetailsContactActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        imageAdress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("DEBUG", "adresse sur maps");
                Uri location = Uri.parse("geo:0,0?q=" + adressData.getText().toString());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
                startActivity(mapIntent);
            }
        });
    }
}
