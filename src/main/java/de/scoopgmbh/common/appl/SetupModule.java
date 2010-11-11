package de.scoopgmbh.common.appl;

import com.google.inject.Binder;
import com.google.inject.Module;

import de.scoopgmbh.common.db.setup.Setup;

public class SetupModule implements Module {

	@Override
	public void configure(Binder arg0) {
		Setup.resetData();
	}

}
