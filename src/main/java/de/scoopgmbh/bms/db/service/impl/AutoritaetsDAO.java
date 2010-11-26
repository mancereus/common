package de.scoopgmbh.bms.db.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import de.scoopgmbh.bms.db.exceptions.DBLayerException;
import de.scoopgmbh.bms.db.exceptions.EntityNotDeletedException;
import de.scoopgmbh.bms.db.exceptions.EntityNotFoundException;
import de.scoopgmbh.bms.db.exceptions.EntityNotInsertedException;
import de.scoopgmbh.bms.db.exceptions.EntityNotUpdatedException;
import de.scoopgmbh.bms.db.model.Angestellte;
import de.scoopgmbh.bms.db.model.AutobahnMeisterei;
import de.scoopgmbh.bms.db.model.AutobahnPolizeiStation;
import de.scoopgmbh.bms.db.model.Autoritaet;
import de.scoopgmbh.bms.db.model.BaustoffBodenPruefAmt;
import de.scoopgmbh.bms.db.model.DB;
import de.scoopgmbh.bms.db.model.FremdFirma;
import de.scoopgmbh.bms.db.model.StrassenVerkehrsAmt;

class AutoritaetsDAO extends HibernateDaoSupport {

	private static final Logger LOGGER = Logger.getLogger(AutoritaetsDAO.class);

	public AutoritaetsDAO(SessionFactory sf) {
		setSessionFactory(sf);
	}

	// ==============================================================
	// GENERISCH METHODEN FUER ALLE KLASSEN GLEICH - Reihenfolge: CRUD
	// ==============================================================

	void addAutoritaet(Autoritaet at, long loginId) {
		try {
			at.setEditorId(loginId);
			getSession().persist(at);
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new EntityNotInsertedException(
					"AutobahnMeisterei konnte nicht eingefügt werden.", e);
		}
	}

	Autoritaet getAutoritaet(long autoritaetId) {
		try {
			return (Autoritaet) getSession().createCriteria(Autoritaet.class)
					.add(Restrictions.eq("id", autoritaetId))
					.add(Restrictions.eq("deleted", DB.NO)).uniqueResult();
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new EntityNotFoundException(
					"Autoritaet konnte nicht gefunden werden.", e);
		}
	}

	void updateAutoritaet(Autoritaet at, long loginId) {
		try {
			at.setEditorId(loginId);
			getSession().update(at);
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new EntityNotUpdatedException(
					"Autoritaet konnte nicht aktualisiert werden.", e);
		}
	}

	void deleteAutoritaet(Autoritaet at, long loginId) {
		try {
			at.setDeleted(DB.YES);
			at.setEditorId(loginId);
			getSession().update(at);
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new EntityNotDeletedException(
					"Autoritaet konnte nicht gelöscht werden.", e);
		}
	}

	// ==============================================================
	// FREMDFRIMEN bezogen - Reihenfolge: CRUD
	// ==============================================================

	void addFremdFirma(FremdFirma ff, long loginId) {
		try {
			getSession().persist(ff);
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new EntityNotInsertedException(
					"FremdFirma konnte nicht eingefügt werden.", e);
		}
	}

	FremdFirma getFremdFirma(long fremdFirmaId) {
		try {
			return (FremdFirma) getSession().createCriteria(FremdFirma.class)
					.add(Restrictions.eq("id", fremdFirmaId))
					.add(Restrictions.eq("deleted", DB.NO)).uniqueResult();
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new EntityNotFoundException(
					"FremdFirma konnte nicht gefunden werden.", e);
		}
	}

	@SuppressWarnings("unchecked")
	List<FremdFirma> getAllFremdfirmen() {
		List<FremdFirma> ret = null;
		try {
			ret = (List<FremdFirma>) getSession()
					.createCriteria(FremdFirma.class)
					.add(Restrictions.eq("deleted", DB.NO)).list();
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new DBLayerException(
					"Fehler beim Lesen der Fremdfirmen. Fehler bei Hibernate.",
					e);
		}

		if (ret == null) {
			ret = new ArrayList<FremdFirma>();
		}

		return ret;
	}

