package CompulsoryL9;

import CompulsoryL9.entities.CityEntity;
import CompulsoryL9.repositories.CityEntityRepository;

import javax.persistence.EntityManager;

public class Main {
    public static void main(String[] args) {
        EntityManager em = JPAUtil.getEntityManager();
        CityEntityRepository cityRepo = new CityEntityRepository(em);

        CityEntity newCity = new CityEntity("Bucharest", true, 44.4333, 26.1000, null);

        em.getTransaction().begin();
        cityRepo.create(newCity);
        em.getTransaction().commit();

        System.out.println("City added successfully!");
        JPAUtil.close();
    }
}

