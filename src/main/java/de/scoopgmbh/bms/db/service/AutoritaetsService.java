package de.scoopgmbh.bms.db.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import de.scoopgmbh.bms.db.exceptions.DBLayerException;
import de.scoopgmbh.bms.db.exceptions.EntityNotDeletedException;
import de.scoopgmbh.bms.db.exceptions.EntityNotInsertedException;
import de.scoopgmbh.bms.db.exceptions.EntityNotUpdatedException;
import de.scoopgmbh.bms.db.model.Angestellte;
import de.scoopgmbh.bms.db.model.AutobahnMeisterei;
import de.scoopgmbh.bms.db.model.AutobahnPolizeiStation;
import de.scoopgmbh.bms.db.model.Autoritaet;
import de.scoopgmbh.bms.db.model.BaustoffBodenPruefAmt;
import de.scoopgmbh.bms.db.model.FremdFirma;
import de.scoopgmbh.bms.db.model.StrassenVerkehrsAmt;

public interface AutoritaetsService {

	// ==============================================================
	// GENERISCH METHODEN FUER ALLE KLASSEN GLEICH - Reihenfolge: CRUD
	// ==============================================================

	/**
	 * Fügt eine neue Autoritaet in die Datenbank ein.
	 * 
	 * @param at
	 *            Entität die eingefügt werden soll.
	 * @param loginId
	 *            ID des Logins, der diese Methode aufruft
	 * @throws DBLayerException
	 *             wenn das Hinzufügen fehl schlägt
	 */
	public void addAutoritaet(Autoritaet at, long loginId)
			throws EntityNotInsertedException;

	/**
	 * Holt eine Autoritaet aus der Datenbank.
	 * 
	 * @param autoritaetId
	 *            ID der designierten Autoritaet
	 * @return die Autoritaet als detached object
	 * @throws EntityNotFoundException
	 *             wenn die Entität nicht existiert
	 */
	public Autoritaet getAutoritaet(long autoritaetId)
			throws EntityNotFoundException;

	/**
	 * Updatet eine Autoritaet in der Datenbank.
	 * 
	 * @param at
	 *            Entität die eingefügt werden soll.
	 * @param loginId
	 *            ID des Logins, der diese Methode aufruft
	 * @throws DBLayerException
	 *             wenn das Updaten fehl schlägt
	 */
	public void updateAutoritaet(Autoritaet at, long loginId)
			throws EntityNotUpdatedException;

	/**
	 * Löscht eine Autoritaet in der Datenbank.
	 * 
	 * @param ff
	 *            Entität die gelöscht werden soll.
	 * @param loginId
	 *            ID des Logins, der diese Methode aufruft
	 * @throws DBLayerException
	 *             wenn das Löschen fehl schlägt
	 */
	public void deleteAutoritaet(Autoritaet at, long loginId)
			throws EntityNotDeletedException;

	// ==============================================================
	// FREMDFRIMEN bezogen - Reihenfolge: CRUD
	// ==============================================================

	/**
	 * Fügt eine neue FremdFirma in die Datenbank ein.
	 * 
	 * @param ff
	 *            Entität die eingefügt werden soll.
	 * @param loginId
	 *            ID des Logins, der diese Methode aufruft
	 * @throws DBLayerException
	 *             wenn das Hinzufügen fehl schlägt
	 */
	public void addFremdFirma(FremdFirma ff, long loginId)
			throws EntityNotInsertedException;

	/**
	 * Holt eine FremdFirma aus der Datenbank ein.
	 * 
	 * @param fremdFirmaId
	 *            ID der designierten Fremdfirma
	 * @return die Fremdfirma als detached object
	 * @throws EntityNotFoundException
	 *             wenn die Entität nicht existiert
	 */
	public FremdFirma getFremdFirma(long fremdFirmaId)
			throws EntityNotFoundException;

	/**
	 * Lierfert aller Fremdfirmen
	 * 
	 * @return Liste von FremdFirma
	 * @throws DBLayerException
	 *             falls ein Fehler im DBLayer auftritt, der so schwerwiegend
	 *             ist, dass das Ergebnis verfäscht ist
	 */
	public List<FremdFirma> getAllFremdfirmen() throws DBLayerException;

