package com.example.mescontacts;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ContactDbAdapter {

    public static final String KEY_NOM = "nom";
    public static final String KEY_PRENOM = "prenom";
    public static final String KEY_NUM = "telephone";
    public static final String KEY_MAIL = "mail";
    public static final String KEY_ADR = "adresse";
    public static final String KEY_FAVORI = "favori";
    public static final String KEY_ROWID = "_id";

    private static final String TAG = "ContactDbAdapter";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    /**
     * Database creation sql statement
     */
    private static final String DATABASE_CREATE =
            "create table contact (_id integer primary key autoincrement, "
                    + "nom text not null, prenom text not null, " +
                    "telephone text not null, mail text not null, adresse text," +
                    "favori text not null)";

    private static final String DATABASE_NAME = "data";
    private static final String DATABASE_TABLE = "contact";
    private static final int DATABASE_VERSION = 2;

    private final Context mCtx;

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS contact");
            onCreate(db);
        }
    }

    /**
     * Constructor - takes the context to allow the database to be
     * opened/created
     *
     * @param ctx the Context within which to work
     */
    public ContactDbAdapter(Context ctx) {
        this.mCtx = ctx;
    }

    /**
     * Open the notes database. If it cannot be opened, try to create a new
     * instance of the database. If it cannot be created, throw an exception to
     * signal the failure
     *
     * @return this (self reference, allowing this to be chained in an
     *         initialization call)
     * @throws SQLException if the database could be neither opened or created
     */
    public ContactDbAdapter open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDbHelper.close();
    }


    /**
     * Create a new note using the title and body provided. If the note is
     * successfully created return the new rowId for that note, otherwise return
     * a -1 to indicate failure.
     *
     * @return rowId or -1 if failed
     */
    public long createContact(String nom, String prenom, String num, String mail, String adr, String favori ) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NOM, nom);
        initialValues.put(KEY_PRENOM, prenom);
        initialValues.put(KEY_NUM, num);
        initialValues.put(KEY_MAIL, mail);
        initialValues.put(KEY_ADR, adr);
        initialValues.put(KEY_FAVORI, favori);

        return mDb.insert(DATABASE_TABLE, null, initialValues);
    }

    /**
     * Delete the note with the given rowId
     *
     * @param rowId id of note to delete
     * @return true if deleted, false otherwise
     */
    public boolean deleteContact(long rowId) {

        return mDb.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    public void deleteAllContact() {

        mDb.delete(DATABASE_TABLE, null, null);
    }


    /**
     * Return a Cursor over the list of all notes in the database
     *
     * @return Cursor over all notes
     */
    public Cursor fetchAllContact() {


        return mDb.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_NOM,
                KEY_PRENOM, KEY_NUM, KEY_MAIL, KEY_ADR, KEY_FAVORI}, null, null, null, null, KEY_PRENOM + "," + KEY_NOM);
    }

    public Cursor fetchPreferedContact() {
        return mDb.query(DATABASE_TABLE, new String[] {
                KEY_ROWID,
                KEY_NOM,
                KEY_PRENOM,
                KEY_NUM,
                KEY_MAIL,
                KEY_ADR,
                KEY_FAVORI
        }, KEY_FAVORI + "=1", null, null, null, null);
    }

    /**
     * Return a Cursor positioned at the note that matches the given rowId
     *
     * @param rowId id of note to retrieve
     * @return Cursor positioned to matching note, if found
     * @throws SQLException if note could not be found/retrieved
     */
    public Cursor fetchContact(long rowId) throws SQLException {

        Cursor mCursor =

                mDb.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,
                                KEY_NOM, KEY_PRENOM, KEY_NUM, KEY_MAIL, KEY_ADR, KEY_FAVORI}, KEY_ROWID + "=" + rowId, null,
                        null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    /**
     * Update the note using the details provided. The note to be updated is
     * specified using the rowId, and it is altered to use the title and body
     * values passed in
     *
     * @param rowId id of note to update
     * @return true if the note was successfully updated, false otherwise
     */
    public boolean updateContact(long rowId, String nom, String prenom, String num, String mail, String adr, String favori) {


        ContentValues args = new ContentValues();
        args.put(KEY_NOM, nom);
        args.put(KEY_PRENOM, prenom);
        args.put(KEY_NUM, num);
        args.put(KEY_MAIL, mail);
        args.put(KEY_ADR, adr);
        args.put(KEY_FAVORI, favori);

        Log.i("DEBUG", "dans la abse");

        return mDb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }
}
