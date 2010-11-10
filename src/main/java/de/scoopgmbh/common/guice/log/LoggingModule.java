package de.scoopgmbh.common.guice.log;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;


public class LoggingModule extends AbstractModule {
	@Override
	protected void configure() {
		// logger
		bindListener(Matchers.any(), new Slf4jTypeListener());
	}

}
