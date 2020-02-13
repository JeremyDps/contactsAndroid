package com.example.mescontacts;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {

    ContactDbAdapter maBase;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        list = findViewById(R.id.taskList);

        maBase = new ContactDbAdapter(this);
        maBase.open();

        registerForContextMenu(list);

        fillData();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor c = maBase.fetchContact(id);

                if (c.moveToFirst()) {
                    String nom = c.getString(1);
                    String prenom = c.getString(2);
                    String numero = c.getString(3);
                    String mail = c.getString(4);
                    String adresse = c.getString(5);

                    switchActivityDetail(view, nom, prenom, numero, mail, adresse);
                }


            }
        });

        //lorsqu'on clique sur le +, on appelle switchActivity
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchActivity(view);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_delete) {
            delete();
        }

        return super.onOptionsItemSelected(item);
    }

    //dirige vers la vue d'ajout de contact
    public void switchActivity(View view) {
        Intent intent = new Intent(this, NewContactActivity.class);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void call() {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + "0782289527"));
        startActivity(callIntent);
    }

    public void switchActivityDetail(View view, String nom, String prenom, String numero, String mail, String adresse) {
        Intent intent = new Intent(this, DetailsContactActivity.class);
        intent.putExtra("nom", nom);
        intent.putExtra("prenom", prenom);
        intent.putExtra("numero", numero);
        intent.putExtra("mail", mail);
        intent.putExtra("adresse", adresse);
        startActivity(intent);
    }

    public void delete() {
        // Build the dialog and set up the button click handlers
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.dialog_text)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        maBase.deleteAllContact();
                        fillData();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Send the negative button event back to the host activity
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }



    private void fillData() {
        // Get all of the notes from the database and create the item list
        Cursor c = maBase.fetchAllContact();
        startManagingCursor(c);

        String[] from = new String[] { ContactDbAdapter.KEY_NOM };
        int[] to = new int[] { R.id.contact};

        // Now create an array adapter and set it to display using our row
        SimpleCursorAdapter notes =
                new SimpleCursorAdapter(this, R.layout.contacts_row, c, from, to);
        list.setAdapter(notes);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Cursor SelectedTaskCursor = (Cursor) list.getItemAtPosition(info.position);
        final String SelectedTask = SelectedTaskCursor.getString(SelectedTaskCursor.getColumnIndex("nom"));
        final String SelectedAddress = SelectedTaskCursor.getString(SelectedTaskCursor.getColumnIndex("adresse"));
        final String SelectedPhoneNumber = SelectedTaskCursor.getString(SelectedTaskCursor.getColumnIndex("telephone"));
        switch (item.getItemId()) {
            case R.id.supp:
                maBase.deleteContact(list.getItemIdAtPosition(info.position));
                fillData();
                return true;
            case R.id.appeler:
                Log.i("DEBUG", SelectedPhoneNumber);
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + SelectedPhoneNumber));
                startActivity(callIntent);
                return true;
            case R.id.message:
                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.putExtra("sms_body", "default content");
                sendIntent.setType("vnd.android-dir/mms-sms");
                startActivity(sendIntent);
                return true;
            case R.id.mail:
                return true;
            case R.id.adresse:
                Uri location = Uri.parse("geo:0,0?q=" + SelectedAddress);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
                startActivity(mapIntent);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}