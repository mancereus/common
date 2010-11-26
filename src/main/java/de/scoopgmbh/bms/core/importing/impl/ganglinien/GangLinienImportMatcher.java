package de.scoopgmbh.bms.core.importing.impl.ganglinien;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.scoopgmbh.bms.core.importing.impl.AbstractCSVLoader.CSVRowIterator;
import de.scoopgmbh.bms.core.importing.impl.CSVRow;
import de.scoopgmbh.bms.core.importing.impl.CSVRow.ValueHolder;
import de.scoopgmbh.bms.db.dto.ganglinien.EventKlassenDTO;
import de.scoopgmbh.bms.db.dto.ganglinien.EventKlassenEntry;
import de.scoopgmbh.bms.db.dto.ganglinien.GAPImportDAO;
import de.scoopgmbh.bms.db.dto.ganglinien.GangLinienDTO;
import de.scoopgmbh.bms.db.dto.ganglinien.GangLinienEntry;
import de.scoopgmbh.bms.db.dto.ganglinien.MessquerschnittDTO;
import de.scoopgmbh.bms.db.dto.ganglinien.RefWertDTO;
import de.scoopgmbh.bms.db.model.EventKlasse;
import de.scoopgmbh.bms.db.model.ReferenzGangLinienWert;
import de.scoopgmbh.bms.db.service.GangLinienService;
import de.scoopgmbh.bms.db.service.IdGeneratorService;

class GangLinienImportMatcher {

	private IdGeneratorService idgen = null;
	private GangLinienService gls = null;
	private GangLinienLoader loader = null;
	private List<MessquerschnittDTO> mqList = null;
	private List<EventKlassenDTO> ekList = null;
	private HashMap<GangLinienDTO, GangLinienDTO> glMap = null;
	
	Map<String, Long> mqMap = null;
	Map<EventKlassenEntry, Long> eventMap = null;
	Map<GangLinienEntry, Long> rgMap = null;
	Map<Long, Long> rgWerteMap = null; //rgId, rgwId
	
	GangLinienImportMatcher(GangLinienLoader loader, GangLinienService gls, IdGeneratorService idgen) {
		this.loader = loader;
		this.gls = gls;
		this.idgen = idgen;
	}
	
	public GAPImportDAO getResult() {
		GAPImportDAO gid = new GAPImportDAO(this.mqList, this.ekList);
		return gid;
	}
	
	public void matchWithDatabase() {
		// DB Werte organisieren
		mqMap = gls.getAllMessquerschnitte();
		eventMap = gls.getEventKlassen();
		rgMap = gls.getGangLinien();
		rgWerteMap = gls.getGangLinienWerte(); //rgId, rgwId
		
		MessquerschnittDTO mqdto = null;
		GangLinienDTO gldto = null;
		EventKlassenDTO ekdto = null;
		RefWertDTO rglw = null;
		
		// 
		mqList = new LinkedList<MessquerschnittDTO>();
		ekList = new LinkedList<EventKlassenDTO>();
		
		CSVRowIterator iter = loader.getCSVRowIterator();
		glMap = new HashMap<GangLinienDTO, GangLinienDTO>( (iter.getItemsCountRemaining()/2) +1, 1f);
		
		while(iter.hasNext()) {
			
			CSVRow r = iter.next();
			
			// 1. Messquerschnitt
			mqdto = buildMq(r.get(GangLinienLoader.MQNAME));
			
			// 2. EventKlasse
			ekdto = buildEK(r);
			
			// 3. ReferenzGangLinie
			gldto = buildGL(ekdto, mqdto);
			
			// 4. Erstellen der Werte
			rglw = buildRGLW(r, gldto);
			gldto.addOrOverride(rglw);
			
			// 5. Ganglinie zu Messquerschnitt hinzuf�gen
			mqdto.getGangLinien().put(gldto, gldto);
			
			// 6. gelesenen CSV Datensatz entfernen
			iter.remove();
		}
	}
	
	private MessquerschnittDTO buildMq(ValueHolder colVal) {
		MessquerschnittDTO mq = new MessquerschnittDTO((String) colVal.getExpectation().getConvertedValue(colVal.getValue()));
		
		int idx = mqList.indexOf(mq);
		if (idx!=-1)
			mq = mqList.get(idx);
		else {
			mqList.add(mq);
			if (mqMap.containsKey(mq.getMqName())) {
				mq.setEntityId(mqMap.get(mq.getMqName()));
				mq.setForInsert(false);
			} else {
				// Der Messquerschnitt existiert noch nicht und muss eingef�gt werden
				mq.setForInsert(true);
				mq.setEntityId(idgen.getNextId());
			}
		}
		
		return mq;
	}
	
