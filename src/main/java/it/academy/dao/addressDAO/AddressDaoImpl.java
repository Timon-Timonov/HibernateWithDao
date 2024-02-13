package it.academy.dao.addressDAO;

import it.academy.Utils.HibernateUtil;
import it.academy.dao.DaoImpl;
import it.academy.dto.Address;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

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
}
