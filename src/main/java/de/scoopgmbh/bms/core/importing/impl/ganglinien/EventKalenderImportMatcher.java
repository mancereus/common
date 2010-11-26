package de.scoopgmbh.bms.core.importing.impl.ganglinien;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import de.scoopgmbh.bms.core.exceptions.ImportException;
import de.scoopgmbh.bms.core.importing.impl.AbstractCSVLoader.CSVRowIterator;
import de.scoopgmbh.bms.core.importing.impl.CSVRow;
import de.scoopgmbh.bms.core.importing.impl.CSVRow.ValueHolder;
import de.scoopgmbh.bms.db.dto.ganglinien.EventDTO;
import de.scoopgmbh.bms.db.exceptions.EntityNotFoundException;
import de.scoopgmbh.bms.db.model.Event;
import de.scoopgmbh.bms.db.model.EventKlasse;
import de.scoopgmbh.bms.db.service.GangLinienService;
import de.scoopgmbh.bms.db.service.IdGeneratorService;

class EventKalenderImportMatcher {
	
	EventKalenderLoader loader;
	GangLinienService gls;
	IdGeneratorService idgen;
	List<EventDTO> edtoList = new LinkedList<EventDTO>();
	
	EventKalenderImportMatcher(EventKalenderLoader loader, GangLinienService gls, IdGeneratorService idgen) {
		this.loader = loader;
		this.gls = gls;
		this.idgen = idgen;
	}
	
	void matchWithDataBase() throws ImportException {
		CSVRowIterator iter = loader.getCSVRowIterator();
		EventDTO edto = null;
		while(iter.hasNext()) {
			CSVRow r = iter.next();
			
			// 1. DTO erstellen
			edto = buildEventDTO(r);
			edtoList.add(edto);
			
			iter.remove();
		}
	}
	
	List<EventDTO> getResult() {
		return this.edtoList;
	}
	
	private EventDTO buildEventDTO(CSVRow r) throws ImportException {
		
		EventDTO ret = new EventDTO();
		Event ev = null;
		EventKlasse ek = null;
		ValueHolder vh = null;
		
		vh = r.get(EventKalenderLoader.EVID);
		long extId = (Long)vh.getExpectation().getConvertedValue(vh.getValue());
		
		// 1. Entity - laden/erstellen
		try {
			ev = gls.getEventByExternalId(extId);
			ret.setForUpdate();
		} catch (EntityNotFoundException e) {
			ev = new Event();
			ev.setId(idgen.getNextId());
			ret.setForInsert();
		}
		
		// 2. Entity - Datum setzen
		vh = r.get(EventKalenderLoader.EVDATE);
		ev.setDatum((Date)vh.getExpectation().getConvertedValue(vh.getValue()));
		
		// 3. Entity - Name setzen
		vh = r.get(EventKalenderLoader.EVNAME);
		ev.setName((String)vh.getExpectation().getConvertedValue(vh.getValue()));
		
		// 4. Entity - External Foreign Key setzen
		vh = r.get(EventKalenderLoader.EVFKID);
		long extFkId = (Long)vh.getExpectation().getConvertedValue(vh.getValue());
		ev.setExternalFkId(extFkId);
		
		ev.setExternalId(extId);
		
		// 5. EventTyp holen
		try {
			ek = gls.getEventKlasseByExternalId(extFkId);
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
			throw new ImportException("EventKlasse konnte nicht zur externen ID gefunden werden. extID: " + extFkId + ". " +
					"Das Importieren des Event schlug fehl.");
		}
		
		// 6. Foreign-Key Beziehung setzen
		ev.setIdEventKlasse(ek.getId());
		
		// 7. DTO befüllen
		ret.setEntity(ev);
		ret.setId(ev.getId());
		
		return ret;
	}
	
}
