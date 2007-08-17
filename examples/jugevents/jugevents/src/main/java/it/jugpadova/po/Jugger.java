/**
 *
 */
package it.jugpadova.po;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import org.parancoe.persistence.po.hibernate.EntityBase;
import org.parancoe.plugins.security.User;
import org.parancoe.plugins.world.Country;
import org.springmodules.validation.bean.conf.loader.annotation.handler.CascadeValidation;
import org.springmodules.validation.bean.conf.loader.annotation.handler.Email;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotBlank;

/**
 * @author Admin
 *
 */
@Entity
@NamedQueries(value = {@NamedQuery(name = "Jugger.searchByUsername", query =
        "from Jugger j where j.user.username = ?"),
    @NamedQuery(name = "Jugger.findByPartialJugNameAndCountryAndContinent", query =
        "from Jugger j where upper(j.jugName) like upper(?) and upper(j.country.localName) like upper(?) and upper(j.country.continent.name) like upper(?) order by j.jugName asc")})
public class Jugger extends EntityBase {

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    @Email
    private String email;

    private String jugName;

    @CascadeValidation
    private Country country;
    @CascadeValidation
    private User user;
    
    
    private String confirmationCode;
    private Boolean confirmed;

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

    @ManyToOne
    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @OneToOne
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

	public String getConfirmationCode() {
		return confirmationCode;
	}

	public void setConfirmationCode(String confirmationCode) {
		this.confirmationCode = confirmationCode;
	}

	public Boolean getConfirmed() {
		return confirmed;
	}

	public void setConfirmed(Boolean confirmed) {
		this.confirmed = confirmed;
	}
}
