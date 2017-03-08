package parkinglocator.jeva.washington.edu.parkinglocator;

/**
 * Created by Ethan on 3/6/2017.
 */

public class CarObject {
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

    public CarObject(String make, String modle, String year, String color, String lat, String lon, String details){
        this.make = make;
        this.model = modle;
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


}
