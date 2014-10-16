package activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
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
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import fragments.EventDetailsFragment;
import fragments.MyFavEventDetailsFragment;
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
    FragmentManager fragManager;
    FragmentTransaction fragTrans;
    MyFavEventDetailsFragment frag;
    LinearLayout layout;
    TextView uuidView;
    String uuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_events);

        fragManager = getFragmentManager();
        favEventsGrid = (GridView)findViewById(R.id.grid_fav_events);

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

    public void FavEventsDetailsOnClick(View v){
        Log.d("CLICKED","IS CLICKED");
        Bundle eventDetails = new Bundle();
        fragTrans = fragManager.beginTransaction();
        frag = new MyFavEventDetailsFragment();
        layout = (LinearLayout)v;
        uuidView = (TextView)layout.getChildAt(0);
        uuid = uuidView.getText().toString();

        Event myFavDetailsEvent = getEventById(uuid);

        eventDetails.putString("Title",myFavDetailsEvent.getTitle());
        eventDetails.putString("SportType",myFavDetailsEvent.getSportType());
        eventDetails.putString("City",myFavDetailsEvent.getCity());
        eventDetails.putString("Content",myFavDetailsEvent.getContent());
        eventDetails.putString("OrganizerName",myFavDetailsEvent.getOrganizerName());
        eventDetails.putString("OrganizerPhone",this.contactNameIfExists(this,myFavDetailsEvent.getOrganizerPhone()));
        eventDetails.putString("OriginalOrganizerPhone",myFavDetailsEvent.getOrganizerPhone());

        frag.setArguments(eventDetails);
        fragTrans.add(R.id.activity_fav_layout,frag,"MyFavEventsDetails");

        fragTrans.addToBackStack(null);

        fragTrans.commit();

    }

    private Event getEventById(String uuid){
        for (int i = 0; i < myEvents.size(); i++){
            if (myEvents.get(i).getId().toString().equals(uuid)){
                return myEvents.get(i);
            }
        }
        return null;
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

    public String contactNameIfExists(Activity _activity, String number) {
        if (number != null) {
            Uri lookupUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));
            String[] mPhoneNumberProjection = { ContactsContract.PhoneLookup._ID, ContactsContract.PhoneLookup.NUMBER, ContactsContract.PhoneLookup.DISPLAY_NAME };
            Cursor cur = _activity.getContentResolver().query(lookupUri, mPhoneNumberProjection, null, null, null);
            try {
                if (cur.moveToFirst()) {
                    return cur.getString(cur.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
                }
            } finally {
                if (cur != null)
                    cur.close();
            }
            return number;
        } else {
            return null;
        }
    }
}