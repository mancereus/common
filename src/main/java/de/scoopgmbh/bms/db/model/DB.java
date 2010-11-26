package de.scoopgmbh.bms.db.model;

/**
 * Klasse enhält alle Konstanten die im Zusammenhang mit dem RDBMS
 * in der Anwendung Verwendung finden.
 *
 * @author hirtech
 *
 */
public class DB {

	/**
	 * Für Oracle:<br>
	 * Konstante die als char den Wert "false" repräsentiert.
	 */
	public static final char NO = 'n';

	/**
	 * Für Oracle:<br>
	 * Konstante die als char den Wert "true" repräsentiert.
	 */
	public static final char YES = 'y';

	public static final char SPERRUNG_VON_LINKS = 'l';
	public static final char SPERRUNG_VON_RECHTS = 'r';
	public static final char SPERRUNG_INSEL = 'i';

	public static final char ABSICHERUNG_SBA_ANLAGE = 'a';
	public static final char ABSICHERUNG_SBA_FS = 'f';
}