	/**
	 * Liefert alle Fremdfirmen, die einer Autobahnmeisterei zugeordnet sind.
	 * Werden keine gefunden, dann ist <code>size()==0</code>.<br/>
	 * 
	 * @param autobahnMeistereiId
	 *            ID der designierten Autobahnmeisterei
	 * @return Liste von FremdFirma; aber nicht null.
	 */
	public List<FremdFirma> getAllFremdFirmenByAutobahnMeisterei(
			long autobahnMeistereiId) throws DBLayerException;

	/**
	 * Updatet eine FremdFirma in der Datenbank.
	 * 
	 * @param ff
	 *            Entität die eingefügt werden soll.
	 * @param loginId
	 *            ID des Logins, der diese Methode aufruft
	 * @throws DBLayerException
	 *             wenn das Updaten fehl schlägt
	 */
	public void updateFremdFirma(FremdFirma ff, long loginId)
			throws EntityNotUpdatedException;

	/**
	 * Ordnet eine Fremdfirma einer Autobahnmeisterei zu.
	 * 
	 * @param autobahnMeistereiId
	 *            ID der designierten AutobahnMeisterei
	 * @param fremdFirmaId
	 *            ID der hinzu zu fügenden Fremdfirma
	 * @param loginId
	 *            ID des Logins, der diese Methode aufruft
	 * @throws DBLayerException
	 *             wenn das Hinzufügen fehl schlägt
	 */
	public void addFremdFirmaToAutobahnMeisterei(long autobahnMeistereiId,
			long fremdFirmaId, long loginId) throws EntityNotInsertedException;

	/**
	 * Löscht eine FremdFirma in der Datenbank.
	 * 
	 * @param ff
	 *            Entität die gelöscht werden soll.
	 * @param loginId
	 *            ID des Logins, der diese Methode aufruft
	 * @throws DBLayerException
	 *             wenn das Löschen fehl schlägt
	 */
	public void deleteFremdFirma(FremdFirma ff, long loginId)
			throws EntityNotDeletedException;

	// ==============================================================
	// BAUSTOFFBODENPRUEFAMT - Reihenfolge: CRUD
	// ==============================================================

	/**
	 * Fügt eine neues BaustoffBodenPruefAmt in die Datenbank ein.
	 * 
	 * @param bbpa
	 *            Entität die eingefügt werden soll.
	 * @param loginId
	 *            ID des Logins, der diese Methode aufruft
	 * @throws DBLayerException
	 *             wenn das Hinzufügen fehl schlägt
	 */
	public void addBaustoffBodenPruefAmt(BaustoffBodenPruefAmt bbpa,
			long loginId) throws EntityNotInsertedException;

	/**
	 * Holt ein BaustoffBodenPruefAmt aus der Datenbank.
	 * 
	 * @param autoritaetId
	 *            ID der designierten Autoritaet
	 * @return BaustoffBodenPruefAmt als detached object
	 * @throws EntityNotFoundException
	 *             wenn die Entität nicht existiert
	 */
	public BaustoffBodenPruefAmt getBaustoffBodenPruefAmt(long autoritaetId)
			throws EntityNotFoundException;

	/**
	 * Lierfert alle BaustoffbodenprüfÄmter
	 * 
	 * @return Liste von BaustoffBodenPruefAmt
	 * @throws DBLayerException
	 *             falls ein Fehler im DBLayer auftritt, der so schwerwiegend
	 *             ist, dass das Ergebnis verfäscht ist
	 */
	public List<BaustoffBodenPruefAmt> getAllBaustoffBodenPruefAemter()
			throws DBLayerException;

	/**
	 * Updatet ein BaustoffBodenPruefAmt in der Datenbank.
	 * 
	 * @param bbpa
	 *            BaustoffBodenPruefAmt die eingefügt werden soll.
	 * @param loginId
	 *            ID des Logins, der diese Methode aufruft
	 * @throws DBLayerException
	 *             wenn das Updaten fehl schlägt
	 */
	public void updateBaustoffBodenPruefAmt(BaustoffBodenPruefAmt bbpa,
			long loginId) throws EntityNotUpdatedException;

