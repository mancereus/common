package de.scoopgmbh.bms.db.exceptions;

/**
 * Zeit an, dass eine Entität nicht in die Datenbank eingefügt werden konnten.
 * 
 * 
 * @author hirtech
 *
 */
public class EntityNotInsertedException extends EntityNotUpdatedException {

	private static final long serialVersionUID = 961752784587722453L;

	public EntityNotInsertedException(String msg) {
		super(msg);
	}
	
	public EntityNotInsertedException(String msg, Throwable e) {
		super(msg, e);
	}

}
