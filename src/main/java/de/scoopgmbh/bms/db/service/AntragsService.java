package de.scoopgmbh.bms.db.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import de.scoopgmbh.bms.db.model.BaustellenAntrag;
import de.scoopgmbh.bms.db.model.BaustellenAntragPosition;

public interface AntragsService {

	/**
	 * F�gt eine SperrFormular in die Datenbank ein.
	 * 
	 * @param sf
	 *            Eintit�t die eingef�gt werden soll
	 * @param loginId
	 *            ID des Logins der die Methode aufruft
	 * @throws EntityNotInsertedException
	 *             Wenn die Entit�t nicht eingef�gt werden konnte
	 */
	public void addFormular(BaustellenAntrag sf, long loginId);

	/**
	 * L�scht ein SperrFormular in der Datenbank.
	 * 
	 * @param sf
	 *            Entit�t die gel�scht werden soll.
	 * @param loginId
	 *            ID des Logins, der die Methode aufruf
	 * @throws EntityNotDeletedException
	 *             wenn die Entit�t nicht gel�scht werden konnte
	 */
	public void deleteFormular(BaustellenAntrag sf, long loginId);

	/**
	 * Aktualisiert ein SperrFormular in der Datenbank.
	 * 
	 * @param sf
	 *            Entit�t die in die Datenbank geschrieben werden soll.
	 * @param loginId
	 *            ID des Logins, der die Methode aufruf
	 * @throws EntityNotUpdatedException
	 *             wenn die Entit�t nicht geschrieben wurde
	 */
	public void updateFormular(BaustellenAntrag sf, long loginId);

	/**
	 * Liest ein SperrFormular aus der Datenbank.
	 * 
	 * @param formularId
	 *            ID des SperrFormulars, dass gelesen werden soll.
	 * @return das designierte SperrFormular
	 * @throws EntityNotFoundException
	 *             wenn kein SperrFormular gefunden wurde
	 */
	public BaustellenAntrag getFormular(long formularId);

	/**
	 * Liest eine Liste aller SperrFormulare aus der Datenbank.
	 * 
	 * @return die Liste der SperrFormulare
	 */
	public List<BaustellenAntrag> getFormularList();

	/**
	 * 
	 * @param baustellenAntragId
	 * @return
	 */
	public List<BaustellenAntragPosition> getAntragsPositionen(
			long baustellenAntragId);
}
