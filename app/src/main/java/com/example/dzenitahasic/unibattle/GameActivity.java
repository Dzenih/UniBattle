package com.example.dzenitahasic.unibattle;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;

import objects.Monster;
import objects.Player;

import static android.os.Build.VERSION_CODES.M;
import static com.example.dzenitahasic.unibattle.R.drawable.monster;
import static com.example.dzenitahasic.unibattle.R.id.map;
import static com.google.android.gms.analytics.internal.zzy.B;
import static com.google.android.gms.analytics.internal.zzy.m;
import static com.google.android.gms.analytics.internal.zzy.p;

public class GameActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;


    @Override
    public void onBackPressed() {
        GameActivity.this.finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        permissionCheck();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);
        if(player == null)
        {
            player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.player_icon), 100, 12);
        }
        if(monsters.isEmpty())
        {
            createRandomMonster(new LatLng(55.370468, 10.428188)); // starbucks
            createRandomMonster(new LatLng(55.369967, 10.428477)); // somewhere
            createRandomMonster(new LatLng(55.369149, 10.428066)); // canteen
            createRandomMonster(new LatLng(55.367320, 10.430752)); // toilet
        }

    }
    private LocationManager locationManager;
    private LocationUpdateListener listener;


    private Marker playerMarker;
    public static Player player;

    public static HashMap<String, Monster> monsters = new HashMap();
    private ArrayList<Marker> monsterMarkers = new ArrayList();

    private static final int ICON_HEIGHT = 90;
    private static final int ICON_WIDTH = 80;

    @Override
    public void onMapReady(GoogleMap googleMap) {


        mMap = googleMap;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }

        LatLng sduStarbucks = new LatLng(55.370468, 10.428188);

        addAllMonstersToMap();

        Log.d("Game", "Sensor changed");

        mMap.setMinZoomPreference(16);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sduStarbucks));
        mMap.setMyLocationEnabled(false);

        playerMarker = mMap.addMarker(new MarkerOptions().title("HP: "+player.getHealthValue()).position(sduStarbucks).icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("player_icon",ICON_WIDTH,ICON_HEIGHT))));

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        listener = new LocationUpdateListener();
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, listener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,  listener);


    }

    private String[] names = {"Ulrik", "Per", "Slemme Klemme"};
    private String[] monsterPics = {"blue", "orange", "orc"};
    private void createRandomMonster(LatLng position) {

        int randomChoice = (int)(Math.random()*3);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),getResources().getIdentifier(monsterPics[randomChoice], "drawable", getPackageName()));

        Monster monster = new Monster( bitmap, (int)(50+Math.random()*50),(int)(3+Math.random()*5),names[randomChoice], position);
        monsters.put(monster.getID(), monster);

    }
    private void addAllMonstersToMap()
    {
        for(Monster monster : monsters.values())
        {
            if(monster.getHealthValue()>0)
            {
                MarkerOptions option = new MarkerOptions();
                option.position(monster.getPosition());
                option.title("Unknown evil.. MONSTER!");
                option.icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("monster_icon",ICON_WIDTH,ICON_HEIGHT)));
                option.snippet(monster.getID());

                monsterMarkers.add(mMap.addMarker(option));
            }

        }


    }
    private void permissionCheck() {
        //Checks if the app can use the sensor if it is anything but PERMISSION_GRANTED then it requests access.
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                //Can be used to show a dialog explaining why the app needs the permission, it will return true if the user has denied it earlier.
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //You now have access to the sensor, so here you can call the method to set it up.

                } else {
                    //Here you can show a Dialog box telling them the impact of denying access.
                }
            }
            default:
                System.out.println("The requestCode was unhandled.");
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    class LocationUpdateListener implements LocationListener{


        @Override
        public void onLocationChanged(Location location) {
            LatLng position = new LatLng(location.getLatitude(),location.getLongitude());
            mMap.moveCamera(CameraUpdateFactory.newLatLng(position));
           // playerMarker.position(position);
            playerMarker.setPosition(position);



            for(Marker marker : monsterMarkers)
            {
                float[] distance = new float[1];
                Location.distanceBetween(marker.getPosition().latitude,marker.getPosition().longitude,position.latitude, position.longitude,distance);
                Monster monster = monsters.get(marker.getSnippet());
                if(distance[0]<40&&monster.getHealthValue()>0) // less than approx 40 meters then start a fight!
                {
                    Intent fightActivity = new Intent(getApplicationContext(), FightActivity.class);
                    fightActivity.putExtra("MONSTER",marker.getSnippet());
                    GameActivity.this.startActivity(fightActivity );
                    GameActivity.this.finish();
                    break;
                }
            }


        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }

    public Bitmap resizeMapIcons(String iconName, int width, int height){
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(),getResources().getIdentifier(iconName, "drawable", getPackageName()));
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false);
        return resizedBitmap;
    }
}
