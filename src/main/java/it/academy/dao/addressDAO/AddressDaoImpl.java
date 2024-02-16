package it.academy.dao.addressDAO;


import it.academy.dao.DaoImpl;
import it.academy.dto.Address;
import it.academy.dto.People;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Set;

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

    @Override
    public void delete(int id) {

        Address address = super.get(id);
        if (address != null) {
            getEm().getTransaction().begin();
            getEm().refresh(address);
            getEm().getTransaction().commit();
            Set<People> peopleSet = address.getPeople();
            if (peopleSet != null && !peopleSet.isEmpty()) {
                getEm().getTransaction().begin();
                peopleSet.stream()
                    .peek(people -> people.getAddresses().remove(address))
                    .forEach(getEm()::merge);
                // address.getPeople().clear();
                //getEm().merge(address);
                getEm().getTransaction().commit();
            }
        }
        super.delete(id);
    }
}
