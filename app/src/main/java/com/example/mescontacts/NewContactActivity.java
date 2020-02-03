package com.example.mescontacts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class NewContactActivity extends AppCompatActivity {

    EditText nom;
    EditText prenom;
    EditText num;
    EditText mail;
    EditText adr;
    ListView list;
    ContactDbAdapter maBase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        nom = findViewById(R.id.nom);
        prenom = findViewById(R.id.prenom);
        num = findViewById(R.id.numTel);
        mail = findViewById(R.id.email);
        adr = findViewById(R.id.adresse);

        maBase = new ContactDbAdapter(this);
        maBase.open();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);
    }

    public void addItem() {
        maBase.createContact(nom.getText().toString(), prenom.getText().toString(), num.getText().toString(), mail.getText().toString(), adr.getText().toString());
        fillData();
    }

    private void fillData() {
        // Get all of the notes from the database and create the item list
        Cursor c = maBase.fetchAllContact();
        startManagingCursor(c);

        String[] from = new String[] { ContactDbAdapter.KEY_NOM };
        int[] to = new int[] { R.id.nom};

        // Now create an array adapter and set it to display using our row
        SimpleCursorAdapter notes =
                new SimpleCursorAdapter(this, R.layout.activity_new_contact, c, from, to);
        list.setAdapter(notes);
    }

}
