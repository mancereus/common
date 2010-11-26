package de.scoopgmbh.bms.db.service;

import java.util.List;

import de.scoopgmbh.bms.db.exceptions.DBLayerException;
import de.scoopgmbh.bms.db.exceptions.EntityNotDeletedException;
import de.scoopgmbh.bms.db.exceptions.EntityNotFoundException;
import de.scoopgmbh.bms.db.exceptions.EntityNotInsertedException;
import de.scoopgmbh.bms.db.exceptions.EntityNotUpdatedException;
import de.scoopgmbh.bms.db.model.Abschnitt;
import de.scoopgmbh.bms.db.model.AutobahnMeisterei;
import de.scoopgmbh.bms.db.model.KnotenTyp;
import de.scoopgmbh.bms.db.model.NetzPunkt;
import de.scoopgmbh.bms.db.model.Strasse;
import de.scoopgmbh.bms.db.model.Zuordnung;

public interface StreckenNetzService {

	/**
	 * Aktualisiert alle Zuordnungen in der Datenbank.
	 * 
	 * @param zidto
	 *            Root-Elmenent mit alle neuen und alten Daten.
	 * @param id
	 * @throws DBLayerException
	 */
	public void updateZurodnungen(List<AutobahnMeisterei> meistereien, long id)
			throws DBLayerException;

	public List<Zuordnung> getZuordnungen(long autoritaetId);

	/**
	 * Liefert alle AutobahnAbschnitte.
	 * 
	 * @return Liste aller AutobahnAbschnitte
	 * @throws DBLayerException
	 *             falls ein Fehler im DBLayer auftritt, der so schwerwiegend
	 *             ist, dass das Ergebnis verfäscht ist
	 */
	public List<Strasse> getAllStrassen() throws DBLayerException;

	/**
	 * Liefert alle KnotenPunkte eine Autobahn.
	 * 
	 * @param babId
	 *            ID der designierten Autobahn
	 * @return Liste aller KnotenPunkte
	 * @throws DBLayerException
	 *             falls ein Fehler im DBLayer auftritt, der so schwerwiegend
	 *             ist, dass das Ergebnis verfäscht ist
	 */
	public List<NetzPunkt> getAllKnotenPunkteForStrasse(long strasseId)
			throws DBLayerException;

	/**
	 * Liefert alle Knotenpunkte einer Autobahn, die einer Autobahnmeisterei
	 * zugeordnet sind.
	 * 
	 * @param babId
	 *            ID der designierten Autobahn
	 * @param autobahnMeistereiId
	 *            ID der designierten Autobahnmeisterei
	 * @return Liste mit Knotenpunkten
	 * @throws DBLayerException
	 *             falls ein Fehler im DBLayer auftritt, der so schwerwiegend
	 *             ist, dass das Ergebnis verf�scht ist
	 */
	public List<NetzPunkt> getAllNetzPunkteForStrasseAndAM(long strassenId,
			long autobahnMeistereiId) throws DBLayerException;

	/**
	 * Fügt eine Autobahn in die Datenbank ein.
	 * 
	 * @param bab
	 *            Autobahn die eingef�gt werden soll
	 * @param loginId
	 *            ID des Logins, der die Methode aufruft
	 * @throws EntityNotInsertedException
	 *             wenn das Einf�gen fehl schl�gt
	 */
	public void addStrasse(Strasse bab, long loginId)
			throws EntityNotInsertedException;

	/**
	 * Aktualisiert eine Autobahn in der Datenbank.
	 * 
	 * @param bab
	 *            Autobahn die in die Datenbank geschrieben werden soll
	 * @param loginId
	 *            ID des Login, der die Methode aufruft
	 * @throws EntityNotUpdatedException
	 *             falls das Aktualisieren fehl schl�gt
	 */
	public void updateStrasse(Strasse bab, long loginId)
			throws EntityNotUpdatedException;