	/**
	 * Löscht ein BaustoffBodenPruefAmt in der Datenbank.
	 * 
	 * @param bbpa
	 *            Entität die gelöscht werden soll.
	 * @param loginId
	 *            ID des Logins, der diese Methode aufruft
	 * @throws DBLayerException
	 *             wenn das Löschen fehl schlägt
	 */
	public void deleteBaustoffBodenPruefAmt(BaustoffBodenPruefAmt bbpa,
			long loginId) throws EntityNotDeletedException;

	// ==============================================================
	// AUTOBAHNPOLIZEISTATION - Reihenfolge: CRUD
	// ==============================================================

	/**
	 * Fügt eine neue AutobahnPolizeiStation in die Datenbank ein.
	 * 
	 * @param aps
	 *            Entität die eingefügt werden soll.
	 * @param loginId
	 *            ID des Logins, der diese Methode aufruft
	 * @throws DBLayerException
	 *             wenn das Hinzufügen fehl schlägt
	 */
	public void addAutobahnPolizeiStation(AutobahnPolizeiStation aps,
			long loginId) throws EntityNotInsertedException;

	/**
	 * Holt eine AutobahnPolizeiStation aus der Datenbank.
	 * 
	 * @param autoritaetId
	 *            ID der designierten AutobahnPolizeiStation
	 * @return AutobahnPolizeiStation als detached object
	 * @throws EntityNotFoundException
	 *             wenn die Entität nicht existiert
	 */
	public AutobahnPolizeiStation getAutobahnPolizeiStation(long autoritaetId)
			throws EntityNotFoundException;

	/**
	 * Lierfert alle Autobahnpolizeien
	 * 
	 * @return Liste von AutobahnPolizeiStation
	 * @throws DBLayerException
	 *             falls ein Fehler im DBLayer auftritt, der so schwerwiegend
	 *             ist, dass das Ergebnis verfäscht ist
	 */
	public List<AutobahnPolizeiStation> getAllAutobahnPolizeiStationen()
			throws DBLayerException;

	/**
	 * Updatet eine AutobahnPolizeiStation in der Datenbank.
	 * 
	 * @param aps
	 *            Entität die eingefügt werden soll.
	 * @param loginId
	 *            ID des Logins, der diese Methode aufruft
	 * @throws DBLayerException
	 *             wenn das Updaten fehl schlägt
	 */
	public void updateAutobahnPolizeiStation(AutobahnPolizeiStation aps,
			long loginId) throws EntityNotUpdatedException;

	/**
	 * Löscht eine Autoritaet in der Datenbank.
	 * 
	 * @param aps
	 *            Entität die gelöscht werden soll.
	 * @param loginId
	 *            ID des Logins, der diese Methode aufruft
	 * @throws DBLayerException
	 *             wenn das Löschen fehl schlägt
	 */
	public void deleteAutobahnPolizeiStation(AutobahnPolizeiStation aps,
			long loginId) throws EntityNotDeletedException;

	// ==============================================================
	// STRASSENVERKEHRSAMT - Reihenfolge: CRUD
	// ==============================================================

	/**
	 * Fügt ein neues StrassenVerkehrsAmt in die Datenbank ein.
	 * 
	 * @param asv
	 *            Entität die eingefügt werden soll.
	 * @param loginId
	 *            ID des Logins, der diese Methode aufruft
	 * @throws DBLayerException
	 *             wenn das Hinzufügen fehl schlägt
	 */
	public void addStrassenVerkehrsAmt(StrassenVerkehrsAmt asv, long loginId)
			throws EntityNotInsertedException;

	/**
	 * Holt ein StrassenVerkehrsAmt aus der Datenbank.
	 * 
	 * @param autoritaetId
	 *            ID des designierten StrassenVerkehrsAmt
	 * @return das StrassenVerkehrsAmt als detached object
	 * @throws EntityNotFoundException
	 *             wenn die Entität nicht existiert
	 */
	public StrassenVerkehrsAmt getStrassenVerkehrsAmt(long autoritaetId)
			throws EntityNotFoundException;

