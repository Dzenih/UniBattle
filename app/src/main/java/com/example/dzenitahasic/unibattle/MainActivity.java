package com.example.dzenitahasic.unibattle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
        Button settingsBtn = (Button) findViewById(R.id.settingsBtn);

        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newActivity1 = new Intent(getApplicationContext(), MapsActivity.class);
                MainActivity.this.startActivity(newActivity1);
                MainActivity.this.finish();

            }

        });

        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newActivity2 = new Intent(getApplicationContext(), SettingsActivity.class);
                MainActivity.this.startActivity(newActivity2);
                MainActivity.this.finish();

            }

        });
    }

    /**
     * This method displays the given quantity value on the screen.
     */

    private void startGame(View view) {

    }


}