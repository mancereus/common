package de.scoopgmbh.bms.core.importing.impl.ganglinien;

import java.util.LinkedList;
import java.util.List;

import de.scoopgmbh.bms.core.importing.impl.AbstractCSVLoader.CSVRowIterator;
import de.scoopgmbh.bms.core.importing.impl.CSVRow;
import de.scoopgmbh.bms.core.importing.impl.CSVRow.ValueHolder;
import de.scoopgmbh.bms.db.DB;
import de.scoopgmbh.bms.db.dto.ganglinien.EventKlassenDTO;
import de.scoopgmbh.bms.db.exceptions.EntityNotFoundException;
import de.scoopgmbh.bms.db.model.EventKlasse;
import de.scoopgmbh.bms.db.service.GangLinienService;
import de.scoopgmbh.bms.db.service.IdGeneratorService;

class EventKlassenImportMatcher {

	private List<EventKlassenDTO> ekDtoList = new LinkedList<EventKlassenDTO>();
	private EventKlassenLoader loader;
	private GangLinienService gls;
	private IdGeneratorService idgen;
	
	EventKlassenImportMatcher(EventKlassenLoader loader, GangLinienService gls, IdGeneratorService idgen) {
		this.loader = loader;
		this.gls = gls;
		this.idgen = idgen;
	}
	
	void matchWithDatabase() {
		CSVRowIterator cri = loader.getCSVRowIterator();
		EventKlassenDTO ekdto = null;
		while (cri.hasNext()) {
			CSVRow r = cri.next();
			
			ekdto = buildEventKlasse(r);
			ekDtoList.add(ekdto);
			
			cri.remove();
		}
	}
	
	List<EventKlassenDTO> getResult() {
		return this.ekDtoList;
	}
	
	private EventKlassenDTO buildEventKlasse(CSVRow r) {
		EventKlassenDTO ret = new EventKlassenDTO();
		EventKlasse ek = null;
		
		ValueHolder vh = r.get(EventKlassenLoader.EVTID);
		long extId = (Long)vh.getExpectation().getConvertedValue(vh.getValue());
		
		vh = r.get(EventKlassenLoader.EVTDESCRIPTION);
		String beschreibung = (String)vh.getExpectation().getConvertedValue(vh.getValue());
		
		vh = r.get(EventKlassenLoader.EVTNAME);
		String bezeichnung = (String)vh.getExpectation().getConvertedValue(vh.getValue());
		
		vh = r.get(EventKlassenLoader.EVTDATEBASED);
		char datumsBezogen = vh.getValue().equals("NEIN") ? DB.NO : DB.YES;
		
		vh = r.get(EventKlassenLoader.EVTPRIORITY);
		int prio = (Integer)vh.getExpectation().getConvertedValue(vh.getValue());
		
		vh = r.get(EventKlassenLoader.EVTWEEKDAY);
		int tagInWoche = (Integer)vh.getExpectation().getConvertedValue(vh.getValue());
		
		try {
			ek = gls.getEventKlasseByExternalId(extId);
			ret.setForUpdate();
		} catch (EntityNotFoundException e1) {
			ek = null;
		}
		
		if (ek==null) {
			try {
				ek = gls.getEventKlasseByNameAndDay(bezeichnung, tagInWoche);
				ret.setForUpdate();
			} catch (EntityNotFoundException e2) {
				ek = new EventKlasse();
				ek.setId(idgen.getNextId());
				ret.setForInsert();
			}
		}
		
		ek.setBezeichnung(bezeichnung);
		ek.setBeschreibung(beschreibung);
		ek.setIstKlasseDatumsbezogen(datumsBezogen);
		ek.setPrio(prio);
		ek.setTagInWoche(tagInWoche);
		ek.setExternalId(extId);
		ek.setDeleted(DB.NO);
		ek.setEditorId(0);
		
		ret.setEntity(ek);
		ret.setId(ek.getId());
		
		return ret;
	}
	
}
