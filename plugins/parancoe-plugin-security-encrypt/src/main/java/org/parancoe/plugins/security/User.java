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
package org.parancoe.plugins.security;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import org.parancoe.persistence.po.hibernate.EntityBase;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotBlank;


/**
 * A PO for UserProfile table.
 *
 * @author <a href="mailto:enrico.giurin@gmail.com">Enrico Giurin</a>
 * @author <a href="mailto:michele.franzin@seesaw.it">Michele Franzin</a>
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
    private String oldPassword = null;

    private boolean enabled = true;
    /**
     * Empty constructor
     *
     */
    public User()
    {
    	
    }
    

    private List<UserAuthority> userAuthority = new ArrayList<UserAuthority>();

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

    @OneToMany(mappedBy="user")
    public List<UserAuthority> getUserAuthority() {
        return userAuthority;
    }

    public void setUserAuthority(List<UserAuthority> userAuthority) {
        this.userAuthority = userAuthority;
    }

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "username: "+username+" - password: <XXX> - enabled: "+enabled;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
    
}
