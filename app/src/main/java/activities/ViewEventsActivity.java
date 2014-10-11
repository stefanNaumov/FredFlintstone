package activities;

import android.app.Activity;
import android.os.Bundle;

import com.example.stefan.sportseventsorganizer.R;

/**
 * Created by Stefan on 10/11/2014.
 */
public class ViewEventsActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_events_layout);
    }
}
