package com.example.moscowsightsguide;


import androidx.annotation.NonNull;

import android.Manifest;


import com.yandex.mapkit.geometry.Point;

import android.location.Location;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class SightsListActivity extends AppCompatActivity implements LocationListenerInterface {

    Sight[] sights = {new Sight("Lefortovo Park", "Lefortovo Park is a historical" +
            " and natural monument of architecture and landscape art, built at the beginning of" +
            " the 18th century.", 0,
            new Point(55.764291, 37.690815)),

            new Sight("BMSTU", "Bauman Moscow State Technical University - " +
                    "Russian national research university, scientific center, " +
                    "especially valuable object of cultural heritage " +
                    "of the peoples of Russia", 300000,
                    new Point(55.765909, 37.685103), 8, 0, 21, 0),

            new Sight("BMSTU's Sports Complex", "Sports complex of BMSTU",
                    150, new Point(55.77238, 37.697869), 7, 30, 22, 0),

            new Sight("Sports complex \"Metallurg\"", "Sports complex on the Yauza River", 0,
                    new Point(55.774452, 37.70108)),

            new Sight("Subway", "A great place to have a good time",
                    500, new Point(55.771589, 37.682107), 12, 0, 2, 0),

            new Sight("Red & White", "A store that sells not very high quality, but" +
                    " not expensive stationery supplies.", 400, new Point(55.774274, 37.688684), 9, 0, 22, 5)
    };

    private LocationManager locationManager;
    private MyLocationListener myLocationListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        sights[0].setImageResourceId(R.drawable.lefortovsky_park);
        sights[1].setImageResourceId(R.drawable.mgtu_baum);
        sights[2].setImageResourceId(R.drawable.fv_mgtu);
        sights[3].setImageResourceId(R.drawable.metallurg);
        sights[4].setImageResourceId(R.drawable.subway);
        sights[5].setImageResourceId(R.drawable.kb);


        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_sights_list);
        TextView sightListGreetings = findViewById(R.id.sightListGreetings);
        if (!sightListGreetings.getText().equals("Greetings ") &&
                !sightListGreetings.getText().equals("Greetings Incognito")) {
            User user;
            try {
                user = (User) getIntent().getSerializableExtra("user");
                if (user.getFirstName().replaceAll(" ", "").isEmpty()) {
                    user.setFirstName("Incognito");
                }
            } catch (Exception e) {
                user = new User("", "Incognito", "");
            }
            sightListGreetings.setText(getString(R.string.sightsListActivityGreetings).
                    concat(user.getFirstName()));
        }
        ListView mainLV = findViewById(R.id.mainLV);
        SightAdapter sightAdapter = new SightAdapter(this, sights);
        mainLV.setAdapter(sightAdapter);

        mainLV.setOnItemClickListener((parent, view, position, id) -> {
            Sight sight = (Sight) parent.getItemAtPosition(position);


            Intent intent = new Intent(SightsListActivity.this, SightActivity.class);

            intent.putExtra("sight", sight);
            startActivity(intent);

        });


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        myLocationListener = new MyLocationListener();
        myLocationListener.setLocationListenerInterface(this);
        checkPermission();


        ToggleButton toggleButton = findViewById(R.id.toggle);
        toggleButton.setOnClickListener(v -> {
            if (toggleButton.isChecked()) {
                toggleButton.setText(toggleButton.getTextOn());
                toggleButton.setBackground(ContextCompat.getDrawable(this, R.drawable.toggle_button_selector));
                float[] results = new float[sights.length];

                for (int i = 0; i < sights.length; ++i) {

                    Location.distanceBetween(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLatitude(),
                            locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLongitude(),
                            sights[i].getCoordinates().getLatitude(), sights[i].getCoordinates().getLongitude(),
                            results);
                    sights[i].setDistance((int) results[0]);
                }
                sightAdapter.notifyDataSetChanged();


            } else {
                toggleButton.setText(toggleButton.getTextOff());
                toggleButton.setBackground(ContextCompat.getDrawable(this, R.drawable.toggle_button_selector));
                for (int i = 0; i < sights.length; ++i) {
                    sights[i].setDistance(0);
                }
                sightAdapter.notifyDataSetChanged();
            }
        });


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults[0] == RESULT_OK) {
            checkPermission();
        } else {
            Toast.makeText(this, "No GPS permission", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2, 10, myLocationListener);
        }
    }


    @Override
    public void onLocationChanged(Location loc) {
    }
}