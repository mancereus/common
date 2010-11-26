package de.scoopgmbh.bms.db.service.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import de.scoopgmbh.bms.db.DB;
import de.scoopgmbh.bms.db.exceptions.DBLayerException;
import de.scoopgmbh.bms.db.exceptions.EntityNotDeletedException;
import de.scoopgmbh.bms.db.exceptions.EntityNotFoundException;
import de.scoopgmbh.bms.db.exceptions.EntityNotInsertedException;
import de.scoopgmbh.bms.db.exceptions.EntityNotUpdatedException;
import de.scoopgmbh.bms.db.model.Abschnitt;
import de.scoopgmbh.bms.db.model.KnotenTyp;
import de.scoopgmbh.bms.db.model.NetzPunkt;
import de.scoopgmbh.bms.db.model.Strasse;
import de.scoopgmbh.bms.db.model.Zuordnung;

class StreckenNetzDAO extends HibernateDaoSupport {

	private static final Logger LOGGER = Logger.getLogger(RechenRegelDAO.class);
	
	StreckenNetzDAO(SessionFactory sf) {
		setSessionFactory(sf);
	}
	
	@SuppressWarnings("unchecked")
	List<Strasse> getAllBabs() {
		List<Strasse> ret = null;
		try {
			ret = getSession()
				.createCriteria(Strasse.class)
				.add(Restrictions.eq("deleted", DB.NO))
				.list();
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new DBLayerException("Autobahnen konnte nicht gefunden werden. Fehler bei Hibernate.");
		}
		if (ret==null) ret = new LinkedList<Strasse>();
		return ret;
	}
	
	@SuppressWarnings("unchecked")
	List<NetzPunkt> getAllPunkteForBab(long babId) {
		List<NetzPunkt> ret = null;
		try {
			ret = getSession().createQuery(
					"SELECT DISTINCT kp " +
					"FROM KnotenPunkt kp, Abschnitt aa " +
					"WHERE aa.babId = :babid " +
					"AND (kp.idKnotenPunkt1 = kp.id " +
					"OR kp.idKnotenPunkt2 = kp.id)" +
					"AND kp.deleted = :notDelted")
					.setParameter("babid", babId)
					.setParameter("notDelted", DB.NO)
					.list();
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new DBLayerException("Fehler beim Lesen der Knotenpunkte. Fehler bei Hibernate.");
		}
		if (ret==null) ret = new LinkedList<NetzPunkt>();
		return ret;
	}
	
	List<NetzPunkt> getAllNetzPunkteForBabAndAM(long babId, long autobahnMeistereiId) {
		return new LinkedList<NetzPunkt>();
	}
	
	void addBab(Strasse bab, long loginId) {
		try {
			bab.setEditorId(loginId);
			getSession().save(bab);
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new EntityNotInsertedException("Autobahn konnte nicht hinzugefügt werden. Fehler bei Hibernate.");
		}
	}
	
	void updateBab(Strasse bab, long loginId) {
		try {
			bab.setEditorId(loginId);
			getSession().update(bab);
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new EntityNotUpdatedException("Autobahn konnte nicht aktualisiert werden. Fehler bei Hibernate.");
		}
	}
	
	void deleteBab(Strasse bab, long loginId) {
		try {
			bab.setDeleted(DB.YES);
			bab.setEditorId(loginId);
			getSession().update(bab);
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new EntityNotDeletedException("Autobahn konnte nicht gelöscht werden. Fehler bei Hibernate.",e);
		}
	}
	
	Strasse getBabById(long babId) {
		Strasse ret = null;
		try {
			ret = (Strasse) getSession()
					.createCriteria(Strasse.class)
					.add(Restrictions.eq("id", babId))
					.add(Restrictions.eq("deleted", DB.NO))
					.uniqueResult();
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new EntityNotFoundException("Autobahn konnte nicht gefunden werden. Fehler bei Hibernate.");
		}
		if (ret==null) throw new EntityNotFoundException("Autobahn wurde nicht gefunden. ID: " + babId); 
		return ret;
	}
	
