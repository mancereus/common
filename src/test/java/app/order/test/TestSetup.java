package app.order.test;

import junit.framework.Assert;

import org.junit.Test;

import com.avaje.ebean.Ebean;

import de.scoopgmbh.common.db.data.Order;
import de.scoopgmbh.common.db.setup.Setup;

public class TestSetup {

	@Test
	public void test() {

		Setup.resetData();

		int rc = Ebean.find(Order.class).findRowCount();

		Assert.assertTrue("got orders", rc > 0);

	}
}
