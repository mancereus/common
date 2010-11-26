package de.scoopgmbh.bms.core.importing;

import java.net.URL;

import de.scoopgmbh.bms.core.exceptions.ImportException;

public interface GangLinienImportService {

	public void importGangLinieCSV(URL csv, long loginId) throws ImportException;
	public void importEventKalenderCSV(URL csv, long loginId) throws ImportException;
	public void importEventKlassenCSV(URL csv, long loginId) throws ImportException;
}
