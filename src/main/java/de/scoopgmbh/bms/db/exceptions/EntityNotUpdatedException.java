package de.scoopgmbh.bms.db.exceptions;

/**
 * Zeit an, dass der Inhalt einer Eintität nicht auf die Datenbank übertragen werden konnte.
 * 
 * @author hirtech
 *
 */
public class EntityNotUpdatedException extends DBLayerException {

	private static final long serialVersionUID = 3937152055274503044L;

	public EntityNotUpdatedException(String message) {
		super(message);
	}
	
	public EntityNotUpdatedException(String message, Throwable e) {
		super(message, e);
	}

}
