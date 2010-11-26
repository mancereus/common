package de.scoopgmbh.bms.db.service.impl;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.annotation.Transactional;
import com.google.inject.Inject;

import de.scoopgmbh.bms.db.exceptions.EntityNotDeletedException;
import de.scoopgmbh.bms.db.exceptions.EntityNotFoundException;
import de.scoopgmbh.bms.db.exceptions.EntityNotInsertedException;
import de.scoopgmbh.bms.db.exceptions.EntityNotUpdatedException;
import de.scoopgmbh.bms.db.model.Skizze;
import de.scoopgmbh.bms.db.service.SkizzenService;

public class SkizzenServiceImpl extends EbeanDao<Skizze> implements
		SkizzenService {

	@Inject
	public SkizzenServiceImpl(EbeanServer ebean) {
		super(ebean);
	}

	@Transactional(readOnly = false)
	public void addSkizze(Skizze at, long loginId)
			throws EntityNotInsertedException {
		this.impl.addSkizze(at, loginId);

	}

	@Transactional(readOnly = false)
	public void deleteSkizze(Skizze at, long loginId)
			throws EntityNotDeletedException {
		this.impl.deleteSkizze(at, loginId);

	}

	public Skizze getSkizze(long skizzeId) throws EntityNotFoundException {
		return this.impl.getSkizze(skizzeId);
	}

	public Skizze getSkizzeByNetzpunktId(long netzpunktId)
			throws EntityNotFoundException {
		return this.impl.getSkizzeByNetzpunktId(netzpunktId);
	}

	@Transactional(readOnly = false)
	public void updateSkizze(Skizze at, long loginId)
			throws EntityNotUpdatedException {
		this.impl.updateSkizze(at, loginId);

	}

}
