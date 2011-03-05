package de.db12.common.web.guice;

import javax.servlet.ServletContextEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import de.db12.common.guice.log.LoggingModule;

public class GuiceServletConfig extends GuiceServletContextListener {
	private final static Logger log = LoggerFactory.getLogger(GuiceServletConfig.class);

	public GuiceServletConfig() {
		log.warn("init config");
	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		super.contextInitialized(servletContextEvent);
		log.warn("GuiceServletConfig.contextInitialized");
	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		super.contextDestroyed(servletContextEvent);
		log.warn("GuiceServletConfig.contextDestroyed");
	}

	@Override
	protected Injector getInjector() {
		log.warn("getInjector");
		return Guice.createInjector(new LoggingModule(), new JacksonServletModule(), new GuiceServletModule());
	}
}