package de.scoopgmbh.bms.core.importing.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import de.scoopgmbh.bms.core.exceptions.ImportException;

/**
 * Abstrakte Basisklasse zum Laden von CSV-Dateien. Clients m�ssen diese Klasse
 * beerben und möglichst im Konstruktor sich selbst über <code>setExpectedCsvColumns()</code>
 * eine LinkedHashSet von ColumnExpectations setzen. Jene ColumnExpectations definieren
 * die Spalten, wie sie in der Datei erwartet werden.<br/>
 * <br/>
 * Über den Aufruf von <code>loadCSV(File csv)</code> kann eine konkrete
 * CSV-Datei geladen werden. 
 * Über <code>getData()</code> kann auf die validen Zeilen
 * der CSV zugegriffen werden. Über <code>getDiscardedData()</code> kann
 * auf die fehlerhaften Zeilen der CSV zugegriffen werden. Fehlerhafte und valide Zeilen
 * sind disjunkt.<br/>
 * <br/>
 * Aufeinanderfolge Aufrufe von <code>loadCSV(File csv)</code> verwerfen die
 * Ergebnisse vorheriger Aufrufe. Sollte zuvor jedoch
 * <code>getData()</code> aufgerufen worden sein und jene Referenz in einer Variablen
 * abgespeichert, so ist das Ergebnis weiterhin g�ltig.<br/>
 * <br/>
 * Über <code>getCSVRowIterator()</code> können Clients Zugriff auf ein 
 * CSVRowIterator-Objekt erhalten, welcher die validen Zeilen der CSV-Datei 
 * als CSVRow-Objekte zurückgibt. Ist seit dem Erstellen des Iterators
 * erneut <code>loadCSV(File csv)</code> aufgerufen worden sein, wirft <code>hasNext()</code>
 * eine RuntimeException.
 * 
 * 
 * @author hirtech
 * @version 1.0
 *
 */
public abstract class AbstractCSVLoader {

	/**
	 * Menge von erwarteten CSV-Spalten, die unbedingt in der CSV-Datei
	 * vorhanden sein m�ssen, damit diese richtig importiert werden kann.
	 * 
	 * DIE REIHENFOLGE DER SPALTEN IN DER CSV-DATEI IST UNERHEBLICH.
	 */
	private LinkedHashSet<CSVColumnExpectation<?>> expectedCsvColumns = null;

	/**
	 * Das in der CSV Datei verwendete Trennzeichen zwischen den Spalten.
	 */
	private String delimiter = ";";
	
	/**
	 * Liste mit allen in der CSV Datei vorgefundenen Zeilen.
	 */
	private List<String> table = null;
	
	/**
	 * Liste mit alle kurrupten Zeilen der CSV-Datei.
	 */
	private List<String> discarded = null;
	
	/**
	 * Liste mit alle validen Zeilen der CSV-Datei.
	 */
	private List<String> allowed = null;
	
	/**
	 * List von CSVRowIterator, die seit dem letzten Aufruf von 
	 * <code>loadCSV()</code> erstellt wurden.
	 */
	private List<CSVRowIterator> criList = new LinkedList<CSVRowIterator>();
	
	/**
	 * Speichert die Position von erwarteten Spalten der CSV-Datei.
	 * Key = Name der Spalte
	 * Value = Position in der CSV; beginnt bei 0
	 */
	private Map<String, Integer> headerMapping;
	
	private List<String> wrongLines = new LinkedList<String>();
	
	private int columnsInCSVCount = 0;
	
	public AbstractCSVLoader() {}
	
	/**
	 * Läd eine CSV Datei in den Speicher und überprüft den Inhalt. Sollten Fehler bei der
	 * Verarbeitung entstehen, so wir eine ImportException geworfen.<br/>
	 * <br/>
	 * Kehrt die Methode ohne Exception zum Aufrufer zurück, kann über getData() und 
	 * getDiscardedData() sowohl auf die validen als auch die kurrupten CSV-Zeilen
	 * zugegriffen werden.
	 * 
	 * @param csv
	 * @throws ImportException
	 */
	public void loadCSV(URL csv) throws ImportException {
		if (expectedCsvColumns==null || expectedCsvColumns.size()==0)
			throw new ImportException("CSV-Dateiformat unbekannt. Es wurden keine erwarteten Spalten definiert.");
		
		// 0. Iteratoren benachrichtigen
		this.makeIteratorsInvalid();
		
		// 1. Datenstrukturen vorbereiten
		table = new LinkedList<String>();
		allowed = new LinkedList<String>();
		discarded = new LinkedList<String>();
		
		// 2. Parameter überprüfen
		if (csv==null) throw new ImportException("Keine Datei zum Importieren vorhanden.");
		
		// 3. Datei öffnen, Inhalt laden und schließen
		openLoadAndClose(csv);
		
		// 4. Inhalt prüfen
		checkLoadedData();
		
	}
	
