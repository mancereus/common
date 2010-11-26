package de.scoopgmbh.bms.db.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import de.scoopgmbh.bms.db.DB;
import de.scoopgmbh.bms.db.dto.ganglinien.EventKlassenEntry;
import de.scoopgmbh.bms.db.dto.ganglinien.GangLinienEntry;
import de.scoopgmbh.bms.db.exceptions.DBLayerException;
import de.scoopgmbh.bms.db.exceptions.EntityNotFoundException;
import de.scoopgmbh.bms.db.exceptions.EntityNotInsertedException;
import de.scoopgmbh.bms.db.exceptions.EntityNotUpdatedException;
import de.scoopgmbh.bms.db.model.Event;
import de.scoopgmbh.bms.db.model.EventKlasse;
import de.scoopgmbh.bms.db.model.Messquerschnitt;
import de.scoopgmbh.bms.db.model.ReferenzGangLinie;
import de.scoopgmbh.bms.db.model.ReferenzGangLinienWert;

class GangLinienDAO extends HibernateDaoSupport {

	private static final Logger LOGGER = Logger.getLogger(GangLinienDAO.class);
	
	GangLinienDAO(SessionFactory sf) {
		setSessionFactory(sf);
	}
	
	ReferenzGangLinie getGangLinie(long id) {
		return new ReferenzGangLinie();
	}
	
	Messquerschnitt getMessquerschnittByName(String mq) {
		Messquerschnitt ret = null;
		try {
			ret = (Messquerschnitt) getSession()
				.createCriteria(Messquerschnitt.class)
				.add(Restrictions.eq("bezeichnung", mq))
				.add(Restrictions.eq("deleted", DB.NO))
				.uniqueResult();
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new EntityNotFoundException("Entity nicht gefunden. Fehler bei Hibernate.", e);
		}
		if (ret==null) throw new EntityNotFoundException("Entity nicht gefunden. Name: " + mq);
		return ret;
	}
	
	void addMessquerschnitt(Messquerschnitt mq, long editorId) {
		try {
			mq.setEditorId(editorId);
			getSession().save(mq);
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new EntityNotInsertedException("Messquerschnitt konnte nicht eingefügt werden.", e);
		}
	}
	
	ReferenzGangLinie getGangLinieByMqAndEventKlasse(long mqId, long eventKlasseId) {
		ReferenzGangLinie ret = null;
		try {
			ret = 	(ReferenzGangLinie) 
					getSession().createCriteria(ReferenzGangLinie.class)
					.add(Restrictions.eq("idMessquerschnitt", mqId))
					.add(Restrictions.eq("idEventKlasse", eventKlasseId))
					.add(Restrictions.eq("deleted", DB.NO))
					.uniqueResult();
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new EntityNotFoundException("Entity nicht gefunden. Fehler bei Hibernate.", e);
		}
		
		if (ret==null) 
			throw new EntityNotFoundException("Entity nicht gefunden. idMessquerschnitt: " + mqId + "; idEventKlasse: " + eventKlasseId);
		
		return ret;
	}
	
	EventKlasse getEventKlasseByNameAndDay(String bez, int dayOfWeek) {
		EventKlasse ret = null;
		try {
			ret = (EventKlasse) 
					getSession().createCriteria(EventKlasse.class)
					.add(Restrictions.eq("bezeichnung", bez))
					.add(Restrictions.eq("tagInWoche", dayOfWeek))
					.add(Restrictions.eq("deleted", DB.NO))
					.uniqueResult();
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new EntityNotFoundException("Entity nicht gefunden. Fehler bei Hibernate.");
		}
		
		if (ret==null) throw new EntityNotFoundException("Entity nicht gefunden. Bezeichnung: " + bez + "; dayOfWeek: " + dayOfWeek);
		return ret;
	}
	
	void addEventKlasse(EventKlasse ek, long editorId) {
		try {
			ek.setEditorId(editorId);
			getSession().save(ek);
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new EntityNotInsertedException("EventKlasse konnte nicht eingefügt werden. Fehler bei Hibernate.");
		}
	}
	
