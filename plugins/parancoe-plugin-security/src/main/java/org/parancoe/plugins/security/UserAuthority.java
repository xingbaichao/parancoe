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

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import org.parancoe.persistence.po.hibernate.EntityBase;


/**
 * A PO for User-Authority relationship.
 *
 * @author Lucio Benfante
 */
@javax.persistence.Entity()
@Table(name="USER_AUTHORITY")
/*
@NamedQueries({
@NamedQuery(name = "User_Authority.insertByUsernameAndRole",
        query = "insert into user_authority (user_id, authority_id) values "+
        "((select id from user where username=?), (select id from authority where role=?))")
        })
*/
public class UserAuthority extends EntityBase {

    private User user;
    private Authority authority;

    @ManyToOne    
    @JoinColumn(name="authority_id")
    public Authority getAuthority() {
        return authority;
    }

    public void setAuthority(Authority authority) {
        this.authority = authority;
    }

    @ManyToOne
    @JoinColumn(name="user_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

	
	/*
    @Override
	public String toString() {
		// TODO Auto-generated method stub
		return "\nUser: "+user.toString()+"\nAuthority: "+authority.toString();
	}
	*/
        
}
