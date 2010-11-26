package de.scoopgmbh.bms.db.service.impl;

import java.util.LinkedList;
import java.util.List;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.annotation.Transactional;
import com.google.inject.Inject;

import de.scoopgmbh.bms.db.model.RechenRegelKonstante;
import de.scoopgmbh.bms.db.model.RechenRegelMessquerschnitt;
import de.scoopgmbh.bms.db.model.RechenRegelNode;
import de.scoopgmbh.bms.db.service.RechenRegelService;

public class RechenRegelServiceImpl extends EbeanDao<RechenRegelNode> implements
		RechenRegelService {

	@Inject
	public RechenRegelServiceImpl(EbeanServer ebean) {
		super(ebean);
	}

	public List<RechenRegelNode> getRechenRegelForAbschnittId(long abschnittId) {
		return this.impl.getRechenRegelForAbschnittId(abschnittId);
	}

	public RechenRegelNode getRechenRegelForAbschnittIdAsTree(long abschnittId)
			throws RuntimeException {
		List<RechenRegelNode> completNodeList = this.impl
				.getRechenRegelForAbschnittId(abschnittId);

		// Null ist kein Baum
		if (completNodeList == null)
			throw new RuntimeException("Falsches Format. Liste leer.");

		// Baum der größe 1 ist nur ein Knoten
		if (completNodeList.size() == 1)
			return completNodeList.get(0);

		// Der vollständige Baum hat immer eine ungerade Anzahl Elemente
		if (completNodeList.size() % 2 != 1)
			throw new RuntimeException("Falsches Format. Gerade Anzahl Knoten.");

		// Den Baum in zwei Phasen aufbauen
		List<RechenRegelNode> innerNodeList = new LinkedList<RechenRegelNode>();
		RechenRegelNode t = null;

		// Phase 1: alle Leaf-Knoten einem Inner-Knoten zuweisen und
		// alle Inner-Knoten in die "innerNodeList" legen
		while (hasInnerNodes(completNodeList)) {
			t = getFirstInnerNode(completNodeList);
			t.setLeftNode(getLeftLeafNode(t, completNodeList));
			t.setRightNode(getRightLeafNode(t, completNodeList));
			innerNodeList.add(t);
		}

		// Phase 2: alle Inner-Knoten untereinander verbinden. Der letzte
		// Knoten der in der "innerNodeList" übrig bleibt ist der root-Knoten
		while (innerNodeList.size() != 1) {
			t = getFirstInnerNode(innerNodeList);
			if (t.getLeftNode() == null)
				t.setLeftNode(getLeftInnerNode(t, innerNodeList));
			else if (t.getRightNode() == null)
				t.setRightNode(getRightInnerNode(t, innerNodeList));
			else
				putNodeBackToTheEndOfList(t, innerNodeList);
		}

		return innerNodeList.get(0);
	}

	/* @Override */
	@Transactional(isolation = Isolation.READ_COMMITTED, readOnly = false)
	public void updateRechenRegeln(RechenRegelImportDTO rrid, long editorId) {
		for (RechenRegelDTO rrd : rrid.getNodes()) {
			if (rrd.getOldRRTree() != null)
				this.deleteRRNode(rrd.getOldRRTree(), editorId);
			if (rrd.getNewRRTree() != null)
				this.insertRRNode(rrd.getNewRRTree(), editorId);
		}

		for (AbschnittDTO adto : rrid.getAbschnitte()) {
			streckenDao.updateBabAbs(adto.getEntity(), editorId);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	private void insertRRNode(RechenRegelNode rrn, long editorId) {
		this.impl.insertRechenRegel(rrn, editorId);
		if (rrn.getLeftNode() != null)
			insertRRNode(rrn.getLeftNode(), editorId);
		if (rrn.getRightNode() != null)
			insertRRNode(rrn.getRightNode(), editorId);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	private void deleteRRNode(RechenRegelNode rrn, long editorId) {
		this.impl.deleteRechenRegeln(rrn, editorId);
		if (rrn.getLeftNode() != null)
			deleteRRNode(rrn.getLeftNode(), editorId);
		if (rrn.getRightNode() != null)
			deleteRRNode(rrn.getRightNode(), editorId);
	}

	// ============================================================
	// HELPER
	// ============================================================

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	private boolean hasInnerNodes(List<RechenRegelNode> sourceList) {
		for (RechenRegelNode rrn : sourceList) {
			if (!(rrn instanceof RechenRegelMessquerschnitt)
					&& !(rrn instanceof RechenRegelKonstante))
				return true;
		}
		return false;
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	private RechenRegelNode getFirstInnerNode(List<RechenRegelNode> sourceList) {
		for (RechenRegelNode rrn : sourceList) {
			if (!(rrn instanceof RechenRegelMessquerschnitt)
					&& !(rrn instanceof RechenRegelKonstante)
					&& sourceList.remove(rrn)) {
				return rrn;
			}
		}
		throw new RuntimeException("Na sowas. "
				+ "Keinen inneren Knoten in der Liste "
				+ "gefunden oder Löschen schlug fehl.");
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	private RechenRegelNode getLeftLeafNode(RechenRegelNode parent,
			List<RechenRegelNode> sourceList) {
		for (RechenRegelNode rrn : sourceList) {
			if (rrn.getId() == parent.getIdChildLeft()
					&& ((rrn instanceof RechenRegelMessquerschnitt) || (rrn instanceof RechenRegelKonstante)))
				return rrn;
		}
		return null;
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	private RechenRegelNode getRightLeafNode(RechenRegelNode parent,
			List<RechenRegelNode> sourceList) {
		for (RechenRegelNode rrn : sourceList) {
			if (rrn.getId() == parent.getIdChildRigth()
					&& ((rrn instanceof RechenRegelMessquerschnitt) || (rrn instanceof RechenRegelKonstante)))
				return rrn;
		}
		return null;
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	private RechenRegelNode getLeftInnerNode(RechenRegelNode parent,
			List<RechenRegelNode> sourceList) {
		for (RechenRegelNode rrn : sourceList) {
			if (rrn.getId() == parent.getIdChildLeft()
					&& (!(rrn instanceof RechenRegelMessquerschnitt) && !(rrn instanceof RechenRegelKonstante)))
				return rrn;
		}
		return null;
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	private RechenRegelNode getRightInnerNode(RechenRegelNode parent,
			List<RechenRegelNode> sourceList) {
		for (RechenRegelNode rrn : sourceList) {
			if (rrn.getId() == parent.getIdChildRigth()
					&& (!(rrn instanceof RechenRegelMessquerschnitt) && !(rrn instanceof RechenRegelKonstante)))
				return rrn;
		}
		return null;
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	private void putNodeBackToTheEndOfList(RechenRegelNode parent,
			List<RechenRegelNode> sourceList) {
		sourceList.add(parent);
	}

}
