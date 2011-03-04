package de.db12.common.service;

import java.util.List;
import org.slf4j.Logger;
import com.avaje.ebean.Ebean;
import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.annotation.Transactional;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.db12.common.db.data.Product;
import de.db12.common.guice.log.InjectLogger;

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
	public static List<Product> findAllProducts() {
		return Ebean.find(Product.class).findList();

	}

	public void persist(Product product) {
		ebean.save(product);
	}

	public void remove(Product product) {
		ebean.delete(product);
	}
}