	/**
	 * Holt alle StrassenVerkehrsÄmter aus der Datenbank.
	 * 
	 * @return Liste von StrassenVerkehrsAmt
	 * @throws DBLayerException
	 *             falls ein Fehler im DBLayer auftritt, der so schwerwiegend
	 *             ist, dass das Ergebnis verfäscht ist
	 */
	public List<StrassenVerkehrsAmt> getAllStrassenVerkehrsAemter()
			throws DBLayerException;

	/**
	 * Updatet ein StrassenVerkehrsAmt in der Datenbank.
	 * 
	 * @param asv
	 *            Entität die eingefügt werden soll.
	 * @param loginId
	 *            ID des Logins, der diese Methode aufruft
	 * @throws DBLayerException
	 *             wenn das Updaten fehl schlägt
	 */
	public void updateStrassenVerkehrsAmt(StrassenVerkehrsAmt asv, long loginId)
			throws EntityNotUpdatedException;

	/**
	 * Löscht ein StrassenVerkehrsAmt in der Datenbank.
	 * 
	 * @param asv
	 *            Entität die gelöscht werden soll.
	 * @param loginId
	 *            ID des Logins, der diese Methode aufruft
	 * @throws DBLayerException
	 *             wenn das Löschen fehl schlägt
	 */
	public void deleteStrassenVerkehrsAmt(StrassenVerkehrsAmt asv, long loginId)
			throws EntityNotDeletedException;

	// ==============================================================
	// AUTOBAHNMEISTEREI - Reihenfolge: CRUD
	// ==============================================================

	/**
	 * Fügt eine neue Autobahnmeisterei in die Datenbank ein.
	 * 
	 * @param am
	 *            Entität die eingefügt werden soll.
	 * @param loginId
	 *            ID des Logins, der diese Methode aufruft
	 * @throws DBLayerException
	 *             wenn das Hinzufügen fehl schlägt
	 */
	public void addAutobahnMeisterei(AutobahnMeisterei am, long loginId)
			throws EntityNotInsertedException;

	/**
	 * Holt eine neue Autobahnmeisterei aus der Datenbank.
	 * 
	 * @param autobahnMeistereiId
	 *            ID der designierten Autobahnmeisterei
	 * @return die Autobahnmeisterei als detached object
	 * @throws EntityNotFoundException
	 *             wenn die Entität nicht existiert
	 */
	public AutobahnMeisterei getAutobahnMeisterei(long autobahnMeistereiId)
			throws EntityNotFoundException;

	/**
	 * Holt eine neue Autobahnmeisterei aus der Datenbank.
	 * 
	 * @param name
	 *            Name der designierten Autobahnmeisterei
	 * @return die Autobahnmeisterei als detached object
	 * @throws EntityNotFoundException
	 *             wenn die Entität nicht existiert
	 */
	public AutobahnMeisterei getAutobahnMeistereiByName(String name)
			throws EntityNotFoundException;

	/**
	 * Liefert eine Liste von allen in der Datenbank vorhanden
	 * Autobahnmeistereien. Werden keine gefunden, dann ist
	 * <code>size()==0</code>.<br/>
	 * 
	 * @return Liste aller im System vorhandenen Autobahnmeistereien; aber nicht
	 *         null.
	 */
	public List<AutobahnMeisterei> getAllAutobahnMeistereien()
			throws DBLayerException;

	/**
	 * Liefert alle Autobahnmeistereien die einer Fremdfirma zugeordnet sind.
	 * Werden keine gefunden, dann ist <code>size()==0</code>.<br/>
	 * 
	 * @param fremdFirmaId
	 *            ID der designierten Fremdfirma
	 * @return List von Autobahnmeistereien; aber nicht null.
	 */
	public List<AutobahnMeisterei> getAllAutobahnMeistereienByFremdFirma(
			long fremdFirmaId) throws DBLayerException;

