package de.db12.common.web;

import javax.servlet.ServletContextEvent;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

public class GuiceServletListener extends GuiceServletContextListener {

	public GuiceServletListener() {
	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		super.contextInitialized(servletContextEvent);
	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		super.contextDestroyed(servletContextEvent);
	}

	@Override
	protected Injector getInjector() {
		return CommonWebServer.injector;
	}
}