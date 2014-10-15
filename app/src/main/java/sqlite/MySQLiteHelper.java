package sqlite;

/**
 * Created by Rusekov on 14/10/2014.
 */
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import models.FavEvent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "FavEventDB";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_FAVEVENT_TABLE = "CREATE TABLE favEvents ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "everliteIndex TEXT )";

        // create favEvents table
        db.execSQL(CREATE_FAVEVENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older favEvents table if existed
        db.execSQL("DROP TABLE IF EXISTS favEvents");

        // create fresh favEvents table
        this.onCreate(db);
    }
    //---------------------------------------------------------------------

    /**
     * CRUD operations (create "add", read "get", update, delete) favEvents + get all favEvents + delete all favEvents
     */

    // favEvents table name
    private static final String TABLE_FAVEVENTS = "favEvents";

    // favEvents Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_EVERLITEINDEX = "everliteIndex";

    private static final String[] COLUMNS = {KEY_ID,KEY_EVERLITEINDEX};

    public void AddFavEvent(FavEvent favEvent){
        Log.d("AddFavEvent", favEvent.toString());
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_EVERLITEINDEX, favEvent.getEverliteIndex()); // get EverliteIndex

        // 3. insert
        db.insert(TABLE_FAVEVENTS, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    public FavEvent getFavEvent(int id){

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(TABLE_FAVEVENTS, // a. table
                        COLUMNS, // b. column names
                        " id = ?", // c. selections
                        new String[] { String.valueOf(id) }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // 4. build FavEvent object
        FavEvent favEvent = new FavEvent();
        favEvent.setId(Integer.parseInt(cursor.getString(0)));
        favEvent.setEverliteIndex(cursor.getString(1));

        Log.d("getFavEvent("+id+")", favEvent.toString());

        // 5. return favEvent
        return favEvent;
    }

    // Get All FavEvents
    public List<FavEvent> getAllFavEvents() {
        List<FavEvent> favEvents = new ArrayList<FavEvent>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_FAVEVENTS;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build favEvent and add it to list
        FavEvent favEvent = null;
        if (cursor.moveToFirst()) {
            do {
                favEvent = new FavEvent();
                favEvent.setId(Integer.parseInt(cursor.getString(0)));
                favEvent.setEverliteIndex(cursor.getString(1));

                // Add favEvent to favEvents
                favEvents.add(favEvent);
            } while (cursor.moveToNext());
        }

        Log.d("getAllFavEvents()", favEvents.toString());

        return favEvents;
    }

    // Updating single favEvent
    public int updateFavEvent(FavEvent favEvent) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("everliteIndex", favEvent.getEverliteIndex()); // get everliteIndex

        // 3. updating row
        int i = db.update(TABLE_FAVEVENTS, //table
                values, // column/value
                KEY_ID+" = ?", // selections
                new String[] { String.valueOf(favEvent.getId()) }); //selection args

        // 4. close
        db.close();

        return i;
    }

    // Deleting single favEvent
    public void deleteFavEvent(FavEvent favEvent) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_FAVEVENTS,
                KEY_ID+" = ?",
                new String[] { String.valueOf(favEvent.getId()) });

        // 3. close
        db.close();

        Log.d("deleteFavEvent", favEvent.toString());

    }
}