package de.scoopgmbh.bms.db.service.impl;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.annotation.Transactional;
import com.google.inject.Inject;

import de.scoopgmbh.bms.db.exceptions.EntityNotFoundException;
import de.scoopgmbh.bms.db.model.DB;
import de.scoopgmbh.bms.db.model.Event;
import de.scoopgmbh.bms.db.model.EventKlasse;
import de.scoopgmbh.bms.db.model.Messquerschnitt;
import de.scoopgmbh.bms.db.model.ReferenzGangLinie;
import de.scoopgmbh.bms.db.model.ReferenzGangLinienWert;
import de.scoopgmbh.bms.db.service.GangLinienService;

@Transactional(isolation = Isolation.SERIALIZABLE, readOnly = true, noRollbackFor = EntityNotFoundException.class)
public class GangLinienServiceImpl extends EbeanDao<ReferenzGangLinie>
		implements GangLinienService {

	@Inject
	public GangLinienServiceImpl(EbeanServer ebean) {
		super(ebean);
	}

	/* @Override */
	public Map<String, Long> getAllMessquerschnitte() {
		return this.impl.getAllMessquerschnitte();
	}

	/* @Override */
	public Map<EventKlassenEntry, Long> getEventKlassen() {
		return this.impl.getEventKlassen();
	}

	/* @Override */
	public Map<GangLinienEntry, Long> getGangLinien() {
		return this.impl.getGangLinien();
	}

	/* @Override */
	public Map<Long, Long> getGangLinienWerte() {
		return this.impl.getGangLinienWerte();
	}

	/* @Override */
	public ReferenzGangLinienWert getReferenzGangLinienWert(long id) {
		return this.impl.getReferenzGangLinienWert(id);
	}

	/* @Override */
	@Transactional(isolation = Isolation.READ_COMMITTED, readOnly = false)
	public void updateEvent(EventDTO edto, long editorId) {
		if (edto.isForInsert()) {
			this.impl.insertEvent(edto.getEntity(), editorId);
		} else if (edto.isForUpdate()) {
			this.impl.updateEvent(edto.getEntity(), editorId);
		}
	}

	/* @Override */
	@Transactional(isolation = Isolation.READ_COMMITTED, readOnly = false)
	public void updateEventKlasse(EventKlassenDTO ekDto, long editorId) {
		if (ekDto.isForInsert()) {
			this.impl.addEventKlasse(ekDto.getEntity(), editorId);
		} else if (ekDto.isForUpdate()) {
			this.impl.updateEventKlasse(ekDto.getEntity(), editorId);
		}
	}

	/* @Override */
	@Transactional(isolation = Isolation.READ_COMMITTED, readOnly = false)
	public void updateMessQuerschnittDeep(MessquerschnittDTO mqDto,
			long editorId) {
		Messquerschnitt mq = null;

		// Messquerschnitt einfügen
		if (mqDto.isForInsert()) {
			mq = new Messquerschnitt();
			mq.setBezeichnung(mqDto.getMqName());
			mq.setDeleted(DB.NO);
			mq.setId(mqDto.getEntityId());
			this.impl.addMessquerschnitt(mq, editorId);
		}

		for (GangLinienDTO gldto : mqDto.getGangLinien().values()) {

			// OPT - Ganglinie einfügen
			if (gldto.isForInsert()) {
				ReferenzGangLinie rgl = new ReferenzGangLinie();
				rgl.setDeleted(DB.NO);
				rgl.setEditorId(editorId);
				rgl.setIdEventKlasse(gldto.getEventKlasse().getId());
				rgl.setIdMessquerschnitt(mqDto.getEntityId());
				rgl.setId(gldto.getReferenzGangLinienId());
				this.impl.addReferenzGangLinie(rgl, editorId);
			}

			// ReferenzGangLinienWerte einfügen
			List<RefWertDTO> rglwList = gldto.getWerte();
			for (RefWertDTO rglw : rglwList) {
				if (rglw.isForInsert()) {
					this.impl.addReferenzGangLinienWert(rglw.getEntity(),
							editorId);
				} else {
					this.impl.updateReferenzGangLinienWert(rglw.getEntity(),
							editorId);
				}
			}
		}
	}

	/* @Override */
	public Event getEventByExternalId(long extId) {
		return this.impl.getEventByExternalId(extId);
	}

	/* @Override */
	public EventKlasse getEventKlasseByExternalId(long extId) {
		return this.impl.getEventKlasseByExternalId(extId);
	}

	public EventKlasse getEventKlasseByNameAndDay(String bezeichnung,
			int tagInWoche) {
		return this.impl.getEventKlasseByNameAndDay(bezeichnung, tagInWoche);
	}

	/* @Override */
	public EventKlasse getEventKlasseById(long id) {
		return this.impl.getEventKlasseById(id);
	}

	/* @Override */
	public List<Event> getEventByDate(Date day) {
		Calendar cal = GregorianCalendar.getInstance(Locale.GERMANY);
		cal.setTime(day);
		cal.set(Calendar.MILLISECOND, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		Date d1 = cal.getTime();
		cal.add(Calendar.HOUR_OF_DAY, 23);
		cal.add(Calendar.MINUTE, 59);
		cal.add(Calendar.SECOND, 59);
		cal.add(Calendar.MILLISECOND, 999);
		Date d2 = cal.getTime();
		return this.impl.getEventForTimeSpan(d1, d2);
	}

	// /* @Override */
	// public List<Integer> getGangLinienWertAsList(long id) {
	// return this.impl.getGangLinienWertAsList(id);
	// }

	/* @Override */
	public List<Integer> getStandardGangLinieAsList(String mqName, String typ) {
		Calendar cal = GregorianCalendar.getInstance(Locale.GERMANY);
		cal.get(Calendar.DAY_OF_WEEK);
		EventKlasse ek = this.impl.getEventKlasseByNameAndDay(
				EventKlasse.STANDARD, cal.get(Calendar.DAY_OF_WEEK) - 1);
		return this.getGangLinieAsList(mqName, ek.getId(), typ);
	}

	/* @Override */
	public List<Integer> getGangLinieAsList(String mqName, long eventKlassenId,
			String typ) {
		Messquerschnitt mq = this.impl.getMessquerschnittByName(mqName);
		ReferenzGangLinie rgl = this.impl.getGangLinieByMqAndEventKlasse(
				mq.getId(), eventKlassenId);
		return this.impl.getGangLinienWertAsList(rgl.getId(), typ);
	}

	/* @Override */
	public TreeMap<Date, Map<String, List<Integer>>> getGanlinienForMqAndTimespan(
			Date fromDate, Date toDate, Collection<String> mqBezeichungen) {
		return this.impl.getGanlinienForMqAndTimespan(fromDate, toDate,
				mqBezeichungen);
	}

}