	void updateEventKlasse(EventKlasse ek, long editorId) {
		try {
			ek.setEditorId(editorId);
			getSession().update(ek);
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new EntityNotInsertedException("EventKlasse konnte nicht aktualisiert werden. Fehler bei Hibernate.");
		}
	}
	
	void addReferenzGangLinie(ReferenzGangLinie rgl, long editorId) {
		try {
			rgl.setEditorId(editorId);
			getSession().save(rgl);
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new EntityNotInsertedException("ReferenzGangLinie konnte nicht eingefügt werden.");
		}
	}
	
	ReferenzGangLinienWert getReferenzGangLinienWertByRG(long referenzGangLinienId) {
		ReferenzGangLinienWert ret = null;
		try {
			ret = (ReferenzGangLinienWert) getSession().createCriteria(ReferenzGangLinienWert.class)
				.add(Restrictions.eq("idReferenzGangLinie", referenzGangLinienId))
				.add(Restrictions.eq("typ", "qKfz"))
				.add(Restrictions.eq("deleted", DB.NO))
				.uniqueResult();
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new EntityNotFoundException("Fehler beim Lesen der ReferenzGangLinineWerte. Fehler bei Hibernate.", e);
		}
		
		if (ret==null) throw new EntityNotFoundException("Fehler beim Lesen der ReferenzGangLinineWerte für ReferenzGangLinie: " + referenzGangLinienId);
		return ret;
	}
	
	@SuppressWarnings("unchecked")
	List<ReferenzGangLinienWert> getReferenzGangLinienWerteByRG(long referenzGangLinienId) {
		List<ReferenzGangLinienWert> ret = null;
		try {
			ret = getSession()
				.createCriteria(ReferenzGangLinienWert.class)
				.add(Restrictions.eq("idReferenzGangLinie", referenzGangLinienId))
				.add(Restrictions.eq("deleted", DB.NO))
				.list();
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new EntityNotFoundException("Fehler beim Lesen der ReferenzGangLinineWerte. Fehler bei Hibernate.", e);
		}
		
		if (ret==null) ret = new LinkedList<ReferenzGangLinienWert>();
		return ret;
	}
	
	void addReferenzGangLinienWert(ReferenzGangLinienWert rglw, long editorId) {
		try {
			rglw.setEditorId(editorId);
			getSession().save(rglw);
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new EntityNotInsertedException("ReferenzGangLinienWert konnte nich eingefügt werden. Fehler bei Hibernate.", e);
		}
	}
	
	void deleteReferenzGangLinienWertByGangLinienId(long gangLinienId, long editorId) {
		try {
			// FIXME CHIRTE DELETE überarbeiten
			getSession()
			.createQuery("DELETE FROM ReferenzGangLinienWert r WHERE r.idReferenzGangLinie = :idRgl")
			.setParameter("idRgl", gangLinienId)
			.executeUpdate();
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new DBLayerException("Fehler beim Löschen von ReferenzGangLinienWerte. Fehler bei Hibernate.", e);
		}
	}
	
	void updateReferenzGangLinienWert(ReferenzGangLinienWert rglw, long editorId) {
		try {
			rglw.setEditorId(editorId);
			getSession().merge(rglw);
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new EntityNotUpdatedException("Fehler beim Update des ReferenzGangLinienWertes. Fehler bei Hibernate.", e);
		}
	}
	
	void updateEvent(Event e, long editorId) {
		try {
			e.setEditorId(editorId);
			getSession().update(e);
		} catch (Exception ex) {
			LOGGER.error(ex, ex);
			throw new EntityNotUpdatedException("Event konnte nicht umgedated werden. Fehler bei Hibernate.", ex);
		}
	}
	
	void insertEvent(Event e, long editorId) {
		try {
			e.setEditorId(editorId);
			getSession().save(e);
		} catch (Exception ex) {
			LOGGER.error(ex, ex);
			throw new EntityNotUpdatedException("Event konnte nicht upgedated werden. Fehler bei Hibernate.", ex);
		}
	}
	
