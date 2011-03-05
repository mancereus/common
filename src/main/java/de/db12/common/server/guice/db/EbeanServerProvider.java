package de.db12.common.server.guice.db;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.EbeanServer;
import com.google.inject.Provider;

public class EbeanServerProvider implements Provider<EbeanServer> {

	@Override
	public EbeanServer get() {
		return Ebean.getServer(null);
	}

}
