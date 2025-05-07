package CompulsoryL9.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "continents")
@NamedQuery(name = "ContinentEntity.findByName", query = "SELECT c FROM ContinentEntity c WHERE c.name LIKE :name")
public class ContinentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "continent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CountryEntity> countries;

    public ContinentEntity() {
    }

    public ContinentEntity(String name) {
        this.name = name;
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

    public List<CountryEntity> getCountries() {
        return countries;
    }

    public void setCountries(List<CountryEntity> countries) {
        this.countries = countries;
    }
}

