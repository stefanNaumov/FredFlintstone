package fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.stefan.sportseventsorganizer.R;

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
}
