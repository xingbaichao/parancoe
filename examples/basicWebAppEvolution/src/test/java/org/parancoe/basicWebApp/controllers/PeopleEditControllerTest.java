package org.parancoe.basicWebApp.controllers;

import java.util.List;
import javax.annotation.Resource;
import org.parancoe.basicWebApp.dao.PersonDao;
import org.parancoe.basicWebApp.po.Person;
import org.parancoe.web.test.ControllerTest;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.bind.support.SimpleSessionStatus;
import org.springframework.web.servlet.HandlerAdapter;

public class PeopleEditControllerTest extends ControllerTest {

    @Resource
    private PeopleEditController controller;
    @Resource(name="org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter#0")
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

    public void testSavePerson() {
        List<Person> people = personDao.findByLastName("Rossi");
        assertNotEmpty(people);
        Person person = people.get(0);
        BindingResult result = new BeanPropertyBindingResult(person, "person");
        SessionStatus status = new SimpleSessionStatus();
        String direction = controller.update(person, result, status);
        assertEquals("redirect:list.html", direction);
    }

    public void testEdit() {
        Model model = new ExtendedModelMap();
        controller.edit(1L, model);
    }
}