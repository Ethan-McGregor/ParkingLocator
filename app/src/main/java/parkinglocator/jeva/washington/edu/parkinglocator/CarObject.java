package parkinglocator.jeva.washington.edu.parkinglocator;

/**
 * Created by Ethan on 3/6/2017.
 */

public class CarObject {
    private String make;
    private String model;
    private String year;
    private String color;

    public CarObject(){
        this.make = "";
        this.model = "";
        this.year = "";
        this.color = "";
    }

    public CarObject(String make, String modle, String year, String color){
        this.make = make;
        this.model = modle;
        this.year = year;
        this.color = color;
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
}
