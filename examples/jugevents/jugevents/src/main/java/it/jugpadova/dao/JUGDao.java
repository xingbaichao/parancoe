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
package it.jugpadova.dao;

import it.jugpadova.po.JUG;

import java.util.List;

import org.parancoe.persistence.dao.generic.Dao;
import org.parancoe.persistence.dao.generic.GenericDao;

/**
 * @author Enrico Giurin
 *
 */
@Dao(entity=JUG.class)
public interface JUGDao extends GenericDao<JUG, Long> {
	 JUG findByName(String jugName);
	 List<JUG> findByPartialName(String s);
	 List<JUG> findByPartialJugNameAndCountry(String partialName, String partialCountryEnglishName);
	 List<JUG> findByNameAndCountryEN(String name, String countryEnglishName);
	 List<JUG> findByPartialJugNameAndCountryAndContinent(String partialName, String partialCountry, String partialContinent);
}