	Strasse getBabByName(char typ, int babNummer) {
		Strasse ret = null;
		try {
			ret = (Strasse) getSession()
				.createCriteria(Strasse.class)
				.add(Restrictions.eq("nummer", babNummer))
				.add(Restrictions.eq("typ", typ))
				.add(Restrictions.eq("deleted", DB.NO))
				.uniqueResult();
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new EntityNotFoundException("Autobahn konnte nicht gefunden werden. Fehler bei Hibernate.");
		}
		if (ret==null) throw new EntityNotFoundException("Autobahn nicht gefunden zur Nummer: " + babNummer);
		return ret;
	}
	
	void addBabPunkt(NetzPunkt np, long loginId) {
		try {
			np.setEditorId(loginId);
			getSession().save(np);
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new EntityNotInsertedException("Knotenpunkt konnte nicht hinzugefügt werden. Fehler bei Hibernate.");
		}
	}
	
	void udpateBabPunkt(NetzPunkt np, long loginId) {
		try {
			np.setEditorId(loginId);
			getSession().update(np);
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new EntityNotUpdatedException("Knotenpunkt konnte nicht aktualisiert werden. Fehler bei Hibernate.");
		}
	}
	
	void deleteBabPunkt(NetzPunkt np, long loginId) {
		try {
			np.setDeleted(DB.YES);
			np.setEditorId(loginId);
			getSession().update(np);
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new EntityNotDeletedException("Knotenpunkt konnte nicht gelöscht werden. Fehler bei Hibernate.", e);
		}
	}
	
	NetzPunkt getBabPunkt(long netzPunktId) {
		NetzPunkt kp = null;
		try {
			kp = (NetzPunkt) getSession()
				.createCriteria(NetzPunkt.class)
				.add(Restrictions.eq("id", netzPunktId))
				.add(Restrictions.eq("deleted", DB.NO))
				.uniqueResult();
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new EntityNotFoundException("Knotenpunkt konnte nicht gefunden werden. Fehler bei Hibernate.");
		}
		if (kp==null) throw new EntityNotFoundException("Knotenpunkt nicht gefunden. ID: " + netzPunktId);
		return kp;
	}
	
	void addBabAbs(Abschnitt aa, long babId, long loginId) {
		try {
			aa.setEditorId(loginId);
			getSession().save(aa);
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new EntityNotInsertedException("Autobahnabschnitt konnte nicht hinzugefügt werden. Fehler bei Hibernate.");
		}
	}
	
	void updateBabAbs(Abschnitt aa, long loginId) {
		try {
			aa.setEditorId(loginId);
			getSession().update(aa);
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new EntityNotUpdatedException("Autobahnabschnitt konnte nicht aktualisiert werden. Fehler bei  Hibernate.");
		}
	}
	
	void deleteBabAbs(Abschnitt aa, long loginId) {
		try {
			aa.setDeleted(DB.YES);
			aa.setEditorId(loginId);
			getSession().update(aa);
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new EntityNotDeletedException("Autobahnabschnitt konnte nicht gelöscht werden. Fehler bei Hibernate.", e);
		}
	}
	
	Abschnitt getBabAbs(long absId) {
		Abschnitt ret = null;
		try {
			ret = (Abschnitt) getSession()
				.createCriteria(Abschnitt.class)
				.add(Restrictions.eq("id", absId))
				.add(Restrictions.eq("deleted", DB.NO))
				.uniqueResult();
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new EntityNotFoundException("Autobahnabschnitt konnte nicht gefunden werden. Fehler bei Hibernate.");
		}
		if (ret==null) throw new EntityNotFoundException("Autobahnabschnitt nicht gefunden. ID: " + absId);
		return ret;
	}
	
	@SuppressWarnings("unchecked")
	List<Abschnitt> getAllAbschnittByStrasse(char typ, int nummer, String richtung) {
		List<Abschnitt> ret = null;
		try {
			ret = getSession().createQuery(
					"SELECT DISTINCT a " +
					"FROM Abschnitt a, Strasse s " +
					"WHERE (s.typ = :typ) " +
					"AND (s.nummer = :nummer) " +
					"AND (a.strassenId = s.id) " +
					"AND (a.richtung = :richtung) " +
					"AND (a.deleted = :notDeleted)")
					.setParameter("typ", typ)
					.setParameter("nummer", nummer)
					.setParameter("richtung", richtung)
					.setParameter("notDeleted", DB.NO)
					.list();
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new DBLayerException("Abschnitte konnten nicht gelesen werden. Fehler bei Hibernate.");
		}
		
		if (ret==null) ret = new LinkedList<Abschnitt>();
		
		return ret;
	}

