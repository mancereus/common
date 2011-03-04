package de.db12.common.web.guice;

import javax.servlet.ServletContextEvent;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

public class GuiceServletConfig extends GuiceServletContextListener {
	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		super.contextInitialized(servletContextEvent);
		System.out.println("GuiceServletConfig.contextInitialized");
	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		super.contextDestroyed(servletContextEvent);
		System.out.println("GuiceServletConfig.contextDestroyed");
	}

	@Override
	protected Injector getInjector() {
		return Guice.createInjector(new JacksonServletModule(), new GuiceServletModule());
	}
}