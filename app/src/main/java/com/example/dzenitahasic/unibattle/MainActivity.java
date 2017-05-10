package com.example.dzenitahasic.unibattle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;


/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button playBtn = (Button) findViewById(R.id.playBtn);
        Button killedBtn = (Button) findViewById(R.id.killedBtn);
        Button settingsBtn = (Button) findViewById(R.id.settingsBtn);


        killedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent killedActivity = new Intent(getApplicationContext(), KilledMonsters.class);
                MainActivity.this.startActivity(killedActivity);

            }

        });

        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapsActivity = new Intent(getApplicationContext(), GameActivity.class);
                MainActivity.this.startActivity(mapsActivity);

            }

        });

        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent securityActivity = new Intent(getApplicationContext(), SettingsActivity.class);
                MainActivity.this.startActivity(securityActivity);

            }

        });
    }



}