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

import java.util.List;

import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.lambico.po.hibernate.EntityBase;

/**
 * A PO for UserProfile table.
 * @version $Revision$
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "psec_user")
@NamedQueries(value = {
	    @NamedQuery(name = "User.findByPartialUsername",
	    query =
	    "from User u where upper(u.username) like concat(concat('%', upper(?)), '%')")
	})
public class User extends EntityBase {

    private static final long serialVersionUID = 832363948575562242L;
    @NotBlank
    private String username = null;
    @NotBlank
    private String password = null;
    private String oldPassword = null;

    @Email
    private String contactEmail;
    private boolean enabled = true;    
    private boolean locked = true;
    private List<Group> groups;

    /**
     * Empty constructor
     *
     */
    public User() {
    }
   

    public boolean isEnabled() {
        return enabled;
    }

    @NotBlank
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NotBlank
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @ManyToMany
    @ForeignKey(name = "none", inverseName = "none")
    

    @Override
    public String toString() {
        return "username: " + username + " - password: <XXX> - enabled: "
                + enabled;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }


	


	public boolean isLocked() {
		return locked;
	}


	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	@ManyToMany
    @ForeignKey(name = "none", inverseName = "none")
    @JoinTable(name = "psec_user_group",
    joinColumns = {
        @JoinColumn(name = "user_id")},
    inverseJoinColumns = {
        @JoinColumn(name = "group_id")})
	public List<Group> getGroups() {
		return groups;
	}


	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}


	public String getContactEmail() {
		return contactEmail;
	}


	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}
}
