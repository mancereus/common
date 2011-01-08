package de.db12.common.db.data.query;

import java.util.List;

import org.junit.Test;

import com.avaje.ebean.Ebean;

import de.db12.common.db.data.Order;

public class QueryWithLotsOfJoins extends SetupTestCase {

	@Test
	public void testJoinsWithFullObjects() {

		List<Order> list = Ebean.find(Order.class).join("details")
				.join("details.product").join("customer")
				.join("customer.billingAddress")
				.join("customer.shippingAddress").where()
				.eq("status", Order.Status.NEW).findList();

		System.out.println(list);

	}

	@Test
	public void testJoinsWithPartialObjects() {

		List<Order> list = Ebean.find(Order.class)
				.select("id, status, orderDate").join("customer", "name")
				.join("details", "id, orderQty")
				.join("details.product", "name, sku").where()
				.eq("status", Order.Status.NEW).findList();

		System.out.println(list);

	}

}
