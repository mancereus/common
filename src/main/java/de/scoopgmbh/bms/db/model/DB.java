package de.scoopgmbh.bms.db.model;

/**
 * Klasse enh�lt alle Konstanten die im Zusammenhang mit dem RDBMS
 * in der Anwendung Verwendung finden.
 *
 * @author hirtech
 *
 */
public class DB {

	/**
	 * F�r Oracle:<br>
	 * Konstante die als char den Wert "false" repr�sentiert.
	 */
	public static final char NO = 'n';

	/**
	 * F�r Oracle:<br>
	 * Konstante die als char den Wert "true" repr�sentiert.
	 */
	public static final char YES = 'y';

	public static final char SPERRUNG_VON_LINKS = 'l';
	public static final char SPERRUNG_VON_RECHTS = 'r';
	public static final char SPERRUNG_INSEL = 'i';

	public static final char ABSICHERUNG_SBA_ANLAGE = 'a';
	public static final char ABSICHERUNG_SBA_FS = 'f';
}
