package de.db12.common.server.guice.db;

import com.avaje.ebean.EbeanServer;
import com.google.inject.Binder;
import com.google.inject.Module;

public class EbeanConfigurationModule implements Module {

	@Override
	public void configure(Binder binder) {
		binder.bind(EbeanServer.class).toProvider(EbeanServerProvider.class);

	}

}
