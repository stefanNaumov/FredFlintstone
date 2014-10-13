package activities;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;

import com.example.stefan.sportseventsorganizer.R;
import com.telerik.everlive.sdk.core.EverliveApp;
import com.telerik.everlive.sdk.core.result.RequestResult;

import java.util.ArrayList;

import models.Event;
import models.Everlive;
import android.provider.ContactsContract.PhoneLookup;
/**
 * Created by Stefan on 10/11/2014.
 */
//TODO implement getting an array of Events from DB
   // and make a custom ListView
public class ViewEventsActivity extends Activity {

    EverliveApp app;
    RequestResult<ArrayList<Event>> requestResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_events_layout);

        //app = Everlive.getEverliveObj();
        app = new EverliveApp("Se1uyHp5A8LQJMr6");
       // requestResult = app.workWith().data(Event.class).getAll().executeSync();
        new Thread(new Runnable() {

            @Override
            public void run() {
                RequestResult res = app.workWith().data(Event.class)
                        .getById("869c95b0-52b3-11e4-ade6-217b597a0541").executeSync();

                if (res.getSuccess()){
                    Event ev = (Event)res.getValue();

                    System.out.println(ev.getTitle());
                }
                else{
                    System.out.println(res.getError().toString());
                }
                Event e = ((Event) res.getValue());
                Log.d("NAME",e.getTitle());
            }
        }).start();


        //for (Event event : requestResult.getValue()) {
            //result = app.workWith().data(Event.class).getCount().executeSync();
            //Log.i("App_name", "retrieved count: " + event.getTitle());
       //}
    }


}
