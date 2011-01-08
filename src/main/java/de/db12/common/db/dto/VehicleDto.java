package de.db12.common.db.dto;

import java.io.Serializable;

public class VehicleDto implements Serializable {

	Integer id;

	String license;

	Integer version;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

}
