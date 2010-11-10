package de.scoopgmbh.common.db.data.query;

import java.util.List;


import com.avaje.ebean.Ebean;

import de.scoopgmbh.common.db.data.Order;
import de.scoopgmbh.common.db.setup.Setup;


public class QueryWithLotsOfJoins {

	public static void main(String[] args) {
		
		// load some test data...
		Setup.resetData();
		
		QueryWithLotsOfJoins me = new QueryWithLotsOfJoins();

		me.joinsWithFullObjects();
		me.joinsWithPartialObjects();
	}

	
	/**
	 * Fetch a full object graph.
	 */
	private void joinsWithFullObjects() {

		List<Order> list = Ebean.find(Order.class)
			.join("details")
			.join("details.product")
			.join("customer")
			.join("customer.billingAddress")
			.join("customer.shippingAddress")
			.where().eq("status",Order.Status.NEW)
			.findList();

		System.out.println(list);

	}

	private void joinsWithPartialObjects() {
		
		List<Order> list = Ebean.find(Order.class)
			.select("id, status, orderDate")
			.join("customer", "name")
			.join("details", "id, orderQty")
			.join("details.product", "name, sku")
			.where().eq("status",Order.Status.NEW)
			.findList();

		System.out.println(list);

	}
	
}
