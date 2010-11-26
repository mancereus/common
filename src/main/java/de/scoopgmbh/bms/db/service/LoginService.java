package de.scoopgmbh.bms.db.service;

import java.util.List;

import de.scoopgmbh.bms.db.exceptions.DBLayerException;
import de.scoopgmbh.bms.db.exceptions.EntityNotDeletedException;
import de.scoopgmbh.bms.db.exceptions.EntityNotFoundException;
import de.scoopgmbh.bms.db.exceptions.EntityNotInsertedException;
import de.scoopgmbh.bms.db.exceptions.EntityNotUpdatedException;
import de.scoopgmbh.bms.db.model.Login;
import de.scoopgmbh.bms.db.model.Rolle;

public interface LoginService {

	/**
	 * Liest Login aus Datenbank.
	 * 
	 * @param name Benutzername
	 * @return Login als detached object
	 * @throws EntityNotFoundException wenn Login nicht gefunden
	 */
	public Login getLoginByName(String name) throws EntityNotFoundException;
	
	/**
	 * Liest Login aus Datenbank.
	 * 
	 * @param loginId ID des Logins
	 * @return Login als detached object
	 * @throws EntityNotFoundException wenn Login nicht gefunden wurde
	 */
	public Login getLoginById(long loginId) throws EntityNotFoundException;
	
	/**
	 * Liefert alle Logins zu einer Autorit�t.
	 * 
	 * @param autoritaetId ID der designierten Autorit�t
	 * @return List von Logins
	 * @throws DBLayerException falls ein Fehler im DBLayer auftritt, der so schwerwiegend ist, dass
	 * das Ergebnis verf�scht ist
	 */
	public List<Login> getLoginsByAutoritaet(long autoritaetId) throws DBLayerException;
	
	/**
	 * Lierfert alle Logins
	 * 
	 * @return Liste von Logins
	 * @throws DBLayerException falls ein Fehler im DBLayer auftritt, der so schwerwiegend ist, dass
	 * das Ergebnis verf�scht ist
	 */
	public List<Login> getAllLogins() throws DBLayerException;
	
	/**
	 * Liefert alle Rollen
	 * 
	 * @return 
	 * @throws DBLayerException falls ein Fehler im DBLayer auftritt, der so schwerwiegend ist, dass
	 * das Ergebnis verf�scht ist
	 */
	public List<Rolle> getAllRoles() throws DBLayerException;
	
	/**
	 * Liefert alle Rollen, die einem Login zugewiesen sind.
	 * 
	 * @param loginId ID des designierten Logins
	 * @return Liste aller Rollen f�r den Login
	 * @throws DBLayerException falls ein Fehler im DBLayer auftritt, der so schwerwiegend ist, dass
	 * das Ergebnis verf�scht ist
	 */
	public List<Rolle> getRolesForLoginId(long loginId) throws DBLayerException;
	
	/**
	 * F�gt eine Rolle in der Datenbank ein.
	 * 
	 * @param r Entit� die eingef�gt werden soll
	 * @param loginId ID des Logins, der die Methode aufruft
	 * @throws EntityNotInsertedException falls das Einf�gen fehlschl�gt
	 */
	public void addRole(Rolle r, long loginId) throws EntityNotInsertedException;
	
	/**
	 * L�scht eine Rolle in der Datenbank.
	 * 
	 * @param r Rolle die gel�scht werden soll
	 * @param loginId ID des Logins, der die Methode aufruft
	 * @throws EntityNotDeletedException falls das L�schen fehl schl�gt
	 */
	public void deleteRole(Rolle r, long loginId) throws EntityNotDeletedException;
	
	/**
	 * Liefert eine Rolle.
	 * 
	 * @param rolleId ID der designierten Rolle
	 * @return die Rolle
	 * @throws EntityNotFoundException falls die Rolle nicht gefunden wurde
	 */
	public Rolle getRole(long rolleId) throws EntityNotFoundException;
	
	/**
	 * Aktualisiert eine Rolle in der Datenbank.
	 * 
	 * @param r Rolle die in die Datenbank geschrieben werden soll
	 * @param editorId ID des Logins der die Methode aufruft
	 * @throws EntityNotUpdatedException falls das Aktualisieren fehlgeschlagen ist
	 */
	public void updateRole(Rolle r, long editorId) throws EntityNotUpdatedException;
	
	/**
	 * F�gt ein Login in die Datenbank ein
	 * 
	 * @param l Login der in die Datenbank geschrieben werden soll
	 * @param editorId ID des Login der die Methode aufruft
	 * @throws EntityNotInsertedException wenn der Login nicht eingef�gt werden kann
	 */
	public void addLogin(Login l, long editorId) throws EntityNotInsertedException;
	
	/**
	 * L�scht einen Login in der Datenbank.
	 * 
	 * @param l Login der gel�scht werden soll
	 * @param editorId ID des Logins der die Methode aufruft
	 * @throws EntityNotDeletedException falls der Login nicht gel�scht werden konnte
	 */
	public void deleteLogin(Login l, long editorId) throws EntityNotDeletedException;
	
	/**
	 * Sperrt einen Login.
	 * 
	 * @param l Login der gesperrt werden soll
	 * @param editorId ID des Logins, der die Methode aufruft
	 * @throws EntityNotUpdatedException falls das Sperren fehl schl�gt
	 */
	public void disableLogin(Login l, long editorId) throws EntityNotUpdatedException;
	
	/**
	 * Aktualisiert einen Login in der Datenbank.
	 * 
	 * @param lg Login der in die Datenbank geschrieben werden soll
	 * @param longinId ID des Logins der die Methode aufruft
	 * @throws EntityNotUpdatedException falls das Aktualisieren fehl schl�gt
	 */
	public void updateLogin(Login lg, long longinId) throws EntityNotUpdatedException;
	
	
	public long getAutoritaetenIdByAngestelltenId(long angestelltenId) throws EntityNotFoundException;
}
