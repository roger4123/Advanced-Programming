package CompulsoryL9.repositories;

import CompulsoryL9.entities.CityEntity;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class CityEntityRepository {
    private EntityManager entityManager;

    public CityEntityRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void create(CityEntity city) {
        entityManager.getTransaction().begin();
        entityManager.persist(city);
        entityManager.getTransaction().commit();
    }

    public CityEntity findById(int id) {
        return entityManager.find(CityEntity.class, id);
    }

    public List<CityEntity> findByName(String name) {
        TypedQuery<CityEntity> query = entityManager.createQuery("SELECT c FROM CityEntity c WHERE c.name LIKE :name", CityEntity.class);
        query.setParameter("name", name);
        return query.getResultList();
    }
}

