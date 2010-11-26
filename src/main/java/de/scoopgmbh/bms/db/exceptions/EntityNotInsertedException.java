package de.scoopgmbh.bms.db.exceptions;

/**
 * Zeit an, dass eine Entit�t nicht in die Datenbank eingef�gt werden konnten.
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
