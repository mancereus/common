package de.scoopgmbh.bms.core.importing.impl;

import java.util.LinkedHashMap;


public class CSVRow {
	
	/**
	 * Jede CSV-Row ansich ist eine Liste von Feldern.
	 * Einfügereihenfolge interessiert!
	 * Key = Name der Spalte
	 * Value = Wert der Spalte
	 */
	private LinkedHashMap<String, ValueHolder> felder = new LinkedHashMap<String, ValueHolder>();
	
	public CSVRow() {}

	public void put(String k, ValueHolder o) {
		felder.put(k,o);
	}
	
	public ValueHolder get(String k) {
		return felder.get(k);
	}

	@Override
	public String toString() {
		return "CSVRow [felder=" + felder + "]";
	}
	
	/**
	 * ValueHolder ist eine Wrapper-Klasse die dazu dient, 
	 * den String-Wert eines Feldes aus der CSV-Datei zusammen mit seiner
	 * Expectation abzuspeichern. 
	 * Die Expectation kennt den Algorithmus zum Umwandeln des Strings in
	 * den designierten Datentyp durch <code>getConvertedValue()</code>.
	 * 
	 * @author hirtech
	 *
	 */
	public static class ValueHolder {
		private String value;
		private CSVColumnExpectation<?> expectation;
		
		public ValueHolder(String s, CSVColumnExpectation<?> exp) {
			this.setValue(s);
			this.expectation = exp;
		}

		public CSVColumnExpectation<?> getExpectation() {
			return expectation;
		}

		public void setExpectation(CSVColumnExpectation<?> expectation) {
			this.expectation = expectation;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}	
		
		public String toString() {
			return "'"+value+"'";
		}
	}
	
}
