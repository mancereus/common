package de.scoopgmbh.common.db.data.query;

import java.util.List;


import com.avaje.ebean.Ebean;

import de.scoopgmbh.common.db.data.Order;
import de.scoopgmbh.common.db.setup.Setup;


public class SimpleQuery {

	public static void main(String[] args) {
		
		// load some test data...
		Setup.resetData();
		
		SimpleQuery me = new SimpleQuery();
		me.run();

	}

	private void run() {

		
		List<Order> list = Ebean.find(Order.class)
			.join("customer")
			.where()
				.gt("id", 0)
				.eq("status", Order.Status.NEW)
				.ilike("customer.name", "Ro%")
			.findList();

		System.out.println(list);
	}
	
	
}
