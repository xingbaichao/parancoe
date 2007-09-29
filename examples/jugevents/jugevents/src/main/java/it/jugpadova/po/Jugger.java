/**
 *
 */
package it.jugpadova.po;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.parancoe.persistence.po.hibernate.EntityBase;
import org.parancoe.plugins.security.User;
import org.springmodules.validation.bean.conf.loader.annotation.handler.CascadeValidation;
import org.springmodules.validation.bean.conf.loader.annotation.handler.Email;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotBlank;
import it.jugpadova.blo.JuggerBo;




/**
 * @author Enrico Giurin & Lucio benfante.
 *
 */
@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames = {"email"})})
@NamedQueries(value = {@NamedQuery(name = "Jugger.searchByUsername", query =
        "from Jugger j where j.user.username = ?"), @NamedQuery(name =
        "Jugger.findByUsernameAndConfirmationCode", query =
        "from Jugger j where j.user.username = ? and j.confirmationCode = ?"), @NamedQuery(name =
        "Jugger.findByUsernameAndChangePasswordCode", query =
        "from Jugger j where j.user.username = ? and j.changePasswordCode = ?"), @NamedQuery(name =
        "Jugger.findByPartialJugNameAndCountryAndContinent", query =
        "from Jugger j where upper(j.jug.name) like upper(?) and upper(j.jug.country.localName) like upper(?) and upper(j.jug.country.continent.name) like upper(?) order by j.jug.name asc"), @NamedQuery(name =
        "Jugger.findAllOrderByUsername", query =
        "from Jugger j order by j.user.username asc")})
public class Jugger extends EntityBase {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    @Email
    private String email;

    @CascadeValidation
    private JUG jug;

    @CascadeValidation
    private User user;

    private String confirmationCode;
    private String changePasswordCode;
    private double  reliability=0.0d;
     

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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @OneToOne(cascade = CascadeType.ALL)
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

    public String getChangePasswordCode() {
        return changePasswordCode;
    }

    public void setChangePasswordCode(String changePasswordCode) {
        this.changePasswordCode = changePasswordCode;
    }

    @ManyToOne
    public JUG getJug() {
        return jug;
    }

    public void setJug(JUG jug) {
        this.jug = jug;
    }

	public double getReliability() {
		return reliability;
	}

	public void setReliability(double reliability) {		
		this.reliability = reliability;
	}
}
