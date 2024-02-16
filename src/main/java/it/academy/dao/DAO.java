package it.academy.dao;

import java.io.Serializable;
import java.util.List;

public interface DAO<T extends Serializable> {

    T get(int id);

    void delete(int id);

    T save(T t);

    void update(T t);

    List<T> getAll();

    void closeEntityManager();
}
