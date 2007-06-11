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
package it.jugpadova.blo;

import org.springframework.transaction.annotation.Transactional;
import it.jugpadova.po.Person;
import it.jugpadova.dao.PersonDao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.proxy.dwr.Util;
import org.directwebremoting.proxy.scriptaculous.Effect;

public class PersonBo {
    private PersonDao dao;

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

    @Transactional(readOnly=true)
    public Person retrievePerson(Long id) {
        return dao.read(id);
    }
    
    @Transactional(readOnly=true)
    public void showPerson(Long id) {
        WebContext wctx = WebContextFactory.get();        
        ScriptSession session = wctx.getScriptSession();
        Util util = new Util(session);
        Person p = dao.read(id);
        util.setValue("firstName", p.getFirstName());
        util.setValue("lastName", p.getLastName());
        util.setValue("birthDate", p.getBirthDate().toString());
        util.setStyle("personData", "display", "block");
        Effect effect = new Effect(session);
        effect.highlight("personData");
    }
    
}

