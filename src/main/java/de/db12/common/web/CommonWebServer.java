package de.db12.common.web;

import java.util.EnumSet;
import javax.servlet.DispatcherType;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.bio.SocketConnector;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceFilter;
import de.db12.common.server.guice.log.LoggingModule;
import de.db12.common.web.guice.GuiceServletModule;
import de.db12.common.web.guice.JacksonServletModule;

public class CommonWebServer {

	private final static Logger log = LoggerFactory.getLogger(CommonWebServer.class);
	public static Injector injector;

	public static void main(String[] args) {

		injector = Guice.createInjector(new LoggingModule(), new JacksonServletModule(), new GuiceServletModule());

		Server server = new Server();
		SocketConnector connector = new SocketConnector();

		// Set some timeout options to make debugging easier.
		connector.setMaxIdleTime(1000 * 60 * 60);
		connector.setSoLingerTime(-1);
		connector.setPort(8080);
		connector.setAcceptors(10);
		server.setConnectors(new Connector[] { connector });

		// ServletContextHandler context = new ServletContextHandler(server, "/test");
		// context.addFilter(GuiceFilter.class, "/*", EnumSet.allOf(DispatcherType.class));
		// context.addEventListener(new GuiceServletListener());
		// context.addServlet(EmptyServlet.class, "/empty");
		// context.addServlet(DefaultServlet.class, "/");

		WebAppContext war = new WebAppContext();
		// context.setDescriptor(webapp+"/WEB-INF/web.xml");
		war.setResourceBase("war");
		war.setContextPath("/");
		war.setParentLoaderPriority(true);
		war.addFilter(GuiceFilter.class, "/*", EnumSet.allOf(DispatcherType.class));
		war.addEventListener(new GuiceServletListener());
		// IMPORTANT: add default servelet needed to initialize config !!
		war.addServlet(DefaultServlet.class, "/default");
		server.setHandler(war);
		log.info(war.dump());
		// WebAppContext bb = new WebAppContext();
		// bb.setServer(server);
		// bb.setContextPath("/common");
		// bb.setWar("WebContent");
		// server.addHandler(bb);

		// START JMX SERVER
		// MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
		// MBeanContainer mBeanContainer = new MBeanContainer(mBeanServer);
		// server.getContainer().addEventListener(mBeanContainer);
		// mBeanContainer.start();

		try {
			System.out.println(">>> STARTING EMBEDDED JETTY SERVER, PRESS ANY KEY TO STOP");
			server.start();
			System.in.read();
			System.out.println(">>> STOPPING EMBEDDED JETTY SERVER");
			// while (System.in.available() == 0) {
			// Thread.sleep(5000);
			// }
			server.stop();
			server.join();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(100);
		}
	}
}
