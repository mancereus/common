package de.scoopgmbh.common.db.data.query;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.junit.Test;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.FutureList;

import de.scoopgmbh.common.db.data.Order;

public class AsyncQueryExecution extends SetupTestCase {

	@Test
	public void testAsync() throws InterruptedException, ExecutionException {

		FutureList<Order> futureList = Ebean.find(Order.class).join("customer")
				.where().gt("id", 0).eq("status", Order.Status.NEW)
				.ilike("customer.name", "Ro%").findFutureList();

		// You could do something else now with the
		// foreground thread (while the query is running)
		// ... we will just sleep
		Thread.sleep(100);

		// check if it has finished
		// futureList.isDone();

		// cancel the running query
		// futureList.cancel(true);

		// return the list waiting for it to complete
		List<Order> list = futureList.get();

		// wait with a timeout
		// list.get(10, TimeUnit.SECONDS);

		System.out.println(list);
	}

}
