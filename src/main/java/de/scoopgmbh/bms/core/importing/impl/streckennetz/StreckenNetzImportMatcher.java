package de.scoopgmbh.bms.core.importing.impl.streckennetz;

import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

import de.scoopgmbh.bms.core.exceptions.ImportException;
import de.scoopgmbh.bms.core.importing.impl.AbstractCSVLoader.CSVRowIterator;
import de.scoopgmbh.bms.core.importing.impl.CSVRow;
import de.scoopgmbh.bms.db.model.Abschnitt;
import de.scoopgmbh.bms.db.model.DB;
import de.scoopgmbh.bms.db.model.KnotenTyp;
import de.scoopgmbh.bms.db.model.NetzPunkt;
import de.scoopgmbh.bms.db.model.Strasse;

/**
 * Spezialisierte Klase zum Vergleichen des Datenbank StreckenNetzes und des
 * CSV-Datei Streckennetzes.
 * 
 * @author hirtech
 * 
 */
class StreckenNetzImportMatcher {

	private IdGeneratorService idgen = null;
	private StreckenNetzService sns = null;
	private StreckenNetzLoader snl = null;
	private StreckenNetzDTO snDto;

	StreckenNetzImportMatcher(StreckenNetzService sns, StreckenNetzLoader snl) {
		this.sns = sns;
		this.snl = snl;
	}

	StreckenNetzDTO matchWithDatabase() throws ImportException {

		this.snDto = new StreckenNetzDTO();

		LinkedList<StrasseDTO> strasseDto = null;
		try {
			strasseDto = buildNetzDTOModel();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ImportException(
					"Schwerwiegender Fehler beim Erstellen der Datenstruktur. "
							+ "CSVColumnExpectation zuvor fehlgeschlagen?");
		}

		// Reihenfolge nicht verändern!!!
		matchStrassen(strasseDto);
		matchPunkte(strasseDto);
		matchAbschnitte(strasseDto);

		return snDto;
	}

