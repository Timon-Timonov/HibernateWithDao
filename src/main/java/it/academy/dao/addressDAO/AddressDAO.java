package it.academy.dao.addressDAO;

import it.academy.dao.DAO;
import it.academy.dto.Address;

import java.util.List;

public interface AddressDAO extends DAO<Address> {

    List<Address> getByStreet(String streetName);
}
