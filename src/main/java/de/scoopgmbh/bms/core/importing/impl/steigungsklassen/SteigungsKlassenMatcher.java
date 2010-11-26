package de.scoopgmbh.bms.core.importing.impl.steigungsklassen;

import java.util.List;

import de.scoopgmbh.bms.core.importing.impl.AbstractCSVLoader.CSVRowIterator;
import de.scoopgmbh.bms.core.importing.impl.CSVRow;
import de.scoopgmbh.bms.core.importing.impl.Matcher;
import de.scoopgmbh.bms.db.dto.steigungsklassen.SteigungsKlasseImportDTO;

public class SteigungsKlassenMatcher implements Matcher<SteigungsKlasseImportDTO> {

	public SteigungsKlassenMatcher() {
		
	}
	
	/* @Override */
	public SteigungsKlasseImportDTO getResult() {
		// TODO Auto-generated method stub
		return null;
	}

	/* @Override */
	public void matchWithDatabase(CSVRowIterator iter) {
		// TODO Auto-generated method stub
		
	}

	public List<CSVRow> getFailedRows() {
		// TODO Auto-generated method stub
		return null;
	}

}
