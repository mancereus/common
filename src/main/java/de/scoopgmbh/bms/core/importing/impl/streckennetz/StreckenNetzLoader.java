package de.scoopgmbh.bms.core.importing.impl.streckennetz;

import java.util.LinkedHashSet;

import de.scoopgmbh.bms.core.importing.impl.AbstractCSVLoader;
import de.scoopgmbh.bms.core.importing.impl.CSVColumnExpectation;
import de.scoopgmbh.bms.core.importing.impl.EnumerationCSVColumnExpectation;
import de.scoopgmbh.bms.db.model.Abschnitt;
import de.scoopgmbh.bms.db.model.KnotenTyp;

/**
 * Spezialisierte Klasse zum Laden einer StreckenNetz CSV Datei.
 *  
 * @author hirtech
 *
 */
class StreckenNetzLoader extends AbstractCSVLoader {

	/**
	 * Spaltenname in CSV Datei f�r den die Spalte mit den 
	 * Straßentypen, z.B. 'A' für Autobahn, 'B' für Bundesstraße, 'K' für Kreisstraße
	 */
	protected static final String ROADTYP = "ROADTYP";
	
	/**
	 * Spaltenname in CSV Datei für die Spalte mit den Straßennummer.
	 * Zusammen mit {@link ROADTYP} eindeutiges Indentifizierungsmerkal
	 * einer deutschen Straße
	 */
	protected static final String ROADNO = "ROADNO";
	
	/**
	 * Spaltenname in CSV Datei für die Spalte mit den Bezeichnung 
	 * der Knotenpunkten, die jeweils den Anfang eines Abschnittes
	 * darstellen.
	 */
	protected static final String KNT_NAME_VON = "KNT_NAME_VON";
	
	/**
	 * Spaltenname in CSV Datei für die Spalte mit den Bezeichnung 
	 * der Knotenpunkten, die jeweils das Ende eines Abschnittes
	 * darstellen.
	 */
	protected static final String KNT_NAME_BIS = "KNT_NAME_BIS";
	
	/**
	 * Spaltenname in CSV Datei für die Spalte mit den Bezeichnungen
	 * der Knotentypen.
	 */
	protected static final String KNT_TYP_VON = "KNT_TYP_VON";
	
	/**
	 * Spaltenname in CSV Datei für die Spalte mit den Bezeichnungen
	 * der Knotentypen.
	 */
	protected static final String KNT_TYP_BIS = "KNT_TYP_BIS";
	
	/**
	 * Spaltenname in CSV Datei für die Spalte mit den Zahlen für
	 * die Betriebskilometer der jeweiligen Knoten.
	 */
	protected static final String BKM_VON = "BKM_VON";
	
	/**
	 * Spaltenname in CSV Datei f�r die Spalte mit den Zahlen f�r
	 * die Betriebskilometer der jeweiligen Knoten.
	 */
	protected static final String BKM_BIS = "BKM_BIS";
	
	/**
	 * Spaltenname in CSV Datei f�r die Spalte mit den Zahlen f�r
	 * die Betriebskilometer der jeweiligen Knoten.
	 */
	protected static final String BKM_VON2 = "BKM_VON2";
	
	/**
	 * Spaltenname in CSV Datei f�r die Spalte mit den Zahlen f�r
	 * die Betriebskilometer der jeweiligen Knoten.
	 */
	protected static final String BKM_BIS2 = "BKM_BIS2";
	
	protected static final String BLOCKNUM = "BLOCK";
	
	/**
	 * Spaltenname in CSV Datei für die Spalte mit den Zahlen für
	 * die Länge eines Autobahnabschnittes
	 */
	protected static final String LAENGE = "LAENGE";
	
	/**
	 * Spaltenname in CSV Datei für die Spalte mit den Zahlen für
	 * die Anzahl der Fahrstreifen
	 */
	protected static final String FS_COUNT = "FS_COUNT";
	
	/**
	 * Spaltenname in CSV Datei für die Spalte mit den Zahlen für
	 * die Anzahl der Standstreifen
	 */
	protected static final String STST_COUNT = "STST_COUNT";
	
	protected static final String RICHTUNG = "RICHTUNG";
	
	protected static final String STRECKENNR = "STRECKENNR";
	
	StreckenNetzLoader() {
		
		LinkedHashSet<CSVColumnExpectation<?>> cols = new LinkedHashSet<CSVColumnExpectation<?>>();
		cols.add(new CSVColumnExpectation<String>(ROADTYP, String.class, false, 1, 1));
		cols.add(new CSVColumnExpectation<Integer>(ROADNO, Integer.class, false, 1, 1000));
		cols.add(new CSVColumnExpectation<String>(KNT_NAME_VON, String.class, false, 1, 255));
		cols.add(new CSVColumnExpectation<String>(KNT_NAME_BIS, String.class, false, 1, 255));
		cols.add(new EnumerationCSVColumnExpectation(KNT_TYP_VON, 
				KnotenTyp.ANSCHLUSSSTELLE, KnotenTyp.AUTOBAHNANSCHLUSS, KnotenTyp.AUTOBAHNDREIECK, KnotenTyp.AUTOBAHNKREUZ,
				KnotenTyp.BUNDESSTRASSENPUNKT, KnotenTyp.LANDESGRENZE, KnotenTyp.RAMPENPUNKT, KnotenTyp.BAUAMTSGRENZE));
		cols.add(new EnumerationCSVColumnExpectation(KNT_TYP_BIS, 
				KnotenTyp.ANSCHLUSSSTELLE, KnotenTyp.AUTOBAHNANSCHLUSS,	KnotenTyp.AUTOBAHNDREIECK, KnotenTyp.AUTOBAHNKREUZ, 
				KnotenTyp.BUNDESSTRASSENPUNKT, KnotenTyp.LANDESGRENZE, KnotenTyp.RAMPENPUNKT, KnotenTyp.BAUAMTSGRENZE));
		cols.add(new CSVColumnExpectation<Float>(BKM_VON, Float.class, false, 0, 999));
		cols.add(new CSVColumnExpectation<Float>(BKM_BIS, Float.class, false, 0, 999));
		cols.add(new CSVColumnExpectation<Float>(BKM_VON2, Float.class, true, 0, 999, -1.0f));
		cols.add(new CSVColumnExpectation<Float>(BKM_BIS2, Float.class, true, 0, 999, -1.0f));
		cols.add(new CSVColumnExpectation<Integer>(BLOCKNUM, Integer.class, false, 1, 999));
		// FIXME Länge vielleicht (mindestens) größer oder gleich 0,001?
		cols.add(new CSVColumnExpectation<Float>(LAENGE, Float.class, false, 0, 999)); 
		cols.add(new CSVColumnExpectation<Integer>(FS_COUNT, Integer.class, false, 1, 5));
//		cols.add(new CSVColumnExpectation(STST_COUNT, Integer.class, false, 0, 2));
		cols.add(new EnumerationCSVColumnExpectation(RICHTUNG, Abschnitt.NORDOST, Abschnitt.SUEDWEST));
		cols.add(new CSVColumnExpectation<Integer>(STRECKENNR, Integer.class, false, 1, 999));
		setExpectedCsvColumns(cols);
	}

}
