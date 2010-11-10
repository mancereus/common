package de.scoopgmbh.common.db.data.query;

import java.util.List;

import org.junit.Test;

import com.avaje.ebean.Ebean;

import de.scoopgmbh.common.db.data.Order;

public class SimpleQuery extends SetupTestCase {

	@Test
	public void testSimpleQuery() {

		List<Order> list = Ebean.find(Order.class).join("customer").where()
				.gt("id", 0).eq("status", Order.Status.NEW)
				.ilike("customer.name", "Ro%").findList();

		System.out.println(list);
	}

}
