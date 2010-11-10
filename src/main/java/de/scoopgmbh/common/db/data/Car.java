package de.scoopgmbh.common.db.data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;

@Entity
@Inheritance
@DiscriminatorValue("C")
public class Car extends Vehicle {

	String driver;

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

}
