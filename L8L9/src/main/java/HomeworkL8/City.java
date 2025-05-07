package HomeworkL8;

public class City {
    private int id;
    private int country_id;
    private String name;
    private boolean isCapital;
    private double longitude;
    private double latitude;

    public City(int id, int country_id, String name, boolean isCapital, double longitude, double latitude) {
        this.id = id;
        this.country_id = country_id;
        this.name = name;
        this.isCapital = isCapital;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCountry_id() {
        return country_id;
    }

    public void setCountry_id(int country_id) {
        this.country_id = country_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCapital() {
        return isCapital;
    }

    public void setCapital(boolean capital) {
        isCapital = capital;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
