package fragments;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.stefan.sportseventsorganizer.R;

import java.io.InputStream;

/**
 * Created by Stefan on 10/14/2014.
 */
public class EventDetailsFragment extends Fragment {
    TextView title,sportType,city,date,content,orgName,orgPhone,longitude,latitude;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.events_layout_detailed, container,false);
        Bundle args = getArguments();

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
        return v;
    }

    // Download image from Google Maps

    @Override
    public void onResume() {
        super.onResume();

        String lat = "48.858235";
        String lng = "2.294571";
        String mapUrl = "http://maps.google.com/maps/api/staticmap?center=" + latitude + "," + longitude + "&zoom=15&size=200x200&sensor=false";
        new DownloadImageTask((ImageView) getView().findViewById(R.id.imageView1))
                .execute(mapUrl);
        return;
    }

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
