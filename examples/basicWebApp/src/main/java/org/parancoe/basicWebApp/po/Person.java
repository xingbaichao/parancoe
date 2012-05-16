// Copyright 2006-2007 The Parancoe Team
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package org.parancoe.basicWebApp.po;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.lambico.po.hibernate.EntityBase;

import org.parancoe.util.BaseConf;
import org.springframework.format.annotation.DateTimeFormat;

@javax.persistence.Entity()
public class Person extends EntityBase {
   
    private boolean test = BaseConf.isDevelopment();
    
    private String firstName;
    
    private String lastName;
    
    @DateTimeFormat(pattern="dd/MM/yyyy")
    private Date birthDate;

    /** Creates a new instance of Person */
    public Person() {
    }

    public Person(String firstName, String lastName, Date birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    @NotBlank
    @Length(min=2, max=7)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @NotBlank
    @Length(min=2, max=10)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Temporal(TemporalType.DATE)
    @NotNull
    @Past
    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public void setTest(boolean test){
        this.test = test;
    }
    
    public boolean isTest(){
        return test;
    }
    
    @Override
    public String toString() {
        return firstName + " " + lastName + " nato il " +  birthDate ;
    }
}