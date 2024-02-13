package it.academy.dao.peopleDAO;

import it.academy.MockConstants;
import it.academy.MockUtils;
import it.academy.Utils.HibernateUtil;
import it.academy.dao.addressDAO.AddressDAO;
import it.academy.dao.addressDAO.AddressDaoImpl;
import it.academy.dto.People;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class PeopleDaoImplTest {

    private static final PeopleDAO dao = new PeopleDaoImpl();
    private int id;

    @BeforeAll
    public static void beforeAll() {
        EntityManager em = HibernateUtil.getEntityManager();
        em.getTransaction().begin();
        IntStream.range(0, MockConstants.TEST_PEOPLE_COUNT)
            .mapToObj(i -> MockUtils.getPeople(MockUtils.getAddress()))
            .peek(people -> em.persist(people.getAddress()))
            .forEach(em::persist);
        em.getTransaction().commit();
        em.close();
    }

    @Test
    void delete() {
        id = MockConstants.ID_FOR_DEL;
        List<People> list_1 = dao.getAll();
        dao.delete(id);
        List<People> list_2 = dao.getAll();
        assertNotNull(list_1);
        assertNotNull(list_2);
        assertEquals(list_1.size() - 1, list_2.size());
        assertTrue(list_1.containsAll(list_2));

    }

    @Test
    void save() {
        People people = MockUtils.getPeople(MockUtils.getAddress());
        assertEquals(0, people.getId());
        new AddressDaoImpl().save(people.getAddress());
        dao.save(people);
        assertNotNull(people);
        assertNotEquals(0, people.getId());

        EntityManager em = HibernateUtil.getEntityManager();
        em.getTransaction().begin();
        People peopleFromDB = em.find(People.class, people.getId());
        em.getTransaction().commit();
        assertNotNull(peopleFromDB);
        assertEquals(peopleFromDB, people);

        em = HibernateUtil.getEntityManager();
        em.getTransaction().begin();
        Object rootEntity = em.getReference(People.class, people.getId());
        em.remove(rootEntity);
        em.getTransaction().commit();
        em.close();
    }

    @Test
    void getByAgeGreaterThan() {
        id = MockConstants.ID_FOR_SPEC;
        int age = MockConstants.AGE[id - 1];
        List<People> list = dao.getByAgeGreaterThan(age);
        assertNotNull(list);
        assertTrue(list.size() != 0);
        list.stream()
            .peek(Assertions::assertNotNull)
            .forEach(people -> assertTrue(people.getAge() >= age));
    }
}