/**
 * Copyright (C) 2006-2012 The Parancoe Team <info@parancoe.org>
 *
 * This file is part of Parancoe Plugin Configuration.
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
package org.parancoe.plugin.configuration.po;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import org.lambico.po.hibernate.EntityBase;

/**
 * A configuration property.
 *
 * @author Lucio Benfante <lucio@benfante.com>
 */
@Entity
@Table(name = "PLUGIN_CONFIGURATION_PROPERTY")
@NamedQueries({
    @NamedQuery(name = "Property.findByNameAndCategoryId", query =
    "from Property p where p.name = ? and p.category.id = ? order by p.category.name, p.name"),
    @NamedQuery(name = "Property.findByCategoryId", query =
    "from Property p where p.category.id = ? order by p.name")
})
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, 
                  property = "@id")
@XmlRootElement
public class Property extends EntityBase {

    private String name;
    private String description;
    private Category category;
    private String value;
    private PropertyType type = PropertyType.STRING;

    /**
     * Get the value of name
     *
     * @return the value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the value of name
     *
     * @param name new value of name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the value of description
     *
     * @return the value of description
     */
    @Column(length = 4096)
    public String getDescription() {
        return description;
    }

    /**
     * Set the value of description
     *
     * @param description new value of description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get the value of category
     *
     * @return the value of category
     */
    @ManyToOne
    public Category getCategory() {
        return category;
    }

    /**
     * Set the value of category
     *
     * @param category new value of category
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * Get the value of value
     *
     * @return the value of value
     */
    @Lob
    public String getValue() {
        return value;
    }

    /**
     * Set the value of value
     *
     * @param value new value of value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Get the value of type
     *
     * @return the value of type
     */
    @Enumerated(EnumType.STRING)
    public PropertyType getType() {
        return type;
    }

    /**
     * Set the value of type
     *
     * @param type new value of type
     */
    public void setType(PropertyType type) {
        this.type = type;
    }

    /**
     * Return the value of the property as an object of the correct type: <ul> <li>STRING to
     * java.lang.String</li> <li>TEXT to java.lang.String</li> <li>INTEGER to java.lang.Integer</li>
     * <li>REAL to java.math.BigDecimal</li> <li>BOOLEAN to java.lang.Boolean</li> </ul>
     *
     * @return The value as an object of the correct type.
     */
    @Transient
    public Object getTypedValue() {
        Object result = null;
        switch (this.getType()) {
            case INTEGER:
                result = getValueAsInteger();
                break;
            case REAL:
                result = getValueAsReal();
                break;
            case BOOLEAN:
                result = getValueAsBoolean();
                break;
            default:
                result = this.getValue();
        }
        return result;
    }

    /**
     * Set the property value from a typed object. This conversion is done calling the toString()
     * method of the passed object.
     *
     * @param value The value to set.
     */
    public void setTypedValue(Object value) {
        this.value = value.toString();
    }

    @Transient
    @JsonIgnore
    public Integer getValueAsInteger() throws NumberFormatException {
        return Integer.valueOf(this.getValue());
    }

    @Transient
    @JsonIgnore
    public BigDecimal getValueAsReal() {
        return new BigDecimal(this.getValue());
    }

    @Transient
    @JsonIgnore
    public Boolean getValueAsBoolean() {
        return Boolean.valueOf(this.getValue());
    }
}
