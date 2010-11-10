package de.scoopgmbh.common.db.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class AddressDto implements Serializable {

	Short id;

	String line1;

	String line2;

	String city;

	Timestamp cretime;

	Timestamp updtime;

	CountryDto country;

	public AddressDto() {

	}

	/**
	 * Return id.
	 */
	public Short getId() {
		return id;
	}

	/**
	 * Set id.
	 */
	public void setId(Short id) {
		this.id = id;
	}

	/**
	 * Return line 1.
	 */
	public String getLine1() {
		return line1;
	}

	/**
	 * Set line 1.
	 */
	public void setLine1(String line1) {
		this.line1 = line1;
	}

	/**
	 * Return line 2.
	 */
	public String getLine2() {
		return line2;
	}

	/**
	 * Set line 2.
	 */
	public void setLine2(String line2) {
		this.line2 = line2;
	}

	/**
	 * Return city.
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Set city.
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * Return cretime.
	 */
	public Timestamp getCretime() {
		return cretime;
	}

	/**
	 * Set cretime.
	 */
	public void setCretime(Timestamp cretime) {
		this.cretime = cretime;
	}

	/**
	 * Return updtime.
	 */
	public Timestamp getUpdtime() {
		return updtime;
	}

	/**
	 * Set updtime.
	 */
	public void setUpdtime(Timestamp updtime) {
		this.updtime = updtime;
	}

	/**
	 * Return country.
	 */
	public CountryDto getCountry() {
		return country;
	}

	/**
	 * Set country.
	 */
	public void setCountry(CountryDto country) {
		this.country = country;
	}

}
