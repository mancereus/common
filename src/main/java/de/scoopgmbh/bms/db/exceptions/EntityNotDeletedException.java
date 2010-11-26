package de.scoopgmbh.bms.db.exceptions;

/**
 * Zeit an, dass die Entit�t nicht in der Datebank gel�scht wurde.
 * 
 * @author hirtech
 *
 */
public class EntityNotDeletedException extends EntityNotFoundException {

	private static final long serialVersionUID = 3555599785257269722L;

	public EntityNotDeletedException(String msg, Throwable e) {
		super(msg, e);
	}

}
