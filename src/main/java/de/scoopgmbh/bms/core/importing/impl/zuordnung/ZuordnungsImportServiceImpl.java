package de.scoopgmbh.bms.core.importing.impl.zuordnung;

import java.net.URL;
import java.rmi.server.ExportException;

import de.scoopgmbh.bms.core.exceptions.ImportException;
import de.scoopgmbh.bms.core.importing.ImportCapable;
import de.scoopgmbh.bms.db.service.AutoritaetsService;
import de.scoopgmbh.bms.db.service.IdGeneratorService;
import de.scoopgmbh.bms.db.service.StreckenNetzService;

public class ZuordnungsImportServiceImpl implements ImportCapable {

	private IdGeneratorService idgen;
	private AutoritaetsService as;
	private StreckenNetzService sns;
	
	public ZuordnungsImportServiceImpl(IdGeneratorService idgen, 
			AutoritaetsService as, StreckenNetzService sns) {
		this.idgen = idgen;
		this.as = as;
		this.sns = sns;
	}
	
	public void importCsv(URL csv, long loginId) throws ImportException {
		
		ZuordnungsLoader zl = new ZuordnungsLoader();
		zl.loadCSV(csv);
		
		ZuordnungsMatcher zm = new ZuordnungsMatcher(idgen, as, sns);
		zm.matchWithDatabase(zl.getCSVRowIterator());
		
		this.sns.updateZurodnungen(zm.getResult(), loginId);
		
	}

	public StringBuilder getErrosMessages() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean hasImportErros() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean getRunCount() {
		// TODO Auto-generated method stub
		return false;
	}

	public void saveInvalidCSVRecords(URL csv) throws ExportException {
		// TODO Auto-generated method stub

	}

}
