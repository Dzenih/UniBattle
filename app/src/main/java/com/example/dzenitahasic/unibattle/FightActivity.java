package com.example.dzenitahasic.unibattle;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.FloatMath;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import objects.Monster;

import static android.os.Build.VERSION_CODES.M;
import static com.example.dzenitahasic.unibattle.GameActivity.player;

public class FightActivity extends AppCompatActivity {

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;
    @Override
    public void onBackPressed() {
        FightActivity.this.finish();
    }
    private Monster monster;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fight);
        String id = getIntent().getStringExtra("MONSTER");
        monster = GameActivity.monsters.get(id);
        ((ImageView) findViewById(R.id.imageView1)).setImageBitmap(monster.getImage());
        GameActivity.player.doDamage(monster.getDamage());
        TextView text1 = (TextView) findViewById(R.id.text1);
        text1.setText(monster.getName());


        db=openOrCreateDatabase("MonsterDB", Context.MODE_PRIVATE, null);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

            @Override
            public void onShake(int count) {
                monster.doDamage(150);
                db.execSQL("INSERT INTO monster VALUES('"+monster.getName()+"');");
                FightActivity.this.finish();
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        mSensorManager.registerListener(mShakeDetector, mAccelerometer,	SensorManager.SENSOR_DELAY_UI);
    }
    @Override
    public void onPause() {
        mSensorManager.unregisterListener(mShakeDetector);
        super.onPause();
    }


}
