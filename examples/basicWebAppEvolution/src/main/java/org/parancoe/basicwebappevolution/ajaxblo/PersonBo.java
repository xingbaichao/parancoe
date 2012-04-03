// Copyright 2006-2008 The Parancoe Team
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
package org.parancoe.basicwebappevolution.ajaxblo;

import org.parancoe.basicwebappevolution.dao.PersonDao;
import org.parancoe.basicwebappevolution.po.Person;

import org.directwebremoting.ScriptSession;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.proxy.dwr.Util;
import org.directwebremoting.proxy.scriptaculous.Effect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("ajaxPersonBo")
@RemoteProxy(name = "personBo")
public class PersonBo {

    @Autowired
    private PersonDao dao;

    @RemoteMethod
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

