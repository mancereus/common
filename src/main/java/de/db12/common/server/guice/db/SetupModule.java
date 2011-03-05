package de.db12.common.server.guice.db;

import com.google.inject.Binder;
import com.google.inject.Module;
import de.db12.common.db.setup.Setup;

public class SetupModule implements Module {

	@Override
	public void configure(Binder arg0) {
		Setup.resetData();
	}

}
