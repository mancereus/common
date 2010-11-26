package de.scoopgmbh.bms.core;

import java.net.URL;

import org.slf4j.Logger;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.Update;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

import de.scoopgmbh.bms.core.importing.SomeTest;
import de.scoopgmbh.common.appl.DozerBeanMappingModule;
import de.scoopgmbh.common.appl.EbeanConfigurationModule;
import de.scoopgmbh.common.appl.SetupModule;
import de.scoopgmbh.common.guice.log.InjectLogger;
import de.scoopgmbh.common.guice.log.LoggingModule;

public class SetupService {
	@InjectLogger
	Logger log;

	URL fStreckenNetz = getResourceURL("/basisdaten/streckenUndRegeln.csv");
	URL fGangLinien = getResourceURL("/basisdaten/100823-gap.csv.gz");
	URL fRechenRegeln = getResourceURL("/basisdaten/streckenUndRegeln.csv");
	URL fEventKalender = getResourceURL("/basisdaten/eventKalender.csv");
	URL fBaseData = getResourceURL("/sql_scripts/SETUP.sql");
	URL feventTypen = getResourceURL("/basisdaten/eventTypen.csv");
	URL fIndicies = getResourceURL("/sql_scripts/CREATE_INDEXES.sql");
	URL fUpdateScript = getResourceURL("/sql_scripts/DATA_UPDATE.sql");
	URL fZuordnungen = SomeTest.class
			.getResource("/basisdaten/zuordnungen.csv");

	private EbeanServer ebean;

	@Inject
	public SetupService(EbeanServer ebean) {
		this.ebean = ebean;
	}

	URL getResourceURL(String name) {
		return getClass().getResource(name);
	}

	public void deleteAll(Class clazz) {
		String query = "delete from " + clazz.getSimpleName();
		Update deleteAll = Ebean.createUpdate(clazz, query);
		deleteAll.execute();
	}

	// @Override
	public void removeStrassenNetz() {
		StringBuilder query = new StringBuilder();
		runTransaction("DELETE FROM ABSCHNITTE", "DELETE FROM Netzpunkte",
				"DELETE FROM STrassen");

	}

	private void runTransaction(String... queries) {
		for (String query : queries) {
			log.info(query);
			ebean.createSqlUpdate(query).execute();
		}
	}

	// @Override
	public void removeEventKalender() {
		runTransaction("DELETE FROM EVENTS");
	}

	// @Override
	public void removeGanglinien() {
		runTransaction("DELETE FROM REFWERTE", "DELETE FROM REFGANGLINIEN",
				"DELETE FROM MESSQUERSCHNITTE", "DELETE FROM EVENT_KLASSEN");
	}

	// @Override
	public void removeRechenRegeln() {
		runTransaction("DELETE FROM RECHENREGEL");
	}

	// @Override
	public void removeBaseData() {

		runTransaction("DELETE FROM ROLLEN_LOGINS", "DELETE FROM ROLLEN",
				"DELETE FROM LOGINS", "DELETE FROM ANGESTELLTE",
				"DELETE FROM FREMDFIRMA_AM", "DELETE FROM AUTORITAETEN",
				"DELETE FROM KNOTENTYPEN", "DELETE FROM RSAPLAENE");

	}

	public void removeEventKlassen() {
		runTransaction("DELETE FROM EVENT_KLASSEN");
	}

	public void removeZuordnungen() {
		runTransaction("DELETE FROM ZUORDNUNG");
	}

	// public void importStrassenNetz() {
	// this.removeStrassenNetz();
	// StreckenNetzImportService isns =
	// .getStreckenNetzImportService();
	// try {
	// isns.importStreckenNetzCSV(fStreckenNetz, 1l);
	// } catch (Exception e) {
	// e.printStackTrace(System.err);
	// }
	// this.updateStrecken();
	// }

	//
	// // @Override
	// public void importEventKlassen() {
	// GangLinienImportService glis = ctx.getImportingContext()
	// .getGangLinineImportServer();
	// try {
	// glis.importEventKlassenCSV(feventTypen, 1l);
	// } catch (Exception e) {
	// e.printStackTrace(System.err);
	// }
	// }
	//
	// // @Override
	// public void importEventKalender() {
	// this.removeEventKalender();
	// GangLinienImportService glis = ctx.getImportingContext()
	// .getGangLinineImportServer();
	// try {
	// glis.importEventKalenderCSV(fEventKalender, 1l);
	// } catch (Exception e) {
	// e.printStackTrace(System.err);
	// }
	//
	// }
	//
	// // @Override
	// public void importGanglinien() {
	// GangLinienImportService glis = ctx.getImportingContext()
	// .getGangLinineImportServer();
	// try {
	// glis.importGangLinieCSV(fGangLinien, 1l);
	// } catch (Exception e) {
	// e.printStackTrace(System.err);
	// }
	// }
	//
	// // @Override
	// public void importRechenRegeln() {
	// this.removeRechenRegeln();
	// RechenRegelImportService rris = ctx.getImportingContext()
	// .getRechenRegelImportService();
	// try {
	// rris.importRechenRegelCSV(fRechenRegeln, 1l);
	// } catch (Exception e) {
	// e.printStackTrace(System.err);
	// }
	// }
	//
	// // @Override
	// public void importBaseData() {
	// this.removeBaseData();
	// String s = null;
	// BufferedReader br;
	// StringBuilder sb = new StringBuilder();
	// try {
	// br = new BufferedReader(new InputStreamReader(
	// fBaseData.openStream()));
	// while (null != (s = br.readLine())) {
	// sb.append(s);
	// }
	// ctx.getDbLayerContext().getSupervisorUtil().execBatch(sb);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	//
	// public void importZuordnungen() {
	// this.removeZuordnungen();
	// ImportCapable zis = ctx.getImportingContext()
	// .getZuordnungsImportService();
	// try {
	// zis.importCsv(fZuordnungen, 1l);
	// } catch (Exception e) {
	// e.printStackTrace(System.err);
	// }
	// }
	//
	// public void createIndicies() {
	// String s = null;
	// BufferedReader br = null;
	// StringBuilder sb = new StringBuilder();
	// try {
	// br = new BufferedReader(new InputStreamReader(
	// fIndicies.openStream()));
	// while (null != (s = br.readLine())) {
	// sb.append(s);
	// }
	// ctx.getDbLayerContext().getSupervisorUtil().execBatch(sb);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	//
	// // @Override
	// public void importAll() {
	// this.importBaseData();
	// this.importStrassenNetz();
	// this.importGanglinien();
	// this.importEventKlassen();
	// this.importEventKalender();
	// this.importRechenRegeln();
	// this.importZuordnungen();
	// }
	//
	// private void updateStrecken() {
	// String s = null;
	// StringBuilder sb = new StringBuilder();
	// BufferedReader br;
	// try {
	// br = new BufferedReader(new InputStreamReader(
	// fUpdateScript.openStream()));
	// while (null != (s = br.readLine())) {
	// sb.append(s);
	// }
	// ctx.getDbLayerContext().getSupervisorUtil().execDML(sb);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }

	// @Override
	public void deleteAll() {
		this.removeZuordnungen();
		this.removeBaseData();
		this.removeEventKalender();
		this.removeEventKlassen();
		this.removeGanglinien();
		this.removeRechenRegeln();
		this.removeStrassenNetz();
	}

	public static void main(String[] args) {
		Injector injector = Guice.createInjector(new DozerBeanMappingModule(),
				new EbeanConfigurationModule(), new LoggingModule(),
				new SetupModule());
		SetupService setup = injector.getInstance(SetupService.class);
		setup.deleteAll();
	}
}
