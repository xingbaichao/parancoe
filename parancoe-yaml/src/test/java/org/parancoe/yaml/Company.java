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
import java.util.List;

public class Company {
    String name;

    Person president;

    long yearsInOperation;

    List<Department> departments;

    Department[] departmentArray;

    BigDecimal assets;

    /**
     * @return Returns the assets.
     */
    public BigDecimal getAssets() {
        return assets;
    }

    /**
     * @param assets
     *            The assets to set.
     */
    public void setAssets(BigDecimal assets) {
        this.assets = assets;
    }

    /**
     * @return Returns the departments.
     */
    public List<Department> getDepartments() {
        return departments;
    }

    /**
     * @param departments
     *            The departments to set.
     */
    public void setDepartments(List<Department> departments) {
        this.departments = departments;
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
     * @return Returns the president.
     */
    public Person getPresident() {
        return president;
    }

    /**
     * @param president
     *            The president to set.
     */
    public void setPresident(Person president) {
        this.president = president;
    }

    /**
     * @return Returns the yearsInOperation.
     */
    public long getYearsInOperation() {
        return yearsInOperation;
    }

    /**
     * @param yearsInOperation
     *            The yearsInOperation to set.
     */
    public void setYearsInOperation(long yearsInOperation) {
        this.yearsInOperation = yearsInOperation;
    }

    /**
     * @return Returns the departmentArray.
     */
    public Department[] getDepartmentArray() {
        return departmentArray;
    }

    /**
     * @param departmentArray
     *            The departmentArray to set.
     */
    public void setDepartmentArray(Department[] departmentArray) {
        this.departmentArray = departmentArray;
    }

}
