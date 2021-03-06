/**
 * Copyright (C) 2006-2012 The Parancoe Team <info@parancoe.org>
 *
 * This file is part of Parancoe Plugin Security.
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
package org.parancoe.plugins.security;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.validator.constraints.NotBlank;
import org.lambico.po.hibernate.EntityBase;


/**
 * A PO for UserProfile table.
 *
 * @author <a href="mailto:enrico.giurin@gmail.com">Enrico Giurin</a>
 * @author michele franzin <michele at franzin.net>
 * @version $Revision$
 */
@javax.persistence.Entity
@javax.persistence.Table(name="PSEC_USER")
public class User extends EntityBase {

    private static final long serialVersionUID = 832363948575562242L;
    @NotBlank
    private String username = null;
    @NotBlank
    private String password = null;

    private boolean enabled = true;
    /**
     * Empty constructor
     *
     */
    public User()
    {
    	
    }
    

    private List<Authority> authorities = new ArrayList<Authority>();

    public boolean isEnabled() {
        return enabled;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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
    @JoinTable(name="USER_AUTHORITY", 
               joinColumns={@JoinColumn(name="USER_ID")},
               inverseJoinColumns={@JoinColumn(name="AUTHORITY_ID")})
    public List<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "username: "+username+" - password: <XXX> - enabled: "+enabled;
	}
    
}
