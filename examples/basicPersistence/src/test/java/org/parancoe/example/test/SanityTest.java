package org.parancoe.example.test;

import javax.annotation.Resource;
import org.parancoe.example.bo.PersonBO;
import org.parancoe.example.dao.PersonDao;

/**
 * Sanity tests
 * 
 * @author Lucio Benfante
 */
public class SanityTest extends BaseTest{
    @Resource
    private PersonDao personDao;
    @Resource
    private PersonBO personBO;
    
    /**
     * Test the wiring of BO resources.
     */
    public void testBoResources() {
        assertNotNull(personBO);
    }
    
    /**
     * Test the wiring of DAO resources.
     */
    public void testDaoResources() {
        assertNotNull(personDao);
    }

}
