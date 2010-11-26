package de.scoopgmbh.bms.core.importing.impl.rechenregel;

import java.net.URL;

import de.scoopgmbh.bms.core.exceptions.ImportException;
import de.scoopgmbh.bms.core.importing.RechenRegelImportService;
import de.scoopgmbh.bms.db.dto.rechenregel.RechenRegelImportDTO;
import de.scoopgmbh.bms.db.service.IdGeneratorService;
import de.scoopgmbh.bms.db.service.RechenRegelService;
import de.scoopgmbh.bms.db.service.StreckenNetzService;

public class RechenRegelImportServiceImpl implements RechenRegelImportService {

	StreckenNetzService sns;
	RechenRegelService rrs;
	IdGeneratorService idgen;
	
	public RechenRegelImportServiceImpl(StreckenNetzService sns, RechenRegelService rrs, IdGeneratorService idgen) {
		this.sns = sns;
		this.rrs = rrs;
		this.idgen = idgen;
	}
	
	/* @Override */
	public void importRechenRegelCSV(URL csv, long loginId)
			throws ImportException {
		
		// 1. CSV laden
		RechenRegelLoader rrl = new RechenRegelLoader();
		rrl.loadCSV(csv);
		
		// 2. CSV-Daten mit Datenbank abgleichen
		RechenRegelImportMatcher rrim = new RechenRegelImportMatcher(idgen, sns, rrs);
		rrim.matchWithDatabase(rrl.getCSVRowIterator());
		
		// 3. Update Rechenregel
		this.updateRechenRegel(rrim.getResult(), loginId);

	}
	
	private void updateRechenRegel(RechenRegelImportDTO rrid, long loginId) {
		try {
			this.rrs.updateRechenRegeln(rrid, loginId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
