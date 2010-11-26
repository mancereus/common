package de.scoopgmbh.bms.db.exceptions;

/**
 * Zeit an, dass eine Entit�t nicht in der Datenbank gefunden wurden.
 * Im Regelfall wird diese Exception auch in solchen F�llen geworfen werden,
 * wenn (e==null) ist: <br>
 * <pre>
 * Session sess = ...
 * MyEntity e = sess.get(MyEntity.class, myIdentifyer);
 * if (e==null) throw new EntityNotFoundException();
 * </pre>
 * 
 * @author hirtech
 *
 */
public class EntityNotFoundException extends DBLayerException {

	private static final long serialVersionUID = 6570576153531935149L;

	public EntityNotFoundException(String msg) {
		super(msg);
	}
	
	public EntityNotFoundException(String msg, Throwable e) {
		super(msg, e);
	}
}