	/**
	 * Löscht eine Autobahn in der Datenbank
	 * 
	 * @param bab
	 *            Autobahn die gel�scht werden soll
	 * @param loginId
	 *            ID des Login der die Methode aurfut
	 * @throws EntityNotDeletedException
	 *             falls das L�schen fehl schl�gt
	 */
	public void deleteStrasse(Strasse bab, long loginId)
			throws EntityNotDeletedException;

	/**
	 * Liefert eine Autobahn
	 * 
	 * @param babId
	 *            ID der designierten Autobahn
	 * @return Autobahn als detached object
	 * @throws EntityNotFoundException
	 *             falls die Autobahn nicht gefunden wurde
	 */
	public Strasse getStrasseById(long strasseId)
			throws EntityNotFoundException;

	/**
	 * Liefert eine Autobahn
	 * 
	 * @param babNummer
	 *            Nummer der designierten Autobahn
	 * @return Autobahn als detached object
	 * @throws EntityNotFoundException
	 *             falls die Autobahn nicht gefunden wurde
	 */
	public Strasse getStrasseByName(char typ, int strassenNummer)
			throws EntityNotFoundException;

	/**
	 * Fügt einen Knotenpunkt in die Datenbank zu einer Autobahn ein.
	 * 
	 * @param np
	 *            Knotenpunkt der eingef�gt werden soll
	 * @param babId
	 *            ID der designierten Autobahn
	 * @param loginId
	 *            ID des Login der die Methode aufruft
	 * @throws EntityNotInsertedException
	 *             falls beim Einf�gen etwas schief ging
	 */
	public void addNetzPunkt(NetzPunkt np, long loginId)
			throws EntityNotInsertedException;

	/**
	 * Aktualisiert einen Knotenpunkt
	 * 
	 * @param np
	 *            Knotenpunkt der in die Datenbank geschrieben werden soll
	 * @param babId
	 *            ID der designierten Autobahn
	 * @param loginId
	 *            ID des Login der die Methode aufruft
	 * @throws EntityNotUpdatedException
	 *             falls das Aktualisieren fehl schl�gt
	 */
	public void udpateNetzPunkt(NetzPunkt np, long loginId)
			throws EntityNotUpdatedException;

	/**
	 * Löscht einen Knotenpunkt aus der Datenbank
	 * 
	 * @param np
	 *            Knotenpunkt der gel�scht werden soll
	 * @param babId
	 *            ID der designierten Autobahn
	 * @param loginId
	 *            ID des Login der die Methode aufruft
	 * @throws EntityNotDeletedException
	 *             falls das L�schen fehl schl�gt
	 */
	public void deleteNetzPunkt(NetzPunkt np, long loginId)
			throws EntityNotDeletedException;

	/**
	 * Liefert einen Knotenpunkt
	 * 
	 * @param netzPunktId
	 *            ID des designierten Knotenpunktes
	 * @return Knotenpunkt als detached object
	 * @throws EntityNotFoundException
	 *             falls der Knotenpunkt nicht gefunden wurde
	 */
	public NetzPunkt getNetzPunkt(long netzPunktId)
			throws EntityNotFoundException;

	/**
	 * Fügt einen Autobahnabschnitt in die Datenbank ein.
	 * 
	 * @param aa
	 *            Autobahnabschnitt der eingef�gt werden soll
	 * @param babId
	 * @param loginId
	 * @throws EntityNotInsertedException
	 */
	public void addAbschnitt(Abschnitt aa, long babId, long loginId)
			throws EntityNotInsertedException;

	/**
	 * Aktualisiert einen Autobahnabschnitt in der Datenbank.
	 * 
	 * @param aa
	 *            Autobahnabschnitt der in die Datenbank geschrieben werden soll
	 * @param babId
	 *            ID der designierten Autobahn
	 * @param loginId
	 *            ID des Login der die Methode aufruft
	 * @throws EntityNotUpdatedException
	 *             falls das Einf�gen fehl schl�gt
	 */
	public void updateAbschnitt(Abschnitt aa, long loginId)
			throws EntityNotUpdatedException;