	@SuppressWarnings("unchecked")
	Map<String, Long> getAllMessquerschnitte() {
		Map<String, Long> ret = new HashMap<String, Long>();
		try {
			List<Object[]> ol = getSession()
				.createQuery("SELECT mq.bezeichnung, mq.id FROM Messquerschnitt mq WHERE mq.deleted = :para1")
				.setParameter("para1", DB.NO)
				.list();
			for(Object[] x : ol) {
				ret.put((String)x[0], (Long)x[1]);
			}
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new DBLayerException("Fehler beim Lesen der Messquerschnitte. Fehler bei Hibernate.", e);
		}
		return ret;
	}
	
	@SuppressWarnings("unchecked")
	Map<EventKlassenEntry, Long> getEventKlassen() {
		Map<EventKlassenEntry, Long> ret = new HashMap<EventKlassenEntry, Long>();
		try {
			List<Object[]> ol = getSession()
				.createQuery("SELECT ek.id, ek.bezeichnung, ek.tagInWoche FROM EventKlasse ek WHERE ek.deleted = :para1")
				.setParameter("para1", DB.NO)
				.list();
			for(Object[] x : ol) {
				ret.put(new EventKlassenEntry((String)x[1],(Integer)x[2]), (Long)x[0]);
			}
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new DBLayerException("Fehler beim Lesen der EventKlassen. Fehler bei Hibernate.", e);
		}
		return ret;
	}
	
	@SuppressWarnings("unchecked")
	Map<GangLinienEntry, Long> getGangLinien() {
		Map<GangLinienEntry, Long> ret = new HashMap<GangLinienEntry, Long>();
		try {
			List<Object[]> ol = getSession()
				.createQuery("SELECT rgl.id, rgl.idMessquerschnitt, rgl.idEventKlasse FROM ReferenzGangLinie rgl WHERE rgl.deleted = :para1")
				.setParameter("para1", DB.NO)
				.list();
			for(Object[] x : ol) {
				ret.put(new GangLinienEntry((Long)x[1],(Long)x[2]), (Long)x[0]);
			}
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new DBLayerException("Fehler beim Lesen der GangLinien. Fehler bei Hibernate.", e);
		}
		return ret;
	}
	
	@SuppressWarnings("unchecked")
	Map<Long, Long> getGangLinienWerte() {
		Map<Long, Long> ret = new HashMap<Long, Long>();
		try {
			List<Object[]> ol = getSession()
				.createQuery("SELECT rglw.idReferenzGangLinie, rglw.id FROM ReferenzGangLinienWert rglw WHERE rglw.deleted = :para1")
				.setParameter("para1", DB.NO)
				.list();
			for(Object[] x : ol) {
				ret.put((Long)x[0], (Long)x[1]);
			}
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new DBLayerException("Fehler beim Lesen der GangLinienWerte. Fehler bei Hibernate.", e);
		}
		return ret;
	}
	
	ReferenzGangLinienWert getReferenzGangLinienWert(long id) {
		ReferenzGangLinienWert ret = null;
		try {
			ret = (ReferenzGangLinienWert) getSession()
				.createCriteria(ReferenzGangLinienWert.class)
				.add(Restrictions.eq("id", id))
				.add(Restrictions.eq("deleted", DB.NO))
				.uniqueResult();
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new EntityNotFoundException("ReferenzGangLinienWert nicht gefunden. Fehler bei Hibernate.", e);
		}
		
		if (ret==null) new EntityNotFoundException("ReferenzGangLinienWert nicht gefunden. ID: " + id);
		return ret;
	}
	
	Event getEventByExternalId(long extId) {
		Event ret = null;
		try {
			ret = (Event) getSession().createCriteria(Event.class)
				.add(Restrictions.eq("externalId", extId))
				.add(Restrictions.eq("deleted", DB.NO))
				.uniqueResult();
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new EntityNotFoundException("Event nicht gefunden. Fehler bei Hibernate.", e);
		}
		
		if (ret==null) throw new EntityNotFoundException("Event nicht gefunden. ID: " + extId);
		
		return ret;
	}
	
