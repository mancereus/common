package de.scoopgmbh.bms.db.exceptions;

/**
 * Root-Klasse für alle Exceptions im DBLayer.<br>
 * 
 * 
 * @author hirtech
 *
 */
public class DBLayerException extends RuntimeException {

	private static final long serialVersionUID = 8745606356218859234L;
	
	public DBLayerException(String message) {
		super(message);
	}
	
	public DBLayerException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
