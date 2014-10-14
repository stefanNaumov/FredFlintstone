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

/**
 * Created by Stefan on 10/13/2014.
 */
public class EventsListAdapter extends BaseAdapter{

    private Context context;
    private ArrayList<Event> events;

    @Override
    public int getCount() {

        return events.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public EventsListAdapter(Context context, ArrayList<Event> events){

        this.context = context;
        this.events = events;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Log.d("ZAQVKAAAA","GETVIEWWWWW");

        LayoutInflater inflater = (LayoutInflater)context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.view_events_layout, null);
        }

        Event eventItem = events.get(position);

        TextView title = (TextView)convertView.findViewById(R.id.grid_title);
        TextView sportType = (TextView)convertView.findViewById(R.id.grid_sportType);
        TextView city = (TextView)convertView.findViewById(R.id.grid_city);

        title.setText(eventItem.getTitle());
        sportType.setText(eventItem.getSportType());
        city.setText(eventItem.getCity());

        Log.d("ZAQVKAAAA",eventItem.getTitle());

        return convertView;
    }

}
