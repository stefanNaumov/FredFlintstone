package models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.stefan.sportseventsorganizer.R;

/**
 * Created by Stefan on 10/13/2014.
 */
public class EventsListView extends BaseAdapter{

    private Context context;
    private int[] imageIds;

    public EventsListView(Context context, int[] images) {
        this.context = context;
        this.imageIds = images;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View grid;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null) {
            grid = new View(context);
            grid = inflater.inflate(R.layout.view_events_layout, null);

            //ImageView imageView = (ImageView)grid.findViewById(R.id.grid_image);
            //imageView.setImageResource(imageIds[position]);
        }
        else {
            grid = (View) view;
        }

        return grid;
    }
}
