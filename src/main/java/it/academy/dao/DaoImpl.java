package it.academy.dao;

import it.academy.Utils.HibernateUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Table;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

public abstract class DaoImpl<T extends Serializable> implements DAO<T> {

    public static final String THERE_IS_NO_ROW_WITH_SUCH_ID = "There is no row with such ID in table %s.";

    private final Class<T> clazz;
    private EntityManager em;

    protected DaoImpl(Class<T> clazz) {
        this.clazz = clazz;
        em = HibernateUtil.getEntityManager();
    }

    public T get(int id) {

        T t = null;
        try {
            getEm().getTransaction().begin();
            t = getEm().find(clazz, id);
            getEm().getTransaction().commit();
        } catch (EntityNotFoundException e) {
            System.out.println(String.format(THERE_IS_NO_ROW_WITH_SUCH_ID, (clazz.isAnnotationPresent(Table.class) ? clazz.getAnnotation(Table.class).name() : clazz.getName())));
        }
        return t;
    }

    public void delete(int id) {

        try {
            getEm().getTransaction().begin();
            Object rootEntity = getEm().getReference(clazz, id);
            getEm().remove(rootEntity);
            getEm().getTransaction().commit();
        } catch (EntityNotFoundException e) {
            System.out.println(
                String.format(THERE_IS_NO_ROW_WITH_SUCH_ID,
                    (clazz.isAnnotationPresent(Table.class) ?
                         clazz.getAnnotation(Table.class).name()
                         : clazz.getName())));
        }
    }

    public T save(T t) {

        getEm().getTransaction().begin();
        getEm().persist(t);
        getEm().getTransaction().commit();
        return t;
    }

    public void update(T t) {

        try {
            getEm().getTransaction().begin();
            getEm().merge(t);
            getEm().getTransaction().commit();
        } catch (EntityNotFoundException e) {
            System.out.println(
                String.format(THERE_IS_NO_ROW_WITH_SUCH_ID,
                    (t.getClass().isAnnotationPresent(Table.class) ?
                         t.getClass().getAnnotation(Table.class).name()
                         : t.getClass().getName())));
        }
    }

    public List<T> getAll() {

        List<T> list;
        CriteriaBuilder cb = getEm().getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(clazz);
        Root<T> rootEntry = cq.from(clazz);
        CriteriaQuery<T> all = cq.select(rootEntry);
        TypedQuery<T> allQuery = getEm().createQuery(all);
        list = allQuery.getResultList();
        return list;
    }

    protected EntityManager getEm() {

        if (!em.isOpen()) {
            em = HibernateUtil.getEntityManager();
        }
        return em;
    }

    public void closeEntityManager() {

        if (em.isOpen()) {
            em.close();
        }
    }
}
