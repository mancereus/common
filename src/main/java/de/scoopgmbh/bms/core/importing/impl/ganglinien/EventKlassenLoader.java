package de.scoopgmbh.bms.core.importing.impl.ganglinien;

import java.util.LinkedHashSet;

import de.scoopgmbh.bms.core.importing.impl.AbstractCSVLoader;
import de.scoopgmbh.bms.core.importing.impl.CSVColumnExpectation;

class EventKlassenLoader extends AbstractCSVLoader {
	
	protected static final String EVTID = "EVT_ID";
	
	protected static final String EVTWEEKDAY = "EVT_WEEKDAY";
	
	protected static final String EVTNAME = "EVT_NAME";
	
	protected static final String EVTPRIORITY = "EVT_PRIORITY";
	
	protected static final String EVTDATEBASED = "EVT_DATE_BASED";
	
	protected static final String EVTREGIONAL = "EVT_REGIONAL";
	
	protected static final String EVTDESCRIPTION = "EVT_DESCRIPTION";
	
	EventKlassenLoader() {
		LinkedHashSet<CSVColumnExpectation<?>> cols = new LinkedHashSet<CSVColumnExpectation<?>>();
		
		cols.add(new CSVColumnExpectation<Long>(EVTID, Long.class, false, 1, Long.MAX_VALUE));
		cols.add(new CSVColumnExpectation<Integer>(EVTWEEKDAY, Integer.class, true, 0, Integer.MAX_VALUE, -1));
		cols.add(new CSVColumnExpectation<String>(EVTNAME, String.class, false, 1, 255));
		cols.add(new CSVColumnExpectation<Integer>(EVTPRIORITY, Integer.class, false, Integer.MIN_VALUE, Integer.MAX_VALUE));
		cols.add(new CSVColumnExpectation<String>(EVTDATEBASED, String.class, false, 2, 4));
		cols.add(new CSVColumnExpectation<String>(EVTREGIONAL, String.class, true, 2, 4));
		cols.add(new CSVColumnExpectation<String>(EVTDESCRIPTION, String.class, true, 1, 255));
		
		setExpectedCsvColumns(cols);
	}
	
	
	
}