	List<FremdFirma> getAllFremdFirmenByAutobahnMeisterei(
			long autobahnMeistereiId) {

		List<FremdFirma> ret = new ArrayList<FremdFirma>();

		try {
			AutobahnMeisterei am = (AutobahnMeisterei) getSession()
					.createCriteria(AutobahnMeisterei.class)
					.add(Restrictions.eq("id", autobahnMeistereiId))
					.add(Restrictions.eq("deleted", DB.NO)).uniqueResult();
			for (FremdFirma ff : am.getFremdFirmen()) {
				if (ff.getDeleted() == DB.NO)
					ret.add(ff);
			}
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new DBLayerException(
					"Fremdfirmen konnten nicht gefunden werden. Fehler bei Hibernate.",
					e);
		}
		return ret;
	}

	void updateFremdFirma(FremdFirma ff, long loginId) {
		try {
			ff.setEditorId(loginId);
			getSession().update(ff);
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new EntityNotUpdatedException(
					"FremdFirma konnte nicht aktualisiert werden.", e);
		}
	}

	void addFremdFirmaToAutobahnMeisterei(long autobahnMeistereiId,
			long fremdFirmaId, long loginId) {
		try {
			AutobahnMeisterei am = (AutobahnMeisterei) getSession().get(
					AutobahnMeisterei.class, autobahnMeistereiId);
			FremdFirma ff = (FremdFirma) getSession().get(FremdFirma.class,
					fremdFirmaId);

			if (am == null || ff == null) {
				throw new EntityNotInsertedException(
						"FremdFirma konnte nicht zur Autobahnmeisterei hinzugefügt werden.");
			}

			ff.getAutobahnMeistereien().add(am);
			getSession().merge(ff);
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new EntityNotInsertedException(
					"FremdFirma konnte nicht zur Autobahnmeisterei hinzugefügt werden.",
					e);
		}
	}

	void deleteFremdFirma(FremdFirma ff, long loginId) {
		try {
			ff.setDeleted(DB.YES);
			ff.setEditorId(loginId);
			getSession().update(ff);
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new EntityNotDeletedException(
					"AutobahnMeisterei konnte nicht gelöscht werden.", e);
		}
	}

	// ==============================================================
	// BAUSTOFFBODENPRUEFAMT - Reihenfolge: CRUD
	// ==============================================================

	void addBaustoffBodenPruefAmt(BaustoffBodenPruefAmt at, long loginId) {
		try {
			at.setEditorId(loginId);
			getSession().persist(at);
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new EntityNotInsertedException(
					"BaustoffBodenPruefAmt konnte nicht eingefügt werden.", e);
		}
	}

	BaustoffBodenPruefAmt getBaustoffBodenPruefAmt(long autoritaetId) {
		try {
			return (BaustoffBodenPruefAmt) getSession()
					.createCriteria(BaustoffBodenPruefAmt.class)
					.add(Restrictions.eq("id", autoritaetId))
					.add(Restrictions.eq("deleted", DB.NO)).uniqueResult();
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new EntityNotFoundException(
					"BaustoffBodenPruefAmt konnte nicht gefunden werden.", e);
		}
	}

	void updateBaustoffBodenPruefAmt(BaustoffBodenPruefAmt at, long loginId) {
		try {
			at.setEditorId(loginId);
			getSession().update(at);
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new EntityNotUpdatedException(
					"BaustoffBodenPruefAmt konnte nicht aktualisiert werden.",
					e);
		}
	}

	void deleteBaustoffBodenPruefAmt(BaustoffBodenPruefAmt at, long loginId) {
		try {
			at.setDeleted(DB.YES);
			at.setEditorId(loginId);
			getSession().update(at);
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new EntityNotDeletedException(
					"BaustoffBodenPruefAmt konnte nicht gelöscht werden.", e);
		}
	}

