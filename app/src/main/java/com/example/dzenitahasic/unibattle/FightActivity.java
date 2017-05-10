package com.example.dzenitahasic.unibattle;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.FloatMath;
import android.util.Log;

import objects.Monster;

import static android.os.Build.VERSION_CODES.M;
import static com.example.dzenitahasic.unibattle.GameActivity.player;

public class FightActivity extends AppCompatActivity {

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;
    @Override
    public void onBackPressed() {
        Intent gameActivity = new Intent(getApplicationContext(), GameActivity.class);
        FightActivity.this.startActivity(gameActivity );
        FightActivity.this.finish();
    }
    private Monster monster;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fight);
        String id = getIntent().getStringExtra("MONSTER");
        monster = GameActivity.monsters.get(id);
        GameActivity.player.doDamage(monster.getDamage());



        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

            @Override
            public void onShake(int count) {
                monster.doDamage(150);
                Intent gameActivity = new Intent(getApplicationContext(), GameActivity.class);
                FightActivity.this.startActivity(gameActivity );
                FightActivity.this.finish();
            }
        });
    }


}
