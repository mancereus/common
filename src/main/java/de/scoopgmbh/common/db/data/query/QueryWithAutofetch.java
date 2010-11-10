package de.scoopgmbh.common.db.data.query;

import java.util.List;


import com.avaje.ebean.Ebean;

import de.scoopgmbh.common.db.data.Order;
import de.scoopgmbh.common.db.setup.Setup;

public class QueryWithAutofetch {

	public static void main(String[] args) throws Exception {
	
		QueryWithAutofetch me = new QueryWithAutofetch();
		me.test();

		System.out.println("done");
	}
	
	private void test() throws Exception {
		
		Setup.resetData();

		List<Order> list = Ebean.find(Order.class)
			.setAutofetch(true)
			.join("customer")
			.where()
				.eq("status", Order.Status.NEW)
			.order().desc("shipDate")
			.order().asc("id")
			.findList();
		
		for (Order order : list) {
			order.getOrderDate();
			order.getCustomer().getName();
			System.out.println(order);
		}
			
	}
}
