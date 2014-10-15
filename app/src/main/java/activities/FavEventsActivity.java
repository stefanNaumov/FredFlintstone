package activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.stefan.sportseventsorganizer.R;
import com.telerik.everlive.sdk.core.EverliveApp;
import com.telerik.everlive.sdk.core.result.RequestResult;
import com.telerik.everlive.sdk.core.result.RequestResultCallbackAction;

import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
import android.app.Activity;
import android.widget.GridView;

import models.Event;
import models.EventsListAdapter;
import models.Everlive;
import models.FavEvent;
import models.FavEventsListAdapter;
import sqlite.MySQLiteHelper;

public class FavEventsActivity extends Activity {

    MySQLiteHelper db;
    List<FavEvent> myEventsList;
    FavEventsListAdapter adapter;
    GridView favEventsGrid;
    EverliveApp app;
    List<Event> allEvents;
    List<Event> myEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_events);


        app = Everlive.getEverliveObj();
        app.workWith().data(Event.class).get().executeAsync(new RequestResultCallbackAction<ArrayList<Event>>() {
            @Override
            public void invoke(final RequestResult<ArrayList<Event>> requestResult) {

                if (requestResult.getSuccess()){
                    allEvents = requestResult.getValue();
                    db = new MySQLiteHelper(FavEventsActivity.this);
                    myEventsList = db.getAllFavEvents();
                    myEvents = getMyEvents();
                    adapter = new FavEventsListAdapter(FavEventsActivity.this,myEvents);

                    favEventsGrid = (GridView)findViewById(R.id.grid_fav_events);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            favEventsGrid.setAdapter(adapter);
                        }
                    });
                }
            }
        });
    }

    private List<Event> getMyEvents(){
        List<Event> myList = new ArrayList<Event>();

        for (int i = 0; i < myEventsList.size(); i++){

            FavEvent currFavEvent = myEventsList.get(i);

            for (int j = 0; j < allEvents.size(); j++){
               if (currFavEvent.getEverliteIndex().equals(allEvents.get(j).getId().toString())){
                   myList.add(allEvents.get(j));
                   break;
               }
            }
        }

        return myList;
    }
}