	/**
	 * Löscht einen Autobahnabschnitt in der Datenbank.
	 * 
	 * @param aa
	 *            Autobahabschnitt der gel�scht werden soll
	 * @param loginId
	 *            ID des Login, der die Methode aufruft
	 * @throws EntityNotDeletedException
	 *             falls das L�schen fehl schl�gt
	 */
	public void deleteAbschnitt(Abschnitt aa, long loginId)
			throws EntityNotDeletedException;

	/**
	 * Liefert einen Autobahnabschnitt
	 * 
	 * @param absId
	 *            ID des designierten Autobahnabschnittes
	 * @return Autobahnabschnitt
	 * @throws EntityNotFoundException
	 *             falls der Autobahnabschnitt nicht gefunden wurde
	 */
	public Abschnitt getAbschnitt(long strassenId)
			throws EntityNotFoundException;

	/**
	 * Liefert alle Abschnitt zu einer Straße zurück.
	 * 
	 * @param typ
	 * @param nummer
	 * @throws DBLayerException
	 *             falls ein Fehler im DBLayer auftritt, der so schwerwiegend
	 *             ist, dass das Ergebnis verfäscht ist
	 */
	public List<Abschnitt> getAllAbschnittByStrasse(char typ, int nummer,
			String richtung) throws DBLayerException;

	/**
	 * Liefert alle Abschnitte zurück.
	 * 
	 * @throws DBLayerException
	 *             falls ein Fehler im DBLayer auftritt, der so schwerwiegend
	 *             ist, dass das Ergebnis verfäscht ist
	 */
	public List<Abschnitt> getAllAbschnitt();

	/**
	 * Liefert alle NetzPunkte zu einer Stra�e zur�ck.
	 * 
	 * @param typ
	 *            Typ der Strasse
	 * @param nummer
	 *            Nummer der Straße
	 * @throws DBLayerException
	 *             falls ein Fehler im DBLayer auftritt, der so schwerwiegend
	 *             ist, dass das Ergebnis verfäscht ist
	 */
	public List<NetzPunkt> getAllNetzPunkteByStrasse(char typ, int nummer)
			throws DBLayerException;

	/**
	 * Liefert alle NetzPunkte zurück.
	 * 
	 * @throws DBLayerException
	 *             falls ein Fehler im DBLayer auftritt, der so schwerwiegend
	 *             ist, dass das Ergebnis verfäscht ist
	 */
	public List<NetzPunkt> getAllNetzPunkte() throws DBLayerException;

	/**
	 * Abbildung des großen Use-Case: StreckenNetz austauschen.
	 * 
	 * @param neuesNetz
	 *            Referenz auf Datenstruktur des neuen Netzes
	 * @param editorId
	 *            ID des Login der die Methode aufruft
	 */
	public void updateStreckenNetz(List<Strasse> strassen, long editorId)
			throws DBLayerException;

	/**
	 * Liefert eine Liste mit allen Knotentypen, die in der Datenbank vorhanden
	 * sind.
	 * 
	 * @return List alle Knotentypen
	 * @throws DBLayerException
	 */
	public List<KnotenTyp> getAllKnotenTypen() throws DBLayerException;

	/**
	 * Liefert den Abschnitt, der über die beiden NetzPunkte bestimmt ist.
	 * 
	 * @param nameNP1
	 * @param nameNP2
	 * @return
	 * @throws DBLayerException
	 */
	public Abschnitt getAbschnittByNetzPunktNames(String nameNP1,
			String nameNP2, char roadTyp, int roadNr) throws DBLayerException;

	/**
	 * Liefert die Abschnitte die zu den Parametern passen.
	 * 
	 * @param typ
	 * @param nummer
	 * @param strecke
	 * @param block
	 * @param richtung
	 * @return
	 * @throws DBLayerException
	 */
	public List<Abschnitt> getAllAbschnittByStreckeBlock(char typ, int nummer,
			int strecke, int block, String richtung) throws DBLayerException;

}