	@SuppressWarnings("unchecked")
	List<Abschnitt> getAllAbschnitt() {
		List<Abschnitt> ret = null;
		try {
			ret = getSession()
				.createCriteria(Abschnitt.class)
				.add(Restrictions.eq("deleted", DB.NO))
				.list();
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new DBLayerException("Abschnitte konnten nicht gelesen werden. Fehler bei Hibernate.",e);
		}
		
		if (ret==null) ret = new LinkedList<Abschnitt>();
		
		return ret;
	}

	@SuppressWarnings("unchecked")
	List<NetzPunkt> getAllNetzPunkteByStrasse(char typ, int nummer) {
		List<NetzPunkt> ret = null;
		try {
			ret = getSession().createQuery(
					"SELECT DISTINCT np " +
					"FROM NetzPunkt np, Abschnitt a, Strasse s " +
					"WHERE (s.typ = :typ) " +
					"AND (s.nummer = :nummer) " +
					"AND (a.strassenId = s.id) " +
					"AND (np.deleted = :notDeleted) " +
					"AND ((a.idKnotenPunkt1 = np.id)" +
					"OR (a.idKnotenPunkt2 = np.id))")
					.setParameter("typ", typ)
					.setParameter("nummer", nummer)
					.setParameter("notDeleted", DB.NO)
					.list();
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new DBLayerException("NetzPunkte konnten nicht gelesen werden. Fehler bei Hibernate.");
		}
		
		if (ret==null) ret = new LinkedList<NetzPunkt>();
		
		return ret;
	}
	
	@SuppressWarnings("unchecked")
	List<NetzPunkt> getAllNetzPunkte() {
		List<NetzPunkt> ret = null;
		try {
			ret = getSession()
				.createCriteria(NetzPunkt.class)
				.add(Restrictions.eq("deleted", DB.NO))
				.list();
			
			if (ret==null) 
				ret = new LinkedList<NetzPunkt>();
			
			for(NetzPunkt np : ret) {
				np.setKnotenTyp(getKnotenTypById(np.getIdKnotenTyp()));
			}
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new DBLayerException("NetzPunkte konnten nicht gelesen werden. Fehler bei Hibernate.",e);
		}
		
		return ret;
	}
	
	NetzPunkt getNetzPunktByName(String npName) {
		NetzPunkt ret = null;
		try {
			ret = (NetzPunkt) getSession()
				.createCriteria(NetzPunkt.class)
				.add(Restrictions.eq("bezeichnung", npName))
				.add(Restrictions.eq("deleted", DB.NO))
				.uniqueResult();
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new EntityNotFoundException("NetzPunkt nicht gefunden. Fehler bei Hibernate");
		}
		
		if (ret == null) throw new EntityNotFoundException("NetzPunkt nicht gefunden. Bezeichnung: " + npName);
		
		return ret;
	}
	
	@SuppressWarnings("unchecked")
	List<KnotenTyp> getAllKnotenTypen() {
		List<KnotenTyp> ret = null;
		try {
			ret = getSession()
				.createCriteria(KnotenTyp.class)
				.list();
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new DBLayerException("KnotenTypen konnten nicht gelesen werden. Fehler bei Hibernate.");
		}
		
		if (ret==null) ret = new LinkedList<KnotenTyp>();
		
		return ret;
	}
	
	KnotenTyp getKnotenTypById(long id) {
		KnotenTyp ret = null;
		try {
			ret = (KnotenTyp) getSession()
				.createCriteria(KnotenTyp.class)
				.add(Restrictions.eq("id", id))
				.uniqueResult();
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new EntityNotFoundException("KnotenTyp konnten nicht gelesen werden. Fehler bei Hibernate.");
		}
		
		if (ret==null) throw new EntityNotFoundException("KnotenTyp nicht gefunden. ID: " + id);
		return ret;
	}
	
