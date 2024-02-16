package it.academy.dao.addressDAO;

import it.academy.dao.DaoImpl;
import it.academy.dto.Address;

import javax.persistence.TypedQuery;
import java.util.List;

public class AddressDaoImpl extends DaoImpl<Address> implements AddressDAO {

    public AddressDaoImpl() {

        super(Address.class);
    }

    public List<Address> getByStreet(String streetName) {

        getEm().getTransaction().begin();
        String str = String.format("SELECT a from Address a WHERE a.street='%s'", streetName);
        TypedQuery<Address> query = getEm().createQuery(str, Address.class);
        List<Address> list = query.getResultList();
        getEm().getTransaction().commit();
        return list;
    }
}
