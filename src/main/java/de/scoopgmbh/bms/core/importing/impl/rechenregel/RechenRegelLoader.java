package de.scoopgmbh.bms.core.importing.impl.rechenregel;

import java.util.LinkedHashSet;

import de.scoopgmbh.bms.core.importing.impl.AbstractCSVLoader;
import de.scoopgmbh.bms.core.importing.impl.CSVColumnExpectation;

class RechenRegelLoader extends AbstractCSVLoader {

	protected static final String ROADTYP = "ROADTYP";
	
	protected static final String ROADNO = "ROADNO";
	
	protected static final String KNT_NAME_VON = "KNT_NAME_VON";
	
	protected static final String KNT_NAME_BIS = "KNT_NAME_BIS";
	
	protected static final String ZST1 = "ZST1";
	
	protected static final String OP1 = "OP1";
	
	protected static final String ZST2 = "ZST2";
	
	protected static final String OP2 = "OP2";
	
	protected static final String ZST3 = "ZST3";
	
	RechenRegelLoader() {
		super();
		LinkedHashSet<CSVColumnExpectation<?>> cols = new LinkedHashSet<CSVColumnExpectation<?>>();
		cols.add(new CSVColumnExpectation<String>(ROADTYP, String.class, false, 1, 1));
		cols.add(new CSVColumnExpectation<Integer>(ROADNO, Integer.class, false, 1, 1000));
		cols.add(new CSVColumnExpectation<String>(KNT_NAME_VON, String.class, false, 1, 255));
		cols.add(new CSVColumnExpectation<String>(KNT_NAME_BIS, String.class, false, 1, 255));
		cols.add(new CSVColumnExpectation<String>(ZST1, String.class, false, 1, 255));
		cols.add(new CSVColumnExpectation<String>(OP1, String.class, true, 1, 1));
		cols.add(new CSVColumnExpectation<String>(ZST2, String.class, true, 1, 255));
		cols.add(new CSVColumnExpectation<String>(OP2, String.class, true, 1, 1));
		cols.add(new CSVColumnExpectation<String>(ZST3, String.class, true, 1, 255));
		setExpectedCsvColumns(cols);
	}

}
