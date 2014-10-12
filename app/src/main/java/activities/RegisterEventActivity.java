package activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stefan.sportseventsorganizer.R;
import com.telerik.everlive.sdk.core.EverliveApp;

import java.security.Provider;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import models.Event;
import models.Everlive;

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
    EverliveApp app;

    Button cameraStartBtn;
    Button registerEventBtn;
    Button eventDateBtn;

    static TextView calendarView;
    EditText organizerName, organizerPhone,eventTitle,eventSportType,eventContent,
            eventCity;
    String eventLongitude, eventLatitude;
    Date eventDate;
    Image eventImg;

    String name;
    String phone;
    String title;
    String sportType;
    String content;
    String city;

    Event event;
    boolean isLocationFound;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_event_layout);

        event = new Event();
        app = Everlive.getEverliveObj();
        isLocationFound = false;
        calendarView = (TextView)findViewById(R.id.eventDateCalendarView);

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
        currLocation = getLastKnownLocation();
        if (currLocation != null) {
            eventLongitude = String.valueOf(currLocation.getLongitude());
            eventLatitude = String.valueOf(currLocation.getLatitude());
            isLocationFound = true;
        }


        cameraStartBtn = (Button)findViewById(R.id.capturePlaceBtn);
        registerEventBtn = (Button)findViewById(R.id.registerEventBtn);
        eventDateBtn = (Button)findViewById(R.id.eventDatePickBtn);

        cameraStartBtn.setOnClickListener(this);
        registerEventBtn.setOnClickListener(this);
        eventDateBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == cameraStartBtn.getId()){
            Intent i = new Intent("android.media.action.IMAGE_CAPTURE");

            startActivityForResult(i,0);
        }
        else if (view.getId() == registerEventBtn.getId()){
            boolean areFieldsValid = inputValidator();

            if (areFieldsValid && isLocationFound){
                //TODO implement creating Everlive object
                //and add to DB
            }
        }
        else if (view.getId() == eventDateBtn.getId()){
            DialogFragment newFragment = new DatePickerFragment();
            newFragment.show(getFragmentManager(), "datePicker");
        }
    }

    private void addEventToDb(){
        event.setOrganizerName(name);
        event.setOrganizerPhone(phone);
        event.setTitle(title);
        event.setSportType(sportType);
        event.setContent(content);
        event.setCity(city);
        event.setLongitude(eventLongitude);
        event.setLatitude(eventLatitude);
    }

    private Location getLastKnownLocation() {
        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            Location l = locationManager.getLastKnownLocation(provider);
            Log.d(provider,l.toString());
            if (l == null) {
                continue;
            }
            if (bestLocation == null
                    || l.getAccuracy() < bestLocation.getAccuracy()) {
                Log.d("Best location",l.toString());
                bestLocation = l;
            }
        }
        if (bestLocation == null) {
            return null;
        }
        return bestLocation;
    }

    private boolean inputValidator(){
        boolean areFieldsValid = false;
        name = organizerName.getText().toString().trim();
        phone = organizerPhone.getText().toString().trim();
        title = eventTitle.getText().toString().trim();
        sportType = eventSportType.getText().toString().trim();
        content = eventContent.getText().toString().trim();
        city = eventCity.getText().toString().trim();

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

        if (location != null){
            eventLongitude = String.valueOf(currLocation.getLongitude());
            eventLatitude = String.valueOf(currLocation.getLatitude());
        }

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

    private static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        public EditText editText;
        DatePicker dpResult;

        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {

            calendarView .setText(String.valueOf(day) + "/"
                    + String.valueOf(month + 1) + "/" + String.valueOf(year));
        }
    }
}
