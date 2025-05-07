package CompulsoryL9.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "countries")
@NamedQuery(name = "CountryEntity.findByName", query = "SELECT c FROM CountryEntity c WHERE c.name LIKE :name")
public class CountryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(length = 3, unique = true, nullable = false)
    private String code;

    @ManyToOne
    @JoinColumn(name = "continent_id", nullable = false)
    private ContinentEntity continent;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CityEntity> cities;

    // Constructors
    public CountryEntity() {}

    public CountryEntity(String name, String code, ContinentEntity continent) {
        this.name = name;
        this.code = code;
        this.continent = continent;
    }

    // Getters & Setters
    public int getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public ContinentEntity getContinent() { return continent; }
    public void setContinent(ContinentEntity continent) { this.continent = continent; }
    public List<CityEntity> getCities() { return cities; }
    public void setCities(List<CityEntity> cities) { this.cities = cities; }
}

