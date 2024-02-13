package it.academy.dao.addressDAO;


import it.academy.Utils.HibernateUtil;
import it.academy.dao.DaoImpl;
import it.academy.dto.Address;
import it.academy.dto.People;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Set;

public class AddressDaoImpl extends DaoImpl<Address> implements AddressDAO {

    {
        clazz = Address.class;
    }

    public List<Address> getByStreet(String streetName) {

        EntityManager em = HibernateUtil.getEntityManager();
        em.getTransaction().begin();
        String str = String.format("SELECT a from Address a WHERE a.street='%s'", streetName);
        TypedQuery query = em.createQuery(str, clazz);
        List<Address> list = (List<Address>) query.getResultList();
        em.getTransaction().commit();
        em.close();
        return list;
    }

    @Override
    public void delete(int id) {
        Address address = super.get(id);
        if (address != null) {
            Set<People> peopleSet = address.getPeople();
            if (peopleSet != null && !peopleSet.isEmpty()) {
                EntityManager em = HibernateUtil.getEntityManager();
                em.getTransaction().begin();
                peopleSet.stream()
                    .peek(people -> people.getAddresses().remove(address))
                    .forEach(em::merge);
                em.getTransaction().commit();
                em.close();
            }
        }
        super.delete(id);
    }
}
