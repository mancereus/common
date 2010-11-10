package de.scoopgmbh.common.db.setup;

import com.avaje.ebean.Ebean;

import de.scoopgmbh.common.db.data.Address;
import de.scoopgmbh.common.db.data.Customer;
import de.scoopgmbh.common.db.data.Order;
import de.scoopgmbh.common.db.data.OrderDetail;

public class DeleteData {

	public static void main(String[] args) {
		DeleteData me = new DeleteData();
		me.deleteAll();
	}

	public void deleteAll() {
		Ebean.beginTransaction();
		try {
			// orm update use bean name and bean properties
			Ebean.createUpdate(OrderDetail.class, "delete from orderDetail")
					.execute();

			Ebean.createUpdate(Order.class, "delete from order").execute();

			Ebean.createUpdate(Customer.class, "delete from Customer")
					.execute();

			Ebean.createUpdate(Address.class, "delete from address").execute();

			// sql update uses table and column names
			Ebean.createSqlUpdate("delete from o_country").execute();

			Ebean.createSqlUpdate("delete from o_product").execute();

			Ebean.commitTransaction();

		} finally {
			Ebean.endTransaction();
		}
	}
}