	@SuppressWarnings("unchecked")
	List<BaustoffBodenPruefAmt> getAllBaustoffBodenPruefAemter() {
		List<BaustoffBodenPruefAmt> ret = null;
		try {
			ret = getSession().createCriteria(BaustoffBodenPruefAmt.class)
					.add(Restrictions.eq("deleted", DB.NO)).list();
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new DBLayerException(
					"Fehler beim Lesen der BaustoffBodenPruefAemter. Fehler bei Hibernate.",
					e);
		}

		if (ret == null) {
			ret = new ArrayList<BaustoffBodenPruefAmt>();
		}

		return ret;
	}

	// ==============================================================
	// AUTOBAHNPOLIZEISTATION - Reihenfolge: CRUD
	// ==============================================================

	void addAutobahnPolizeiStation(AutobahnPolizeiStation at, long loginId) {
		try {
			at.setEditorId(loginId);
			getSession().persist(at);
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new EntityNotInsertedException(
					"AutobahnPolizeiStation konnte nicht eingefügt werden.", e);
		}
	}

	AutobahnPolizeiStation getAutobahnPolizeiStation(long autoritaetId) {
		try {
			return (AutobahnPolizeiStation) getSession()
					.createCriteria(AutobahnPolizeiStation.class)
					.add(Restrictions.eq("id", autoritaetId))
					.add(Restrictions.eq("deleted", DB.NO)).uniqueResult();
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new EntityNotFoundException(
					"BaustoffBodenPruefAmt konnte nicht gefunden werden.", e);
		}
	}

	void updateAutobahnPolizeiStation(AutobahnPolizeiStation at, long loginId) {
		try {
			at.setEditorId(loginId);
			getSession().update(at);
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new EntityNotUpdatedException(
					"AutobahnPolizeiStation konnte nicht aktualisiert werden.",
					e);
		}
	}

	void deleteAutobahnPolizeiStation(AutobahnPolizeiStation at, long loginId) {
		try {
			at.setDeleted(DB.YES);
			at.setEditorId(loginId);
			getSession().update(at);
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new EntityNotDeletedException(
					"AutobahnPolizeiStation konnte nicht gelöscht werden.", e);
		}
	}

	@SuppressWarnings("unchecked")
	List<AutobahnPolizeiStation> getAllAutobahnPolizeiStationen() {
		List<AutobahnPolizeiStation> ret = null;
		try {
			ret = getSession().createCriteria(AutobahnPolizeiStation.class)
					.add(Restrictions.eq("deleted", DB.NO)).list();
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new DBLayerException(
					"Fehler beim Lesen der AutobahnPolizeiStation. Fehler bei Hibernate.",
					e);
		}

		if (ret == null) {
			ret = new ArrayList<AutobahnPolizeiStation>();
		}

		return ret;
	}

	// ==============================================================
	// STRASSENVERKEHRSAMT - Reihenfolge: CRUD
	// ==============================================================

	void addStrassenVerkehrsAmt(StrassenVerkehrsAmt at, long loginId) {
		try {
			at.setEditorId(loginId);
			getSession().persist(at);
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new EntityNotInsertedException(
					"StrassenVerkehrsAmt konnte nicht eingefügt werden.", e);
		}
	}

	StrassenVerkehrsAmt getStrassenVerkehrsAmt(long autoritaetId) {
		try {
			return (StrassenVerkehrsAmt) getSession()
					.createCriteria(StrassenVerkehrsAmt.class)
					.add(Restrictions.eq("id", autoritaetId))
					.add(Restrictions.eq("deleted", DB.NO)).uniqueResult();
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new EntityNotFoundException(
					"StrassenVerkehrsAmt konnte nicht gefunden werden.", e);
		}
	}

