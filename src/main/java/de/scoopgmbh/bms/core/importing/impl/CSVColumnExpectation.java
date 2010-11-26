package de.scoopgmbh.bms.core.importing.impl;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.log4j.Logger;

/**
 * Einfache Basisklasse welche nach Instantiierung eine Spalte einer
 * CSV-Datei abbildet. Für gewöhnlich sind die Werte in den 
 * Spalten der CSV je Spalte homogen in ihrem Typ. Diese Klasse
 * speichert daher ebenfalls den erwarteten Datentyp und kann
 * konkrete Werte aus der CSV auf ihre Validität hin überprüfen
 * und ggf. konvertieren.
 * 
 * An isValid() und getConvertedValue() sollten nur Werte übergeben werden,
 * die aus der entsprechenden Spalte kommen, welche diese CSVColumnExpectation
 * abbildet.
 * 
 * @author hirtech
 *
 */
public class CSVColumnExpectation<T> {

	private static final Logger LOGGER = Logger.getLogger(CSVColumnExpectation.class);
	private static final NumberFormat NF = NumberFormat.getNumberInstance(Locale.GERMANY);
	private static final DateFormat DF = SimpleDateFormat.getDateInstance(SimpleDateFormat.MEDIUM, Locale.GERMANY);
	
	private String headerName = null;		// Bezeichnung der Spalte in der CSV Datei
	protected Class<T> dataType = null;		// Datentyp der Spalte
	private boolean nullAllowed = false;	// Darf die Spalte null sein?
	private long maxLength = -1;			// maximale Länge der Daten. Bei String: Anzahl der Character, bei Zahlen: die Größe der Zahl
	private long minLength = -1;			// siehe maxLength
	protected T defaultValue = null;
	
	public CSVColumnExpectation(){}
	
	public CSVColumnExpectation(String headerName, Class<T> dataType, boolean nullAllowed, long minLength, long maxLength) {
		super();
		this.headerName = headerName;
		this.dataType = dataType;
		this.nullAllowed = nullAllowed;
		this.maxLength = maxLength;
		this.minLength = minLength;
	}
	
	public CSVColumnExpectation(String headerName, Class<T> dataType, boolean nullAllowed, long minLength, long maxLength, T defaultValue) {
		super();
		this.headerName = headerName;
		this.dataType = dataType;
		this.nullAllowed = nullAllowed;
		this.maxLength = maxLength;
		this.minLength = minLength;
		this.defaultValue = defaultValue;
	}
	
	public boolean isNullAllowed() {
		return nullAllowed;
	}
	
	/**
	 * Prüft ob der Übergebene Wert der Erwartung enspricht.
	 * Kann von Unterklassen Überschrieben werden.
	 * @param value
	 * @return
	 */
	public boolean isValid(String value) {
		boolean isValueValid = false; 
		
		if (value==null || value.length()==0) {
			// Hier gleich aussteigen, da sonst unten
			// NPEs auftreten
			return this.isNullAllowed(); 
		}
		
		try {
			if (dataType.equals(String.class)) {
				isValueValid = (value.length()>=this.minLength 
						&& value.length()<=this.maxLength);
			} else if (dataType.equals(Long.class)) {
				Long l = NF.parse(value).longValue();
				isValueValid = (l>=this.minLength && l<=this.maxLength);
			} else if (dataType.equals(Float.class)) {
				Float f = NF.parse(value).floatValue();
				isValueValid = (f>=this.minLength && f<=this.maxLength);
			} else if (dataType.equals(Integer.class)) {
				Integer i = NF.parse(value).intValue();
				isValueValid = (i>=this.minLength && i<=this.maxLength);
			} else if (dataType.equals(Date.class)) {
				isValueValid = (DF.parse(value)!=null);
			}  else if (dataType.equals(Double.class)) {
				Double f = NF.parse(value).doubleValue();
				isValueValid = (f>=this.minLength && f<=this.maxLength);
			}
		} catch (Exception e) {
			LOGGER.error(e, e);
		}

		return isValueValid;
	}
	
	/**
	 * Sollte erst nach einem Aufruf von <code>isValid()</code>
	 * aufgerufen werden und auch nur dann, wenn <code>isValid()</code>
	 * true zurück gegeben hat. 
	 * 
	 * @param value
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T getConvertedValue(String value) {
		T convertedValue = null;
		
		if (value==null || value.length()==0) {
			// Hier gleich aussteigen, da sonst unten
			// NPEs auftreten
			return (T)this.defaultValue;
		}
		
		try {
			if (dataType.equals(String.class)) {
				convertedValue = (T)value;
			} else if (dataType.equals(Long.class)) {
				convertedValue = (T) new Long(NF.parse(value).longValue());
			} else if (dataType.equals(Float.class)) {
				convertedValue = (T) new Float(NF.parse(value).floatValue());
			} else if (dataType.equals(Integer.class)) {
				convertedValue = (T) new Integer(NF.parse(value).intValue());
			} else if (dataType.equals(Date.class)) {
				convertedValue = (T) DF.parse(value);
			} else if (dataType.equals(Double.class)) {
				convertedValue = (T) new Double(NF.parse(value).doubleValue());
			}
		} catch (Exception e) {
			LOGGER.error(e, e);
			try {
				convertedValue = (T) this.dataType.newInstance();
			} catch (Exception e1) {
				LOGGER.error(e1, e1);
			}
		}
		return convertedValue;
	}
	
	@Override
	/**
	 * ACHTUNG - KLEINER HACK AN DISER STELLE.
	 * 
	 * NICHT ZUHAUSE NACHMACHEN !!!!!
	 */
	public String toString() {
		return headerName;
	}

	@Override
	/**
	 * ACHTUNG - KLEINER HACK AN DISER STELLE.
	 * 
	 * NICHT ZUHAUSE NACHMACHEN !!!!!
	 */
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((headerName == null) ? 0 : headerName.hashCode());
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * ACHTUNG - KLEINER HACK AN DISER STELLE.
	 * 
	 * NICHT ZUHAUSE NACHMACHEN !!!!
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof CSVColumnExpectation))
			return false;
		CSVColumnExpectation<T> other = (CSVColumnExpectation<T>) obj;
		if (headerName == null) {
			if (other.headerName != null)
				return false;
		} else if (!headerName.equalsIgnoreCase(other.headerName))
			return false;
		return true;
	}

	public String getHeaderName() {
		return headerName;
	}

	public void setHeaderName(String headerName) {
		this.headerName = headerName;
	}

	public Class<T> getDataType() {
		return dataType;
	}

	public void setDataType(Class<T> dataType) {
		this.dataType = dataType;
	}

	public long getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(long maxLength) {
		this.maxLength = maxLength;
	}

	public long getMinLength() {
		return minLength;
	}

	public void setMinLength(long minLength) {
		this.minLength = minLength;
	}

	public void setNullAllowed(boolean nullAllowed) {
		this.nullAllowed = nullAllowed;
	}
}
