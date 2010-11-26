package de.scoopgmbh.bms.core.importing;

import java.net.URL;

import de.scoopgmbh.bms.core.exceptions.ImportException;

public interface StreckenNetzImportService {

	public void importStreckenNetzCSV(URL csv, long loginId) throws ImportException;
}
