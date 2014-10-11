package activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.stefan.sportseventsorganizer.R;

import java.security.Provider;
import java.sql.Date;

import models.Event;

/**
 * Created by Stefan on 10/11/2014.
 */
//TODO: add DatePicker DatePicker validation
    //add creation of Everlive event object
    //add adding object to everlive database
public class RegisterEventActivity extends Activity implements View.OnClickListener,
        LocationListener{

    protected LocationManager locationManager;
    protected LocationListener locationListener;
    String provider;
    Location currLocation;

    Button cameraStartBtn;
    Button registerEventBtn;
    EditText organizerName, organizerPhone,eventTitle,eventSportType,eventContent,
            eventCity;
    String eventLongitude, eventLatitude;
    Date eventDate;
    Image eventImg;
    Event event;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_event_layout);

        event = new Event();

        organizerName = (EditText)findViewById(R.id.userNameInput);
        organizerPhone = (EditText)findViewById(R.id.userPhoneInput);
        eventTitle = (EditText)findViewById(R.id.eventTitleInput);
        eventSportType = (EditText)findViewById(R.id.eventSportTypeInput);
        eventContent = (EditText)findViewById(R.id.eventContentInput);
        eventCity = (EditText)findViewById(R.id.eventCityInput);

        //Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        provider = locationManager.getBestProvider(criteria, false);
        currLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (currLocation != null) {
            eventLongitude = String.valueOf(currLocation.getLongitude());
            eventLatitude = String.valueOf(currLocation.getLatitude());
        }

        cameraStartBtn = (Button)findViewById(R.id.capturePlaceBtn);
        cameraStartBtn.setOnClickListener(this);
        registerEventBtn = (Button)findViewById(R.id.registerEventBtn);
        registerEventBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        if (view.getId() == cameraStartBtn.getId()){
            Intent i = new Intent("android.media.action.IMAGE_CAPTURE");

            startActivityForResult(i,0);
        }
        else if (view.getId() == registerEventBtn.getId()){
            boolean areFieldsValid = inputValidator();

        }
    }
    private boolean inputValidator(){
        boolean areFieldsValid = false;
        String name = organizerName.getText().toString().trim();
        String phone = organizerPhone.getText().toString().trim();
        String title = eventTitle.getText().toString().trim();
        String sportType = eventSportType.getText().toString().trim();
        String content = eventContent.getText().toString().trim();
        String city = eventCity.getText().toString().trim();

        if (name == null || name.isEmpty() || name == "" || name.matches("Username")){

            Toast.makeText(RegisterEventActivity.this,"Enter valid userName", Toast.LENGTH_LONG).show();
            return false;
        }
        if (phone == null || phone.isEmpty() || phone == "" || phone.matches("Phone Number")){

            Toast.makeText(RegisterEventActivity.this,"Enter valid phone", Toast.LENGTH_LONG).show();
            return false;
        }
        if (title == null || title.isEmpty() || title == "" || title.matches("Event Title")){

            Toast.makeText(RegisterEventActivity.this,"Enter valid event title", Toast.LENGTH_LONG).show();
            return false;
        }
        if (sportType == null || sportType.isEmpty() || sportType == "" || sportType.matches("Sport Type")){

            Toast.makeText(RegisterEventActivity.this,"Enter valid sport type", Toast.LENGTH_LONG).show();
            return false;
        }
        if (content == null || content.isEmpty() || content == "" || content.matches("Event Content")){

            Toast.makeText(RegisterEventActivity.this,"Enter valid content", Toast.LENGTH_LONG).show();
            return false;
        }
        if (city == null || city.isEmpty() || city == "" || city.matches("Event City")){

            Toast.makeText(RegisterEventActivity.this,"Enter valid city", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    @Override
    public void onLocationChanged(Location location) {
        eventLongitude = String.valueOf(currLocation.getLongitude());
        eventLatitude = String.valueOf(currLocation.getLatitude());
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
