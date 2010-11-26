package de.scoopgmbh.bms.core.importing.impl.rechenregel;

import java.util.LinkedList;
import java.util.List;

import de.scoopgmbh.bms.core.importing.impl.AbstractCSVLoader.CSVRowIterator;
import de.scoopgmbh.bms.core.importing.impl.CSVRow;
import de.scoopgmbh.bms.core.importing.impl.CSVRow.ValueHolder;
import de.scoopgmbh.bms.core.importing.impl.Matcher;
import de.scoopgmbh.bms.db.DB;
import de.scoopgmbh.bms.db.dto.rechenregel.RechenRegelDTO;
import de.scoopgmbh.bms.db.dto.rechenregel.RechenRegelImportDTO;
import de.scoopgmbh.bms.db.dto.streckennetz.AbschnittDTO;
import de.scoopgmbh.bms.db.model.Abschnitt;
import de.scoopgmbh.bms.db.model.RechenRegelMessquerschnitt;
import de.scoopgmbh.bms.db.model.RechenRegelNode;
import de.scoopgmbh.bms.db.service.IdGeneratorService;
import de.scoopgmbh.bms.db.service.RechenRegelService;
import de.scoopgmbh.bms.db.service.StreckenNetzService;

class RechenRegelImportMatcher implements Matcher<RechenRegelImportDTO> {

	private final IdGeneratorService idgen;
	private final StreckenNetzService sns;

	private RechenRegelImportDTO data = null;
	private final RechenRegelService rrs;

	RechenRegelImportMatcher(final IdGeneratorService idgen, final StreckenNetzService sns, final RechenRegelService rrs) {
		this.idgen = idgen;
		this.sns = sns;
		this.rrs = rrs;
	}

	/* @Override */
	public void matchWithDatabase(final CSVRowIterator iter) {

		List<RechenRegelDTO> rrnList = new LinkedList<RechenRegelDTO>();
		List<AbschnittDTO> absList = new LinkedList<AbschnittDTO>();
		
		this.data = new RechenRegelImportDTO();
		RechenRegelDTO rr = null;
		Abschnitt abs = null;
		AbschnittDTO absDto = null;

		List<CSVRow> notImported = new LinkedList<CSVRow>();
		while(iter.hasNext()) {
			// 1. Zeile holen
			CSVRow r = iter.next();

			// 2. Abschnitte und Rechenregeln in Datenbank suchen
			try {
				abs = findAbschnitt(r);
				rr = buildRechenRegel (r, abs.getId());
				abs.setRechenRegelId(rr.getNewRRTree().getId());
			} catch (Exception e) {
				e.printStackTrace();
				notImported.add(r);
				continue;
			}

			absDto = new AbschnittDTO();
			absDto.setForUpdate();
			absDto.setEntity(abs);

			absList.add(absDto);
			rrnList.add(rr);
			iter.remove();
		}

		data.setNodes(rrnList);
		data.setAbschnitte(absList);
	}

	/* @Override */
	public RechenRegelImportDTO getResult() {
		return this.data;
	}

	private Abschnitt findAbschnitt(final CSVRow r) throws Exception {
		Abschnitt ret = null;

		Integer roadNr = (Integer) r.get(RechenRegelLoader.ROADNO).getExpectation().getConvertedValue(r.get(RechenRegelLoader.ROADNO).getValue());
		char roadTyp = r.get(RechenRegelLoader.ROADTYP).getValue().charAt(0);
		String knt1 = r.get(RechenRegelLoader.KNT_NAME_VON).getValue();
		String knt2 = r.get(RechenRegelLoader.KNT_NAME_BIS).getValue();

		try {
			ret = this.sns.getAbschnittByNetzPunktNames(knt1, knt2, roadTyp, roadNr);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}

		if (ret==null) {
			throw new Exception();
		}

		return ret;
	}

	private RechenRegelDTO buildRechenRegel(final CSVRow r, final long abschnittId) throws Exception {
		RechenRegelDTO ret = new RechenRegelDTO();

		RechenRegelNode rrn = null;
		List<RechenRegelNode> rrnList = null;

		rrnList = this.rrs.getRechenRegelForAbschnittId(abschnittId);
		if (rrnList.size()>0) {
			rrn = this.buildInnerNode(rrnList);
		}

		ret.setOldRRTree(rrn);

		ValueHolder vh = r.get(RechenRegelLoader.ZST1);
		String mq1 = vh.getValue();

		vh = r.get(RechenRegelLoader.OP1);
		String op1 = vh.getValue();

		vh = r.get(RechenRegelLoader.ZST2);
		String mq2 = vh.getValue();

		vh = r.get(RechenRegelLoader.OP2);
		String op2 = vh.getValue();

		vh = r.get(RechenRegelLoader.ZST3);
		String mq3 = vh.getValue();

		if (mq1==null || mq1.length()==0) {
			throw new Exception("Kein Messquerschnitt gefunden. Es muss mindestens ein Messquerschnitt angegeben sein.");
		}

		if (mq2==null || mq2.length()==0) {
			// Rechenregel besteht nur aus den Werten eines Messquerschnittes
			RechenRegelMessquerschnitt tmp = new RechenRegelMessquerschnitt();
			tmp.setMqName(mq1);
			tmp.setId(idgen.getNextId());
			tmp.setIdAbschnitt(abschnittId);
			rrn = tmp;
		} else if (mq3==null || mq3.length()==0) {
			// genau 2 MQ
			if (op1==null || op1.length()==0) {
				throw new Exception("Zwei Messquerschnitte angegeben aber der Operator1 fehlt: r=" + r);
			}
			rrn = buildInnerNode(mq1, mq2, op1.charAt(0), abschnittId);
		} else {
			// genau 3 MQ
			if (op1==null || op1.length()==0) {
				throw new Exception("Drei Messquerschnitte angegeben aber der Operator1 fehlt: r=" + r);
			}
			if (op2==null || op2.length()==0) {
				throw new Exception("Drei Messquerschnitte angegeben aber der Operator2 fehlt: r=" + r);
			}
			// Rechenregel besteht aus den Werten dreier Messquerschnitte
			rrn = buildInnerNode(mq1, op1.charAt(0), abschnittId);
			rrn.setRightNode(buildInnerNode(mq2, mq3, op2.charAt(0), abschnittId));
		}

		ret.setNewRRTree(rrn);
		return ret;
	}

