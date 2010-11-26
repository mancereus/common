package de.scoopgmbh.bms.db.service.impl;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import de.scoopgmbh.bms.db.DB;
import de.scoopgmbh.bms.db.exceptions.EntityNotDeletedException;
import de.scoopgmbh.bms.db.exceptions.EntityNotFoundException;
import de.scoopgmbh.bms.db.exceptions.EntityNotInsertedException;
import de.scoopgmbh.bms.db.exceptions.EntityNotUpdatedException;
import de.scoopgmbh.bms.db.model.Skizze;

class SkizzenDAO extends HibernateDaoSupport {

	private static final Logger LOGGER = Logger.getLogger(SkizzenDAO.class);

	SkizzenDAO(SessionFactory sf) {
		setSessionFactory(sf);
	}

	void addSkizze(Skizze f, long editorId) {
		try {
			f.setEditorId(editorId);
			getSession().save(f);
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new EntityNotInsertedException("Skizze konnte nicht hinzugefügt werden. Fehler bei Hibernate.", e);
		}
	}

	void deleteSkizze(Skizze f, long editorId) {
		try {
			f.setDeleted(DB.YES);
			f.setEditorId(editorId);
			getSession().update(f);
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new EntityNotDeletedException("Skizze konnte nicht gelöscht werden. Fehler bei Hibernate.", e);
		}
	}

	void updateSkizze(Skizze f, long editorId) {
		try {
			f.setEditorId(editorId);
			getSession().merge(f);
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new EntityNotUpdatedException("Skizze konnte nicht aktualisiert werden. Fehler bei Hibernate.", e);
		}
	}

	Skizze getSkizze(long id) {
		Skizze ret = null;
		try {
			ret = (Skizze) getSession()
				.createCriteria(Skizze.class)
				.add(Restrictions.eq("id", id))
				.add(Restrictions.eq("deleted", DB.NO))
				.uniqueResult();
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new EntityNotFoundException("Skizze nicht gefunden. Fehler bei Hibernate.", e);
		}

		if (ret == null) {
			throw new EntityNotFoundException("Skizze nicht gefunden. ID: " + id);
		}
		return ret;
	}

	Skizze getSkizzeByNetzpunktId(long id) {
		Skizze ret = null;
		try {
			ret = (Skizze) getSession()
				.createCriteria(Skizze.class)
				.add(Restrictions.eq("netzpunktId", id))
				.add(Restrictions.eq("deleted", DB.NO))
				.uniqueResult();
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new EntityNotFoundException("Skizze nicht gefunden. Fehler bei Hibernate.", e);
		}

		if (ret == null) {
			throw new EntityNotFoundException("Skizze nicht gefunden. ID: " + id);
		}
		return ret;
	}
}
