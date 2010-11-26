package de.scoopgmbh.bms.db.service.impl;

import java.util.List;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.Expr;
import com.avaje.ebean.annotation.Transactional;
import com.google.inject.Inject;
import com.google.inject.internal.Lists;

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
import de.scoopgmbh.bms.db.service.AutoritaetsService;

public class AutoritaetsServiceImpl extends EbeanDao<Autoritaet> implements
		AutoritaetsService {

	@Inject
	public AutoritaetsServiceImpl(EbeanServer ebean) {
		super(ebean);
	}

	@Transactional(readOnly = false)
	public void addAutoritaet(Autoritaet at, long loginId)
			throws EntityNotInsertedException {
		at.setEditorId(loginId);
		ebean.save(at);

	}

	public Autoritaet getAutoritaet(long autoritaetId)
			throws EntityNotFoundException {
		return ebean.find(Autoritaet.class, autoritaetId);
	}

	public void updateAutoritaet(Autoritaet at, long loginId)
			throws EntityNotUpdatedException {
		at.setEditorId(loginId);
		ebean.save(at);
	}

	public void deleteAutoritaet(Autoritaet at, long loginId)
			throws EntityNotDeletedException {
		ebean.delete(at);
	}

	public void addFremdFirma(FremdFirma ff, long loginId)
			throws EntityNotInsertedException {
		ff.setEditorId(loginId);
		ebean.save(ff);
	}

	public FremdFirma getFremdFirma(long fremdFirmaId)
			throws EntityNotFoundException {
		return ebean.find(FremdFirma.class, fremdFirmaId);
	}

	public List<FremdFirma> getAllFremdfirmen() throws DBLayerException {
		return ebean.find(FremdFirma.class).findList();
	}

	public List<FremdFirma> getAllFremdFirmenByAutobahnMeisterei(
			long autobahnMeistereiId) throws DBLayerException {
		AutobahnMeisterei am = ebean.find(AutobahnMeisterei.class)
				.where(Expr.eq("deleted", DB.NO))
				.where(Expr.eq("id", autobahnMeistereiId)).findUnique();
		if (am != null) {
			return am.getFremdFirmen();
		}
		return Lists.newArrayList();
	}

	@Transactional(readOnly = false)
	public void updateFremdFirma(FremdFirma ff, long loginId)
			throws EntityNotUpdatedException {
		this.impl.updateFremdFirma(ff, loginId);
	}

	@Transactional(readOnly = false)
	public void addFremdFirmaToAutobahnMeisterei(long autobahnMeistereiId,
			long fremdFirmaId, long loginId) throws EntityNotInsertedException {
		this.impl.addFremdFirmaToAutobahnMeisterei(autobahnMeistereiId,
				fremdFirmaId, loginId);
	}

	@Transactional(readOnly = false)
	public void deleteFremdFirma(FremdFirma ff, long loginId)
			throws EntityNotDeletedException {
		// FIXME CHIRTE Angestellte und Logins löschen
		this.impl.deleteFremdFirma(ff, loginId);
	}

	@Transactional(readOnly = false)
	public void addBaustoffBodenPruefAmt(BaustoffBodenPruefAmt bbpa,
			long loginId) throws EntityNotInsertedException {
		this.impl.addBaustoffBodenPruefAmt(bbpa, loginId);
	}

	public BaustoffBodenPruefAmt getBaustoffBodenPruefAmt(long autoritaetId)
			throws EntityNotFoundException {
		return this.impl.getBaustoffBodenPruefAmt(autoritaetId);
	}

	public List<BaustoffBodenPruefAmt> getAllBaustoffBodenPruefAemter()
			throws DBLayerException {
		return this.impl.getAllBaustoffBodenPruefAemter();
	}

	@Transactional(readOnly = false)
	public void updateBaustoffBodenPruefAmt(BaustoffBodenPruefAmt bbpa,
			long loginId) throws EntityNotUpdatedException {
		this.impl.updateBaustoffBodenPruefAmt(bbpa, loginId);
	}

	@Transactional(readOnly = false)
	public void deleteBaustoffBodenPruefAmt(BaustoffBodenPruefAmt bbpa,
			long loginId) throws EntityNotDeletedException {
		// FIXME CHIRTE angestellte und Logins löschen
		this.impl.deleteBaustoffBodenPruefAmt(bbpa, loginId);
	}

	@Transactional(readOnly = false)
	public void addAutobahnPolizeiStation(AutobahnPolizeiStation aps,
			long loginId) throws EntityNotInsertedException {
		this.impl.addAutobahnPolizeiStation(aps, loginId);
	}

	public AutobahnPolizeiStation getAutobahnPolizeiStation(long autoritaetId)
			throws EntityNotFoundException {
		return this.impl.getAutobahnPolizeiStation(autoritaetId);
	}

	public List<AutobahnPolizeiStation> getAllAutobahnPolizeiStationen()
			throws DBLayerException {
		return this.impl.getAllAutobahnPolizeiStationen();
	}

	@Transactional(readOnly = false)
	public void updateAutobahnPolizeiStation(AutobahnPolizeiStation aps,
			long loginId) throws EntityNotUpdatedException {
		this.impl.updateAutobahnPolizeiStation(aps, loginId);
	}

	@Transactional(readOnly = false)
	public void deleteAutobahnPolizeiStation(AutobahnPolizeiStation aps,
			long loginId) throws EntityNotDeletedException {
		// FIXME CHIRTE angestellte und Logins löschen
		this.impl.deleteAutobahnPolizeiStation(aps, loginId);
	}

	@Transactional(readOnly = false)
	public void addStrassenVerkehrsAmt(StrassenVerkehrsAmt asv, long loginId)
			throws EntityNotInsertedException {
		this.impl.addStrassenVerkehrsAmt(asv, loginId);
	}

	public StrassenVerkehrsAmt getStrassenVerkehrsAmt(long autoritaetId)
			throws EntityNotFoundException {
		return this.impl.getStrassenVerkehrsAmt(autoritaetId);
	}

	public List<StrassenVerkehrsAmt> getAllStrassenVerkehrsAemter()
			throws DBLayerException {
		return this.impl.getAllStrassenVerkehrsAemt();
	}

	@Transactional(readOnly = false)
	public void updateStrassenVerkehrsAmt(StrassenVerkehrsAmt asv, long loginId)
			throws EntityNotUpdatedException {
		this.impl.updateStrassenVerkehrsAmt(asv, loginId);
	}

	@Transactional(readOnly = false)
	public void deleteStrassenVerkehrsAmt(StrassenVerkehrsAmt asv, long loginId)
			throws EntityNotDeletedException {
		// FIXME CHIRTE angestellte und Logins löschen
		this.impl.deleteStrassenVerkehrsAmt(asv, loginId);
	}

	@Transactional(readOnly = false)
	public void addAutobahnMeisterei(AutobahnMeisterei am, long loginId)
			throws EntityNotInsertedException {
		this.impl.addAutobahnMeisterei(am, loginId);

	}

	public AutobahnMeisterei getAutobahnMeisterei(long autobahnMeistereiId)
			throws EntityNotFoundException {
		return this.impl.getAutobahnMeisterei(autobahnMeistereiId);
	}

	public AutobahnMeisterei getAutobahnMeistereiByName(String name)
			throws EntityNotFoundException {
		return this.impl.getAutobahnMeistereiByName(name);
	}

	public List<AutobahnMeisterei> getAllAutobahnMeistereien()
			throws DBLayerException {
		return this.impl.getAllAutobahnMeistereien();
	}

	public List<AutobahnMeisterei> getAllAutobahnMeistereienByFremdFirma(
			long fremdFirmaId) throws DBLayerException {
		return this.impl.getAllAutobahnMeistereienByFremdFirma(fremdFirmaId);
	}

	public List<AutobahnMeisterei> getAutobahnMeistereienByASV(
			long strassenVerkehrsAmtId) throws DBLayerException {
		return this.impl.getAutobahnMeistereienByASV(strassenVerkehrsAmtId);
	}

	@Transactional(readOnly = false)
	public void updateAutobahnMeisterei(AutobahnMeisterei am, long loginId)
			throws EntityNotUpdatedException {
		this.impl.updateAutobahnMeisterei(am, loginId);
	}

	@Transactional(readOnly = false)
	public void deleteAutobahnMeisterei(AutobahnMeisterei am, long loginId)
			throws EntityNotDeletedException {
		// FIXME CHIRTE angestellte und Logins löschen
		this.impl.deleteAutobahnMeisterei(am, loginId);
	}

	@Transactional(readOnly = false)
	public void addAngestellte(Angestellte an, long loginId)
			throws EntityNotInsertedException {
		this.impl.addAngestellte(an, loginId);
	}

	public Angestellte getAngestellte(long angestellteId)
			throws EntityNotFoundException {
		return this.impl.getAngestellte(angestellteId);
	}

	public List<Angestellte> getAllAngestellte() throws DBLayerException {
		return this.impl.getAllAngestellte();
	}

	public List<Angestellte> getAllAngestellteByAutoritaet(long autoritaetId)
			throws DBLayerException {
		return this.impl.getAllAngestellteByAutoritaet(autoritaetId);
	}

	@Transactional(readOnly = false)
	public void updateAngestellte(Angestellte an, long loginId)
			throws EntityNotUpdatedException {
		this.impl.updateAngestellte(an, loginId);
	}

	@Transactional(readOnly = false)
	public void deleteAngestellte(Angestellte an, long loginId)
			throws EntityNotDeletedException {
		this.impl.deleteAngestellte(an, loginId);
	}

}
