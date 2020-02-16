package com.example.mescontacts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.Switch;
import android.widget.TextView;

public class UpdateContactActivity extends AppCompatActivity {

    ContactDbAdapter maBase;

    ImageView imageCall, imageEmail, imageMessage, imageAdress;

    TextView firstNameData, lastNameData, phoneNumberData, emailData, adressData;

    Switch favori;

    Button buttonUpdate;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_contact);

        String nom, prenom, numero, mail, adresse, favoris;
        long id;

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

        buttonUpdate = findViewById(R.id.button_update);

        maBase = new ContactDbAdapter(this);
        maBase.open();

        Intent intent = getIntent();

        if(intent != null) {
            if(intent.hasExtra("nom")) {
                nom = intent.getStringExtra("nom");
                Log.i("DEBUG", nom);
                lastNameData = findViewById(R.id.lastname_data);
                lastNameData.setText(nom);
            }
            if(intent.hasExtra("prenom")) {
                firstNameData = findViewById(R.id.firstname_data);
                prenom = intent.getStringExtra("prenom");
                firstNameData.setText(prenom);
            }
            if(intent.hasExtra("numero")) {
                phoneNumberData = findViewById(R.id.phone_number_data);
                numero = intent.getStringExtra("numero");
                phoneNumberData.setText(numero);
            }
            if(intent.hasExtra("mail")) {
                emailData = findViewById(R.id.email_data);
                mail = intent.getStringExtra("mail");
                emailData.setText(mail);
            }
            if(intent.hasExtra("adresse")) {
                adressData = findViewById(R.id.adress_data);
                adresse = intent.getStringExtra("adresse");
                adressData.setText(adresse);
            }
            if(intent.hasExtra("favori")) {
                favoris = intent.getStringExtra("favori");
                if(favoris.equals("1")) {
                    favori.setChecked(true);
                }else{
                    favori.setChecked(false);
                }
            }
        }

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("DEBUG", "update");
                Intent intent = getIntent();
                long id = intent.getLongExtra("id", 0);
                String nom = lastNameData.getText().toString();
                String prenom = firstNameData.getText().toString();
                String numero = phoneNumberData.getText().toString();
                String mail = emailData.getText().toString();
                String adresse = adressData.getText().toString();
                String isFavori;

                if(favori.isChecked()){
                    isFavori = "1";
                }else{
                    isFavori = "0";
                }

                if(maBase.updateContact(id, nom, prenom, numero, mail, adresse, isFavori)) {
                    Log.i("DEBUG", "update success");
                    swithActivity(v);
                }else{
                    Log.i("DEBUG", "update fail");
                }

            }
        });
    }

    public void swithActivity(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
