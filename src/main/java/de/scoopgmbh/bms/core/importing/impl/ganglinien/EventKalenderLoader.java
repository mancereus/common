package de.scoopgmbh.bms.core.importing.impl.ganglinien;

import java.util.Date;
import java.util.LinkedHashSet;

import de.scoopgmbh.bms.core.importing.impl.AbstractCSVLoader;
import de.scoopgmbh.bms.core.importing.impl.CSVColumnExpectation;

class EventKalenderLoader extends AbstractCSVLoader {
	
	protected static final String EVID = "EV_ID";
	protected static final String EVNAME = "EV_NAME";
	protected static final String EVFKID = "EV_TYPE_FK";
	protected static final String EVDATE = "EV_DATE";
	
	
	EventKalenderLoader() {
		LinkedHashSet<CSVColumnExpectation<?>> expectedColumns = new LinkedHashSet<CSVColumnExpectation<?>>();
		expectedColumns.add(new CSVColumnExpectation<Long>(EVID, Long.class, false, 1l, Long.MAX_VALUE));
		expectedColumns.add(new CSVColumnExpectation<String>(EVNAME, String.class, false, 1l, 255l));
		expectedColumns.add(new CSVColumnExpectation<Long>(EVFKID, Long.class, false, 1l, Long.MAX_VALUE));
		expectedColumns.add(new CSVColumnExpectation<Date>(EVDATE, Date.class, false, 0l, 0l));
		setExpectedCsvColumns(expectedColumns);
	}
}
