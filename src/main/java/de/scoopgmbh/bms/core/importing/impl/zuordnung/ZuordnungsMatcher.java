package de.scoopgmbh.bms.core.importing.impl.zuordnung;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import de.scoopgmbh.bms.core.importing.impl.AbstractCSVLoader.CSVRowIterator;
import de.scoopgmbh.bms.core.importing.impl.CSVRow;
import de.scoopgmbh.bms.core.importing.impl.CSVRow.ValueHolder;
import de.scoopgmbh.bms.core.importing.impl.Matcher;
import de.scoopgmbh.bms.db.dto.zuordnung.AbschnittDTO;
import de.scoopgmbh.bms.db.dto.zuordnung.AutobahnMeistereiDTO;
import de.scoopgmbh.bms.db.dto.zuordnung.ZuordnungsDTO;
import de.scoopgmbh.bms.db.dto.zuordnung.ZuordnungsImportDTO;
import de.scoopgmbh.bms.db.model.Abschnitt;
import de.scoopgmbh.bms.db.model.AutobahnMeisterei;
import de.scoopgmbh.bms.db.model.Zuordnung;
import de.scoopgmbh.bms.db.service.AutoritaetsService;
import de.scoopgmbh.bms.db.service.IdGeneratorService;
import de.scoopgmbh.bms.db.service.StreckenNetzService;

