package de.scoopgmbh.bms.core.importing.impl;



/**
 * Ist eine CSVColumnExpectation mit der M�glichkeit eine Menge von erwarteten
 * Werten zu definieren und den Inhalt der CSV gegen die Werte zu vergleichen.
 * 
 * @author hirtech
 *
 * @param <T>
 */
public class EnumerationCSVColumnExpectation extends CSVColumnExpectation<String> {

	private String[] expectedValues = null;
	
	public EnumerationCSVColumnExpectation(String headerName, String... expectedValues) {
		super(headerName, String.class, false, 1, 255);
		this.expectedValues = expectedValues;
	}

	@Override
	public boolean isValid(String value) {
		for(String e : expectedValues) 
			if (e.equalsIgnoreCase(value.trim()))
				return true;
		return false;
	}
}
