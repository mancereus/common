package de.db12.common.db.data.query;

import java.util.List;

import org.junit.Test;

import com.avaje.ebean.Ebean;

import de.db12.common.db.data.Order;

public class QueryWithAutofetch extends SetupTestCase {

	@Test
	public void run() throws Exception {

		List<Order> list = Ebean.find(Order.class).setAutofetch(true)
				.join("customer").where().eq("status", Order.Status.NEW)
				.order().desc("shipDate").order().asc("id").findList();

		for (Order order : list) {
			order.getOrderDate();
			order.getCustomer().getName();
			System.out.println(order);
		}

	}
}
