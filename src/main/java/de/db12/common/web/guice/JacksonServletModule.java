package de.db12.common.web.guice;

import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import de.db12.common.web.json.JacksonResource;
import de.db12.common.web.json.SampleResource;

public class JacksonServletModule extends JerseyServletModule {

	private final static Logger log = LoggerFactory.getLogger(JacksonServletModule.class);

	@Override
	protected void configureServlets() {
		log.info("configure Jackson");
		// super.configureServlets();
		/* bind the REST resources */
		bind(SampleResource.class);
		bind(JacksonResource.class);
		serve("/json").with(GuiceContainer.class);

		/* bind jackson converters for JAXB/JSON serialization */
		bind(MessageBodyReader.class).to(JacksonJsonProvider.class);
		bind(MessageBodyWriter.class).to(JacksonJsonProvider.class);

	}
}
