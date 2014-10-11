package activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.stefan.sportseventsorganizer.R;

/**
 * Created by Stefan on 10/11/2014.
 */
public class RegisterEventActivity extends Activity implements View.OnClickListener{

    Button cameraStartBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_event_layout);

        cameraStartBtn = (Button)findViewById(R.id.capturePlaceBtn);
        cameraStartBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent i = new Intent("android.media.action.IMAGE_CAPTURE");

        startActivityForResult(i,0);
    }
}