	private EventKlassenDTO buildEK(CSVRow c) {
		EventKlassenDTO ret = new EventKlassenDTO();
		EventKlasse ek = null;
		
		ValueHolder vh = c.get(GangLinienLoader.EREIGNIS);
		ret.setBezeichnung((String)vh.getExpectation().getConvertedValue(vh.getValue()));
		
		vh = c.get(GangLinienLoader.WOCHENTAG);
		ret.setDayOfWeek((Integer)vh.getExpectation().getConvertedValue(vh.getValue()));
		
		int idx = ekList.indexOf(ret);
		if (idx!=-1)
			ret = ekList.get(idx);
		else {
			EventKlassenEntry eke = new EventKlassenEntry(ret.getBezeichnung(), ret.getDayOfWeek());
			if (eventMap.containsKey(eke)) {
				ek = this.gls.getEventKlasseByNameAndDay(ret.getBezeichnung(), ret.getDayOfWeek());
				ret.setForUpdate();
			} else {
				// Die EventKlasse existiert also noch nicht und muss eingef�gt werden
				ek = new EventKlasse();
				ek.setBezeichnung(ret.getBezeichnung());
				ek.setTagInWoche(ret.getDayOfWeek());
				ek.setId(idgen.getNextId());
				ret.setForInsert();
			}
			ret.setEntity(ek);
			ret.setId(ek.getId());
			ekList.add(ret);
		}
		return ret;
	}
	
	private GangLinienDTO buildGL(EventKlassenDTO ek, MessquerschnittDTO mq) {
		
		GangLinienDTO gldto = new GangLinienDTO(ek);
		gldto.setEventKlassenId(ek.getId());
		gldto.setMqId(mq.getEntityId());
		
		if (glMap.containsKey(gldto)) {
			gldto = glMap.get(gldto);
			return gldto;
		} else {
			glMap.put(gldto, gldto);
		}
		
		GangLinienEntry gle = new GangLinienEntry(mq.getEntityId(), ek.getId());
		
		if (rgMap.containsKey(gle)) {
			gldto.setReferenzGangLinienId(rgMap.get(gle));
			gldto.setForInsert(false);
		} else {
			gldto.setForInsert(true);
			gldto.setReferenzGangLinienId(idgen.getNextId());
		}
		
		return gldto;
	}
	
