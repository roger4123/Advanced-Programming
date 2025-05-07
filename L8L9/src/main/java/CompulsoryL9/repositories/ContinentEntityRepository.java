package CompulsoryL9.repositories;

import CompulsoryL9.entities.ContinentEntity;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class ContinentEntityRepository {
    private EntityManager entityManager;

    public ContinentEntityRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void create(ContinentEntity continent) {
        entityManager.getTransaction().begin();
        entityManager.persist(continent);
        entityManager.getTransaction().commit();
    }

    public ContinentEntity findById(int id) {
        return entityManager.find(ContinentEntity.class, id);
    }

    public List<ContinentEntity> findByName(String name) {
        TypedQuery<ContinentEntity> query = entityManager.createQuery("SELECT c FROM ContinentEntity c WHERE c.name LIKE :name", ContinentEntity.class);
        query.setParameter("name", name);
        return query.getResultList();
    }
}

