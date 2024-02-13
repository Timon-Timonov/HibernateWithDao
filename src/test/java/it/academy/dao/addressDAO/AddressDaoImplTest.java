package it.academy.dao.addressDAO;

import it.academy.MockConstants;
import it.academy.MockUtils;
import it.academy.Utils.HibernateUtil;
import it.academy.dto.Address;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class AddressDaoImplTest {

    private static final AddressDAO dao = new AddressDaoImpl();
    private int id;


    @BeforeAll
    public static void beforeAll() {
        EntityManager em = HibernateUtil.getEntityManager();
        em.getTransaction().begin();
        IntStream.range(0, MockConstants.TEST_ADDRESSES_COUNT)
            .mapToObj(i -> MockUtils.getAddress())
            .forEach(em::persist);
        em.getTransaction().commit();
        em.close();
    }

    @Test
    void get() {
        id = MockConstants.ID_FOR_GET;
        Address address = dao.get(id);
        assertEquals(address.getId(), MockConstants.ID_FOR_GET);
        assertEquals(address.getStreet(), MockConstants.STREETS[id - 1]);
        assertEquals(address.getHouse(), MockConstants.HOUSES[id - 1]);
    }

    @Test
    void update() {
        id = MockConstants.ID_FOR_UPD;

        Address addressFromDB_1;
        EntityManager em = HibernateUtil.getEntityManager();
        em.getTransaction().begin();
        addressFromDB_1 = em.find(Address.class, id);
        em.getTransaction().commit();
        em.close();

        assertEquals(id, addressFromDB_1.getId());
        assertNotEquals(MockConstants.STREETS[id], addressFromDB_1.getStreet());
        assertNotEquals(MockConstants.HOUSES[id], addressFromDB_1.getHouse());

        addressFromDB_1.setStreet(MockConstants.STREETS[id]);
        addressFromDB_1.setHouse(MockConstants.HOUSES[id]);
        dao.update(addressFromDB_1);

        Address addressFromDB_2;
        em = HibernateUtil.getEntityManager();
        em.getTransaction().begin();
        addressFromDB_2 = em.find(Address.class, id);
        em.getTransaction().commit();
        em.close();

        assertEquals(addressFromDB_1, addressFromDB_2);
    }

    @Test
    void getAll() {
        List<Address> addresses = dao.getAll();
        assertNotNull(addresses);
        assertEquals(MockConstants.TEST_ADDRESSES_COUNT, addresses.size());
        for (int i = 0; i < addresses.size(); i++) {
            if (i != addresses.size() - 1) {
                assertNotEquals(addresses.get(i), addresses.get(i + 1));
            } else {
                assertNotEquals(addresses.get(i), addresses.get(0));
            }
        }
    }

    @Test
    void getByStreet() {
        id = MockConstants.ID_FOR_SPEC;
        String street = MockConstants.STREETS[id - 1];

        List<Address> addresses = dao.getByStreet(street);

        assertNotNull(addresses);
        assertEquals(1, addresses.size());

        Address addressFromDB = addresses.get(0);
        assertEquals(MockConstants.ID_FOR_SPEC, addressFromDB.getId());
        assertEquals(street, addressFromDB.getStreet());
        assertEquals(MockConstants.HOUSES[id - 1], addressFromDB.getHouse());

        Address address = MockUtils.getAddress();
        address.setStreet(street);

        EntityManager em = HibernateUtil.getEntityManager();
        em.getTransaction().begin();
        em.persist(address);
        em.getTransaction().commit();
        em.close();

        assertEquals(2, dao.getByStreet(street).size());

        em = HibernateUtil.getEntityManager();
        em.getTransaction().begin();
        Object rootEntity = em.getReference(Address.class, id);
        em.remove(rootEntity);
        em.getTransaction().commit();
    }
}