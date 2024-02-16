package it.academy.Utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateUtil {

    private static EntityManagerFactory emFactory;

    private HibernateUtil() {
    }

    public static EntityManager getEntityManager() {

        if (emFactory == null) {
            emFactory = Persistence.createEntityManagerFactory("peopleVSaddresses");
        }
        return emFactory.createEntityManager();
    }

    public static void closeFactory() {

        if (emFactory != null) {
            emFactory.close();
        }
    }
}


