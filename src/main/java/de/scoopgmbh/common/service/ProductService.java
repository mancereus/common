package de.scoopgmbh.common.service;

import java.util.List;

import org.slf4j.Logger;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.annotation.Transactional;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.scoopgmbh.common.db.data.Product;
import de.scoopgmbh.common.guice.log.InjectLogger;

@Singleton
public class ProductService {
	@InjectLogger
	Logger log;

	@Inject
	EbeanServer ebean;

	@Transactional
	public void insert(final Product product) {
		// TODO: insert order
	}

	@Transactional
	public List<Product> findAllProducts() {
		return ebean.find(Product.class).findList();

	}

}