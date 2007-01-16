package org.parancoe.basicWebApp.dao;

import org.parancoe.persistence.dao.generic.Dao;
import org.parancoe.persistence.dao.generic.GenericDao;
import org.parancoe.basicWebApp.po.Person;

import java.util.List;
import java.util.Date;

@Dao(entity=Person.class)
public interface PersonDao extends GenericDao<Person, Long> {
    List<Person> findByLastName(String lastName);
    List<Person> findByFirstNameAndLastName(String firstName, String lastName);
    List<Person> findByBirthDate(Date birthDate);
}