	private RefWertDTO buildRGLW(CSVRow c, GangLinienDTO gldto) {
		
		ReferenzGangLinienWert rglw = null;
		RefWertDTO ret = null;
		
		if (rgWerteMap.containsKey(gldto.getReferenzGangLinienId())) {
			rglw = this.gls.getReferenzGangLinienWert(rgWerteMap.get(gldto.getReferenzGangLinienId()));
			ret = new RefWertDTO(rglw);
			ret.setForInsert(false);
		} else {
			rglw = new ReferenzGangLinienWert();
			rglw.setId(idgen.getNextId());
			rglw.setIdReferenzGangLinie(gldto.getReferenzGangLinienId());
			ret = new RefWertDTO(rglw);
			ret.setForInsert(true);
		}
		rglw.setTyp(c.get(GangLinienLoader.DATENART).getValue());
		
		rglw.setVal0((Integer)c.get(GangLinienLoader.$r[0]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[0]).getValue()));
		rglw.setVal1((Integer)c.get(GangLinienLoader.$r[1]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[1]).getValue()));
		rglw.setVal2((Integer)c.get(GangLinienLoader.$r[2]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[2]).getValue()));
		rglw.setVal3((Integer)c.get(GangLinienLoader.$r[3]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[3]).getValue()));
		rglw.setVal4((Integer)c.get(GangLinienLoader.$r[4]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[4]).getValue()));
		rglw.setVal5((Integer)c.get(GangLinienLoader.$r[5]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[5]).getValue()));
		rglw.setVal6((Integer)c.get(GangLinienLoader.$r[6]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[6]).getValue()));
		rglw.setVal7((Integer)c.get(GangLinienLoader.$r[7]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[7]).getValue()));
		rglw.setVal8((Integer)c.get(GangLinienLoader.$r[8]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[8]).getValue()));
		rglw.setVal9((Integer)c.get(GangLinienLoader.$r[9]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[9]).getValue()));
		
		rglw.setVal10((Integer)c.get(GangLinienLoader.$r[10]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[10]).getValue()));
		rglw.setVal11((Integer)c.get(GangLinienLoader.$r[11]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[11]).getValue()));
		rglw.setVal12((Integer)c.get(GangLinienLoader.$r[12]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[12]).getValue()));
		rglw.setVal13((Integer)c.get(GangLinienLoader.$r[13]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[13]).getValue()));
		rglw.setVal14((Integer)c.get(GangLinienLoader.$r[14]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[14]).getValue()));
		rglw.setVal15((Integer)c.get(GangLinienLoader.$r[15]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[15]).getValue()));
		rglw.setVal16((Integer)c.get(GangLinienLoader.$r[16]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[16]).getValue()));
		rglw.setVal17((Integer)c.get(GangLinienLoader.$r[17]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[17]).getValue()));
		rglw.setVal18((Integer)c.get(GangLinienLoader.$r[18]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[18]).getValue()));
		rglw.setVal19((Integer)c.get(GangLinienLoader.$r[19]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[19]).getValue()));
		
		rglw.setVal20((Integer)c.get(GangLinienLoader.$r[20]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[20]).getValue()));
		rglw.setVal21((Integer)c.get(GangLinienLoader.$r[21]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[21]).getValue()));
		rglw.setVal22((Integer)c.get(GangLinienLoader.$r[22]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[22]).getValue()));
		rglw.setVal23((Integer)c.get(GangLinienLoader.$r[23]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[23]).getValue()));
		rglw.setVal24((Integer)c.get(GangLinienLoader.$r[24]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[24]).getValue()));
		rglw.setVal25((Integer)c.get(GangLinienLoader.$r[25]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[25]).getValue()));
		rglw.setVal26((Integer)c.get(GangLinienLoader.$r[26]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[26]).getValue()));
		rglw.setVal27((Integer)c.get(GangLinienLoader.$r[27]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[27]).getValue()));
		rglw.setVal28((Integer)c.get(GangLinienLoader.$r[28]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[28]).getValue()));
		rglw.setVal29((Integer)c.get(GangLinienLoader.$r[29]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[29]).getValue()));
		
		rglw.setVal30((Integer)c.get(GangLinienLoader.$r[30]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[30]).getValue()));
		rglw.setVal31((Integer)c.get(GangLinienLoader.$r[31]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[31]).getValue()));
		rglw.setVal32((Integer)c.get(GangLinienLoader.$r[32]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[32]).getValue()));
		rglw.setVal33((Integer)c.get(GangLinienLoader.$r[33]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[33]).getValue()));
		rglw.setVal34((Integer)c.get(GangLinienLoader.$r[34]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[34]).getValue()));
		rglw.setVal35((Integer)c.get(GangLinienLoader.$r[35]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[35]).getValue()));
		rglw.setVal36((Integer)c.get(GangLinienLoader.$r[36]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[36]).getValue()));
		rglw.setVal37((Integer)c.get(GangLinienLoader.$r[37]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[37]).getValue()));
		rglw.setVal38((Integer)c.get(GangLinienLoader.$r[38]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[38]).getValue()));
		rglw.setVal39((Integer)c.get(GangLinienLoader.$r[39]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[39]).getValue()));
		
		rglw.setVal40((Integer)c.get(GangLinienLoader.$r[40]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[40]).getValue()));
		rglw.setVal41((Integer)c.get(GangLinienLoader.$r[41]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[41]).getValue()));
		rglw.setVal42((Integer)c.get(GangLinienLoader.$r[42]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[42]).getValue()));
		rglw.setVal43((Integer)c.get(GangLinienLoader.$r[43]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[43]).getValue()));
		rglw.setVal44((Integer)c.get(GangLinienLoader.$r[44]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[44]).getValue()));
		rglw.setVal45((Integer)c.get(GangLinienLoader.$r[45]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[45]).getValue()));
		rglw.setVal46((Integer)c.get(GangLinienLoader.$r[46]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[46]).getValue()));
		rglw.setVal47((Integer)c.get(GangLinienLoader.$r[47]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[47]).getValue()));
		rglw.setVal48((Integer)c.get(GangLinienLoader.$r[48]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[48]).getValue()));
		rglw.setVal49((Integer)c.get(GangLinienLoader.$r[49]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[49]).getValue()));
		
		rglw.setVal50((Integer)c.get(GangLinienLoader.$r[50]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[50]).getValue()));
		rglw.setVal51((Integer)c.get(GangLinienLoader.$r[51]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[51]).getValue()));
		rglw.setVal52((Integer)c.get(GangLinienLoader.$r[52]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[52]).getValue()));
		rglw.setVal53((Integer)c.get(GangLinienLoader.$r[53]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[53]).getValue()));
		rglw.setVal54((Integer)c.get(GangLinienLoader.$r[54]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[54]).getValue()));
		rglw.setVal55((Integer)c.get(GangLinienLoader.$r[55]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[55]).getValue()));
		rglw.setVal56((Integer)c.get(GangLinienLoader.$r[56]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[56]).getValue()));
		rglw.setVal57((Integer)c.get(GangLinienLoader.$r[57]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[57]).getValue()));
		rglw.setVal58((Integer)c.get(GangLinienLoader.$r[58]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[58]).getValue()));
		rglw.setVal59((Integer)c.get(GangLinienLoader.$r[59]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[59]).getValue()));
		
		rglw.setVal60((Integer)c.get(GangLinienLoader.$r[60]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[60]).getValue()));
		rglw.setVal61((Integer)c.get(GangLinienLoader.$r[61]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[61]).getValue()));
		rglw.setVal62((Integer)c.get(GangLinienLoader.$r[62]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[62]).getValue()));
		rglw.setVal63((Integer)c.get(GangLinienLoader.$r[63]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[63]).getValue()));
		rglw.setVal64((Integer)c.get(GangLinienLoader.$r[64]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[64]).getValue()));
		rglw.setVal65((Integer)c.get(GangLinienLoader.$r[65]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[65]).getValue()));
		rglw.setVal66((Integer)c.get(GangLinienLoader.$r[66]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[66]).getValue()));
		rglw.setVal67((Integer)c.get(GangLinienLoader.$r[67]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[67]).getValue()));
		rglw.setVal68((Integer)c.get(GangLinienLoader.$r[68]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[68]).getValue()));
		rglw.setVal69((Integer)c.get(GangLinienLoader.$r[69]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[69]).getValue()));
		
		rglw.setVal70((Integer)c.get(GangLinienLoader.$r[70]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[70]).getValue()));
		rglw.setVal71((Integer)c.get(GangLinienLoader.$r[71]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[71]).getValue()));
		rglw.setVal72((Integer)c.get(GangLinienLoader.$r[72]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[72]).getValue()));
		rglw.setVal73((Integer)c.get(GangLinienLoader.$r[73]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[73]).getValue()));
		rglw.setVal74((Integer)c.get(GangLinienLoader.$r[74]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[74]).getValue()));
		rglw.setVal75((Integer)c.get(GangLinienLoader.$r[75]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[75]).getValue()));
		rglw.setVal76((Integer)c.get(GangLinienLoader.$r[76]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[76]).getValue()));
		rglw.setVal77((Integer)c.get(GangLinienLoader.$r[77]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[77]).getValue()));
		rglw.setVal78((Integer)c.get(GangLinienLoader.$r[78]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[78]).getValue()));
		rglw.setVal79((Integer)c.get(GangLinienLoader.$r[79]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[79]).getValue()));
		
		rglw.setVal80((Integer)c.get(GangLinienLoader.$r[80]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[80]).getValue()));
		rglw.setVal81((Integer)c.get(GangLinienLoader.$r[81]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[81]).getValue()));
		rglw.setVal82((Integer)c.get(GangLinienLoader.$r[82]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[82]).getValue()));
		rglw.setVal83((Integer)c.get(GangLinienLoader.$r[83]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[83]).getValue()));
		rglw.setVal84((Integer)c.get(GangLinienLoader.$r[84]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[84]).getValue()));
		rglw.setVal85((Integer)c.get(GangLinienLoader.$r[85]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[85]).getValue()));
		rglw.setVal86((Integer)c.get(GangLinienLoader.$r[86]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[86]).getValue()));
		rglw.setVal87((Integer)c.get(GangLinienLoader.$r[87]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[87]).getValue()));
		rglw.setVal88((Integer)c.get(GangLinienLoader.$r[88]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[88]).getValue()));
		rglw.setVal89((Integer)c.get(GangLinienLoader.$r[89]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[89]).getValue()));
		
		rglw.setVal90((Integer)c.get(GangLinienLoader.$r[90]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[90]).getValue()));
		rglw.setVal91((Integer)c.get(GangLinienLoader.$r[91]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[91]).getValue()));
		rglw.setVal92((Integer)c.get(GangLinienLoader.$r[92]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[92]).getValue()));
		rglw.setVal93((Integer)c.get(GangLinienLoader.$r[93]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[93]).getValue()));
		rglw.setVal94((Integer)c.get(GangLinienLoader.$r[94]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[94]).getValue()));
		rglw.setVal95((Integer)c.get(GangLinienLoader.$r[95]).getExpectation().getConvertedValue(c.get(GangLinienLoader.$r[95]).getValue()));
		
		return ret;
	}
}
