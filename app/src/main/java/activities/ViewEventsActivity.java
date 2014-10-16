package activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.AttributeSet;
import android.util.Log;
import android.provider.ContactsContract.PhoneLookup;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.InputStream;

import com.example.stefan.sportseventsorganizer.R;
import com.telerik.everlive.sdk.core.EverliveApp;
import com.telerik.everlive.sdk.core.result.RequestResult;
import com.telerik.everlive.sdk.core.result.RequestResultCallbackAction;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import fragments.EventDetailsFragment;
import models.Event;
import models.EventsListAdapter;
import models.Everlive;
import models.FavEvent;
import sqlite.MySQLiteHelper;

import android.provider.ContactsContract.PhoneLookup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Stefan on 10/11/2014.
 */

public class ViewEventsActivity extends Activity {

    EverliveApp app;
    RequestResult<ArrayList<Event>> requestResult;
    GridView eventsGrid;
    EventsListAdapter adapter;
    ArrayList<Event> events;
    FragmentManager fmanager;
    FragmentTransaction ftrans;
    EventDetailsFragment frag;
    View viewForButtonClickable;
    Button clickableBtn;
    LayoutInflater inflaterForBtnClickable;
    LinearLayout layout;
    TextView uuidView;
    String uuid;
    MySQLiteHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events_listv_view);

        app = Everlive.getEverliveObj();
        eventsGrid = (GridView)findViewById(R.id.grid_events);
        fmanager = getFragmentManager();
        inflaterForBtnClickable = getLayoutInflater();

        viewForButtonClickable = inflaterForBtnClickable.inflate(R.layout.view_events_layout,null);
        clickableBtn = (Button)viewForButtonClickable.findViewById(R.id.eventDetails_btn);


        app.workWith().data(Event.class).get().executeAsync(new RequestResultCallbackAction<ArrayList<Event>>() {
           @Override
           public void invoke(final RequestResult<ArrayList<Event>> requestResult) {

               if (requestResult.getSuccess()){
                    events = requestResult.getValue();
                    adapter = new EventsListAdapter(ViewEventsActivity.this,events);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            eventsGrid.setAdapter(adapter);
                        }
                    });
               }
           }
       });
 }



    public void listBtnOnClick(View v){

        ftrans = fmanager.beginTransaction();
        frag = new EventDetailsFragment();

        layout = (LinearLayout)v.getParent();
        uuidView = (TextView)layout.getChildAt(0);
        uuid = uuidView.getText().toString();

        Event detailsEvent = getEventById(uuid);

        Bundle eventDetails = new Bundle();
        eventDetails.putString("Title",detailsEvent.getTitle());
        eventDetails.putString("SportType",detailsEvent.getSportType());
        eventDetails.putString("City",detailsEvent.getCity());
        eventDetails.putString("Date",detailsEvent.getDate().toString());
        eventDetails.putString("Content",detailsEvent.getContent());
        eventDetails.putString("OrganizerName",detailsEvent.getOrganizerName());
        eventDetails.putString("OrganizerPhone",this.contactNameIfExists(this,detailsEvent.getOrganizerPhone()));
        eventDetails.putString("Longitude",detailsEvent.getLongitude());
        eventDetails.putString("Latitude",detailsEvent.getLatitude());
        eventDetails.putString("OriginalOrganizerPhone",detailsEvent.getOrganizerPhone());


        frag.setArguments(eventDetails);

        ftrans.add(R.id.event_list_layout,frag,"EventDetailsFrag");
        ftrans.addToBackStack(null);

        ftrans.commit();
    }

    public void addToDbBtn(View v){
        db = new MySQLiteHelper(ViewEventsActivity.this);
        db.AddFavEvent(new FavEvent(uuid));
        Toast.makeText(ViewEventsActivity.this,"Added to my favourites",Toast.LENGTH_LONG).show();
    }

    private Event getEventById(String uuid){
        for (int i = 0; i < events.size(); i++){
             if (events.get(i).getId().toString().equals(uuid)){
                 return events.get(i);
             }
        }
        return null;
    }

    // return contact Display_Name if phone number passed as string already exist in database
    public String contactNameIfExists(Activity _activity, String number) {
        if (number != null) {
            Uri lookupUri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));
            String[] mPhoneNumberProjection = { PhoneLookup._ID, PhoneLookup.NUMBER, PhoneLookup.DISPLAY_NAME };
            Cursor cur = _activity.getContentResolver().query(lookupUri, mPhoneNumberProjection, null, null, null);
            try {
                if (cur.moveToFirst()) {
                    return cur.getString(cur.getColumnIndex(PhoneLookup.DISPLAY_NAME));
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



    //public void onClick(View v) {
    //    startActivity(new Intent(this, IndexActivity.class));
    //    finish();
    //}






}