	EventKlasse getEventKlasseByExternalId(long extId) {
		EventKlasse ek = null;
		try {
			ek = (EventKlasse) 
				getSession().createCriteria(EventKlasse.class)
				.add(Restrictions.eq("externalId", extId))
				.add(Restrictions.eq("deleted", DB.NO))
				.uniqueResult();
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new EntityNotFoundException("EventKlasse nicht gefunden. Fehler bei Hibernate.", e);
		}
		
		if (ek==null) throw new EntityNotFoundException("EventKlasse nicht gefunden. ExternalID: " + extId); 
		return ek;
	}
	
	EventKlasse getEventKlasseById(long id) {
		EventKlasse ret = null;
		
		try {
			ret = (EventKlasse) getSession()
				.createCriteria(EventKlasse.class)
				.add(Restrictions.eq("externalId", id))
				.add(Restrictions.eq("deleted", DB.NO))
				.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
			throw new EntityNotFoundException("EventKlasse nicht gefunden. Fehler bei Hibernate.");
		}
		
		if (ret==null) throw new EntityNotFoundException("EventKlasse nicht gefunden. ID: " +id);
		
		return ret;
	}
	
	@SuppressWarnings("unchecked")
	List<Event> getEventForTimeSpan(Date firstDay, Date lastDay) {
		List<Event> ret = null;
		try {
			ret = getSession().createQuery(
					"SELECT e " +
					"FROM Event e " +
					"WHERE (e.datum BETWEEN :dat1 AND :dat2) " +
					"AND e.deleted = :para1")
					.setParameter("dat1", firstDay)
					.setParameter("dat2", lastDay)
					.setParameter("para1", DB.NO)
					.list();
		} catch (Exception e) {
			throw new DBLayerException("Keine Events gefunden. Fehler bei Hibernate.", e);
		}
		
		if (ret==null) ret = new LinkedList<Event>();
		
		return ret;
	}
	
	List<Integer> getGangLinienWertAsList(long rgLinieId, String typ) {
		List<Integer> ret = new ArrayList<Integer>(96);
		try {
			Object[] vals = (Object[]) getSession().createQuery(
					"SELECT a.val0, a.val1, a.val2, a.val3, a.val4, a.val5, a.val6, a.val7, a.val8, a.val9, " +
					"a.val10, a.val11, a.val12, a.val13, a.val14, a.val15, a.val16, a.val17, a.val18, a.val19, " +
					"a.val20, a.val21, a.val22, a.val23, a.val24, a.val25, a.val26, a.val27, a.val28, a.val29, " +
					"a.val30, a.val31, a.val32, a.val33, a.val34, a.val35, a.val36, a.val37, a.val38, a.val39, " +
					"a.val40, a.val41, a.val42, a.val43, a.val44, a.val45, a.val46, a.val47, a.val48, a.val49, " +
					"a.val50, a.val51, a.val52, a.val53, a.val54, a.val55, a.val56, a.val57, a.val58, a.val59, " +
					"a.val60, a.val61, a.val62, a.val63, a.val64, a.val65, a.val66, a.val67, a.val68, a.val69, " +
					"a.val70, a.val71, a.val72, a.val73, a.val74, a.val75, a.val76, a.val77, a.val78, a.val79, " +
					"a.val80, a.val81, a.val82, a.val83, a.val84, a.val85, a.val86, a.val87, a.val88, a.val89, " +
					"a.val90, a.val91, a.val92, a.val93, a.val94, a.val95 " +
					"FROM ReferenzGangLinienWert a " +
					"WHERE (a.idReferenzGangLinie = :rglId) " +
					"AND (a.typ = :typ) " +
					"AND (a.deleted = :para1)")
				.setParameter("rglId", rgLinieId)
				.setParameter("typ", typ)
				.setParameter("para1", DB.NO)
				.uniqueResult();
			for(Object o : vals) ret.add((Integer)o);
		} catch (Exception e) {
			throw new DBLayerException("ReferenzGangLinie konnte nicht geladen werden. Fehler bei Hibernate", e);
		}
		return ret;
	}
	