	/**
	 * Baut im Speichern aus der Liste von CSV-Zeilen ein vor�bergehendes
	 * Datenmodell aus Proxy-Objekten zusammen.
	 * 
	 */
	private LinkedList<StrasseDTO> buildNetzDTOModel() throws ParseException {
		LinkedList<StrasseDTO> strasseDto = new LinkedList<StrasseDTO>();

		CSVRowIterator cri = this.snl.getCSVRowIterator();
		while (cri.hasNext()) {
			CSVRow r = cri.next();

			// 1. Strasse checken
			String s1 = (String) r
					.get(StreckenNetzLoader.ROADTYP)
					.getExpectation()
					.getConvertedValue(
							r.get(StreckenNetzLoader.ROADTYP).getValue());
			Integer i1 = (Integer) r
					.get(StreckenNetzLoader.ROADNO)
					.getExpectation()
					.getConvertedValue(
							r.get(StreckenNetzLoader.ROADNO).getValue());
			StrasseDTO s = new StrasseDTO(s1.charAt(0), i1);
			if (strasseDto.contains(s))
				s = strasseDto.get(strasseDto.indexOf(s));
			else {
				strasseDto.add(s);
			}

			String bez = (String) r
					.get(StreckenNetzLoader.KNT_NAME_VON)
					.getExpectation()
					.getConvertedValue(
							r.get(StreckenNetzLoader.KNT_NAME_VON).getValue());
			Float bkm1 = (Float) r
					.get(StreckenNetzLoader.BKM_VON)
					.getExpectation()
					.getConvertedValue(
							r.get(StreckenNetzLoader.BKM_VON).getValue());
			Float bkm2 = (Float) r
					.get(StreckenNetzLoader.BKM_VON2)
					.getExpectation()
					.getConvertedValue(
							r.get(StreckenNetzLoader.BKM_VON2).getValue());
			String kz = (String) r
					.get(StreckenNetzLoader.KNT_TYP_VON)
					.getExpectation()
					.getConvertedValue(
							r.get(StreckenNetzLoader.KNT_TYP_VON).getValue());
			NetzPunktDTO npd1 = new NetzPunktDTO(bez, bkm1, kz);
			npd1.setBkm2(bkm2);

			bez = (String) r
					.get(StreckenNetzLoader.KNT_NAME_BIS)
					.getExpectation()
					.getConvertedValue(
							r.get(StreckenNetzLoader.KNT_NAME_BIS).getValue());
			bkm1 = (Float) r
					.get(StreckenNetzLoader.BKM_BIS)
					.getExpectation()
					.getConvertedValue(
							r.get(StreckenNetzLoader.BKM_BIS).getValue());
			bkm2 = (Float) r
					.get(StreckenNetzLoader.BKM_BIS2)
					.getExpectation()
					.getConvertedValue(
							r.get(StreckenNetzLoader.BKM_BIS2).getValue());
			kz = (String) r
					.get(StreckenNetzLoader.KNT_TYP_BIS)
					.getExpectation()
					.getConvertedValue(
							r.get(StreckenNetzLoader.KNT_TYP_BIS).getValue());
			NetzPunktDTO npd2 = new NetzPunktDTO(bez, bkm1, kz);
			npd2.setBkm2(bkm2);

			// 2.1 Netzpunkt 1 checken
			NetzPunktDTO tmp = npd1;
			if (s.getNetzPunkte().contains(npd1))
				tmp = s.getNetzPunkte().get(s.getNetzPunkte().indexOf(npd1));
			else
				s.getNetzPunkte().add(npd1);
			if (tmp.getBkm() != npd1.getBkm()) {
				tmp.setBkm2(npd1.getBkm());
			}
			npd1 = tmp;

			// 2.2 Netzpunkt 2 checken
			tmp = npd2;
			if (s.getNetzPunkte().contains(npd2))
				tmp = s.getNetzPunkte().get(s.getNetzPunkte().indexOf(npd2));
			else
				s.getNetzPunkte().add(tmp);
			if (tmp.getBkm() != npd2.getBkm()) {
				tmp.setBkm2(npd2.getBkm());
			}
			npd2 = tmp;

			// 3. Abschnitt checken
			Float laenge = (Float) r
					.get(StreckenNetzLoader.LAENGE)
					.getExpectation()
					.getConvertedValue(
							r.get(StreckenNetzLoader.LAENGE).getValue());
			Integer fsCount = (Integer) r
					.get(StreckenNetzLoader.FS_COUNT)
					.getExpectation()
					.getConvertedValue(
							r.get(StreckenNetzLoader.FS_COUNT).getValue());
			// Integer stStCount =
			// (Integer)r.get(StreckenNetzLoader.STST_COUNT).getExpectation().getConvertedValue(r.get(StreckenNetzLoader.STST_COUNT).getValue());
			Integer block = (Integer) r
					.get(StreckenNetzLoader.BLOCKNUM)
					.getExpectation()
					.getConvertedValue(
							r.get(StreckenNetzLoader.BLOCKNUM).getValue());
			Integer strecke = (Integer) r
					.get(StreckenNetzLoader.STRECKENNR)
					.getExpectation()
					.getConvertedValue(
							r.get(StreckenNetzLoader.STRECKENNR).getValue());
			String richtung = r.get(StreckenNetzLoader.RICHTUNG).getValue();

			AbschnittDTO ad = new AbschnittDTO(npd1, npd2, 0.0f, 2, false);
			ad.setRichtung(richtung);
			ad.setBlockNr(block);
			ad.setFsCount(fsCount);
			ad.setLaenge(laenge);
			ad.setStrecke(strecke);

			if (!s.getAbschnitte().contains(ad))
				s.getAbschnitte().add(ad);
		}

		return strasseDto;
	}

	/**
	 * Match alle Strassen aus der Datenbank mit den Proxy Objekten des
	 * vor�bergehenden Datenmodells und setzt in jedem Proxy die entsprechende
	 * Entity des Datenbank Objektes.
	 * 
	 * @param strassenDtos
	 */
	private void matchStrassen(LinkedList<StrasseDTO> strassenDtos) {
		List<Strasse> dbStrassen = sns.getAllStrassen();
		boolean found;
		for (StrasseDTO sd : strassenDtos) {
			this.snDto.getAddList().add(sd.makeCopy());
			this.snDto.getDeleteList().add(sd.makeCopy());
			this.snDto.getUpdateList().add(sd.makeCopy());

			found = false;
			for (Strasse s : dbStrassen) {
				if ((sd.getNummer() == s.getNummer())
						&& (sd.getTyp() == s.getTyp())) {
					found = true;
					sd.setEntity(s);
					break;
				}
			}
			if (!found) {
				StrasseDTO tp = this.snDto.getAddList().get(
						snDto.getAddList().indexOf(sd));

				tp.setPerform(true);
				Strasse x = new Strasse();
				x.setId(this.idgen.getNextId());
				x.setTyp(tp.getTyp());
				x.setNummer(tp.getNummer());
				x.setVersion(0);
				tp.setEntity(x);
				sd.setEntity(x);
			} else {

			}
		}
		StrasseDTO tmp = null;
		for (Strasse s : dbStrassen) {
			tmp = new StrasseDTO(s.getTyp(), s.getNummer());
			if (strassenDtos.contains(tmp)) {
				// Update

			} else {
				// Löschen
				int idx = snDto.getDeleteList().indexOf(tmp);
				if (idx == -1) {
					tmp.setPerform(true);
					tmp.setEntity(s);
					this.snDto.getDeleteList().add(tmp);
				}
			}
		}
	}

