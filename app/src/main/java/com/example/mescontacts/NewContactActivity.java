package com.example.mescontacts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
        list = findViewById(R.id.taskList);

        maBase = new ContactDbAdapter(this);
        maBase.open();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);

        //lorsqu'on clique sur le +, on appelle addItem()
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem(view);
            }
        });
    }

    //crée un nouveau contact dans la BDD
    public void addItem(View view) {
        maBase.createContact(nom.getText().toString(), prenom.getText().toString(), num.getText().toString(), mail.getText().toString(), adr.getText().toString());
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void fillData() {
        // Get all of the notes from the database and create the item list
        Cursor c = maBase.fetchAllContact();
        startManagingCursor(c);

        String[] from = new String[] { ContactDbAdapter.KEY_NOM };
        int[] to = new int[] { R.id.nom};

        // Now create an array adapter and set it to display using our row
        SimpleCursorAdapter contacts =
                new SimpleCursorAdapter(this, R.layout.contacts_row, c, from, to);
        list.setAdapter(contacts);
    }

}
