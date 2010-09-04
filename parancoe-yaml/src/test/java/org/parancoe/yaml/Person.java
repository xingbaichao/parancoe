/**
 * Copyright (C) 2006-2010 The Parancoe Team <info@parancoe.org>
 *
 * This file is part of Parancoe Yaml.
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

package org.parancoe.yaml;

import java.math.BigDecimal;

public class Person {

    String name;

    Integer age;

    Double salary;

    BigDecimal netWorth;

    Person spouse;

    Company employer;

    /**
     * @return Returns the age.
     */
    public Integer getAge() {
        return age;
    }

    /**
     * @param age
     *            The age to set.
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return Returns the netWorth.
     */
    public BigDecimal getNetWorth() {
        return netWorth;
    }

    /**
     * @param netWorth
     *            The netWorth to set.
     */
    public void setNetWorth(BigDecimal netWorth) {
        this.netWorth = netWorth;
    }

    /**
     * @return Returns the salary.
     */
    public Double getSalary() {
        return salary;
    }

    /**
     * @param salary
     *            The salary to set.
     */
    public void setSalary(Double salary) {
        this.salary = salary;
    }

    /**
     * @return Returns the spouse.
     */
    public Person getSpouse() {
        return spouse;
    }

    /**
     * @param spouse
     *            The spouse to set.
     */
    public void setSpouse(Person spouse) {
        this.spouse = spouse;
    }
}
