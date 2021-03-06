package com.example.stefan.sportseventsorganizer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import activities.FavEventsActivity;
import activities.RegisterEventActivity;
import activities.ViewEventsActivity;


public class MyActivity extends Activity implements View.OnClickListener{

    Button viewEventsBtn, regEventBtn,getMyEventsBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewEventsBtn = (Button)findViewById(R.id.viewEvents_btn);
        regEventBtn = (Button)findViewById(R.id.makeEvent_btn);
        getMyEventsBtn = (Button)findViewById(R.id.getMyEvents_btn);

        viewEventsBtn.setOnClickListener(this);
        regEventBtn.setOnClickListener(this);
        getMyEventsBtn.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == viewEventsBtn.getId()){
            Intent i = new Intent(this, ViewEventsActivity.class);
            startActivity(i);
        }
        else if (view.getId() == regEventBtn.getId()){
            Intent i = new Intent(this, RegisterEventActivity.class);
            startActivity(i);
        }
        else if (view.getId() == getMyEventsBtn.getId()){
            Intent i = new Intent(this, FavEventsActivity.class);
            startActivity(i);
        }
    }
}
