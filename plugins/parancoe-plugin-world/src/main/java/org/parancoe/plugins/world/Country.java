package org.parancoe.plugins.world;

import javax.persistence.*;

import org.parancoe.persistence.po.hibernate.EntityBase;

/**
 * @author Lucio Benfante lucio.benfante@jugpadova.it
 */
@Entity
@NamedQueries(value = {@NamedQuery(name = "Country.findByPartialLocalName", query = "from Country c where upper(c.localName) like upper(?) order by c.localName asc"),
@NamedQuery(name = "Country.findAllOrderedByEnglishNameAsc", query = "from Country c order by englishname asc")})


public class Country extends EntityBase {

    private String isoCode;
    private String languageIsoCode;
    private String languageVariant;
    private String localName;
    private String englishName;
    private Continent continent;

    public Country() {
    }

    @ManyToOne
    @JoinColumn(name = "continent_id")
    public Continent getContinent() {
        return continent;
    }

    public void setContinent(Continent continent) {
        this.continent = continent;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    @Column(length = 2)
    public String getIsoCode() {
        return isoCode;
    }

    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }

    @Column(length = 2)
    public String getLanguageIsoCode() {
        return languageIsoCode;
    }

    public void setLanguageIsoCode(String languageIsoCode) {
        this.languageIsoCode = languageIsoCode;
    }

    @Column(length = 32)
    public String getLanguageVariant() {
        return languageVariant;
    }

    public void setLanguageVariant(String languageVariant) {
        this.languageVariant = languageVariant;
    }

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    @Override
    public String toString() {
        return "Country{" + "isoCode='" + isoCode + '\'' + ", localName='" + localName + '\'' + ", englishName='" + englishName + '\'' + ", continent=" + continent + '}';
    }
}