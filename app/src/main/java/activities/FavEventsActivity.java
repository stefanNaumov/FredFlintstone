package activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.stefan.sportseventsorganizer.R;
import java.util.List;
import android.os.Bundle;
import android.app.Activity;

import models.Event;
import models.FavEvent;
import sqlite.MySQLiteHelper;

public class FavEventsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MySQLiteHelper db = new MySQLiteHelper(this);

        /**
         * CRUD Operations
         * */
        // add FavEvents
        db.AddFavEvent(new FavEvent("everliteIndex1"));
        db.AddFavEvent(new FavEvent("everliteIndex2"));
        db.AddFavEvent(new FavEvent("everliteIndex3"));

        // get all books
        List<FavEvent> list = db.getAllFavEvents();

        // delete one book
        db.deleteFavEvent(list.get(0));

        // get all books
        db.getAllFavEvents();
    }
}