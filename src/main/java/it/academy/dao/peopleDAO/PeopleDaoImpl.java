package it.academy.dao.peopleDAO;

import it.academy.Utils.HibernateUtil;
import it.academy.dao.DaoImpl;
import it.academy.dto.People;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class PeopleDaoImpl extends DaoImpl<People> implements PeopleDAO {

    {
        clazz = People.class;
    }

    public List<People> getByAgeGreaterThan(int minAge) {
        EntityManager em = HibernateUtil.getEntityManager();
        em.getTransaction().begin();
        String str = String.format("SELECT p FROM People p WHERE p.age>=%d", minAge);
        TypedQuery query = em.createQuery(str, clazz);
        List<People> list = query.getResultList();
        em.getTransaction().commit();
        em.close();
        return list;
    }
}
