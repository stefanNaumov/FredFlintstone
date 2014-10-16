package fragments;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.stefan.sportseventsorganizer.R;

/**
 * Created by Stefan on 10/16/2014.
 */
public class MyFavEventDetailsFragment extends Fragment{
    TextView title,sportType,city,date,content,orgName,orgPhone;

    String originalPhoneNumber;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_favevents_detailed_layout, container,false);
        Bundle args = getArguments();

        originalPhoneNumber = args.getString("OriginalOrganizerPhone");

        title = (TextView)v.findViewById(R.id.myfav_event_title);
        sportType = (TextView)v.findViewById(R.id.myfav_event_sportType);
        city = (TextView)v.findViewById(R.id.myfav_event_city);
        content = (TextView)v.findViewById(R.id.myfav_event_content);
        orgName = (TextView)v.findViewById(R.id.myfav_event_organizerName);
        orgPhone = (TextView)v.findViewById(R.id.myfav_event_organizerPhone);


        title.setText(args.getString("Title"));
        sportType.setText(args.getString("SportType"));
        city.setText(args.getString("City"));
        content.setText(args.getString("Content"));
        orgName.setText(args.getString("OrganizerName"));
        orgPhone.setText(args.getString("OrganizerPhone"));

        return v;
    }
}
