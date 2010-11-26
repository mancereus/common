package de.scoopgmbh.bms.db.service.impl;

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
import de.scoopgmbh.bms.db.model.Angestellte;
import de.scoopgmbh.bms.db.model.Login;
import de.scoopgmbh.bms.db.model.Rolle;

class LoginDAO extends HibernateDaoSupport {

	private static final Logger LOGGER = Logger.getLogger(LoginDAO.class);
	
	LoginDAO(SessionFactory sf) {
		setSessionFactory(sf);
	}
	
	Login getLoginByName(String name) {
		Login ret = null;
		try {
			ret = (Login) getSession()
				.createCriteria(Login.class)
				.add(Restrictions.eq("login", name))
				.add(Restrictions.eq("deleted", DB.NO))
				.uniqueResult();
		} catch (Exception e) {
			LOGGER.error(e,e);
			throw new EntityNotFoundException("Login nicht gefügen für Namen: " + name);
		}
		if (ret==null) throw new EntityNotFoundException("Login nicht gefunden für Namen: " + name);
		
		return ret;
	}
	
	Login getLoginById(long loginId) {
		Login ret = null;
		try {
			ret = (Login) getSession()
				.createCriteria(Login.class)
				.add(Restrictions.eq("id", loginId))
				.add(Restrictions.eq("deleted", DB.NO))
				.uniqueResult();
		} catch (Exception e) {
			LOGGER.error(e,e);
			throw new EntityNotFoundException("Login nicht gefunden.");
		}
		if (ret==null) throw new EntityNotFoundException("Login nicht gefunden.");
		return ret;
	}
	
	@SuppressWarnings("unchecked")
	List<Login> getLoginsByAutoritaet(long autoritaetId) {
		List<Login> ret = null;
		try {
			ret = (List<Login>) getSession()
				.createCriteria(Login.class)
				.add(Restrictions.eq("autoritaetId", autoritaetId))
				.add(Restrictions.eq("deleted", DB.NO))
				.list();
		} catch (Exception e) {
			LOGGER.error(e,e);
			throw new DBLayerException("Fehler beim Lesen des Logins. Fehler bei Hibernate.");
		}
		if (ret==null) ret = new LinkedList<Login>();
		return ret;
	}
	
	@SuppressWarnings("unchecked")
	List<Login> getAllLogins() {
		List<Login> ret = null;
		try {
			ret = (List<Login>) getSession()
				.createCriteria(Login.class)
				.add(Restrictions.eq("deleted", DB.NO))
				.list();
		} catch (Exception e) {
			LOGGER.error(e,e);
			throw new DBLayerException("Fehler beim Lesen der Logins. Fehler bei Hibernate.");
		}
		if (ret==null) ret = new LinkedList<Login>();
		return ret;
	}
	
	@SuppressWarnings("unchecked")
	List<Rolle> getAllRoles() {
		List<Rolle> ret = null;
		try {
			ret = (List<Rolle>) getSession()
				.createCriteria(Rolle.class)
				.add(Restrictions.eq("deleted", DB.NO))
				.list();
		} catch (Exception e) {
			LOGGER.error(e,e);
			throw new DBLayerException("Fehler beim Lesen der Rollen. Fehler bei Hibernate.");
		}
		if (ret==null) ret = new LinkedList<Rolle>();
		return ret;
	}
	
	List<Rolle> getRolesForLoginId(long loginId) {
		List<Rolle> ret = new LinkedList<Rolle>();
		try {
			Login l = (Login) getSession()
				.createCriteria(Login.class)
				.add(Restrictions.eq("id", loginId))
				.add(Restrictions.eq("deleted", DB.NO))
				.uniqueResult();
			for(Rolle r : l.getRollen()) {
				if (r.getDeleted()==DB.NO) ret.add(r);
			}
		} catch (Exception e) {
			LOGGER.error(e,e);
			throw new DBLayerException("Fehler beim Lesen der Rollen. Fehler bei Hibernate.");
		}
		
		return ret;
	}
	
	void addRole(Rolle r, long loginId) {
		try {
			r.setEditorId(loginId);
			getSession().save(r);
		} catch (Exception e) {
			LOGGER.error(e,e);
			throw new EntityNotInsertedException("Fehler beim Einfügen der Rolle. Fehler bei Hibernate.");
		}
	}
	
	void deleteRole(Rolle r, long loginId) {
		try {
			r.setDeleted(DB.YES);
			r.setEditorId(loginId);
			getSession().update(r);
		} catch (Exception e) {
			LOGGER.error(e,e);
			throw new EntityNotDeletedException("Fehler beim Löschen der Rolle. Fehler bei Hibernate.", e);
		}
	}
	
	Rolle getRole(long rolleId) {
		Rolle ret = null;
		try {
			ret = (Rolle) getSession()
				.createCriteria(Login.class)
				.add(Restrictions.eq("id", rolleId))
				.add(Restrictions.eq("deleted", DB.NO))
				.uniqueResult();
		} catch (Exception e) {
			LOGGER.error(e,e);
			throw new EntityNotFoundException("Rolle konnte nicht gefunden werden. Fehler bei Hibernate.");
		}
		if (ret==null) throw new EntityNotFoundException("Rolle nicht gefunden. ID: " + rolleId);
		return ret;
	}
	
	void updateRole(Rolle r, long editorId) {
		try {
			r.setEditorId(editorId);
			getSession().update(r);
		} catch (Exception e) {
			LOGGER.error(e,e);
			throw new EntityNotUpdatedException("Rolle konnte nicht aktualisiert werden. Fehler bei Hibernate.");
		}
	}
	
	void addLogin(Login l, long editorId) {
		try {
			l.setEditorId(editorId);
			getSession().save(l);
		} catch (Exception e) {
			LOGGER.error(e,e);
			throw new EntityNotFoundException("Rolle konnte nicht hinzugefügt werden. Fehler bei Hibernate.");
		}
	}
	
	void deleteLogin(Login l, long editorId) {
		try {
			l.setDeleted(DB.YES);
			l.setEditorId(editorId);
			getSession().update(l);
		} catch (Exception e) {
			LOGGER.error(e,e);
			throw new EntityNotDeletedException("Rolle konnte nicht gelöscht werden. Fehler bei Hibernate.", e);
		}
	}
	
	void disableLogin(Login l, long editorId) {
		try {
			l.setEnabled(DB.NO);
			getSession().update(l);
		} catch (Exception e) {
			LOGGER.error(e,e);
			throw new EntityNotUpdatedException("Rolle konnte nicht disabled werden. Fehler bei Hibernate.");
		}
	}
	
	void updateLogin(Login lg, long longinId) {
		try {
			lg.setEditorId(longinId);
			getSession().update(lg);
		} catch (Exception e) {
			LOGGER.error(e,e);
			throw new EntityNotUpdatedException("Rolle konnte nicht aktualisiert werden. Fehler bei Hibernate.");
		}
	}
	
	long getAutoritaetenIdByAngestelltenId(long angestelltenId) {
		Angestellte ret = null;
		try {
			ret = (Angestellte) getSession().get(Angestellte.class, angestelltenId);
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new EntityNotFoundException("Angestellte konnte nicht geladen werden. Fehler bei Hibernate.", e);
		}
		if (ret==null) return 0;
		return ret.getAuthoritaetenId();
	}
}