public strictfp class ZuordnungsMatcher implements Matcher<ZuordnungsImportDTO> {

	private IdGeneratorService idgen;
	private AutoritaetsService as;
	private StreckenNetzService sns;
	private ZuordnungsImportDTO result;
	private List<CSVRow> failList;
	
	private NumberFormat nf = NumberFormat.getNumberInstance();
	{
		nf.setMaximumFractionDigits(3);
	}
	
	public ZuordnungsMatcher(IdGeneratorService idgen, AutoritaetsService as, StreckenNetzService sns) {
		this.idgen = idgen;
		this.as = as;
		this.sns = sns;
	}
	
	public ZuordnungsImportDTO getResult() {
		return this.result;
	}
	
	public List<CSVRow> getFailedRows() {
		return this.failList;
	}

	public void matchWithDatabase(CSVRowIterator iter) {
		
		// Initialisieren
		this.result = new ZuordnungsImportDTO();
		this.failList = new LinkedList<CSVRow>();
		
		// Die CSV durchlaufen.
		while(iter.hasNext()) {
			CSVRow r = iter.next();
			
			AutobahnMeistereiDTO amdto = null;
			List<ZuordnungsDTO> zdtoList = null;
			
			try {
//				System.out.println(r);
				amdto = this.findAutobahnMeisterei(r);
				
				if (amdto==null) {
					throw new RuntimeException("null");
				}
			
				zdtoList = this.findZuordnungen(r, amdto);
				if (zdtoList==null) {
					throw new RuntimeException("null");
				}
				
			} catch (Exception e) {
				this.failList.add(r);
				continue;
			}
//			System.out.println(zdtoList);
			
			amdto.getZuordnungen().addAll(zdtoList);			
		}
		
	}
	
	private AutobahnMeistereiDTO findAutobahnMeisterei(CSVRow r) {
		AutobahnMeistereiDTO ret = null;
		
		String amName = r.get(ZuordnungsLoader.MEISTEREI).getValue();
		AutobahnMeisterei am = null;
		try {
			am = this.as.getAutobahnMeistereiByName(amName);
		} catch (Exception e) {
			return null;
		}
		ret = new AutobahnMeistereiDTO();
		ret.setEntity(am);
		if (this.result.getMeistereien().contains(ret)) {
			ret = this.result.getMeistereien().get(this.result.getMeistereien().indexOf(ret));
		} else {
			this.result.getMeistereien().add(ret);
		}
		
		return ret;
	}

	private List<ZuordnungsDTO> findZuordnungen(CSVRow r, AutobahnMeistereiDTO amdto) throws RuntimeException {
		List<ZuordnungsDTO> ret = new LinkedList<ZuordnungsDTO>();
		
		/* Auslesen der Werte aus CSVRow */
		String strassenTyp = r.get(ZuordnungsLoader.ROADTYP).getValue();
		
		ValueHolder vh = r.get(ZuordnungsLoader.ROADNR);
		Integer strassenNr = (Integer) vh.getExpectation().getConvertedValue(vh.getValue());
		
		vh = r.get(ZuordnungsLoader.STRECKE);
		Integer strecke = (Integer) vh.getExpectation().getConvertedValue(vh.getValue());
		
		vh = r.get(ZuordnungsLoader.BLOCK);
		Integer block = (Integer) vh.getExpectation().getConvertedValue(vh.getValue());
		
		vh = r.get(ZuordnungsLoader.BKM_VON);
		Double bkmVon = (Double) vh.getExpectation().getConvertedValue(vh.getValue());
		
		vh = r.get(ZuordnungsLoader.BKM_BIS);
		Double bkmBis = (Double) vh.getExpectation().getConvertedValue(vh.getValue());
		
		/* Abschnitte NO holen */
		List<AbschnittDTO> abschnitte = this.sns.getAllAbschnittByStreckeBlock(strassenTyp.charAt(0), strassenNr, strecke, block, Abschnitt.NORDOST);
		if (abschnitte==null || abschnitte.size()==0) {
			return null;
		}
		
		for (int lauf = 0; lauf<=1; lauf++) {
			Collections.sort(abschnitte);
			/* Ermittelt werden die BKM der äußeren Grenzen */
			
			// FIXME die richtigen BKM nehmen !!! 
			double bkm1 = abschnitte.get(0).getNetzPunkt1().getBkm1();
			double bkm2 = abschnitte.get(abschnitte.size()-1).getNetzPunkt2().getBkm1();
			
			if ((bkm1-bkm2)<0) {
				// bkm2 ist größer bkm1
				if ((bkmVon-bkmBis)<0) {
					// bkmBis ist größer als bkmVon
					if ( ltOrEq(bkmBis,bkm2) && gtOrEq(bkmVon, bkm1)) {
						/*
						 * Streckenskizze:
						 * =======================================================
						 * = BKM:  0,0       11,5             34,5         99,5  =
						 * = PKT:  -|------|---,---|-------|----,---|--------|-  =
						 * = VAR:  bkm1     bkmVon           bkmBis        bkm2  =
						 * = IDX:   0      1       2       3        4        5   =
						 * =======================================================
						 * 
						 * */
						int maxIdx = getIdxUpperBorderAbschnitt(abschnitte, bkmBis);
						int minIdx = getIdxLowerBorderAbschnitt(abschnitte, bkmVon);
						if (minIdx<0 || maxIdx>(abschnitte.size()-1)) {
							throw new RuntimeException("Index out of bounds.");
						}
						ret.addAll(this.getZuordnungForwardOrdering(abschnitte, minIdx, maxIdx));
					} else {
						// In Zuordnung angegebene BKM liegen außerhalbe der Grenzen
						return null;
					}
				} else {
					// bkmVon ist größer als bkmBis
					if ( ltOrEq(bkmVon,bkm2) && gtOrEq(bkmBis, bkm1) ) {
						/*
						 * Streckenskizze:
						 * =======================================================
						 * = BKM:  0,0       11,5             34,5         99,5  =
						 * = PKT:  -|------|---,---|-------|----,---|--------|-  =
						 * = VAR:  bkm1     bkmBis           bkmVon        bkm2  =
						 * = IDX:   0      1       2       3        4        5   =
						 * =======================================================
						 * */
						int maxIdx = getIdxUpperBorderAbschnitt(abschnitte, bkmVon);
						int minIdx = getIdxLowerBorderAbschnitt(abschnitte, bkmBis);
						if (minIdx<0 || maxIdx>(abschnitte.size()-1)) {
							throw new RuntimeException("Index out of bounds.");
						}
						ret.addAll(this.getZuordnungForwardOrdering(abschnitte, minIdx, maxIdx));
						
					} else {
						// In Zuordnung angegebene BKM liegen außerhalbe der Grenzen
						return null;
					}
				}
			} else {
				// bkm1 ist größer als bkm2
				if ((bkmVon-bkmBis)<0) {
					// bkmBis ist größer als bkmVon
					if (ltOrEq(bkmBis, bkm1) && gtOrEq(bkmVon, bkm2) ) {
						/*
						 * Streckenskizze:
						 * =======================================================
						 * = BKM:  0,0       11,5             34,5         99,5  =
						 * = PKT:  -|------|---,---|-------|----,---|--------|-  =
						 * = VAR:  bkm2     bkmVon           bkmBis        bkm1  =
						 * = IDX:   5      4       3       2        1        0   =
						 * =======================================================
						 * */
						int maxIdx = getIdxUpperBorderAbschnittReverse(abschnitte, bkmVon);
						int minIdx = getIdxLowerBorderAbschnittReverse(abschnitte, bkmBis);
						if (minIdx<0 || maxIdx>(abschnitte.size()-1)) {
							throw new RuntimeException("Index out of bounds.");
						}
						ret.addAll(this.getZuordnungForwardOrdering(abschnitte, minIdx, maxIdx));
					} else {
						// In Zuordnung angegebene BKM liegen außerhalb der Grenzen
						return null;
					}
				} else {
					// bkmVon ist größer als bkmBis
					if ( ltOrEq(bkmVon, bkm1) && gtOrEq(bkmBis, bkm2) ) {
						/*
						 * Streckenskizze:
						 * =======================================================
						 * = BKM:  0,0       11,5             34,5         99,5  =
						 * = PKT:  -|------|---,---|-------|----,---|--------|-  =
						 * = VAR:  bkm2     bkmBis           bkmVon        bkm1  =
						 * = IDX:   5      4       3       2        1        0   =
						 * =======================================================
						 * */
						int maxIdx = getIdxUpperBorderAbschnittReverse(abschnitte, bkmBis);
						int minIdx = getIdxLowerBorderAbschnittReverse(abschnitte, bkmVon);
						if (minIdx<0 || maxIdx>(abschnitte.size()-1)) {
							throw new RuntimeException("Index out of bounds.");
						}
						ret.addAll(this.getZuordnungForwardOrdering(abschnitte, minIdx, maxIdx));
					} else {
						// In Zuordnung angegebene BKM liegen außerhalb der Grenzen
						return null;
					}
				}
			}
			
			/* Abschnitte SW holen - Die Gegenrichtung! */
			if (lauf!=1) {
				abschnitte = this.sns.getAllAbschnittByStreckeBlock(strassenTyp.charAt(0), strassenNr, strecke, block, Abschnitt.SUEDWEST);
			}
		}
		
		return ret;
	}
	
	private boolean ltOrEq(double d1, double d2) {
		long l1 = (long) (d1*1000); // Komma rechts verschieben, Nachkommastelle verwerfen
		long l2 = (long) (d2*1000); // Komma rechts verschieben, Nachkommastelle verwerfen
		return (l1<=l2);
	}
	
	private boolean gtOrEq(double d1, double d2) {
		long l1 = (long) (d1*1000); // Komma rechts verschieben, Nachkommastelle verwerfen
		long l2 = (long) (d2*1000); // Komma rechts verschieben, Nachkommastelle verwerfen
		return (l1>=l2);
	}
	
	private int getIdxUpperBorderAbschnittReverse(List<AbschnittDTO> abschnitte, double bkmUpperBoundary) {
		int i = abschnitte.size()-1;
		while ((i>=0) && (getBkmBis(abschnitte, i, abschnitte.size()-1)<bkmUpperBoundary)) {
			i--;
		}
		return (i!=(abschnitte.size()-1)?++i:i);
	}
	
	private int getIdxUpperBorderAbschnitt(List<AbschnittDTO> abschnitte, double bkmUpperBoundary) {
		int i = abschnitte.size()-1;
		while((i>=0) && gtOrEq(getBkmBis(abschnitte, i, abschnitte.size()-1), bkmUpperBoundary)) {
			i--;
		}
		return (i!=(abschnitte.size()-1)?++i:i);
	}
	
	private int getIdxLowerBorderAbschnittReverse(List<AbschnittDTO> abschnitte, double bkmLowerBoundary) {
		int j = 0;
		while((j<abschnitte.size()) && getBkmVon(abschnitte, j, abschnitte.size()-1)>bkmLowerBoundary) {
			j++;
		}
		return (j!=0?--j:j);
	}
	
	private int getIdxLowerBorderAbschnitt(List<AbschnittDTO> abschnitte, double bkmLowerBoundary) {
		int j = 0;
		while((j<abschnitte.size()) && ltOrEq(getBkmVon(abschnitte, j, abschnitte.size()-1), bkmLowerBoundary)) {
			j++;
		}
		return (j!=0?--j:j);
	}
	
	private List<ZuordnungsDTO> getZuordnungForwardOrdering(List<AbschnittDTO> abschnitte, int minIdx, int maxIdx) {
		List<ZuordnungsDTO> ret = new ArrayList<ZuordnungsDTO>(maxIdx-minIdx);
		ZuordnungsDTO tmp = null;
		for(int k = minIdx; k<=maxIdx; k++) {
			tmp = newZurodnungsDTO(abschnitte, k, abschnitte.size()-1);
			ret.add(tmp);
		}
		return ret;
	}
	
	private ZuordnungsDTO newZurodnungsDTO(List<AbschnittDTO> abschnitte, int idx, int maxIdx) {
		ZuordnungsDTO ret = new ZuordnungsDTO();
		Zuordnung z = new Zuordnung();
		z.setId(this.idgen.getNextId());
		z.setAbschnitteId(abschnitte.get(idx).getId());
		z.setPkt1BkmVon(getBkmVon(abschnitte, idx, maxIdx));
		z.setPkt2BkmBis(getBkmBis(abschnitte, idx, maxIdx));
		ret.setEntity(z);
		ret.setForInsert();
		return ret;
	}
	
	/**
	 * Von einem beliebigen Abschnitt wird der BKM vom ersten Knoten ermittelt.
	 * 
	 * @param abschnitt
	 * @param idx
	 * @param maxIdx
	 * @return
	 */
	private double getBkmVon(List<AbschnittDTO> abschnitte, int idx, int maxIdx) {
		AbschnittDTO abs = abschnitte.get(idx);
		boolean isEven = (abs.getStrassenNummer() % 2 == 0);
		String dir = abs.getEntity().getRichtung();
		double ret = 0L;
	
		if (dir.equals(Abschnitt.SUEDWEST)) {
			if (isEven) {
				// Gegenrichtung, Regeln entgegengesetzt anwenden
				ret = this.getBkmRevOfPkt1(abs);
			} else {
				// Idealrichtung
				if (idx==0) {
					ret = this.getBkmOfPk1(abs, abs);
				} else {
					ret = this.getBkmOfPk1(abs, abschnitte.get(idx-1));
				}
			}
		} else { // Abschnitt.NORDOST
			if (isEven) {
				// Idealrichtung
				if (idx==0) {
					ret = this.getBkmOfPk1(abs, abs);
				} else {
					ret = this.getBkmOfPk1(abs, abschnitte.get(idx-1));
				}
			} else {
				// Gegenrichtung, Regeln entgegengesetzt anwenden
				ret = this.getBkmRevOfPkt1(abs);
			}
		}
		
		return ret;
	}
	
	/**
	 * Von einem beliebigen Abschnitt wird der BKM vom hinteren Knoten ermittelt.
	 * 
	 * @param abschnitt
	 * @param idx
	 * @param maxIdx
	 * @return
	 */
	private double getBkmBis(List<AbschnittDTO> abschnitte, int idx, int maxIdx) {
		AbschnittDTO abs = abschnitte.get(idx);
		boolean isEven = (abs.getStrassenNummer() % 2 == 0);
		String dir = abs.getEntity().getRichtung();
		double ret = 0L;
	
		if (dir.equals(Abschnitt.SUEDWEST)) {
			if (isEven) {
				// Gegenrichtung, Regeln entgegengesetzt anwenden
				if (idx==maxIdx)
					ret = this.getBkmRevOfPkt2(abs, abs);
				else
					ret = this.getBkmRevOfPkt2(abs, abschnitte.get(idx+1));
			} else {
				// Idealrichtung
				ret = this.getBkmOfPk2(abs);
			}
		} else { // Abschnitt.NORDOST
			if (isEven) {
				// Idealrichtung
				ret = this.getBkmOfPk2(abs);
			} else {
				// Gegenrichtung, Regeln entgegengesetzt anwenden
				if (idx==maxIdx)
					ret = this.getBkmRevOfPkt2(abs, abs);
				else
					ret = this.getBkmRevOfPkt2(abs, abschnitte.get(idx+1));
			}
		}
		
		return ret;
	}
	
	private double getBkmOfPk1(AbschnittDTO actual, AbschnittDTO predecessor) {
		double ret = 0L;
		
		if (actual.getEntity().getStreckenNr() == predecessor.getEntity().getStreckenNr()) {
			if (actual.getEntity().getBlocknr() == predecessor.getEntity().getBlocknr()) {
				// gleicher Block = BKM 1
				ret = actual.getNetzPunkt1().getBkm1();
			} else {
				// Bei Blockwechsel müsste es BKM 2 sein
				ret = actual.getNetzPunkt1().getBkm2();
			}
		} else {
			// Bei Streckenwechsel müsste es BKM 1 sein
			ret = actual.getNetzPunkt1().getBkm1();
		}
		return ret;
	}
	
	private double getBkmOfPk2(AbschnittDTO actual) {
		return actual.getNetzPunkt2().getBkm1();
	}
	
	private double getBkmRevOfPkt1(AbschnittDTO actual) {
		return actual.getNetzPunkt1().getBkm1();
		
	}
	
	private double getBkmRevOfPkt2(AbschnittDTO actual, AbschnittDTO successor) {
		double ret = 0L;
		
		if (actual.getEntity().getStreckenNr() == successor.getEntity().getStreckenNr()) {
			if (actual.getEntity().getBlocknr() == successor.getEntity().getBlocknr()) {
				// gleicher Block = BKM 1
				ret = actual.getNetzPunkt2().getBkm1();
			} else {
				// Bei Blockwechsel müsste es BKM 2 sein
				ret = actual.getNetzPunkt2().getBkm2();
			}
		} else {
			// Bei Streckenwechsel müsste es BKM 1 sein
			ret = actual.getNetzPunkt2().getBkm1();
		}
		
		return ret;
	}
	
}