	/**
	 * Match alle Punkte aus der Datenbank mit den Proxy Objekten des
	 * vorübergehenden Datenmodells und setzt in jedem Proxy die entsprechende
	 * Entity des Datenbank Objektes.
	 * 
	 * @param strassenDtos
	 */
	private void matchPunkte(LinkedList<StrasseDTO> csvStrassen) {
		List<KnotenTyp> knotenTypen = sns.getAllKnotenTypen();

		for (StrasseDTO csvStrasse : csvStrassen) {
			List<NetzPunkt> dbPunkte = sns.getAllNetzPunkteByStrasse(
					csvStrasse.getTyp(), csvStrasse.getNummer());

			for (NetzPunktDTO csvStrassenPunkt : csvStrasse.getNetzPunkte()) {
				if (csvStrassenPunkt.isIn(dbPunkte)) {
					// Update
					// Referenz wird in isIn gesetzt
				} else {
					// insert
					NetzPunkt np = new NetzPunkt();
					np.setBkm1(csvStrassenPunkt.getBkm());
					np.setBkm2(csvStrassenPunkt.getBkm2());
					np.setBezeichnung(csvStrassenPunkt.getBezeichnung());
					np.setId(idgen.getNextId());
					np.setIdKnotenTyp(getIdOfKnotenTyp(
							csvStrassenPunkt.getTypKuerzel(), knotenTypen));
					np.setKnotenTyp(getKnotenTypById(knotenTypen,
							np.getIdKnotenTyp()));
					np.setStrassenId(csvStrasse.getEntity().getId());

					NetzPunktDTO zpdto = csvStrassenPunkt.makeCopy(); // FIXME
																		// muss
																		// vielleicht
																		// gar
																		// nicht,
																		// bei
																		// strasse
																		// ja -
																		// hier
																		// nicht
					zpdto.setEntity(np);
					csvStrassenPunkt.setEntity(np);
					this.snDto.getAddList()
							.get(snDto.getAddList().indexOf(csvStrasse))
							.getNetzPunkte().add(zpdto);
				}
			}

			for (NetzPunkt dbNetzPunkt : dbPunkte) {
				if (isIn(dbNetzPunkt, csvStrasse.getNetzPunkte())) {
					// Update
					// Refrenz wurde in der oberen Schleife gesetzt. Beide
					// Update-Mengen sind identisch.
				} else {
					// Delete
					NetzPunktDTO tmp = new NetzPunktDTO();
					tmp.setEntity(dbNetzPunkt);
					this.snDto.getDeleteList()
							.get(snDto.getUpdateList().indexOf(csvStrasse))
							.getNetzPunkte().add(tmp);
				}

			}
		}
	}