	Abschnitt getAbschnittByNetzPunktNames(String nameNP1, String nameNP2, char roadTyp, int roadNr) {
		Abschnitt ret = null;
		try {
			ret = (Abschnitt) getSession().createQuery(
					"SELECT DISTINCT a " +
					"FROM Abschnitt a, Strasse s " +
					"WHERE (a.idKnotenPunkt1 IN (SELECT np.id FROM NetzPunkt np WHERE ((np.bezeichnung = :param1) OR (np.bezeichnung2 = :param1) OR (np.bezeichnung3 = :param1))) " + 
					"AND (a.idKnotenPunkt2 IN (SELECT np.id FROM NetzPunkt np WHERE ((np.bezeichnung = :param2) OR (np.bezeichnung2 = :param2) OR (np.bezeichnung3 = :param2)))))" +
					"AND (s.id = a.strassenId) " +
					"AND (a.deleted = :notDeleted) " +
					"AND (s.nummer = :roadNr) " +
					"AND (s.typ = :roadTyp)")
					.setParameter("param1", nameNP1)
					.setParameter("param2", nameNP2)
					.setParameter("roadNr", roadNr)
					.setParameter("roadTyp", roadTyp)
					.setParameter("notDeleted", DB.NO)
					.uniqueResult();
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new DBLayerException("Fehler beim Laden des Abschnitts. Fehler bei Hibernate.");
		}
		
		return ret;
	}
	
	@SuppressWarnings("unchecked")
	List<Abschnitt> getAllAbschnittByStreckeBlock(char typ, int nummer, int strecke, int block, String richtung) {
		List<Abschnitt> ret = null;
		try {
			ret = getSession().createQuery(
					"SELECT DISTINCT a " +
					"FROM Abschnitt a, Strasse s " +
					"WHERE (a.strassenId = s.id) " +
					"AND (s.nummer = :nr) " +
					"AND (s.typ = :typ) " +
					"AND (s.deleted = :notDeleted) " +
					"AND (a.blockNr = :blnr) " +
					"AND (a.streckenNr = :stnr) " +
					"AND (a.richtung = :richtung)")
					.setParameter("nr", nummer)
					.setParameter("typ", typ)
					.setParameter("blnr", block)
					.setParameter("stnr", strecke)
					.setParameter("richtung", richtung)
					.setParameter("notDeleted", DB.NO)
					.list();
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new DBLayerException("Abschnitte konnten nicht gelesen werden. Fehler bei Hibernate.", e);
		}
		
		if (ret==null) return new ArrayList<Abschnitt>(0);
		
		return ret;
	}
	
	NetzPunkt getNetzPunktById(long id) {
		NetzPunkt np = null;
		try {
			np = (NetzPunkt) getSession()
				.createCriteria(NetzPunkt.class)
				.add(Restrictions.eq("id", id))
				.add(Restrictions.eq("deleted", DB.NO))
				.uniqueResult();
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new EntityNotFoundException("NetzPunkt nicht gefunden. Fehler bei Hibernate.", e);
		}
		if (np==null) throw new EntityNotFoundException("NetzPunkt nicht gefunden. ID " + id);
		return np;
	}
	
	void deleteZuordnung(Zuordnung z, long loginId) {
		try {
			z.setDeleted(DB.YES);
			z.setEditorId(loginId);
			getSession().update(z);
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new EntityNotDeletedException("Zuordnung konnte nicht gelöscht werden.", e);
		}
	}
	
	void addZuordnung(Zuordnung z, long loginId) {
		try {
			z.setEditorId(loginId);
			getSession().save(z);
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new EntityNotInsertedException("Zuordnung konnte nicht eingefügt werden.", e);
		}
	}
	
	void updateZuordnung(Zuordnung z, long loginId) {
		try {
			z.setEditorId(loginId);
			getSession().update(z);
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new EntityNotUpdatedException("Zuordnung konnte nicht aktualisiert werden.", e);
		}
	}
	
	@SuppressWarnings("unchecked")
	List<Zuordnung> getZuordnungen(long autobahneMeistereiId) {
		List<Zuordnung> ret = null;
		try {
			ret = getSession()
				.createCriteria(Zuordnung.class)
				.add(Restrictions.eq("autoritaetenId", autobahneMeistereiId))
				.add(Restrictions.eq("deleted", DB.NO))
				.list();
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new DBLayerException("Zuordnung konnte nicht geladen werden. Fehler bei Hibernate.", e);
		}
		return ret;
	}
}
