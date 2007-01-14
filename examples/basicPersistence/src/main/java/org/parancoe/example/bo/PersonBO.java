// Copyright 2006 The Parancoe Team
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
package org.parancoe.example.bo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import org.parancoe.example.dao.PersonDao;
import org.parancoe.example.po.Person;
import org.springframework.transaction.annotation.Transactional;

/**
 * A BO to be used for the tests of the generic DAO.
 *
 * @author <a href="mailto:lucio@benfante.com">Lucio Benfante</a>
 * @version $Revision$
 */
public class PersonBO {
    private PersonDao dao;
    
    /**
     * Creates a new instance of PersonBO
     */
    public PersonBO() {
    }
    
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
    
//    @Transactional(readOnly=true)
    public Person retrievePerson(Long id) {
        return dao.read(id);
    }
}
