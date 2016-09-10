package com.atm.atm.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.atm.atm.R;

public class EventCreateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_create);
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        LinearLayout rlayout = (LinearLayout) findViewById(R.id.cameraLayout);
        rlayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                print("camera");
                //TODO
                //take pic
                //crop
                //upload
                //return url
            }

        });

    }

    public void onCloseButtonClick(View view) {
        print("close");
        finish();
    }
    public void onCreateButtonClick (View view) {
        //TODO create event
        print("create");
    }
    private void print(String info) {
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
        Log.i("MapsActivity", info);
    }

}
