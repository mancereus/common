package de.scoopgmbh.bms.core.importing.impl;

import java.util.HashMap;

/**
 * Ist ein CSVColumnExpectation mit der Möglichkeit einzelne Werte
 * in CSV durch andere zu ersetzen.
 * 
 * @author hirtech
 *
 * @param <T>
 */
public class SubstitutionableCSVColumnExpectation<T> extends
		CSVColumnExpectation<T> {

	private HashMap<String, String> substitutions = null;
	
	public SubstitutionableCSVColumnExpectation(String headerName,
			Class<T> dataType, boolean nullAllowed, long minLength,
			long maxLength, T defaultValue, HashMap<String, String> substitutions) {
		super(headerName, dataType, nullAllowed, minLength, maxLength, defaultValue);
		this.substitutions = substitutions;
	}
	
	
	@Override
	public T getConvertedValue(String value) {
		String val = value;
		if (this.substitutions.containsKey(value))
			val = substitutions.get(value);
		return super.getConvertedValue(val);
	}
}
