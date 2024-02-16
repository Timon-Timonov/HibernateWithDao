package it.academy.Utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HibernateUtilTest {

    @Test
    void getEntityManager() {

        assertNotNull(HibernateUtil.getEntityManager());
    }

}