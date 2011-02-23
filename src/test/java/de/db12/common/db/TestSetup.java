package de.db12.common.db;

import org.junit.Test;

import de.db12.common.db.setup.Setup;

public class TestSetup {

	@Test
	public void test() {

		Setup.resetData();

		// Customer customer = Ebean.find(Customer.class, 2);

		// Customer customer =
		// Ebean.find(Customer.class).fetch("vehicle").where()
		// .eq("id", 2).findUnique();
		// if (customer.getVehicle() instanceof Truck) {
		// Truck truck = (Truck) customer.getVehicle();
		// System.out.println(truck.getCapacity());
		// }

	}
}
