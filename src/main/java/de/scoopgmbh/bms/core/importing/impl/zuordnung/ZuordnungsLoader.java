package de.scoopgmbh.bms.core.importing.impl.zuordnung;

import java.util.LinkedHashSet;

import de.scoopgmbh.bms.core.importing.impl.AbstractCSVLoader;
import de.scoopgmbh.bms.core.importing.impl.CSVColumnExpectation;

class ZuordnungsLoader extends AbstractCSVLoader {
	
	protected static final String ROADTYP = "ROADTYP";
	
	protected static final String ROADNR = "ROADNR";
	
	protected static final String MEISTEREI = "MEISTEREINAME";
	
	protected static final String STRECKE = "STRECKENNR";
	
	protected static final String BLOCK = "BLOCKNR";
	
	protected static final String BKM_VON = "BKM-VON";
	
	protected static final String BKM_BIS = "BKM-BIS";
	
	private static final Long MAX_ROADNO = 999l;
	private static final Long MIN_ROADNO = 1l;
	private static final Long MAX_STRECKENNR = 999l;
	private static final Long MIN_STRECKENNR = 1l;
	private static final Long MAX_BLOCKNR = 999l;
	private static final Long MIN_BLOCKNR = 1l;
	private static final Long MAX_BKM = 999l;
	private static final Long MIN_BKM = 0l;
	
	ZuordnungsLoader() {
		LinkedHashSet<CSVColumnExpectation<?>> cols = new LinkedHashSet<CSVColumnExpectation<?>>();
		cols.add(new CSVColumnExpectation<String>(ROADTYP, String.class, false, 1, 1));
		cols.add(new CSVColumnExpectation<Integer>(ROADNR, Integer.class, false, MIN_ROADNO, MAX_ROADNO));
		cols.add(new CSVColumnExpectation<String>(MEISTEREI, String.class, false, 1, 255));
		cols.add(new CSVColumnExpectation<Integer>(STRECKE, Integer.class, false, MIN_STRECKENNR, MAX_STRECKENNR));
		cols.add(new CSVColumnExpectation<Integer>(BLOCK, Integer.class, false, MIN_BLOCKNR, MAX_BLOCKNR));
		cols.add(new CSVColumnExpectation<Double>(BKM_VON, Double.class, false, MIN_BKM, MAX_BKM));
		cols.add(new CSVColumnExpectation<Double>(BKM_BIS, Double.class, false, MIN_BKM, MAX_BKM));
		this.setExpectedCsvColumns(cols);
	}
}