	private RechenRegelNode buildInnerNode(final String mq1, final String mq2, final char op, final long abschnittId) {

		// Rechenregel besteht aus den Werten zweier Messquerschnitte
		RechenRegelNode rrn = new RechenRegelNode();
		rrn.setDeleted(DB.NO);
		rrn.setId(idgen.getNextId());
		rrn.setVersion(0);
		rrn.setIdAbschnitt(abschnittId);

		RechenRegelMessquerschnitt tmp1 = new RechenRegelMessquerschnitt();
		tmp1.setMqName(mq1);
		tmp1.setId(idgen.getNextId());
		tmp1.setIdAbschnitt(abschnittId);

		RechenRegelMessquerschnitt tmp2 = new RechenRegelMessquerschnitt();
		tmp2.setMqName(mq2);
		tmp2.setId(idgen.getNextId());
		tmp2.setIdAbschnitt(abschnittId);

		rrn.setLeftNode(tmp1);
		rrn.setRightNode(tmp2);
		rrn.setOperator(op);

		return rrn;
	}

	private RechenRegelNode buildInnerNode(final String mq1, final char op, final long abschnittId) {

		// Rechenregel besteht aus dem Wert nur eines Messquerschnittes
		RechenRegelNode rrn = new RechenRegelNode();
		rrn.setDeleted(DB.NO);
		rrn.setId(idgen.getNextId());
		rrn.setVersion(0);
		rrn.setIdAbschnitt(abschnittId);

		RechenRegelMessquerschnitt tmp1 = new RechenRegelMessquerschnitt();
		tmp1.setMqName(mq1);
		tmp1.setId(idgen.getNextId());
		tmp1.setIdAbschnitt(abschnittId);

		rrn.setLeftNode(tmp1);
		rrn.setOperator(op);

		return rrn;
	}

	private RechenRegelNode buildInnerNode(final List<RechenRegelNode> rrnList) throws Exception {
		// Null ist kein Baum
		if (rrnList==null) {
			throw new Exception("Falsches Format.");
		}

		// Baum der Groesse 1 ist nur ein Knoten
		if (rrnList.size()==1) {
			return rrnList.get(0);
		}

		// Der vollstaendige Baum hat immer eine ungerade Anzahl Elemente
		if (rrnList.size()%2!=1) {
			throw new Exception("Falsches Format.");
		}

		List<RechenRegelNode> rrnList2 = new LinkedList<RechenRegelNode>();
		RechenRegelNode t = null;

		for(RechenRegelNode rrn : rrnList) {
			t = getUpperParent(rrnList2, rrn);
			if (t==null) {
				rrnList2.add(rrn);
				continue;
			} else {
				if (t.getIdChildLeft()==rrn.getId()) {
					t.setLeftNode(rrn);
				} else {
					t.setRightNode(rrn);
				}
			}
		}

		RechenRegelNode t2 = null;
		while(rrnList2.size()>1) {
			t2 = rrnList2.remove(0);
			t = getUpperParent(rrnList2, t2);
			if (t==null) {
				rrnList2.add(t2);
				throw new Exception();
			} else {
				if (t.getIdChildLeft()==t2.getId()) {
					t.setLeftNode(t2);
				} else {
					t.setRightNode(t2);
				}
			}
		}

		return rrnList2.get(0);
	}

	private RechenRegelNode getUpperParent(final List<RechenRegelNode> rootNodeList, final RechenRegelNode child) {
		RechenRegelNode tmp = null;
		if (rootNodeList==null) {
			return tmp;
		}
		for(RechenRegelNode rrn : rootNodeList) {
			tmp = getUpperLeftParent(rrn, child);
			if (tmp!=null) {
				return tmp;
			}
			tmp = getUpperRightParent(rrn, rrn);
		}
		return tmp;
	}

	private RechenRegelNode getUpperRightParent(final RechenRegelNode rootNode, final RechenRegelNode child) {
		if (rootNode==null) {
			return null;
		}
		if (rootNode.getIdChildLeft()==child.getId()) {
			return rootNode;
		}
		return getUpperRightParent(rootNode.getLeftNode(), child);
	}

	private RechenRegelNode getUpperLeftParent(final RechenRegelNode rootNode, final RechenRegelNode child) {
		if (rootNode==null) {
			return null;
		}
		if (rootNode.getIdChildRigth()==child.getId()) {
			return rootNode;
		}
		return getUpperLeftParent(rootNode.getRightNode(), child);
	}

	public List<CSVRow> getFailedRows() {
		// TODO Auto-generated method stub
		return null;
	}

}
