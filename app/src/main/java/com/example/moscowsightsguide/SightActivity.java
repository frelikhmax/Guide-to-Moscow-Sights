package com.example.moscowsightsguide;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.MapObjectCollection;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.runtime.image.ImageProvider;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SightActivity extends AppCompatActivity {


    private static int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (count == 0) {
            MapKitFactory.setApiKey("52a77248-8dda-4100-b8ef-b03fd31b1fbc");
            MapKitFactory.initialize(this);
        }
        ++count;

        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_sight);


        Sight sight = getIntent().getParcelableExtra("sight");
        MapView mapview = (MapView) findViewById(R.id.mapview);

        MapObjectCollection mapObjects = mapview.getMap().getMapObjects();
        PlacemarkMapObject placemark = mapObjects.addPlacemark(new Point(sight.getCoordinates().getLatitude(),
                sight.getCoordinates().getLongitude()));
        placemark.setIcon(ImageProvider.fromResource(this, R.drawable.marker));


        mapview.getMap().move(
                new CameraPosition(sight.getCoordinates(), 16.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 5),
                null);

        TextView sightInfo = findViewById(R.id.sightInfo);
        sightInfo.setText(sight.getName());
        if (sight.getPrice() > 0) {
            sightInfo.setText(sightInfo.getText().toString().concat("    ").concat(sight.getPrice() + "₽"));
        }

        Calendar targetOpeningTime = Calendar.getInstance();
        targetOpeningTime.set(Calendar.HOUR_OF_DAY, 0);
        targetOpeningTime.set(Calendar.MINUTE, 0);
        targetOpeningTime.set(Calendar.SECOND, 0);

        Calendar targetClosingTime = Calendar.getInstance();
        targetClosingTime.set(Calendar.HOUR_OF_DAY, 23);
        targetClosingTime.set(Calendar.MINUTE, 59);
        targetClosingTime.set(Calendar.SECOND, 59);


        if (!(sight.getOpeningTime().get(Calendar.HOUR_OF_DAY) == targetOpeningTime.get(Calendar.HOUR_OF_DAY) &&
                sight.getOpeningTime().get(Calendar.MINUTE) == targetOpeningTime.get(Calendar.MINUTE) &&
                sight.getOpeningTime().get(Calendar.SECOND) == targetOpeningTime.get(Calendar.SECOND) &&
                sight.getClosingTime().get(Calendar.HOUR_OF_DAY) == targetClosingTime.get(Calendar.HOUR_OF_DAY) &&
                sight.getClosingTime().get(Calendar.MINUTE) == targetClosingTime.get(Calendar.MINUTE) &&
                sight.getClosingTime().get(Calendar.SECOND) == targetClosingTime.get(Calendar.SECOND)
        )) {
            sightInfo.setText(sightInfo.getText().toString().concat("    ").concat(
                    new SimpleDateFormat("HH:mm").format(sight.getOpeningTime().getTime()).concat(
                            "-").concat(
                            new SimpleDateFormat("HH:mm").format(sight.getClosingTime().getTime())
                    )
            ));
        }

        TextView sightDescription = findViewById(R.id.sightDescription);
        sightDescription.setText(sight.getDescription());
        ImageView imageView = findViewById(R.id.image);
        imageView.setImageResource(sight.getImageResourceId());


    }


    @Override
    protected void onStart() {
        super.onStart();
        MapKitFactory.getInstance().onStart();
        MapView mapview = (MapView) findViewById(R.id.mapview);
        mapview.onStart();
    }


    @Override
    protected void onStop() {
        MapView mapview = (MapView) findViewById(R.id.mapview);
        mapview.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }


}