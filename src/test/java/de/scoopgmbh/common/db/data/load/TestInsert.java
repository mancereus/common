package de.scoopgmbh.common.db.data.load;

import java.util.List;

import org.junit.Test;

import com.avaje.ebean.Ebean;

import de.scoopgmbh.common.db.data.Product;

public class TestInsert {

	@Test
	public void test() {

		for (int i = 0; i < 100; i++) {

			Product p = new Product();
			p.setName("Chair");
			p.setSku("C001");

			Ebean.save(p);
		}
		List<Product> list = Ebean.find(Product.class).findList();
		for (Product prod : list) {
			prod.setName(prod.getName() + "_1");
		}
		Ebean.save(list);
	}
}
