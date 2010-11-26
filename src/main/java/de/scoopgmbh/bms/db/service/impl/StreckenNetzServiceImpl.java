package de.scoopgmbh.bms.db.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.annotation.Transactional;
import com.google.inject.Inject;

import de.scoopgmbh.bms.db.exceptions.DBLayerException;
import de.scoopgmbh.bms.db.model.Abschnitt;
import de.scoopgmbh.bms.db.model.KnotenTyp;
import de.scoopgmbh.bms.db.model.NetzPunkt;
import de.scoopgmbh.bms.db.model.Strasse;
import de.scoopgmbh.bms.db.model.Zuordnung;
import de.scoopgmbh.bms.db.service.StreckenNetzService;

public class StreckenNetzServiceImpl extends EbeanDao<Strasse> implements
		StreckenNetzService {

	@Inject
	public StreckenNetzServiceImpl(EbeanServer ebean) {
		super(ebean);
	}

	public List<Strasse> getAllStrassen() {
		return impl.getAllBabs();
	}

	public List<NetzPunkt> getAllKnotenPunkteForStrasse(long strassenId) {
		return impl.getAllPunkteForBab(strassenId);
	}

	public List<NetzPunkt> getAllNetzPunkteForStrasseAndAM(long strassenId,
			long autobahnMeistereiId) {
		return impl
				.getAllNetzPunkteForBabAndAM(strassenId, autobahnMeistereiId);
	}

	@Transactional(readOnly = false)
	public void addStrasse(Strasse bab, long loginId) {
		impl.addBab(bab, loginId);
	}

	@Transactional(readOnly = false)
	public void updateStrasse(Strasse bab, long loginId) {
		impl.updateBab(bab, loginId);
	}

	@Transactional(readOnly = false)
	public void deleteStrasse(Strasse bab, long loginId) {
		impl.deleteBab(bab, loginId);
	}

	public Strasse getStrasseById(long babId) {
		return impl.getBabById(babId);
	}

	public Strasse getStrasseByName(char typ, int strassenNummer) {
		return impl.getBabByName(typ, strassenNummer);
	}

	@Transactional(readOnly = false)
	public void addNetzPunkt(NetzPunkt np, long loginId) {
		impl.addBabPunkt(np, loginId);
	}

	@Transactional(readOnly = false)
	public void udpateNetzPunkt(NetzPunkt np, long loginId) {
		impl.udpateBabPunkt(np, loginId);
	}

	@Transactional(readOnly = false)
	public void deleteNetzPunkt(NetzPunkt np, long loginId) {
		impl.deleteBabPunkt(np, loginId);
	}

	public NetzPunkt getNetzPunkt(long netzPunktId) {
		return impl.getBabPunkt(netzPunktId);
	}

	@Transactional(readOnly = false)
	public void addAbschnitt(Abschnitt aa, long babId, long loginId) {
		impl.addBabAbs(aa, babId, loginId);
	}

	@Transactional(readOnly = false)
	public void updateAbschnitt(Abschnitt aa, long loginId) {
		impl.updateBabAbs(aa, loginId);
	}

	@Transactional(readOnly = false)
	public void deleteAbschnitt(Abschnitt aa, long loginId) {
		impl.deleteBabAbs(aa, loginId);
	}

	public Abschnitt getAbschnitt(long absId) {
		return impl.getBabAbs(absId);
	}

	public List<Abschnitt> getAllAbschnitt() throws DBLayerException {
		return this.impl.getAllAbschnitt();
	}

	public List<NetzPunkt> getAllNetzPunkteByStrasse(char typ, int nummer)
			throws DBLayerException {
		List<NetzPunkt> ret = this.impl.getAllNetzPunkteByStrasse(typ, nummer);
		for (NetzPunkt np : ret) {
			np.setKnotenTyp(this.impl.getKnotenTypById(np.getIdKnotenTyp()));
		}
		return ret;
	}

	public List<NetzPunkt> getAllNetzPunkte() {
		return impl.getAllNetzPunkte();
	}

	@Transactional(readOnly = false)
	public void updateStreckenNetz(StreckenNetzDTO neuesNetz, long editorId) {
		// 1. Insert
		List<StrasseDTO> neueStrassen = neuesNetz.getAddList();
		for (StrasseDTO neueStrasse : neueStrassen) {
			if (neueStrasse.isPerform()) {
				this.impl.addBab(neueStrasse.getEntity(), editorId);
			}
			for (NetzPunktDTO npdto : neueStrasse.getNetzPunkte()) {
				this.impl.addBabPunkt(npdto.getEntity(), editorId);
			}
			for (AbschnittDTO adto : neueStrasse.getAbschnitte()) {
				this.impl.addBabAbs(adto.getEntity(), 0, editorId);
			}
		}

		// 2. Update
		List<StrasseDTO> upzudatendeStrassen = neuesNetz.getUpdateList();
		for (StrasseDTO oldStrasse : upzudatendeStrassen) {
			if (oldStrasse.isPerform()) {
				this.impl.updateBab(oldStrasse.getEntity(), editorId);
			}
			for (NetzPunktDTO npdto : oldStrasse.getNetzPunkte()) {
				this.impl.udpateBabPunkt(npdto.getEntity(), editorId);
			}
			for (AbschnittDTO adto : oldStrasse.getAbschnitte()) {
				this.impl.updateBabAbs(adto.getEntity(), editorId);
			}
		}

		// 3. Delete
		List<StrasseDTO> zuloeschendeStrassen = neuesNetz.getDeleteList();
		for (StrasseDTO oldStrasse : zuloeschendeStrassen) {
			if (oldStrasse.isPerform()) {
				this.impl.deleteBab(oldStrasse.getEntity(), editorId);
			}
			for (NetzPunktDTO npdto : oldStrasse.getNetzPunkte()) {
				this.impl.deleteBabPunkt(npdto.getEntity(), editorId);
			}
			for (AbschnittDTO adto : oldStrasse.getAbschnitte()) {
				this.impl.deleteBabAbs(adto.getEntity(), editorId);
			}
		}
	}

	public List<KnotenTyp> getAllKnotenTypen() throws DBLayerException {
		return this.impl.getAllKnotenTypen();
	}

	@Transactional(isolation = Isolation.SERIALIZABLE, readOnly = false)
	public Abschnitt getAbschnittByNetzPunktNames(String nameNP1,
			String nameNP2, char roadTyp, int roadNr) throws DBLayerException {
		Abschnitt ret = this.impl.getAbschnittByNetzPunktNames(nameNP1,
				nameNP2, roadTyp, roadNr);
		return ret;
	}

	public List<Abschnitt> getAllAbschnittByStrasse(char typ, int nummer,
			String richtung) throws DBLayerException {
		return this.impl.getAllAbschnittByStrasse(typ, nummer, richtung);
	}

	public List<de.scoopgmbh.bms.db.dto.zuordnung.AbschnittDTO> getAllAbschnittByStreckeBlock(
			char typ, int nummer, int strecke, int block, String richtung) {
		List<Abschnitt> abschnitte = this.impl.getAllAbschnittByStreckeBlock(
				typ, nummer, strecke, block, richtung);
		List<de.scoopgmbh.bms.db.dto.zuordnung.AbschnittDTO> ret = new ArrayList<de.scoopgmbh.bms.db.dto.zuordnung.AbschnittDTO>(
				abschnitte.size());
		for (Abschnitt a : abschnitte) {
			de.scoopgmbh.bms.db.dto.zuordnung.AbschnittDTO ab = new de.scoopgmbh.bms.db.dto.zuordnung.AbschnittDTO(
					a);
			NetzPunkt np1, np2;
			try {
				np1 = this.impl.getNetzPunktById(a.getIdKnotenPunkt1());
				np2 = this.impl.getNetzPunktById(a.getIdKnotenPunkt2());
			} catch (Exception e) {
				continue;
			}
			ab.setNetzPunkt1(np1);
			ab.setNetzPunkt2(np2);
			ab.setStrassenTyp(typ);
			ab.setStrassenNummer(nummer);
			ret.add(ab);
		}
		return ret;
	}

	@Transactional(readOnly = false)
	public void updateZurodnungen(ZuordnungsImportDTO zidto, long loginId)
			throws DBLayerException {
		if (zidto == null)
			return;

		for (AutobahnMeistereiDTO amdto : zidto.getMeistereien()) {
			for (ZuordnungsDTO zdto : amdto.getZuordnungen()) {
				if (zdto.isForInsert()) {
					zdto.getEntity().setAutoritaetenId(amdto.getId());
					this.impl.addZuordnung(zdto.getEntity(), loginId);
				} else if (zdto.isForUpdate()) {
					zdto.getEntity().setAutoritaetenId(amdto.getId());
					this.impl.updateZuordnung(zdto.getEntity(), loginId);
				} else if (zdto.isForDelete()) {
					this.impl.deleteZuordnung(zdto.getEntity(), loginId);
				}
			}
		}
	}

	public List<Zuordnung> getZuordnungen(long autoritaetId) {
		return this.impl.getZuordnungen(autoritaetId);
	}
}
