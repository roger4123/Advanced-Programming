package CompulsoryL9.repositories;

import CompulsoryL9.entities.CountryEntity;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class CountryEntityRepository {
    private EntityManager entityManager;

    public CountryEntityRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void create(CountryEntity country) {
        entityManager.getTransaction().begin();
        entityManager.persist(country);
        entityManager.getTransaction().commit();
    }

    public CountryEntity findById(int id) {
        return entityManager.find(CountryEntity.class, id);
    }

    public List<CountryEntity> findByName(String name) {
        TypedQuery<CountryEntity> query = entityManager.createQuery("SELECT c FROM CountryEntity c WHERE c.name LIKE :name", CountryEntity.class);
        query.setParameter("name", name);
        return query.getResultList();
    }
}