	void updateStrassenVerkehrsAmt(StrassenVerkehrsAmt at, long loginId) {
		try {
			at.setEditorId(loginId);
			getSession().update(at);
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new EntityNotUpdatedException(
					"StrassenVerkehrsAmt konnte nicht aktualisiert werden.", e);
		}
	}

	void deleteStrassenVerkehrsAmt(StrassenVerkehrsAmt at, long loginId) {
		try {
			at.setDeleted(DB.YES);
			at.setEditorId(loginId);
			getSession().update(at);
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new EntityNotDeletedException(
					"StrassenVerkehrsAmt konnte nicht gelöscht werden.", e);
		}
	}

	@SuppressWarnings("unchecked")
	List<StrassenVerkehrsAmt> getAllStrassenVerkehrsAemt() {
		List<StrassenVerkehrsAmt> ret = null;

		try {
			ret = getSession().createCriteria(StrassenVerkehrsAmt.class)
					.add(Restrictions.eq("deleted", DB.NO)).list();
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new DBLayerException(
					"StrassenVerkehrsAmt konnte nicht gefunden werden. Fehler bei Hibernate.",
					e);
		}

		if (ret == null) {
			ret = new ArrayList<StrassenVerkehrsAmt>();
		}

		return ret;
	}

	// ==============================================================
	// AUTOBAHNMEISTEREI - Reihenfolge: CRUD
	// ==============================================================

	void addAutobahnMeisterei(AutobahnMeisterei am, long loginId) {
		try {
			am.setEditorId(loginId);
			getSession().persist(am);
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new EntityNotInsertedException(
					"AutobahnMeisterei konnte nicht eingefügt werden.", e);
		}
	}

	AutobahnMeisterei getAutobahnMeisterei(long autobahnMeistereiId) {
		try {
			return (AutobahnMeisterei) getSession()
					.createCriteria(AutobahnMeisterei.class)
					.add(Restrictions.eq("id", autobahnMeistereiId))
					.add(Restrictions.eq("deleted", DB.NO)).uniqueResult();
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new EntityNotFoundException(
					"AutobahnMeisterei konnte nicht gefunden werden.", e);
		}
	}

	AutobahnMeisterei getAutobahnMeistereiByName(String name) {
		AutobahnMeisterei ret = null;
		try {
			ret = (AutobahnMeisterei) getSession()
					.createCriteria(AutobahnMeisterei.class)
					.add(Restrictions.eq("name", name))
					.add(Restrictions.eq("deleted", DB.NO)).uniqueResult();
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new EntityNotFoundException(
					"AutobahnMeisterei konnte nicht gefunden werden. Fehler bei Hibernate.",
					e);
		}
		if (ret == null)
			throw new EntityNotFoundException(
					"AutobahnMeisterei konnte nicht über den Namen gefunden werden. "
							+ name);
		return ret;
	}

	@SuppressWarnings("unchecked")
	List<AutobahnMeisterei> getAutobahnMeistereienByASV(
			long strassenVerkehrsAmtId) {
		List<AutobahnMeisterei> ret = null;

		try {
			ret = getSession().createCriteria(AutobahnMeisterei.class)
					.add(Restrictions.eq("parentId", strassenVerkehrsAmtId))
					.add(Restrictions.eq("deleted", DB.NO)).list();
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new DBLayerException(
					"AutobahnMeisterei konnte nicht gefunden werden. Fehler bei Hibernate.",
					e);
		}

		if (ret == null) {
			ret = new ArrayList<AutobahnMeisterei>();
		}

		return ret;
	}

	@SuppressWarnings("unchecked")
	List<AutobahnMeisterei> getAllAutobahnMeistereien() {
		List<AutobahnMeisterei> ret = null;

		try {
			ret = getSession().createCriteria(AutobahnMeisterei.class)
					.add(Restrictions.eq("deleted", DB.NO)).list();
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new DBLayerException(
					"AutobahnMeisterei-Liste konnten nicht gefunden werden. Fehler bei Hibernate.",
					e);
		}

		if (ret == null) {
			ret = new ArrayList<AutobahnMeisterei>();
		}

		return ret;
	}

