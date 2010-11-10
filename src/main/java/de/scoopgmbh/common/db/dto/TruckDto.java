package de.scoopgmbh.common.db.dto;

import java.io.Serializable;

public class TruckDto extends VehicleDto implements Serializable {

	int capacity;

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

}
