package org.parancoe.basicwebappevolution.controllers;

import java.util.List;
import javax.annotation.Resource;

import org.parancoe.basicwebappevolution.controllers.PersonController;
import org.parancoe.basicwebappevolution.dao.PersonDao;
import org.parancoe.basicwebappevolution.po.Person;
import org.parancoe.web.test.ControllerTest;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.ModelAndView;

public class PersonControllerTest extends ControllerTest {

    @Resource
    private PersonController controller;
    @Resource(
    name =
    "org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter#0")
    private HandlerAdapter methodHandler;
    @Resource
    private PersonDao personDao;

    @Override
    public Class[] getFixtureClasses() {
        return new Class[]{Person.class};
    }

    public void testNotNull() {
        assertNotNull(controller);
        assertNotNull(personDao);
    }

    public void testEditAndSavePerson() throws Exception {
        // Testing the method handler supports the controller
        assertTrue(methodHandler.supports(controller));
        // Retrieve a person for testing
        List<Person> people = personDao.findByLastName("Rossi");
        assertNotEmpty(people);
        Person person = people.get(0);
        // Test edit (showing form)
        resetRequestAndResponse();
        req.setMethod("GET");
        req.setRequestURI("/person/" + person.getId() + "/edit");
        ModelAndView mv = methodHandler.handle(req, res, controller);
        assertEquals("person/edit", mv.getViewName());
        assertTrue(mv.getModelMap().containsAttribute("person"));
        assertNotNull(req.getSession().getAttribute("person"));
        // Test store (posting form)
        resetRequestAndResponse();
        req.setMethod("POST");
        req.setRequestURI("/person/" + person.getId());
        req.addParameter("firstName", "Marione");
        req.addParameter("lastName", "Rossi");
        req.addParameter("birthDate", "25/04/1971");
        mv = methodHandler.handle(req, res, controller);
        assertEquals("redirect:", mv.getViewName());
        // Check on the DB
        people = personDao.findByLastName("Rossi");
        assertNotEmpty(people);
        assertSize(1, people);
        person = people.get(0);
        assertEquals("Marione", person.getFirstName());
    }

    public void testDelete() throws Exception {
        // Testing the method handler supports the controller
        assertTrue(methodHandler.supports(controller));
        // Retrieve a person for testing
        List<Person> people = personDao.findAll();
        int initialSize = people.size();
        assertNotEmpty(people);
        Person person = people.get(0);
        // Test edit (showing form)
        resetRequestAndResponse();
        req.setMethod("DELETE");
        req.setRequestURI("/person/" + person.getId());
        ModelAndView mv = methodHandler.handle(req, res, controller);
        assertEquals("redirect:", mv.getViewName());
        // Check on the DB
        people = personDao.findByLastName("Rossi");
        assertSize(initialSize - 1, people);
    }

}
