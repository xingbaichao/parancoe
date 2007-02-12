package org.parancoe.basicWebApp.po;

import org.parancoe.persistence.po.hibernate.EntityBase;
import org.apache.commons.lang.time.DateUtils;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.text.SimpleDateFormat;

import org.springmodules.validation.bean.conf.loader.annotation.handler.InThePast;
import org.springmodules.validation.bean.conf.loader.annotation.handler.Length;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotBlank;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotNull;

@javax.persistence.Entity()
public class Person extends EntityBase {
    
    @NotBlank
    @Length(min=2, max=7)
    private String firstName;
    
    @NotBlank
    @Length(min=2, max=10)
    private String lastName;
    
    @NotNull
    @InThePast
    private Date birthDate;

    /** Creates a new instance of Person */
    public Person() {
    }

    public Person(String firstName, String lastName, Date birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Temporal(TemporalType.DATE)
    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String toString() {
        return firstName + " " + lastName + " nato il " +  birthDate ;
    }
}