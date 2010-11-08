package de.db12.common.server;

import com.google.inject.servlet.ServletModule;

public class GuiceServletModule extends ServletModule {
	@Override
	protected void configureServlets() {
		super.configureServlets();
		serve("/test/*").with(GuiceServlet.class);
	}
}
