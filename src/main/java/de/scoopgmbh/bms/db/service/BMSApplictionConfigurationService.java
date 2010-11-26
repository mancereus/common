package de.scoopgmbh.bms.db.service;

import java.util.List;

import de.scoopgmbh.bms.db.model.RSARegelPlan;

public interface BMSApplictionConfigurationService {

	public Object getConfigurationValue(String key);
	
	public List<Object> getConfigurationValues();
	public List<Object> getConfigurationValues(String category);
	public List<RSARegelPlan> getAllRSAPlaene();
}
