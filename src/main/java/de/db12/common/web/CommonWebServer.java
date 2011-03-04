package de.db12.common.web;

import java.util.EnumSet;
import javax.servlet.DispatcherType;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.bio.SocketConnector;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.webapp.WebAppContext;
import com.google.inject.servlet.GuiceFilter;
import de.db12.common.web.guice.GuiceServletConfig;

public class CommonWebServer {

	public static void main(String[] args) {

		Server server = new Server();
		SocketConnector connector = new SocketConnector();

		// Set some timeout options to make debugging easier.
		connector.setMaxIdleTime(1000 * 60 * 60);
		connector.setSoLingerTime(-1);
		connector.setPort(8080);
		connector.setAcceptors(10);
		server.setConnectors(new Connector[] { connector });

		ServletContextHandler context = new ServletContextHandler(server, "/", ServletContextHandler.NO_SESSIONS);
		context.addFilter(GuiceFilter.class, "/*", EnumSet.allOf(DispatcherType.class));
		context.addEventListener(new GuiceServletConfig());
		// context.addServlet(EmptyServlet.class, "/");
		context.addServlet(DefaultServlet.class, "/");

		WebAppContext war = new WebAppContext();
		// context.setDescriptor(webapp+"/WEB-INF/web.xml");
		war.setResourceBase("war");
		war.setContextPath("/");
		war.setParentLoaderPriority(true);

		server.setHandler(war);
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
