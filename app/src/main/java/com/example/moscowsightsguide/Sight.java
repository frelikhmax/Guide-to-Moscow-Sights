package com.example.moscowsightsguide;

import java.util.Calendar;

import android.os.Parcel;
import android.os.Parcelable;

import com.yandex.mapkit.geometry.Point;

public class Sight implements Parcelable {
    private String name;
    private String description;
    private Calendar openingTime;

    private Calendar closingTime;
    private int price;
    private Point coordinates;

    private int imageResourceId;

    private int distance = 0;


    public Sight() {
    }

    public Sight(String name, String description, int price, Point coordinates) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.coordinates = coordinates;
        openingTime = Calendar.getInstance();
        openingTime.set(Calendar.HOUR_OF_DAY, 0);
        openingTime.set(Calendar.MINUTE, 0);
        openingTime.set(Calendar.SECOND, 0);
        closingTime = Calendar.getInstance();
        closingTime.set(Calendar.HOUR_OF_DAY, 23);
        closingTime.set(Calendar.MINUTE, 59);
        closingTime.set(Calendar.SECOND, 59);
    }

    public Sight(String name, String description, int price, Point coordinates, int openingHour,
                 int openingMinute, int closingHour, int closingMinute) {
        this.name = name;
        this.description = description;
        openingTime = Calendar.getInstance();
        openingTime.set(Calendar.HOUR_OF_DAY, openingHour);
        openingTime.set(Calendar.MINUTE, openingMinute);
        openingTime.set(Calendar.SECOND, 0);
        closingTime = Calendar.getInstance();
        closingTime.set(Calendar.HOUR_OF_DAY, closingHour);
        closingTime.set(Calendar.MINUTE, closingMinute);
        closingTime.set(Calendar.SECOND, 0);
        this.price = price;
        this.coordinates = coordinates;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Calendar getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(Calendar openingTime) {
        this.openingTime = openingTime;
    }

    public Calendar getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(Calendar closingTime) {
        this.closingTime = closingTime;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Point getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Point coordinates) {
        this.coordinates = coordinates;
    }


    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }


    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "Name " + name + ", Description " + description + ", Open time " + openingTime +
                ", Close time " + closingTime + ", Price " + price;
    }

    protected Sight(Parcel in) {
        name = in.readString();
        description = in.readString();
        openingTime = (Calendar) in.readSerializable();
        closingTime = (Calendar) in.readSerializable();
        price = in.readInt();
        double latitude = in.readDouble();
        double longitude = in.readDouble();
        coordinates = new Point(latitude, longitude);
        imageResourceId = in.readInt();
        distance = in.readInt();
    }

    public static final Creator<Sight> CREATOR = new Creator<Sight>() {
        @Override
        public Sight createFromParcel(Parcel in) {
            return new Sight(in);
        }

        @Override
        public Sight[] newArray(int size) {
            return new Sight[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeSerializable(openingTime);
        dest.writeSerializable(closingTime);
        dest.writeInt(price);
        dest.writeDouble(coordinates.getLatitude());
        dest.writeDouble(coordinates.getLongitude());
        dest.writeInt(imageResourceId);
        dest.writeInt(distance);
    }

    @Override
    public int describeContents() {
        return 0;
    }


}

