package org.parancoe.example.dao;

import java.util.List;
import javax.annotation.Resource;
import org.parancoe.example.po.Person;
import org.parancoe.example.test.BaseTest;

/**
 *
 * @author lucio
 */
public class PersonDaoTest extends BaseTest {
    
    @Resource
    private PersonDao personDao;
    
    /**
     * Test of findByLastName method, of class PersonDao.
     */
    public void testFindByLastName() {
        List<Person> people = personDao.findByLastName("Benfante");
        assertEquals(8, people.size());
        for (Person p: people) {
            assertEquals("Benfante", p.getLastName());
        }
    }

    public void testFindByFirstName() {
        List<Person> people = personDao.findByFirstName("Ugo");
        assertEquals(2, people.size());
        for (Person p: people) {
            assertEquals("Ugo", p.getFirstName());
            System.out.println(p);
        }
    }
    
}
