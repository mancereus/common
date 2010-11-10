package de.scoopgmbh.common.main;

import java.util.List;

import com.google.inject.Guice;
import com.google.inject.Injector;

import de.scoopgmbh.common.db.data.Order;
import de.scoopgmbh.common.guice.log.LoggingModule;
import de.scoopgmbh.common.service.RealContactService;

public class GuiceMain {
	public static void main(String[] args) {

		Injector injector = Guice.createInjector(new DBConnectionModule(),
				new ConfigureEbeanModule(), new LoggingModule());
		RealContactService service = injector
				.getInstance(RealContactService.class);
		List<Order> orders = service.findAllOrder();
		for (Order order : orders) {
			System.out.println(order);
		}
	}
}
