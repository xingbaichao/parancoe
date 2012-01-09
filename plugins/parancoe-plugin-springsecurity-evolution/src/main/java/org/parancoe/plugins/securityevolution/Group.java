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

import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.ForeignKey;
import org.lambico.po.hibernate.EntityBase;


/**
 * @author EGiurin
 *
 */
@javax.persistence.Table(name = "psec_group")
@javax.persistence.Entity
public class Group extends EntityBase {

	
	public Group(String name) {
		super();
		this.name = name;
	}
	
	public Group() {
		super();
		
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 5108134108202277240L;
	
	
	
	private String name;
	private String description;
	private List<User> users = new ArrayList<User>();
	private List<Authority> authorities = new ArrayList<Authority>();
	
	@ManyToMany(mappedBy="groups")
	@ForeignKey(name = "none", inverseName = "none")
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToMany
    @ForeignKey(name = "none", inverseName = "none")
    @JoinTable(name = "psec_group_authority",
    joinColumns = {
        @JoinColumn(name = "group_id")},
    inverseJoinColumns = {
        @JoinColumn(name = "authority_id")})
	public List<Authority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<Authority> authorities) {
		this.authorities = authorities;
	}

}
