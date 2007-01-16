package org.parancoe.basicWebApp.blo;

import org.springframework.transaction.annotation.Transactional;
import org.parancoe.basicWebApp.po.Person;
import org.parancoe.basicWebApp.dao.PersonDao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class PersonBo {
    private PersonDao dao;

    public PersonDao getDao() {
        return dao;
    }

    public void setDao(PersonDao dao) {
        this.dao = dao;
    }

    @Transactional()
    public void populateArchive() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        List<Person> searches = null;
        searches = dao.findByFirstNameAndLastName("Mario", "Rossi");
        if (searches.isEmpty()) {
            Person p = new Person();
            p.setFirstName("Mario");
            p.setLastName("Rossi");
            p.setBirthDate(sdf.parse("25/04/1970"));
            dao.create(p);
        }
        searches = dao.findByFirstNameAndLastName("Francesca", "Verdi");
        if (searches.isEmpty()) {
            Person p = new Person();
            p.setFirstName("Francesca");
            p.setLastName("Verdi");
            p.setBirthDate(sdf.parse("30/08/1990"));
            dao.create(p);
        }
        searches = dao.findByFirstNameAndLastName("Giovanni", "Bianchi");
        if (searches.isEmpty()) {
            Person p = new Person();
            p.setFirstName("Giovanni");
            p.setLastName("Bianchi");
            p.setBirthDate(sdf.parse("15/03/1980"));
            dao.create(p);
        }
    }

    @Transactional(readOnly=true)
    public Person retrievePerson(Long id) {
        return dao.read(id);
    }
}

