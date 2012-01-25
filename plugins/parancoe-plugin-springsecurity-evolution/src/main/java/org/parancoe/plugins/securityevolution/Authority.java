/**
 * Copyright (C) 2006-2010 The Parancoe Team <info@parancoe.org>
 *
 * This file is part of Parancoe Plugin Spring Security Evolution.
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
package org.parancoe.plugins.securityevolution;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.hibernate.annotations.ForeignKey;
import org.lambico.po.hibernate.EntityBase;

@javax.persistence.Table(name = "psec_authority")
@javax.persistence.Entity
@NamedQueries(value = {
	    @NamedQuery(name = "Authority.findAllAuthoritiesAssociatedToUsername",
	    query =
	    "select distinct a from User u, Group g, Authority a where u.username = ? "+
	    		" and u in elements(g.users) and g in elements(a.groups)")
	})
public class Authority extends EntityBase {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String description = null;
    private String role = null;
    private List<Group> groups = new ArrayList<Group>();
    
   
    
    
    /**
     * Constructor with role.
     * @param role
     */
    public Authority(String role)
    {
    	super();
    	this.role = role;    	
    }
    public Authority()
    {
    	super();
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "role: "+role+" - description: "+description;
	}
	@ManyToMany(mappedBy="authorities")
	@ForeignKey(name = "none", inverseName = "none")
	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}
    
}
