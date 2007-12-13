/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.parancoe.example.dao;

import java.util.List;
import org.apache.log4j.Logger;
import org.parancoe.example.po.Person;
import org.parancoe.example.test.BaseTest;

/**
 *
 * @author lucio
 */
public class PersonDaoTest extends BaseTest {
    
    private PersonDao dao;
    
    public PersonDaoTest() {
        this.dao = (PersonDao) this.ctx.getBean("personDao");
    }            

    /**
     * Test of findByLastName method, of class PersonDao.
     */
    public void testFindByLastName() {
        List<Person> people = dao.findByLastName("Benfante");
        assertEquals(7, people.size());
        for (Person p: people) {
            assertEquals("Benfante", p.getLastName());
        }
    }

}