	/**
	 * Läd eine CSV Datei in den Speicher und überprüft den Inhalt. Sollten Fehler bei der
	 * Verarbeitung entstehen, so wir eine ImportException geworfen.<br/>
	 * <br/>
	 * Kehrt die Methode ohne Exception zum Aufrufer zurück, können über getData() und
	 * getDiscardedData() die validen und nicht validen Datens�tze zurückgegeben werden.<br/>
	 * 
	 * @param csv File-Objekt, dass auf die CSV-Datei zeigt.
	 * @param delimiter Delimiter der beim Laden benutzt werden soll.
	 * @throws ImportException
	 */
	public void loadCSV(URL csv, String delimiter) throws ImportException {
		this.delimiter = delimiter;
		this.loadCSV(csv);
	}
	
	/**
	 * Liefert die gelesenen und auf Validität geprüfen Zeilen der CSV-Datei zurück.
	 * 
	 * @return
	 */
	public List<String> getData() {
		return allowed;
	}
	
	/**
	 * Liefert die gelesenen aber als nicht valide eingestufften Zeilen
	 * der CSV-Datei zur�ck.
	 * @return
	 */
	public List<String> getDiscardedData() {
		return discarded;
	}
	
	/**
	 * Erstellt und liefert einen neuen CSVRowIterator
	 * @return
	 */
	public CSVRowIterator getCSVRowIterator() {
		CSVRowIterator cri = new CSVRowIterator();
		this.criList.add(cri);
		return cri;
	}
	
	/**
	 * Müssen vor Aufruf von loadCSV gesetzt werden.
	 * 
	 * @param expectedCsvColumns
	 */
	protected void setExpectedCsvColumns(
			LinkedHashSet<CSVColumnExpectation<?>> expectedCsvColumns) {
		this.expectedCsvColumns = expectedCsvColumns;
	}

	private void openLoadAndClose(URL csv) throws ImportException {
		// 1. Datei öffnen
		BufferedReader csvReader = openFile(csv);
		// 2. Inhalt laden
		loadContents(csvReader);
		// 3. Datei schließen
		closeFile(csvReader);
	}
	
	/**
	 * Erstellt einen BufferedReader und verbindet ihn mit der CSV Datei.
	 * 
	 * @param csv
	 * @return 
	 * @throws ImportException
	 */
	private BufferedReader openFile(URL csv) throws ImportException {
		BufferedReader csvReader = null;
		try {
			InputStream is = csv.openStream();
			if (csv.getFile().endsWith(".gz"))
				is = new GZIPInputStream(is);
			csvReader = new BufferedReader(new InputStreamReader(is,"ISO-8859-1"));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ImportException("CSV Datei konnte nicht gelesen werden.");
		}
		return csvReader;
	}
	
	/**
	 * Läd den gesamten Inhalt der CSV Datei in den Speicher.
	 * @param reader
	 */
	private void loadContents(BufferedReader reader) throws ImportException {
		
		// 1. Header auslesen und gegen Erwartung mappen
		String[] header = readHeader(reader);
		mapHeadersToExpectations(header);

		// 2. Daten auslesen und zwischenspeichern 
		String line = null;
		while (null!=(line=readLine(reader))) {
			this.putLineToTable(line);
		}
	}

