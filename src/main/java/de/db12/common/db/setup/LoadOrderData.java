package de.db12.common.db.setup;

import java.util.ArrayList;
import java.util.List;

import com.avaje.ebean.Ebean;

import de.db12.common.db.data.Address;
import de.db12.common.db.data.Country;
import de.db12.common.db.data.Customer;
import de.db12.common.db.data.Order;
import de.db12.common.db.data.OrderDetail;
import de.db12.common.db.data.Product;
import de.db12.common.db.data.Truck;
import de.db12.common.db.data.Vehicle;

public class LoadOrderData {

	public static void main(String[] args) {

		LoadOrderData me = new LoadOrderData();
		me.insert();
	}

	public void insert() {

		Vehicle veh = insertVehicle();
		Customer cust1 = insertCustomerNoAddress();
		Customer cust2 = insertCustomer(veh);

		createOrder1(cust1);
		createOrder2(cust2);
		createOrder3(cust1);
	}

	private Vehicle insertVehicle() {
		Truck truck = new Truck();
		truck.setCapacity(3000);
		truck.setLicense("licence");
		Ebean.save(truck);
		return truck;
	}

	private Customer insertCustomerNoAddress() {

		Customer c = new Customer();
		c.setName("Cust NoAddress");
		c.setStatus(Customer.Status.NEW);

		Ebean.save(c);
		return c;
	}

	private Customer insertCustomer(Vehicle veh) {

		Customer c = new Customer();
		c.setName("Rob");
		c.setStatus(Customer.Status.NEW);
		c.setVehicle(veh);
		Address shippingAddr = new Address();
		shippingAddr.setLine1("1 Banana St");
		shippingAddr.setLine2("Sandringham");
		shippingAddr.setCity("Auckland");
		shippingAddr.setCountry(Ebean.getReference(Country.class, "NZ"));

		c.setShippingAddress(shippingAddr);

		Address billingAddr = new Address();
		billingAddr.setLine1("P.O.Box 1234");
		billingAddr.setLine2("Sandringham");
		billingAddr.setCity("Auckland");
		billingAddr.setCountry(Ebean.getReference(Country.class, "NZ"));

		c.setBillingAddress(billingAddr);

		Ebean.save(c);

		return c;
	}

	private void createOrder1(Customer customer) {

		Product product1 = Ebean.getReference(Product.class, 1);
		Product product2 = Ebean.getReference(Product.class, 2);
		Product product3 = Ebean.getReference(Product.class, 3);

		Order order = new Order();
		order.setCustomer(customer);

		List<OrderDetail> details = new ArrayList<OrderDetail>();
		details.add(new OrderDetail(product1, 5));
		details.add(new OrderDetail(product2, 3));
		details.add(new OrderDetail(product3, 1));
		order.setDetails(details);

		Ebean.save(order);
	}

	private void createOrder2(Customer customer) {

		Product product1 = Ebean.getReference(Product.class, 1);

		Order order = new Order();
		order.setCustomer(customer);

		List<OrderDetail> details = new ArrayList<OrderDetail>();
		details.add(new OrderDetail(product1, 4));
		order.setDetails(details);

		Ebean.save(order);
	}

	private void createOrder3(Customer customer) {

		Product product1 = Ebean.getReference(Product.class, 1);
		Product product3 = Ebean.getReference(Product.class, 3);

		Order order = new Order();
		order.setCustomer(customer);

		List<OrderDetail> details = new ArrayList<OrderDetail>();
		details.add(new OrderDetail(product1, 3));
		details.add(new OrderDetail(product3, 40));
		order.setDetails(details);

		Ebean.save(order);
	}
}
