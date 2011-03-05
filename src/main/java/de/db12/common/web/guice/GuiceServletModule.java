package de.db12.common.web.guice;

import com.google.inject.servlet.ServletModule;
import de.db12.common.web.GuiceServlet;

public class GuiceServletModule extends ServletModule {
	@Override
	protected void configureServlets() {
		super.configureServlets();
		serve("/test/*").with(GuiceServlet.class);
		serve("/sample").with(GuiceServlet.class);
	}
}
