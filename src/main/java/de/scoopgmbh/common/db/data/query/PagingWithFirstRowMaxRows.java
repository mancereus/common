package de.scoopgmbh.common.db.data.query;

import java.util.List;


import com.avaje.ebean.Ebean;
import com.avaje.ebean.JoinConfig;

import de.scoopgmbh.common.db.data.Order;
import de.scoopgmbh.common.db.setup.Setup;


public class PagingWithFirstRowMaxRows {

	public static void main(String[] args) {
		
		// load some test data...
		Setup.resetData();
		
		PagingWithFirstRowMaxRows me = new PagingWithFirstRowMaxRows();
		me.run();

	}

	private void run() {

		
		List<Order> list = Ebean.find(Order.class)
			.join("details", new JoinConfig().query())
			.where()
				.gt("id", 0)
				.eq("status", Order.Status.NEW)
			
				
			// Use firstRow, maxRows to specify the
			// rows to include in the result
			//.setFirstRow(x)
			.setMaxRows(10)
			
			// You *SHOULD* have an order by 
			.order().desc("id")
			.findList();

		System.out.println(list);
	}
	
	
}
