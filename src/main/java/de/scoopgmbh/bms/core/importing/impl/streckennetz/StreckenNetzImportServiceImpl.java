package de.scoopgmbh.bms.core.importing.impl.streckennetz;

import java.net.URL;

import de.scoopgmbh.bms.core.exceptions.ImportException;
import de.scoopgmbh.bms.core.importing.StreckenNetzImportService;

public class StreckenNetzImportServiceImpl implements StreckenNetzImportService {

	private StreckenNetzService sns = null;

	public StreckenNetzImportServiceImpl(StreckenNetzService sns) {
		this.sns = sns;
	}

	/**
	 * Läd eine komplette CSV Datei validiert sie und updated die Datenbank.
	 * Der gesamte Algorithmus kann zu jeder Zeit und aus verschiedenen
	 * Gr�nden eine ImportException werfen, die bis zum Aufrufer dieser
	 * Methode weitergereicht wird. Das Update der Datenbank erfolgt innerhalb
	 * einer einzigen Transaktion. Ein Fehlschalgen der Transaktion hat
	 * ebenfalls ein Abbruch des Algorithmus zur Folge und ein Zur�ckrollen
	 * der bis dorthin gemachten Änderungen.
	 * 
	 * @param csv
	 *            File Objekt, dass auf die CSV-Datei verweist
	 * @param loginId
	 *            ID des Login, der diese Methode aufruft.
	 * @throws ImportException
	 *             falls innerhalb des Algorithmus schwerwiegende Fehler
	 *             auftreten.
	 */
	public void importStreckenNetzCSV(URL csv, long loginId)
			throws ImportException {
		// 1. CSV Datei laden
		StreckenNetzLoader sncl = new StreckenNetzLoader();
		sncl.loadCSV(csv);

		// 2. Inhalt CSV mit Datenbank abgleichen
		StreckenNetzImportMatcher snim = new StreckenNetzImportMatcher(
				this.idgen, this.sns, sncl);
		StreckenNetzDTO sndto = snim.matchWithDatabase();

		// 3. Abgleich auf Datenbank �bertragen
		this.updateStreckenNetz(sndto, loginId);

	}

	/**
	 * Ruft einfach die update-Service-Methode im SteckenNetzService auf.
	 * 
	 * @param sndto
	 *            Das neue StreckenNetz
	 * @param loginId
	 *            ID des Login, der diese Methode auruft.
	 * @throws ImportException
	 *             Falls beim Updaten mit der Datenbank ein Fehler aufgetreten
	 *             ist.
	 */
	private void updateStreckenNetz(StreckenNetzDTO sndto, long loginId)
			throws ImportException {
		try {
			// System.out.println(sndto.toString());
			this.sns.updateStreckenNetz(sndto, loginId);
		} catch (DBLayerException e) {
			e.printStackTrace();
			throw new ImportException(
					"Import nicht erfolgreich. Fehler beim Updaten der Datenbank.");
		}
	}
}