	/**
	 * Match alle Abschnitte aus der Datenbank mit den Proxy Objekten des
	 * vor�bergehenden Datenmodells und setzt in jedem Proxy die entsprechende
	 * Entity des Datenbank Objektes.
	 * 
	 * @param strassenDtos
	 */
	private void matchAbschnitte(LinkedList<StrasseDTO> strasseDto) {
		List<Abschnitt> dbAbschnitte = null;
		for (StrasseDTO sd : strasseDto) {

			dbAbschnitte = this.sns.getAllAbschnittByStrasse(sd.getTyp(),
					sd.getNummer(), Abschnitt.SUEDWEST);
			dbAbschnitte.addAll(this.sns.getAllAbschnittByStrasse(sd.getTyp(),
					sd.getNummer(), Abschnitt.NORDOST));

			long l = 0;
			for (AbschnittDTO adto : sd.getAbschnitte()) {
				if (adto.isIn(dbAbschnitte)) {
					// Update
					Abschnitt a = adto.getEntity();
					a.setAnzahlFahrstreifen(adto.getFsCount());
					a.setHatStandstreifen(adto.isHatStandstreifen() == true ? DB.YES
							: DB.NO);
					a.setLaenge(adto.getLaenge());
					a.setRichtung(adto.getRichtung());
					a.setPosInList(l++);
					a.setBlocknr(adto.getBlockNr());
					a.setStreckenNr(adto.getStrecke());
					this.snDto.getUpdateList()
							.get(snDto.getAddList().indexOf(sd))
							.getAbschnitte().add(adto);
				} else {
					// Insert
					Abschnitt aNewOne = new Abschnitt();
					aNewOne.setAnzahlFahrstreifen(adto.getFsCount());
					aNewOne.setHatStandstreifen(adto.isHatStandstreifen() == true ? DB.YES
							: DB.NO);
					aNewOne.setIdKnotenPunkt1(adto.getKnt1().getEntity()
							.getId());
					aNewOne.setIdKnotenPunkt2(adto.getKnt2().getEntity()
							.getId());
					aNewOne.setLaenge(Math.abs(adto.getKnt1().getEntity()
							.getBkm1()
							- adto.getKnt2().getEntity().getBkm1()));
					aNewOne.setStrassenId(sd.getEntity().getId());
					aNewOne.setTyp(getAbschnittTyp(adto.getKnt1().getEntity(),
							adto.getKnt2().getEntity()));
					aNewOne.setId(this.idgen.getNextId());
					aNewOne.setRichtung(adto.getRichtung());
					aNewOne.setBlocknr(adto.getBlockNr());
					aNewOne.setStreckenNr(adto.getStrecke());
					adto.setEntity(aNewOne);
					aNewOne.setPosInList(l++);
					this.snDto.getAddList().get(snDto.getAddList().indexOf(sd))
							.getAbschnitte().add(adto);
				}
			}

			for (Abschnitt a : dbAbschnitte) {
				if (isIn(a, sd.getAbschnitte())) {
					// update
				} else {
					// Delete
					AbschnittDTO tmp = new AbschnittDTO();
					tmp.setEntity(a);
					this.snDto.getAddList()
							.get(snDto.getDeleteList().indexOf(sd))
							.getAbschnitte().add(tmp);
				}
			}

		}
	}

	private boolean isIn(Abschnitt a, List<AbschnittDTO> lCsvAbschnitt) {
		for (AbschnittDTO adto : lCsvAbschnitt)
			if (adto.isTheSameAs(a))
				return true;
		return false;
	}

	private boolean isIn(NetzPunkt np, List<NetzPunktDTO> npdtoList) {
		for (NetzPunktDTO npdto : npdtoList)
			if (npdto.isTheSameAs(np))
				return true;
		return false;
	}

	private long getIdOfKnotenTyp(String kuerzel, List<KnotenTyp> typen) {
		for (KnotenTyp kt : typen) {
			if (kt.getKuerzel().equalsIgnoreCase(kuerzel))
				return kt.getId();
		}
		return 0l; // TODO sollte schon in der ColumnExpectation fehlschlagen
					// und gar nicht erst 0 zur�ck geben
	}

	private KnotenTyp getKnotenTypById(List<KnotenTyp> typen, long id) {
		for (KnotenTyp kn : typen) {
			if (kn.getId() == id)
				return kn;
		}
		assert (false);
		return null;
	}

	private String getAbschnittTyp(NetzPunkt np1, NetzPunkt np2) {
		// 1. Rampe?
		if (np1.getKnotenTyp().getKuerzel()
				.equalsIgnoreCase(KnotenTyp.RAMPENPUNKT)
				|| np2.getKnotenTyp().getKuerzel()
						.equalsIgnoreCase(KnotenTyp.RAMPENPUNKT))
			return Abschnitt.RAMPE;

		// 2. Autobahnzubringer?
		if (np1.getKnotenTyp().getKuerzel()
				.equalsIgnoreCase(KnotenTyp.BUNDESSTRASSENPUNKT)
				|| np2.getKnotenTyp().getKuerzel()
						.equalsIgnoreCase(KnotenTyp.BUNDESSTRASSENPUNKT))
			return Abschnitt.BUNDESSTRASSE;

		// 3. wenn alles nicht, dann ist es Autobahn
		return Abschnitt.AUTOBAHN;
	}
}
