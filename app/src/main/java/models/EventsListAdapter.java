package models;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.stefan.sportseventsorganizer.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stefan on 10/13/2014.
 */
public class EventsListAdapter extends BaseAdapter{

    private Context context;
    private List<Event> events;

    @Override
    public int getCount() {

        return events.size();
    }

    @Override
    public Object getItem(int i) {
        return events.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public EventsListAdapter(Context context, List<Event> events){

        this.context = context;
        this.events = events;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater)context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.view_events_layout, null);
        }

        Event eventItem = events.get(position);

        TextView uuid = (TextView)convertView.findViewById(R.id.grid_event_uuid);
        TextView title = (TextView)convertView.findViewById(R.id.grid_title);
        TextView sportType = (TextView)convertView.findViewById(R.id.grid_sportType);
        TextView city = (TextView)convertView.findViewById(R.id.grid_city);

        uuid.setText(eventItem.getId().toString());
        title.setText(eventItem.getTitle());
        sportType.setText(eventItem.getSportType());
        city.setText(eventItem.getCity());

        return convertView;
    }

}
