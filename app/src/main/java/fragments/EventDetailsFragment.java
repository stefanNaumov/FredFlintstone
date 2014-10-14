package fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.stefan.sportseventsorganizer.R;

/**
 * Created by Stefan on 10/14/2014.
 */
public class EventDetailsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.events_layout_detailed, container,false);

        return v;
    }
}
