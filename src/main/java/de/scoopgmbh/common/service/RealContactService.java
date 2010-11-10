package de.scoopgmbh.common.service;

import java.util.List;

import org.slf4j.Logger;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.annotation.Transactional;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.scoopgmbh.common.db.data.Order;
import de.scoopgmbh.common.guice.log.InjectLogger;

@Singleton
public class RealContactService {
	@InjectLogger
	Logger log;

	@Inject
	public RealContactService() {
	}

	@Transactional
	public void insert(final Order order) {
		// TODO: insert order
	}

	@Transactional
	public List<Order> findAllOrder() {
		return Ebean.find(Order.class).findList();

	}

}
