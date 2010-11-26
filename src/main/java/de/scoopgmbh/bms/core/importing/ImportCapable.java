package de.scoopgmbh.bms.core.importing;

import java.net.URL;
import java.rmi.server.ExportException;

import de.scoopgmbh.bms.core.exceptions.ImportException;

/**
 * 
 * @author hirtech
 *
 */
public interface ImportCapable {

	/**
	 * Importiert eine CSV-Datei. Konkrete Unterklassen müssen die entsprechende Logik
	 * zum Importieren hierin abbilden.
	 * 
	 * @param csv Die CSV-Datei
	 * @param loginId ID des Logins, der die Methode aufgerufen hat
	 * @throws ImportException falls beim Importieren ein Fehler auftrat
	 */
	public void importCsv(URL csv, long loginId) throws ImportException;
	
	/**
	 * Gibt die Fehlermeldungen zurück, die beim Importieren auftraten.
	 * 
	 * @return 
	 */
	public StringBuilder getErrosMessages();
	
	/**
	 * Gibt an, ob seit dem letzten Durchlauf Fehler auftraten.
	 * 
	 * @return
	 */
	public boolean hasImportErros();
	
	/**
	 * Gibt die Anzahl der Aufruf von <code>importCsv()</code> zur�ck.
	 * 
	 * @return
	 */
	public boolean getRunCount();
	
	/**
	 * Speichert die fehlerhaften Zeilen des CSV-Import in einer Datei.
	 * 
	 * @param csv
	 * @throws ExportException
	 */
	public void saveInvalidCSVRecords(URL csv) throws ExportException;
}
