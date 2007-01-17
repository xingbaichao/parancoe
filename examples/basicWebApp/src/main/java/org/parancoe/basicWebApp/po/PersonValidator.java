package org.parancoe.basicWebApp.po;

import org.springframework.validation.Validator;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Errors;

public class PersonValidator implements Validator {

    public boolean supports(Class clazz) {
        return Person.class.equals(clazz);
    }

    public void validate(Object obj, Errors e) {
        ValidationUtils.rejectIfEmpty(e, "firstName", null ,"Il nome non può essere vuoto");
        ValidationUtils.rejectIfEmpty(e, "lastName", null, "Il cognome non può essere vuoto");  
    }
}
