package de.scoopgmbh.bms.core.importing.impl.steigungsklassen;

import java.net.URL;
import java.rmi.server.ExportException;

import de.scoopgmbh.bms.core.exceptions.ImportException;
import de.scoopgmbh.bms.core.importing.ImportCapable;

public class SteigungsKlassenImportServiceImpl implements ImportCapable {

	/* @Override */
	public void importCsv(URL csv, long loginId) throws ImportException {
		SteigungsKlassenLoader skl = new SteigungsKlassenLoader();
		skl.loadCSV(csv);
		
	}

	/* @Override */
	public StringBuilder getErrosMessages() {
		// TODO Auto-generated method stub
		return null;
	}

	/* @Override */
	public boolean hasImportErros() {
		// TODO Auto-generated method stub
		return false;
	}

	/* @Override */
	public boolean getRunCount() {
		// TODO Auto-generated method stub
		return false;
	}

	/* @Override */
	public void saveInvalidCSVRecords(URL csv) throws ExportException {
		// TODO Auto-generated method stub

	}

}