	// FIXME CHIRTE
	TreeMap<Date, Map<String, List<Integer>>> getGanlinienForMqAndTimespan(Date fromDate, Date toDate, Collection<String> mqBezeichungen) {
		int cardinality = (int)((toDate.getTime()-fromDate.getTime())/1000/60/60/24)+1;
		StringBuilder queryText = 
			new StringBuilder("SELECT eventKlassen.datum, m.bezeichnung");
		for (int i = 0; i < ReferenzGangLinienWert.numWerte; ++i) {
			queryText.append(", nvl(kfzWerte.val").append(i).append(",0)+")
			         .append("1.5*nvl(lkwWerte.val").append(i).append(",0) val").append(i);
		}
		queryText.append("\nFROM\n")
		.append("refganglinien r, messquerschnitte m, refwerte kfzWerte, refwerte lkwWerte,\n"+
		"(\n"+
		"SELECT standardEvents.datum, NVL(specialEvents.event_klassen_id, standardEvents.event_klassen_id) event_klassen_id\n"+
		"FROM\n"+
		"(\n"+
		"SELECT TRUNC(e.DATUM) datum, MIN(k.id) KEEP (DENSE_RANK FIRST ORDER BY k.prio desc) event_klassen_id\n"+
		"FROM Events e, event_klassen k\n"+
		"WHERE e.datum BETWEEN :fromDate AND :toDate\n"+
		"AND e.event_klassen_id = k.id\n"+
		"GROUP BY TRUNC(e.DATUM)\n"+
		") specialEvents\n"+
		",\n"+
		"(\n"+
		"SELECT /*+ cardinality( d ").append(cardinality).append(" ) */ d.column_value datum, k.id  event_klassen_id\n"+
		"FROM event_klassen k, table(getDates(:fromDate, :toDate)) d\n"+
		"WHERE\n" +
		"k.BEZEICHNUNG = 'Standard' \n" +
		"AND TO_CHAR(d.column_value,'D')-1 = k.TAG_IN_WOCHE\n"+
		") standardEvents\n"+
		"WHERE standardEvents.datum = specialEvents.datum(+)\n"+
		") eventKlassen\n"+
		"WHERE\n"+
		"eventKlassen.event_klassen_id = r.event_klassen_id\n"+
		"AND m.id = r.messsquerschnitte_id\n"+
		"AND kfzWerte.refganglinien_id(+) = r.id\n"+
		"AND kfzWerte.kfztyp(+) = 'qKfz'\n"+
		"AND lkwWerte.refganglinien_id(+) = r.id\n"+
		"AND lkwWerte.kfztyp(+) = 'qLkw'\n"+
		"AND m.bezeichnung in (:mqBezeichnungen)");
		SQLQuery sqlQuery = getSession().createSQLQuery(queryText.toString());
		sqlQuery.setDate("fromDate", fromDate);
		sqlQuery.setDate("toDate", toDate);
		sqlQuery.setParameterList("mqBezeichnungen", mqBezeichungen);
		@SuppressWarnings("unchecked")
		List<Object[]> data = sqlQuery.list();
		TreeMap<Date, Map<String,List<Integer>>> ret = new TreeMap<Date,Map<String,List<Integer>>>();
		for (Object[] line : data) {
			Date date = (Date)line[0];
			String mqBezeichnung = line[1].toString();
			List<Integer> list = new ArrayList<Integer>(ReferenzGangLinienWert.numWerte);
			for (int i = 0; i < ReferenzGangLinienWert.numWerte; ++i) {
				list.add(((Number)line[2+i]).intValue());
			}
			Map<String,List<Integer>> theMap = ret.get(date);
			if (theMap == null) {
				theMap = new HashMap<String, List<Integer>>();
				ret.put(date, theMap);
			}
			theMap.put(mqBezeichnung, list);
		}
		return ret;

	}
	
}
