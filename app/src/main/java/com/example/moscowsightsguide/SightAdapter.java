package com.example.moscowsightsguide;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SightAdapter extends ArrayAdapter<Sight> {

    private final Context context;
    private final Sight[] sights;

    public SightAdapter(Context context, Sight[] sights) {
        super(context, R.layout.item_sights_list, sights);
        this.context = context;
        this.sights = sights;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("ViewHolder") View view = inflater.inflate(R.layout.item_sights_list, parent, false);

        TextView info = (TextView) view.findViewById(R.id.itemInfo);
        info.setText(this.sights[position].getName());
        if (sights[position].getPrice() > 0) {
            info.setText(info.getText().toString().concat("    ").concat(sights[position].getPrice() + "₽"));
        }

        Calendar targetOpeningTime = Calendar.getInstance();
        targetOpeningTime.set(Calendar.HOUR_OF_DAY, 0);
        targetOpeningTime.set(Calendar.MINUTE, 0);
        targetOpeningTime.set(Calendar.SECOND, 0);

        Calendar targetClosingTime = Calendar.getInstance();
        targetClosingTime.set(Calendar.HOUR_OF_DAY, 23);
        targetClosingTime.set(Calendar.MINUTE, 59);
        targetClosingTime.set(Calendar.SECOND, 59);


        if (!(sights[position].getOpeningTime().get(Calendar.HOUR_OF_DAY) == targetOpeningTime.get(Calendar.HOUR_OF_DAY) &&
                sights[position].getOpeningTime().get(Calendar.MINUTE) == targetOpeningTime.get(Calendar.MINUTE) &&
                sights[position].getOpeningTime().get(Calendar.SECOND) == targetOpeningTime.get(Calendar.SECOND) &&
                sights[position].getClosingTime().get(Calendar.HOUR_OF_DAY) == targetClosingTime.get(Calendar.HOUR_OF_DAY) &&
                sights[position].getClosingTime().get(Calendar.MINUTE) == targetClosingTime.get(Calendar.MINUTE) &&
                sights[position].getClosingTime().get(Calendar.SECOND) == targetClosingTime.get(Calendar.SECOND)
        )) {
            info.setText(info.getText().toString().concat("    ").concat(
                    new SimpleDateFormat("HH:mm").format(sights[position].getOpeningTime().getTime()).concat(
                            "-").concat(
                            new SimpleDateFormat("HH:mm").format(sights[position].getClosingTime().getTime())
                    )
            ));
        }


        TextView distance = (TextView) view.findViewById(R.id.itemDistance);
        distance.setText(this.sights[position].getDistance() + "");

        return view;
    }
}

