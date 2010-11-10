package de.scoopgmbh.common.db.dto;

import java.io.Serializable;

public class CarDto extends VehicleDto implements Serializable {

	String driver;

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

}
