package fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.gesture.Gesture;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebStorage;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.stefan.sportseventsorganizer.R;

import java.io.InputStream;

import activities.ViewEventsActivity;

/**
 * Created by Stefan on 10/14/2014.
 */
public class EventDetailsFragment extends Fragment {
    TextView title,sportType,city,date,content,orgName,orgPhone;
    String longitude,latitude;
    String originalPhoneNumber;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.events_layout_detailed, container,false);
        Bundle args = getArguments();

        context = container.getContext();

        originalPhoneNumber = args.getString("OriginalOrganizerPhone");

        title = (TextView)v.findViewById(R.id.event_details_title);
        sportType = (TextView)v.findViewById(R.id.event_details_sportType);
        city = (TextView)v.findViewById(R.id.event_details_city);
        date = (TextView)v.findViewById(R.id.event_details_date);
        content = (TextView)v.findViewById(R.id.event_details_content);
        orgName = (TextView)v.findViewById(R.id.event_details_name);
        orgPhone = (TextView)v.findViewById(R.id.event_details_phone);


        title.setText(args.getString("Title"));
        sportType.setText(args.getString("SportType"));
        city.setText(args.getString("City"));
        date.setText(args.getString("Date"));
        content.setText(args.getString("Content"));
        orgName.setText(args.getString("OrganizerName"));
        orgPhone.setText(args.getString("OrganizerPhone"));
        longitude = args.getString("Longitude");
        latitude = args.getString("Latitude");

        orgPhone.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (SlowConnection()) {
                    InformForSlowConnection();
                }
                else {

                    String callString = "tel:" + originalPhoneNumber;
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse(callString));
                    startActivity(callIntent);
                }
                return true;
            }
        });
        return v;
    }

    // Download image from Google Maps

    @Override
    public void onResume() {
        super.onResume();
        Log.d("LATITUDE",latitude.toString());
        Log.d("LONGITUDE",longitude.toString());
        String mapUrl = "http://maps.google.com/maps/api/staticmap?center=" + latitude + "," + longitude +
                "&zoom=18&size=680x400&markers=size:mid%7color:blue%7Clabel:S%7C" + latitude + "," + longitude;
        new DownloadImageTask((ImageView) getView().findViewById(R.id.imageView1))
                .execute(mapUrl);
        return;
    }

    class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage = (ImageView)getView().findViewById(R.id.imageView1);

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

    public boolean SlowConnection() {

        TelephonyManager myTelephonyManager = (TelephonyManager)
                this.context.getSystemService(Context.TELEPHONY_SERVICE);
        int networkType = myTelephonyManager.getNetworkType();
        switch (networkType) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return true;
            default:
                return false;
        }
    }

    public void InformForSlowConnection(){
        CharSequence text = "This App needs Internet Connection. The call can not be made in 2G";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(this.context, text, duration);
        toast.show();
    }
}