	List<AutobahnMeisterei> getAllAutobahnMeistereienByFremdFirma(
			long fremdFirmaId) {

		List<AutobahnMeisterei> ret = new ArrayList<AutobahnMeisterei>();

		try {
			FremdFirma ff = (FremdFirma) getSession()
					.createCriteria(AutobahnMeisterei.class)
					.add(Restrictions.eq("id", fremdFirmaId))
					.add(Restrictions.eq("deleted", DB.NO)).uniqueResult();
			for (AutobahnMeisterei am : ff.getAutobahnMeistereien()) {
				if (am.getDeleted() == DB.NO)
					ret.add(am);
			}
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new DBLayerException(
					"AutobahnMeistereien konnten nicht gefunden werden. Fehler bei Hibernate.",
					e);
		}

		return ret;
	}

	void updateAutobahnMeisterei(AutobahnMeisterei am, long loginId) {
		try {
			am.setEditorId(loginId);
			getSession().update(am);
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new EntityNotUpdatedException(
					"AutobahnMeisterei konnte nicht aktualisiert werden.", e);
		}
	}

	void deleteAutobahnMeisterei(AutobahnMeisterei am, long loginId) {
		try {
			am.setDeleted(DB.YES);
			am.setEditorId(loginId);
			getSession().update(am);
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new EntityNotDeletedException(
					"AutobahnMeisterei konnte nicht gelöscht werden.", e);
		}
	}

	// ==============================================================
	// ANGESTELLTE - Reihenfolge: CRUD
	// ==============================================================

	void addAngestellte(Angestellte an, long loginId) {
		try {
			an.setEditorId(loginId);
			getSession().save(an);
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new EntityNotInsertedException(
					"Angestellte konnte nicht eingefügt werden.", e);
		}
	}

	Angestellte getAngestellte(long angestellteId) {
		try {
			return (Angestellte) getSession().createCriteria(Angestellte.class)
					.add(Restrictions.eq("id", angestellteId))
					.add(Restrictions.eq("deleted", DB.NO)).uniqueResult();
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new EntityNotFoundException(
					"Angestellte konnte nicht gefunden werden.", e);
		}
	}

	@SuppressWarnings("unchecked")
	List<Angestellte> getAllAngestellte() {
		List<Angestellte> ret = null;
		try {
			ret = getSession().createCriteria(Angestellte.class)
					.add(Restrictions.eq("deleted", DB.NO)).list();
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new DBLayerException(
					"Fehler beim Lesen der Angestellte-Liste. Fehler bei Hibernate.",
					e);
		}

		return ret;
	}

	@SuppressWarnings("unchecked")
	List<Angestellte> getAllAngestellteByAutoritaet(long autoritaetId) {
		List<Angestellte> ret = null;

		try {
			ret = getSession().createCriteria(Angestellte.class)
					.add(Restrictions.eq("authoritaetenId", autoritaetId))
					.add(Restrictions.eq("deleted", DB.NO)).list();
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new DBLayerException(
					"Angestellte konnten nicht gefunden werden. Fehler bei Hibernate.",
					e);
		}

		if (ret == null) {
			ret = new ArrayList<Angestellte>();
		}

		return ret;
	}

	void updateAngestellte(Angestellte an, long loginId) {
		try {
			an.setEditorId(loginId);
			getSession().update(an);
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new EntityNotUpdatedException(
					"Angestellte konnte nicht aktualisiert werden.", e);
		}
	}

	void deleteAngestellte(Angestellte an, long loginId) {
		try {
			an.setDeleted(DB.YES);
			an.setEditorId(loginId);
			getSession().update(an);
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new EntityNotDeletedException(
					"Angestellte konnte nicht gelöscht werden.", e);
		}
	}
}
