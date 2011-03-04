package de.db12.common.web.guice;

import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import com.sun.jersey.guice.JerseyServletModule;
import de.db12.common.web.JacksonResource;
import de.db12.common.web.SampleResource;

public class JacksonServletModule extends JerseyServletModule {
	@Override
	protected void configureServlets() {
		// super.configureServlets();

		/* bind the REST resources */
		bind(SampleResource.class);
		bind(JacksonResource.class);

		/* bind jackson converters for JAXB/JSON serialization */
		bind(MessageBodyReader.class).to(JacksonJsonProvider.class);
		bind(MessageBodyWriter.class).to(JacksonJsonProvider.class);

	}
}
