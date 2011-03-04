package de.db12.common.web;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import org.slf4j.Logger;
import de.db12.common.guice.log.InjectLogger;

/**
 * A minimal Jersey Resource. Note that this class is a POJO - it does not need to know anything about Guice. Although, if we wanted to Inject members using
 * Guice, we could!
 */
@Path("/sample")
public class SampleResource {
	@InjectLogger
	Logger log;

	@GET
	@Produces("text/plain")
	@Path("{who}")
	public String sayGreeting(@PathParam("who") String name) {
		log.debug("request in");
		return "Greetings, " + name + "!";
	}
}