	/**
	 * Schließt den BufferedReader.
	 * 
	 */
	private void closeFile(BufferedReader csvReader) {
		try {
			csvReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Liest die Spalten aus der CSV-Datei aus.
	 * 
	 * @param reader
	 * @return
	 * @throws ImportException
	 */
	private String[] readHeader(BufferedReader reader) throws ImportException {
		String headerRow = null;
		// 1. erste Zeile aus CSV lesen
		try {
			headerRow = readLine(reader);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ImportException("CSV Spalten konnten nicht gelesen werden. CSV-Datei leer?");
		}
		
		// 2. gelesene Zeile überprüfen
		if (headerRow==null || headerRow.length()==0) 
			throw new ImportException("CSV Spalten konnten nicht gelesen werden. Kein Header gefunden.");
		
		// 3. Spalten aus Zeile extrahieren
		String[] headers = headerRow.split(this.delimiter);
		columnsInCSVCount = headers.length;
		return headers;
	}
	
	
	/**
	 * Mapped die Spalten aus der CSV-Datei gegen die erwarteten Spalten.
	 * 
	 * @throws ImportException 
	 * 
	 */
	private void mapHeadersToExpectations(String[] csvHeader) throws ImportException {
		Map<String, Integer> ret = new HashMap<String, Integer>(expectedCsvColumns.size());
		
		// 1. Anzahl der Spalten prüfen
		if (csvHeader.length<expectedCsvColumns.size()) 
			throw new ImportException("CSV enthält nicht genug Spalten ("+csvHeader.length+"). " +
					"Erwartet ("+expectedCsvColumns.size()+"): "
					+ expectedCsvColumns.toString());
		
		// 2. Gefundene und erwartete Spalten vergleichen
		String col = null;
		for (int i = 0; i<csvHeader.length; i++) {
			col = csvHeader[i].trim();
			if (expectedCsvColumns.contains(new CSVColumnExpectation<String>(col, String.class, false, 0, 0))) {
				if (ret.containsKey(col)) 
					throw new ImportException("CSV nicht importiert. Doppelte Spalte: " + col);
				ret.put(col, i);
			}
		}
		
		// 3. prüfen ob auch alle erwarteten Spalten in der Rückgabe enthalten sind
		for (CSVColumnExpectation<?> s : expectedCsvColumns) {
			if (!ret.containsKey(s.toString()))
				throw new ImportException("CSV nicht importiert. Spalte nicht gefunden: " + s);
		}
		
		this.headerMapping = ret;
	}
	
	/**
	 * Fügt eine gelesen Zeile aus der CSV-Datei in die Tabelle ein.
	 * @param line
	 * @throws ImportException
	 */
	private void putLineToTable(String line) throws ImportException {
		try {
			this.table.add(line);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ImportException("Fehler beim Verarbeiten einer Zeile.");
		}
		
	}
	
	/**
	 * Liest eine einzige Zeile aus der CSV-Datei.
	 * 
	 * @param reader
	 * @return
	 * @throws ImportException
	 */
	private String readLine(BufferedReader reader) throws ImportException {
		try {
			return reader.readLine();
		} catch (IOException e) {
			throw new ImportException("Fehler beim Lesen von der CSV-Datei");
		}
	}
	
	/**
	 * Überprüft die gesamte geladene CSV-Datei und sortiert Datensätze
	 * entsprechend ihrer Validität in die Gruppe für "gute" und "schlechte" Daten.
	 * 
	 * @throws ImportException
	 */
	private void checkLoadedData() throws ImportException {
		boolean rowOk = true;
		for(String s : this.table) {
			rowOk = isRowValid(s);
			if (!rowOk)
				this.discarded.add(s);
			else
				this.allowed.add(s);
		}
	}
	
	private boolean isRowValid(String line) throws ImportException {
		String[] fields = line.split(this.delimiter, columnsInCSVCount);
		boolean isRowOk = false;
		
		if (fields.length<expectedCsvColumns.size()) {
			wrongLines.add(line);
			return isRowOk;
		} 
		
		try {
			isRowOk = true;
			for(CSVColumnExpectation<?> e : expectedCsvColumns) {
				isRowOk &= e.isValid(fields[this.headerMapping.get(e.toString())].trim());
				if (!isRowOk) {
					// FIXME vernünftige Fehlerbehandlung !!!
					System.out.println("Fehler in Spalte: " + e.getHeaderName() +"; Wert: " + fields[this.headerMapping.get(e.toString())].trim());
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ImportException("Fehler beim Verarbeiten einer Zeile.");
		}
		
		return isRowOk;
	}
	
	private void makeIteratorsInvalid() {
		if (this.criList==null)
			this.criList = new LinkedList<CSVRowIterator>();
		if (this.criList.size()==0) return;
		for (CSVRowIterator cri : criList)
			cri.setInvalid();
		this.criList.clear();
	}
	
	
	/**
	 * Interator zum Iterieren über eine Liste von Zeilen einer CSV-Datei.
	 * Datenbasis ist <code>java.util.List&lt;String&gt;</code>. In dieser Liste
	 * befinden sich alle validen Zeilen der CSV-Datei. Ein Aufruf von <code>
	 * remove()</code> löscht in der Datenbasis. Wurde die Datenbasis seit Erstellung
	 * des Iterators geändert, so werfen alle weiteren Aufrufe von <code>hasNext()</code>
	 * eine RuntimeException.
	 * 
	 * @author hirtech
	 *
	 */
	public class CSVRowIterator implements Iterator<CSVRow> { 
		
		private int currentIdx = 0;
		private boolean valid = true;
		
		/* @Override */
		public boolean hasNext() throws RuntimeException {
			if (!valid)
				throw new RuntimeException("Iterator ungültig. Datenbasis hat sich seit Erstellung des Iterators geändert.");
			return (currentIdx < allowed.size());
		}

		/* @Override */
		public CSVRow next() {
			String[] fields = allowed.get(currentIdx++).split(delimiter, columnsInCSVCount);
			
			CSVRow csvr = new CSVRow();
			for(CSVColumnExpectation<?> e : expectedCsvColumns) {
				csvr.put(e.toString(), new CSVRow.ValueHolder(fields[headerMapping.get(e.toString())], e));
			}
			return csvr;
		}

		/* @Override */
		public void remove() {
			allowed.remove(--currentIdx);
		}
		
		public int getItemsCountRemaining() {
			return allowed.size();
		}
		
		private void setInvalid() {
			this.valid = false;
		}
	}
}
