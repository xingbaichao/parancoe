package org.parancoe.basicWebApp;

import java.util.Date;
import javax.annotation.Resource;
import org.parancoe.basicWebApp.po.Person;
import org.parancoe.web.test.BaseTest;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

/**
 * A test for the application validation.
 *
 * @author lucio
 */
public class ValidationTest extends BaseTest {

    @Resource
    private Validator validator;

    public void testPersonValidation() {
        Person person =
                new Person("Lucio", "Benfante", new Date());
        BindingResult result = new BeanPropertyBindingResult(person, "person");
        validator.validate(person, result);
        assertFalse(result.hasErrors());
    }

    public void testPersonValidationInError() {
        Person person =
                new Person("", "Benfante", new Date());
        BindingResult result = new BeanPropertyBindingResult(person, "person");
        validator.validate(person, result);
        assertTrue(result.hasErrors());
        assertSize(2, result.getAllErrors());
    }

}
