package parkinglocator.jeva.washington.edu.parkinglocator;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ethan on 3/6/2017.
 */

public class CarObject implements Parcelable {
    private String make;
    private String model;
    private String year;
    private String color;
    private String lat;
    private String lon;
    private String details;

    public CarObject(){
        this.make = "";
        this.model = "";
        this.year = "";
        this.color = "";
        this.lat = "";
        this.lon ="";
        this.details = "";
    }

    public CarObject(String make, String model, String year, String color, String lat, String lon, String details){
        this.make = make;
        this.model = model;
        this.year = year;
        this.color = color;
        this.lat = lat;
        this.lon = lon;
        this.details = details;
    }

    //getters
    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String getYear() {
        return year;
    }

    public String getColor() {
        return color;
    }

    public String getLat() { return lat;}
    public String getLon() { return lon;}
    public String getDetails() { return details;}



    //setters
    public void setMake(String make) {
        this.make = make;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public void setDetails(String details) {
        this.details = details;
    }


    // Parcelling part
    public CarObject(Parcel in){
        String[] data = new String[7];

        in.readStringArray(data);
        // the order needs to be the same as in writeToParcel() method
        this.make = data[0];
        this.model = data[1];
        this.year = data[2];
        this.color = data[3];
        this.lat = data[4];
        this.lon = data[5];
        this.details = data[6];
    }

    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {this.make,
        this.model,
        this.year,
        this.color,
        this.lat,
        this.lon,
        this.details});
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public CarObject createFromParcel(Parcel in) {
            return new CarObject(in);
        }

        public CarObject[] newArray(int size) {
            return new CarObject[size];
        }
    };
}
