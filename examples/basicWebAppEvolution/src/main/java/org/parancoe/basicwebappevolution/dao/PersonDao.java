/**
 * Copyright (C) 2006-2013 The Parancoe Team <info@parancoe.org>
 *
 * This file is part of Parancoe Example - Basic WebApp Evolution.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.parancoe.basicwebappevolution.dao;

import org.parancoe.basicwebappevolution.po.Person;

import java.util.List;
import java.util.Date;
import org.lambico.dao.generic.Dao;
import org.lambico.dao.generic.GenericDao;

@Dao(entity = Person.class)
public interface PersonDao extends GenericDao<Person, Long> {

    List<Person> findByLastName(String lastName);

    List<Person> findByFirstNameAndLastName(String firstName, String lastName);

    List<Person> findByBirthDate(Date birthDate);
}
