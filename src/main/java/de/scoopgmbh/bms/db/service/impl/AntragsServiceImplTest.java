package de.scoopgmbh.bms.db.service.impl;

import java.util.Date;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.EbeanServer;
import com.google.inject.Guice;
import com.google.inject.Injector;

import de.scoopgmbh.bms.db.model.Angestellte;
import de.scoopgmbh.bms.db.model.BaustellenAntrag;
import de.scoopgmbh.bms.db.model.Login;
import de.scoopgmbh.common.appl.DozerBeanMappingModule;
import de.scoopgmbh.common.appl.EbeanConfigurationModule;
import de.scoopgmbh.common.appl.SetupModule;
import de.scoopgmbh.common.guice.log.LoggingModule;

/**
 * The class <code>AntragsServiceImplTest</code> contains tests for the class
 * {@link <code>AntragsServiceImpl</code>}
 * 
 * @pattern JUnit Test Case
 * 
 * @generatedBy CodePro at 26.11.10 09:49
 * 
 * @author mschmitt
 * 
 * @version $Revision$
 */
public class AntragsServiceImplTest extends TestCase {

	private AntragsServiceImpl impl;
	private EbeanServer ebean;

	/**
	 * Construct new test instance
	 * 
	 * @param name
	 *            the test name
	 */
	public AntragsServiceImplTest(String name) {
		super(name);
	}

	/**
	 * Perform pre-test initialization
	 * 
	 * @throws Exception
	 * 
	 * @see TestCase#setUp()
	 */
	@Before
	protected void setUp() throws Exception {
		super.setUp();
		Injector injector = Guice.createInjector(new DozerBeanMappingModule(),
				new EbeanConfigurationModule(), new LoggingModule(),
				new SetupModule());
		impl = injector.getInstance(AntragsServiceImpl.class);
	}

	@Test
	public void testAddFormular() {
		Login login = Ebean.find(Login.class, 1l);
		Angestellte ang = Ebean.find(Angestellte.class,
				login.getAngestellteId());
		// add test code here
		BaustellenAntrag sf = new BaustellenAntrag();
		sf.setDatum(new Date());
		sf.setEditorId(login.getId());
		sf.setIdAnsprechpartner(ang);
		sf.setIdAntragssteller(ang);
		sf.setIdBauleiter(ang);
		impl.addFormular(sf, login.getId());
	}

}