/**
 *
 */
package it.jugpadova.po;

import java.net.URI;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import org.parancoe.persistence.po.hibernate.EntityBase;
import org.parancoe.plugins.world.Country;
import org.springmodules.validation.bean.conf.loader.annotation.handler.CascadeValidation;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotBlank;

/**
 * Represents Java User Group informations.
 * @author Enrico Giurin
 *
 */
@Entity
public class JUG extends EntityBase {

    /**
     * JUG name
     */
    @NotBlank
    private String name;

    @CascadeValidation
    private Country country;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @ManyToOne
    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
