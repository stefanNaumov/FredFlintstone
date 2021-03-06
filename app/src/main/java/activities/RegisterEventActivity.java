package activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stefan.sportseventsorganizer.R;
import com.telerik.everlive.sdk.core.DateTimeUnspecifiedHandling;
import com.telerik.everlive.sdk.core.DateTimeValuesProcessing;
import com.telerik.everlive.sdk.core.EverliveApp;
import com.telerik.everlive.sdk.core.EverliveAppSettings;
import com.telerik.everlive.sdk.core.result.RequestResult;
import com.telerik.everlive.sdk.core.result.RequestResultCallbackAction;

import java.net.URI;
import java.security.Provider;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import models.Event;
import models.Everlive;

/**
 * Created by Stefan on 10/11/2014.
 */

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
    AlertDialog.Builder alert;

    static TextView calendarView;
    EditText organizerName, organizerPhone,eventTitle,eventSportType,eventContent,
            eventCity;
    String eventLongitude, eventLatitude;
    Date eventDate;
    Bitmap eventImg;

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

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        cameraStartBtn = (Button)findViewById(R.id.capturePlaceBtn);
        registerEventBtn = (Button)findViewById(R.id.registerEventBtn);
        eventDateBtn = (Button)findViewById(R.id.eventDatePickBtn);

        cameraStartBtn.setOnClickListener(this);
        registerEventBtn.setOnClickListener(this);
        eventDateBtn.setOnClickListener(this);

        CharSequence message = "You must switch your GPS on in order to register a new event!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER|Gravity.LEFT, 0, 0);

        toast.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1000, this);
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == cameraStartBtn.getId()){
            Intent i = new Intent("android.media.action.IMAGE_CAPTURE");
            if (i.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(i,1);
            }

        }
        else if (view.getId() == eventDateBtn.getId()){
            DialogFragment newFragment = new DatePickerFragment();
            newFragment.show(getFragmentManager(), "datePicker");
        }
        else {
            if (view.getId() == registerEventBtn.getId()) {
                boolean areFieldsValid = inputValidator();

                if (areFieldsValid && isLocationFound) {
                    event.setOrganizerName(name);
                    event.setOrganizerPhone(phone);
                    event.setTitle(title);
                    event.setSportType(sportType);
                    event.setContent(content);
                    event.setCity(city);
                    event.setLongitude(eventLongitude);
                    event.setLatitude(eventLatitude);
                    event.setDate(eventDate);
                    event.setImage(eventImg);

                    Toast toast = Toast.makeText(this,"Added TO DATABASE",Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER|Gravity.LEFT, 0, 0);

                    toast.show();
                    app.workWith().data(Event.class).create(event).executeAsync();

                }
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK){
            Uri selectedImageUri  = data.getData();
            String imgPath = getRealPathFromURI(selectedImageUri);

            Toast.makeText(this, imgPath, Toast.LENGTH_SHORT).show();
            Drawable drawable = Drawable.createFromPath(imgPath);
            eventImg = ((BitmapDrawable) drawable).getBitmap();

            ScrollView rl = (ScrollView)findViewById(R.id.ScrollID);
            rl.setBackground(drawable);

        }
    }

    public Location getLastKnownLocation() {
        List<String> providers = locationManager.getProviders(false);
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

    @Override
    public void onLocationChanged(Location location) {

        if (location != null){

            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            currLocation = getLastKnownLocation();
            eventLongitude = String.valueOf(currLocation.getLongitude());
            eventLatitude = String.valueOf(currLocation.getLatitude());
            isLocationFound = true;
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
//        String status = "";
//
//        switch (i) {
//            case android.location.LocationProvider.AVAILABLE:
//                status = "avaliable";
//                break;
//            case android.location.LocationProvider.TEMPORARILY_UNAVAILABLE:
//                status = "temporary unavaliable";
//                break;
//            case android.location.LocationProvider.OUT_OF_SERVICE:
//                status = "out of service";
//                break;
//            default:
//                break;
//        }

        //Toast.makeText(getBaseContext(), s + "status: " + status, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderEnabled(String s) {
        //Toast.makeText(getBaseContext(),
                //"Provider " + "enabled",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderDisabled(String s) {
        //Toast.makeText(getBaseContext(),
                //"Provider " + "disabled",Toast.LENGTH_LONG).show();
    }

    public static class DatePickerFragment extends DialogFragment
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

            calendarView .setText(String.valueOf(day) + "-"
                    + String.valueOf(month + 1) + "-" + String.valueOf(year));
        }
    }

    private String getRealPathFromURI(Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private boolean inputValidator(){
        Toast toast;
        boolean areFieldsValid = false;
        name = organizerName.getText().toString().trim();
        phone = organizerPhone.getText().toString().trim();
        title = eventTitle.getText().toString().trim();
        sportType = eventSportType.getText().toString().trim();
        content = eventContent.getText().toString().trim();
        city = eventCity.getText().toString().trim();

        DateFormat formatter ;

        if (name == null || name.isEmpty() || name == "" || name.matches("Username")){

            toast = Toast.makeText(RegisterEventActivity.this,"Enter valid userName", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER|Gravity.LEFT, 0, 0);

            toast.show();
            return false;
        }
        if (phone == null || phone.isEmpty() || phone == "" || (!(phone.matches("^(08)([0-9]{8})|([+]3598)[0-9]{8}$")))){

            toast = Toast.makeText(RegisterEventActivity.this,"Enter valid phone", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER|Gravity.LEFT, 0, 0);

            toast.show();
            return false;
        }
        if (title == null || title.isEmpty() || title == "" || title.matches("Event Title")){

            toast = Toast.makeText(RegisterEventActivity.this,"Enter valid event title", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER|Gravity.LEFT, 0, 0);

            toast.show();
            return false;
        }
        if (sportType == null || sportType.isEmpty() || sportType == "" || sportType.matches("Sport Type")){

            toast = Toast.makeText(RegisterEventActivity.this,"Enter valid sport type", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER|Gravity.LEFT, 0, 0);

            toast.show();
            return false;
        }
        if (content == null || content.isEmpty() || content == "" || content.matches("Event Content")){

            toast = Toast.makeText(RegisterEventActivity.this,"Enter valid content", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER|Gravity.LEFT, 0, 0);

            toast.show();
            return false;
        }
        if (city == null || city.isEmpty() || city == "" || city.matches("Event City")){

            toast = Toast.makeText(RegisterEventActivity.this,"Enter valid city", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER|Gravity.LEFT, 0, 0);

            toast.show();
            return false;
        }
        formatter = new SimpleDateFormat("dd-MM-yyyy");
        try {
            eventDate = (Date)formatter.parse(calendarView.getText().toString());
            Log.d("DATE",eventDate.toString());
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}

