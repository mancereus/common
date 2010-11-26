package de.scoopgmbh.bms.core.importing.impl.ganglinien;

import java.net.URL;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import de.scoopgmbh.bms.core.exceptions.ImportException;
import de.scoopgmbh.bms.core.importing.GangLinienImportService;
import de.scoopgmbh.bms.db.dto.ganglinien.EventDTO;
import de.scoopgmbh.bms.db.dto.ganglinien.EventKlassenDTO;
import de.scoopgmbh.bms.db.dto.ganglinien.GAPImportDAO;
import de.scoopgmbh.bms.db.dto.ganglinien.MessquerschnittDTO;
import de.scoopgmbh.bms.db.exceptions.DBLayerException;
import de.scoopgmbh.bms.db.service.GangLinienService;
import de.scoopgmbh.bms.db.service.IdGeneratorService;

public class GangLinienImportServiceImpl extends HibernateDaoSupport implements GangLinienImportService {

	private IdGeneratorService idgen = null;
	private GangLinienService gls = null;
	private static final int FLUSH_INTERVAL=1000;
	
	public GangLinienImportServiceImpl(SessionFactory sf, IdGeneratorService idgen, GangLinienService gls) {
		this.idgen = idgen;
		this.gls = gls;
		setSessionFactory(sf);
	}
	
	/* @Override */
	@Transactional(isolation=Isolation.READ_COMMITTED, readOnly=false)
	public void importGangLinieCSV(URL csv, long loginId)
			throws ImportException {
		// 1. CSV Datei laden
		GangLinienLoader rgll = new GangLinienLoader();
		rgll.loadCSV(csv);
		
		// 2. Datenmodell aufbauen
		GangLinienImportMatcher glim = new GangLinienImportMatcher(rgll, gls, idgen);
		glim.matchWithDatabase();
		
		// 3. Abgleich auf Datenbank �bertragen
		updateRefGangLinie(glim.getResult(), loginId);
		
	}
	
	/* @Override */
	@Transactional(isolation=Isolation.READ_COMMITTED, readOnly=false)
	public void importEventKalenderCSV(URL csv, long loginId)
			throws ImportException {
		// 1. Eventkalender laden
		EventKalenderLoader ekl = new EventKalenderLoader();
		ekl.loadCSV(csv);
		
		// 2. Datenmodell aufbauen
		EventKalenderImportMatcher ekim = new EventKalenderImportMatcher(ekl, gls, idgen);
		ekim.matchWithDataBase();
		
		// 3. Abgleich auf Datenbank �bertragen
		this.updateEventKalender(ekim.getResult(), loginId);
	}

	/* @Override */
	@Transactional(isolation=Isolation.READ_COMMITTED, readOnly=false)
	public void importEventKlassenCSV(URL csv, long loginId) throws ImportException {
		
		// 1. Events laden
		EventKlassenLoader ekl = new EventKlassenLoader();
		ekl.loadCSV(csv);
		
		// 2. Datenmodell aufbauen
		EventKlassenImportMatcher ekim = new EventKlassenImportMatcher(ekl, gls, idgen);
		ekim.matchWithDatabase();
		
		// 3. Abgleich auf Datenbank �bertragen
		this.updateEventKlassen(ekim.getResult(), loginId);
		
	}
	
	private void updateRefGangLinie(GAPImportDAO gid, long loginId) throws ImportException {
		int i = 0;
		for(EventKlassenDTO ekdto : gid.getEkList()) {
			try {
				this.gls.updateEventKlasse(ekdto, loginId);
				if (++i % FLUSH_INTERVAL == 0) {
					getSession().flush();
					getSession().clear();
				}
			} catch (DBLayerException e) {
				e.printStackTrace();
				// FIXME Fehlerbearbeitung �berarbeiten - nicht eingef�gte Datens�tze serialisieren und auf Platte schreiben
			}
		}
		
		getSession().flush();
		getSession().clear();

		for(MessquerschnittDTO mqDto : gid.getMqList()) {
			try {
				this.gls.updateMessQuerschnittDeep(mqDto, loginId);
				if (++i % FLUSH_INTERVAL == 0) {
					getSession().flush();
					getSession().clear();
				}
			} catch (Exception e) {
				e.printStackTrace();
				// FIXME Fehlerbearbeitung �berarbeiten - nicht eingef�gte Datens�tze serialisieren und auf Platte schreiben 
			}
		}

		getSession().flush();
		getSession().clear();

	}
	
	private void updateEventKalender(List<EventDTO> edtoList, long loginId) throws ImportException {
		int i = 0;
		try {
			for (EventDTO edto : edtoList) {
				this.gls.updateEvent(edto, loginId);
				if (++i % FLUSH_INTERVAL == 0) {
					getSession().flush();
					getSession().clear();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ImportException("Fehler beim Importieren des Eventkalender.");
		}
		getSession().flush();
		getSession().clear();
	}
	
	private void updateEventKlassen(List<EventKlassenDTO> ekdtoList, long loginId) throws ImportException {
		int i = 0;
		try {
			for (EventKlassenDTO edto : ekdtoList) {
				this.gls.updateEventKlasse(edto, loginId);
				if (++i % FLUSH_INTERVAL == 0) {
					getSession().flush();
					getSession().clear();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ImportException("Fehler beim Importieren der EventKlassen.");
		}
		getSession().flush();
		getSession().clear();
	}
	
	
}
