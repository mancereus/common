package de.db12.common.db.data.query;

import java.util.List;

import org.junit.Test;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Query;

import de.db12.common.db.data.Order;

public class QueryFetchingRowCount extends SetupTestCase {

	@Test
	public void run() {

		Query<Order> query = Ebean.find(Order.class).join("customer").where()
				.gt("id", 0).eq("status", Order.Status.NEW)
				.ilike("customer.name", "Ro%").query();

		// execute a row count query for this
		int rowCount = query.findRowCount();

		// execute the original query
		List<Order> list = query.findList();

		System.out.println("RowCount was: " + rowCount);
		System.out.println(list);
	}

}
