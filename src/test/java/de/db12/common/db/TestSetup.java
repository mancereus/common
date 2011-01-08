package de.db12.common.db;

import org.junit.Test;

import com.avaje.ebean.Ebean;

import de.db12.common.db.data.Customer;
import de.db12.common.db.data.Truck;
import de.db12.common.db.setup.Setup;

public class TestSetup {

	@Test
	public void test() {

		Setup.resetData();

		Customer customer = Ebean.find(Customer.class, 2);

		// Customer customer =
		// Ebean.find(Customer.class).fetch("vehicle").where()
		// .eq("id", 2).findUnique();
		if (customer.getVehicle() instanceof Truck) {
			System.out.println(((Truck) customer.getVehicle()).getCapacity());
		}

	}
}
