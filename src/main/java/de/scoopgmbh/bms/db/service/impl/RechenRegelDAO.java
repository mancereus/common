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
import de.scoopgmbh.bms.db.exceptions.EntityNotInsertedException;
import de.scoopgmbh.bms.db.model.RechenRegelNode;

class RechenRegelDAO extends HibernateDaoSupport {

	private static final Logger LOGGER = Logger.getLogger(RechenRegelDAO.class);
	
	RechenRegelDAO(SessionFactory sf) {
		setSessionFactory(sf);
	}
	
	@SuppressWarnings("unchecked")
	List<RechenRegelNode> getRechenRegelForAbschnittId(long abschnittId) {
		List<RechenRegelNode> rrnList = null;
		try {
			rrnList = getSession()
					.createCriteria(RechenRegelNode.class)
					.add(Restrictions.eq("idAbschnitt", abschnittId))
					.add(Restrictions.eq("deleted", DB.NO))
					.list();
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new DBLayerException("RechenRegelNode nicht gefunden. Fehler bei Hibernate.");
		}
		
		if (rrnList==null) rrnList = new LinkedList<RechenRegelNode>();
		
		return rrnList;
	}

	void deleteRechenRegeln(RechenRegelNode rrd, long editorId) {
		try {
			rrd.setDeleted(DB.YES);
			rrd.setEditorId(editorId);
			getSession().update(rrd);
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new EntityNotDeletedException("RechenRegel nicht gelöscht. Fehler bei Hibernate.", e);
		}
	}
	
	void insertRechenRegel(RechenRegelNode rrd, long editorId) {
		try {
			rrd.setEditorId(editorId);
			getSession().save(rrd);
		} catch (Exception e) {
			LOGGER.error(e, e);
			throw new EntityNotInsertedException("RechenRegelNode nicht eingefügt. Fehler bei Hibernate.");
		}
	}
}
