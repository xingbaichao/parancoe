/**
 *
 */
package it.jugpadova.po;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import org.parancoe.persistence.po.hibernate.EntityBase;
import org.parancoe.plugins.security.UserProfile;
import org.springmodules.validation.bean.conf.loader.annotation.handler.Email;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotBlank;

/**
 * @author Admin
 *
 */
@Entity
public class Jugger extends EntityBase {

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String username;
    @NotBlank
    private String country;

    private String jugName;
    
    private UserProfile userProfile;

    public Jugger() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getJugName() {
        return jugName;
    }

    public void setJugName(String jugName) {
        this.jugName = jugName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
    @OneToOne
	public UserProfile getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}
}
