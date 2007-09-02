/**
 *
 */
package it.jugpadova.po;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

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
@NamedQueries(value = 
{@NamedQuery(name = "JUG.findByPartialName", query = "from JUG j where upper(j.name) like upper(?) order by j.name asc"),
@NamedQuery(name = "JUG.findByPartialJugNameAndCountry", query =
"from JUG jug where upper(jug.name) like upper(?) and upper(jug.country.englishName) like upper(?) order by jug.name asc"),
@NamedQuery(name = "JUG.findByNameAndCountryEN", query =
"from JUG jug where upper(jug.name) = upper(?) and upper(jug.country.englishName) = upper(?) order by jug.name asc")}
)

public class JUG extends EntityBase {

	private static final long serialVersionUID = -40063909128565029L;

	/**
     * JUG name
     */
    @NotBlank
    private String name;

    @CascadeValidation
    private Country country;
    
    private String webSite;
    
    Double latitude;
    
    Double longitude;

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

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }
        
}
