package de.scoopgmbh.bms.db.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import de.scoopgmbh.bms.db.model.Event;
import de.scoopgmbh.bms.db.model.EventKlasse;
import de.scoopgmbh.bms.db.model.Messquerschnitt;
import de.scoopgmbh.bms.db.model.ReferenzGangLinie;
import de.scoopgmbh.bms.db.model.ReferenzGangLinienWert;

public interface GangLinienService {

	public void updateEvent(Event edto, long editorId);

	public void updateEventKlasse(EventKlasse ekDto, long editorId);

	public void updateMessQuerschnittDeep(Messquerschnitt mqDto, long editorId);

	public Map<String, Long> getAllMessquerschnitte();

	public Map<EventKlasse, Long> getEventKlassen();

	public Map<ReferenzGangLinie, Long> getGangLinien();

	public Map<Long, Long> getGangLinienWerte();

	public ReferenzGangLinienWert getReferenzGangLinienWert(long id);

	// public List<Integer> getGangLinienWertAsList(long id);
	public List<Integer> getStandardGangLinieAsList(String mqName, String typ);

	public List<Integer> getGangLinieAsList(String mqName, long eventKlassenId,
			String typ);

	public EventKlasse getEventKlasseByExternalId(long extId);

	public EventKlasse getEventKlasseByNameAndDay(String bezeichnung,
			int tagInWoche);

	public EventKlasse getEventKlasseById(long id);

	public Event getEventByExternalId(long extId);

	public List<Event> getEventByDate(Date day);

	public TreeMap<Date, Map<String, List<Integer>>> getGanlinienForMqAndTimespan(
			Date fromDate, Date toDate, Collection<String> mqBezeichungen);

}
