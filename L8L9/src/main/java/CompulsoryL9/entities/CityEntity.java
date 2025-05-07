package CompulsoryL9.entities;

import javax.persistence.*;

@Entity
@Table(name = "cities")
@NamedQuery(name = "City.findByName", query = "SELECT c FROM CityEntity c WHERE c.name LIKE :name")
public class CityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    private CountryEntity country;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private boolean capital;

    @Column(nullable = true)
    private double latitude;

    @Column(nullable = true)
    private double longitude;

    public CityEntity() {}

    public CityEntity(String name, boolean capital, double latitude, double longitude, CountryEntity country) {
        this.name = name;
        this.capital = capital;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCapital() {
        return capital;
    }

    public void setCapital(boolean capital) {
        this.capital = capital;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public CountryEntity getCountry() {
        return country;
    }

    public void setCountry(CountryEntity country) {
        this.country = country;
    }
}


