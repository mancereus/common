package de.scoopgmbh.bms.core.importing.impl.steigungsklassen;

import java.util.LinkedHashSet;

import de.scoopgmbh.bms.core.importing.impl.AbstractCSVLoader;
import de.scoopgmbh.bms.core.importing.impl.CSVColumnExpectation;

public class SteigungsKlassenLoader extends AbstractCSVLoader {

	protected static final String ROADTYP = "ROADTYP";
	
	protected static final String ROADNO = "ROADNO";
	
	protected static final String KNOTEN = "KNOTENNAME";
	
	protected static final String BKM_KNOTEN = "BKM_KNOTEN";
	
	protected static final String DIST_NEXT_KNOTEN = "DIST_TO_KNT";
	
	/**
	 * Es handelt sich hierbei nicht um einen BKM sondern 
	 * um den Streckenkilometer!!!
	 */
	protected static final String KM_VON = "KM_VON";
	
	/**
	 * Es handelt sich hierbei nicht um einen BKM sondern
	 * um den Streckenkilometer !!!
	 */
	protected static final String KM_BIS = "KM_BIS";
	
	protected static final String STEIGUNG = "STEIGUNG";
	
	protected static final String LAENGE = "LAENGE";
	
	protected static final String VMINNO = "VMIN_NO";
	
	protected static final String VMINSW = "VMIN_SW";
	
	protected static final String STKL_NO = "STKLNO";
	
	protected static final String STKL_SW = "STKLSW";
	
	protected static final String FSCNT_NO = "STKLNO";
	
	protected static final String FSCNT_SW = "STKLSW";
	
	public SteigungsKlassenLoader() {
		LinkedHashSet<CSVColumnExpectation<?>> set = new LinkedHashSet<CSVColumnExpectation<?>>();
		
		set.add(new CSVColumnExpectation<String>(SteigungsKlassenLoader.ROADTYP, String.class, false, 1, 1));
		set.add(new CSVColumnExpectation<Integer>(SteigungsKlassenLoader.ROADNO, Integer.class, false, 1, 1));
		set.add(new CSVColumnExpectation<String>(SteigungsKlassenLoader.KNOTEN, String.class, false, 1, 255));
		set.add(new CSVColumnExpectation<Float>(SteigungsKlassenLoader.BKM_KNOTEN, Float.class, false, 0, 999));
		set.add(new CSVColumnExpectation<Float>(SteigungsKlassenLoader.DIST_NEXT_KNOTEN, Float.class, false, 0, 999));
		set.add(new CSVColumnExpectation<Float>(SteigungsKlassenLoader.KM_VON, Float.class, false, 0, 999));
		set.add(new CSVColumnExpectation<Float>(SteigungsKlassenLoader.KM_BIS, Float.class, false, 0, 999));
		set.add(new CSVColumnExpectation<Float>(SteigungsKlassenLoader.STEIGUNG, Float.class, false, 0, 1));
		set.add(new CSVColumnExpectation<Float>(SteigungsKlassenLoader.LAENGE, Float.class, false, 0, 999));
		set.add(new CSVColumnExpectation<Integer>(SteigungsKlassenLoader.VMINNO, Integer.class, false, 0, 250));
		set.add(new CSVColumnExpectation<Integer>(SteigungsKlassenLoader.VMINSW, Integer.class, false, 0, 250));
		set.add(new CSVColumnExpectation<Integer>(SteigungsKlassenLoader.STKL_NO, Integer.class, false, 0, 5));
		set.add(new CSVColumnExpectation<Integer>(SteigungsKlassenLoader.STKL_SW, Integer.class, false, 0, 5));
		set.add(new CSVColumnExpectation<Integer>(SteigungsKlassenLoader.FSCNT_NO, Integer.class, false, 1, 10));
		set.add(new CSVColumnExpectation<Integer>(SteigungsKlassenLoader.FSCNT_SW, Integer.class, false, 1, 10));
		
		this.setExpectedCsvColumns(set);
	}
	
}
