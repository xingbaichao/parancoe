package org.parancoe.plugins.world;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import org.parancoe.persistence.po.hibernate.EntityBase;

/**
 * @author Lucio Benfante lucio.benfante@jugpadova.it
 */
@Entity
@NamedQueries(value = {@NamedQuery(name = "Continent.findByPartialName", query = "from Continent c where upper(c.name) like upper(?) order by c.name asc")})
public class Continent extends EntityBase {

    private String name;

    private List<Country> countries;

    public Continent() {
    }

    @Column(length = 32, unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "continent")
    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    public void addCountry(Country country) {
        country.setContinent(this);
        getCountries().add(country);
    }

    public void removeCountry(Country country) {
        getCountries().remove(country);
        country.setContinent(null);
    }

    @Override
    public String toString() {
        return "Continent{" + "name='" + name + '\'' + '}';
    }
}