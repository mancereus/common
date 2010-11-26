package de.scoopgmbh.bms.core.importing.impl;

import java.util.List;

import de.scoopgmbh.bms.core.importing.impl.AbstractCSVLoader.CSVRowIterator;

public interface Matcher<T> {
	T getResult();
	void matchWithDatabase(CSVRowIterator iterator);
	List<CSVRow> getFailedRows();
}
