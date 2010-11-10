package de.scoopgmbh.common.web;

import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

import com.sun.jersey.guice.JerseyServletModule;

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
