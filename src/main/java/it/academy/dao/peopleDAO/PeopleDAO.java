package it.academy.dao.peopleDAO;

import it.academy.dao.DAO;
import it.academy.dto.People;

import java.util.List;

public interface PeopleDAO extends DAO<People> {

    List<People> getByAgeGreaterThan(int minAge);
}
