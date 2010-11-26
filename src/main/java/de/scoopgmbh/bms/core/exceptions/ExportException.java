package de.scoopgmbh.bms.core.exceptions;

public class ExportException extends Exception {

	private static final long serialVersionUID = -5103809886997179222L;
	
	public ExportException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public ExportException(String message) {
		super(message);
	}
}
