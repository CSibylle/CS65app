package edu.dartmouth.cs.memosnap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Class to help perform database operations for the snap.
 *
 * Created by Devin on 3/3/2015.
 */
public class SnapDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "snap.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME_SNAPS = "snaps";
    public static final String KEY_ROWID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_TYPE = "type";
    public static final String KEY_DATETIME = "date_time";
    public static final String KEY_TAG = "tag";
    public static final String KEY_PHOTO = "photo";
    public static final String KEY_NOTE = "note";
    public static final String KEY_RECORDING = "recording";
    public static final String KEY_LOCATION = "location";

    // Add location
    public String[] allColumns = {KEY_ROWID, KEY_NAME, KEY_TYPE, KEY_DATETIME, KEY_TAG, KEY_PHOTO,
                                    KEY_NOTE, KEY_RECORDING};


    // Add location
    public static final String CREATE_TABLE_SNAPS = "CREATE TABLE IF NOT EXISTS "
            + TABLE_NAME_SNAPS
            + " ("
            + KEY_ROWID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_NAME
            + " TEXT, "
            + KEY_TYPE
            + " TEXT, "
            + KEY_DATETIME
            + " TEXT, "
            + KEY_TAG
            + " TEXT, "
            + KEY_PHOTO
            + " BLOB, "
            + KEY_NOTE
            + " TEXT, "
            + KEY_RECORDING
            + " BLOB " + ");";



    public SnapDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SNAPS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.v(SnapDBHelper.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS ");
        onCreate(db);
    }

    public long insertEntry(Snap entry) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, entry.getName());
        values.put(KEY_TYPE, entry.getName());
        values.put(KEY_DATETIME, entry.getName());
        values.put(KEY_TAG, entry.getName());
        values.put(KEY_PHOTO, entry.getName());
        values.put(KEY_NOTE, entry.getName());
        values.put(KEY_RECORDING, entry.getName());
        //values.put(KEY_LOCATION, entry.getLocation());

        long insertId = db.insert(TABLE_NAME_SNAPS, null,
                values);
        Cursor cursor = db.query(TABLE_NAME_SNAPS,
                allColumns, KEY_ROWID + " = "  + insertId, null,
                null, null, null);
        cursor.moveToFirst();

        return insertId;
    }

    public void removeEntry(long id) {
        ;
    }

    public Snap fetchEntryByIndex(long id) {
        return null;
    }

    public Cursor fetchEntries() {
        return null;
    }

    private Snap cursorToEntry(Cursor cursor) {
        Snap entry = new Snap();
        return null;
    }
}
