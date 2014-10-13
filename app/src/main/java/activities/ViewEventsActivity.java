package activities;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.provider.ContactsContract.PhoneLookup;
import android.database.Cursor;
import android.net.Uri;
import android.widget.ImageView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.InputStream;

import com.example.stefan.sportseventsorganizer.R;
import com.telerik.everlive.sdk.core.EverliveApp;
import com.telerik.everlive.sdk.core.result.RequestResult;

import java.util.ArrayList;

import models.Event;
import models.Everlive;

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

    // Download image from Google Maps

    @Override
    protected void onResume() {
        super.onResume();

        String lat = "48.858235";
        String lng = "2.294571";
        String mapUrl = "http://maps.google.com/maps/api/staticmap?center=" + lat + "," + lng + "&zoom=15&size=200x200&sensor=false";
        new DownloadImageTask((ImageView) findViewById(R.id.imageView1))
                .execute(mapUrl);
        return;
    }

    //public void onClick(View v) {
    //    startActivity(new Intent(this, IndexActivity.class));
    //    finish();
    //}

    class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;

    public DownloadImageTask(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
    }
}



}
