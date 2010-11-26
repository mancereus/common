package de.scoopgmbh.bms.db.service;

import de.scoopgmbh.bms.db.exceptions.DBLayerException;
import de.scoopgmbh.bms.db.exceptions.EntityNotDeletedException;
import de.scoopgmbh.bms.db.exceptions.EntityNotFoundException;
import de.scoopgmbh.bms.db.exceptions.EntityNotInsertedException;
import de.scoopgmbh.bms.db.exceptions.EntityNotUpdatedException;
import de.scoopgmbh.bms.db.model.Skizze;

public interface SkizzenService {

	// crud
	/**
	 * Fügt eine neue Skizze in die Datenbank ein.
	 *
	 * @param at Entität die eingefügt werden soll.
	 * @param loginId ID des Logins, der diese Methode aufruft
	 * @throws DBLayerException wenn das Hinzufügen fehl schlägt
	 */
	public void addSkizze(Skizze at, long loginId) throws EntityNotInsertedException;

	/**
	 * Holt eine Skizze aus der Datenbank.
	 *
	 * @param SkizzeId ID der designierten Skizze
	 * @return die Skizze als detached object
	 * @throws EntityNotFoundException wenn die Entität nicht existiert
	 */
	public Skizze getSkizze(long  skizzeId) throws EntityNotFoundException;

	/**
	 * Holt eine Skizze zu einem Knotenpunkt.
	 *
	 * @param SkizzeId ID der designierten Skizze
	 * @return die Skizze als detached object
	 * @throws EntityNotFoundException wenn die Entität nicht existiert
	 */
	public Skizze getSkizzeByNetzpunktId(long  netzpunktId) throws EntityNotFoundException;
	/**
	 * Updatet eine Skizze in der Datenbank.
	 *
	 * @param at Entität die eingefügt werden soll.
	 * @param loginId ID des Logins, der diese Methode aufruft
	 * @throws DBLayerException wenn das Updaten fehl schlägt
	 */
	public void updateSkizze(Skizze at, long loginId) throws EntityNotUpdatedException;

	/**
	 * Löscht eine Skizze in der Datenbank.
	 *
	 * @param ff Entität die gelöscht werden soll.
	 * @param loginId ID des Logins, der diese Methode aufruft
	 * @throws DBLayerException wenn das Löschen fehl schlägt
	 */
	public void deleteSkizze(Skizze at, long loginId) throws EntityNotDeletedException;

}
