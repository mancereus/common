package de.db12.common.db.dto;

import java.io.Serializable;

public class CountryDto implements Serializable {

	String code;

	String name;

	/**
	 * Return code.
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Set code.
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Return name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set name.
	 */
	public void setName(String name) {
		this.name = name;
	}

}
