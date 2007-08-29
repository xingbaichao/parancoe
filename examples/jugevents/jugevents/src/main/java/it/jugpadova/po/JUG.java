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
	 * JUG name, i.e. Padova
	 */
	@NotBlank
	private String name;
	/**
	 * web site JUG, i.e. www.jugpadova.it
	 */
	private URI website;
	/**
	 * address of meeting.
	 */
	private String address;
	
	@CascadeValidation
	private Country country;
	
	private String infoMail;
	/**
	 * email of jug leader.
	 */
	private String leaderMail;
	private String info;
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@ManyToOne
	public Country getCountry() {
		return country;
	}
	public void setCountry(Country country) {
		this.country = country;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getInfoMail() {
		return infoMail;
	}
	public void setInfoMail(String infoMail) {
		this.infoMail = infoMail;
	}
	public String getLeaderMail() {
		return leaderMail;
	}
	public void setLeaderMail(String leaderMail) {
		this.leaderMail = leaderMail;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public URI getWebsite() {
		return website;
	}
	public void setWebsite(URI website) {
		this.website = website;
	}
	
	

}
