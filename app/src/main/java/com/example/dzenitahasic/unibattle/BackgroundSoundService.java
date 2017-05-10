package com.example.dzenitahasic.unibattle;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

public class BackgroundSoundService extends Service {
    private static final String TAG = null;
    MediaPlayer player;
    public IBinder onBind(Intent arg0) {

        Log.d("Sound", "bind");
        return null;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        player = MediaPlayer.create(this, R.raw.funeral_march_for_brass);
        player.setLooping(true); // Set looping
        player.setVolume(100,100);
        Log.d("Sound", "create");

    }
    public int onStartCommand(Intent intent, int flags, int startId) {
        player.start();
        Log.d("Sound", "start");
        return Service.START_NOT_STICKY;
    }

    public IBinder onUnBind(Intent arg0) {
        // TO DO Auto-generated method
        return null;
    }

    public void onStop() {

    }
    public void onPause() {

    }
    @Override
    public void onDestroy() {
        player.stop();
        player.release();
    }

    @Override
    public void onLowMemory() {

    }
}