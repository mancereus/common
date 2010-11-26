package de.scoopgmbh.bms.db.service.impl;

import com.avaje.ebean.EbeanServer;
import com.google.inject.Inject;

public abstract class EbeanDao<T> {

	protected EbeanServer ebean;

	@Inject
	public EbeanDao(EbeanServer ebeanserver) {
		this.ebean = ebeanserver;
	}
}
