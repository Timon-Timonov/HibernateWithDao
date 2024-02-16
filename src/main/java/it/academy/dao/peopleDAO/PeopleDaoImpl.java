package it.academy.dao.peopleDAO;

import it.academy.dao.DaoImpl;
import it.academy.dto.People;

import javax.persistence.TypedQuery;
import java.util.List;

public class PeopleDaoImpl extends DaoImpl<People> implements PeopleDAO {

    public PeopleDaoImpl() {

        super(People.class);
    }

    public List<People> getByAgeGreaterThan(int minAge) {

        getEm().getTransaction().begin();
        String str = String.format("SELECT p FROM People p WHERE p.age>=%d", minAge);
        TypedQuery<People> query = getEm().createQuery(str, People.class);
        List<People> list = query.getResultList();
        getEm().getTransaction().commit();
        return list;
    }
}