	/**
	 * Liefert alle Autobahnmeistereien die einem StrassenVerkehrsAmt zugeordnet
	 * sind. Werden keine gefunden, dann ist <code>size()==0</code>.<br/>
	 * 
	 * @param strassenVerkehrsAmtId
	 *            ID des designierten StrassenVerkehrsAmt
	 * @return List von Autobahnmeistereien; aber nicht null.
	 */
	public List<AutobahnMeisterei> getAutobahnMeistereienByASV(
			long strassenVerkehrsAmtId) throws DBLayerException;

	/**
	 * Updatet eine Autobahnmeisterei in der Datenbank.
	 * 
	 * @param am
	 *            Entität die upgedatet werden soll.
	 * @param loginId
	 *            ID des Logins, der diese Methode aufruft
	 * @throws DBLayerException
	 *             wenn das Updaten fehl schlägt
	 */
	public void updateAutobahnMeisterei(AutobahnMeisterei am, long loginId)
			throws EntityNotUpdatedException;

	/**
	 * Löscht eine Autobahnmeisterei in der Datenbank.
	 * 
	 * @param am
	 *            Entität die gelöscht werden soll.
	 * @param loginId
	 *            ID des Logins, der diese Methode aufruft
	 * @throws DBLayerException
	 *             wenn das Löschen fehl schlägt
	 */
	public void deleteAutobahnMeisterei(AutobahnMeisterei am, long loginId)
			throws EntityNotDeletedException;

	// ==============================================================
	// ANGESTELLTE - Reihenfolge: CRUD
	// ==============================================================

	/**
	 * Fügt einen Angestellten in die Datenbank ein.
	 * 
	 * @param an
	 *            Entität die eingefügt werden soll.
	 * @param loginId
	 *            ID des Logins, der diese Methode aufruft
	 * @throws DBLayerException
	 *             wenn das Hinzufügen fehl schlägt
	 */
	public void addAngestellte(Angestellte an, long loginId)
			throws EntityNotInsertedException;

	/**
	 * Liefert den Angestellten mit der entsprechenden ID
	 * 
	 * @param angestellteId
	 *            ID des designierten Angestellten
	 * @return die gefundene Entität als detached object
	 * @throws EntityNotFoundException
	 *             wenn die Entität nicht existiert
	 */
	public Angestellte getAngestellte(long angestellteId)
			throws EntityNotFoundException;

	/**
	 * Lierfert alle Angestellte
	 * 
	 * @return Liste von Angestellte
	 * @throws DBLayerException
	 *             falls ein Fehler im DBLayer auftritt, der so schwerwiegend
	 *             ist, dass das Ergebnis verfäscht ist
	 */
	public List<Angestellte> getAllAngestellte() throws DBLayerException;

	/**
	 * Liefert eine Liste aller Angestellten einer Autoritaet. Werden keine
	 * gefunden, dann ist <code>size()==0</code>.<br/>
	 * <br/>
	 * 
	 * @param autoritaetId
	 *            ID der designierten Autoritaet
	 * @return Liste aller Angestellten der entsprechende Autoritaet; aber nicht
	 *         null.
	 */
	public List<Angestellte> getAllAngestellteByAutoritaet(long autoritaetId)
			throws DBLayerException;

	/**
	 * Updatet einen Angestellten in der Datenbank.
	 * 
	 * @param an
	 *            Entität die upgedatet werden soll.
	 * @param loginId
	 *            ID des Logins, der diese Methode aufruft
	 * @throws DBLayerException
	 *             wenn das Updaten fehl schlägt
	 */
	public void updateAngestellte(Angestellte an, long loginId)
			throws EntityNotUpdatedException;

	/**
	 * Löscht einen Angestellten in der Datenbank.
	 * 
	 * @param an
	 *            Entität die gelöscht werden soll.
	 * @param loginId
	 *            ID des Logins, der diese Methode aufruft
	 * @throws DBLayerException
	 *             wenn das Löschen fehl schlägt
	 */
	public void deleteAngestellte(Angestellte an, long loginId)
			throws EntityNotDeletedException;
}
