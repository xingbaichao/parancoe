/**
 * Copyright (C) 2006-2010 The Parancoe Team <info@parancoe.org>
 *
 * This file is part of Parancoe Plugin World.
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

package org.parancoe.plugins.world;

import javax.persistence.*;
import org.hibernate.validator.constraints.NotBlank;
import org.lambico.po.hibernate.EntityBase;

/**
 * @author Lucio Benfante lucio.benfante@jugpadova.it
 */
@Entity
@NamedQueries(
value = {
    @NamedQuery(name = "Country.findByPartialLocalName",
    query =
    "from Country c where upper(c.localName) like upper(?) order by c.localName asc"),
    @NamedQuery(name = "Country.findByPartialLocalNameAndContinent",
    query =
    "from Country c where upper(c.localName) like upper(?) and upper(c.continent.name) like upper(?) order by c.localName asc"),
    @NamedQuery(name = "Country.findByPartialEnglishName",
    query =
    "from Country c where upper(c.englishName) like upper(?) order by c.englishName asc"),
    @NamedQuery(name = "Country.findByPartialEnglishNameAndContinent",
    query =
    "from Country c where upper(c.englishName) like upper(?) and upper(c.continent.name) like upper(?) order by c.englishName asc")})
public class Country extends EntityBase {

    private String isoCode;
    private String languageIsoCode;
    private String languageVariant;
    private String localName;
    private String englishName;
    private Continent continent;

    public Country() {
    }

    @ManyToOne
    @JoinColumn(name = "continent_id")
    public Continent getContinent() {
        return continent;
    }

    public void setContinent(Continent continent) {
        this.continent = continent;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    @Column(length = 2)
    @NotBlank
    public String getIsoCode() {
        return isoCode;
    }

    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }

    @Column(length = 2)
    public String getLanguageIsoCode() {
        return languageIsoCode;
    }

    public void setLanguageIsoCode(String languageIsoCode) {
        this.languageIsoCode = languageIsoCode;
    }

    @Column(length = 32)
    public String getLanguageVariant() {
        return languageVariant;
    }

    public void setLanguageVariant(String languageVariant) {
        this.languageVariant = languageVariant;
    }

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    @Override
    public String toString() {
        return "Country{" + "isoCode='" + isoCode + '\'' + ", localName='"
                + localName + '\'' + ", englishName='" + englishName + '\''
                + ", continent=" + continent + '}';
    }
}
