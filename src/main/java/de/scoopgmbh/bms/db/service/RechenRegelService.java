package de.scoopgmbh.bms.db.service;

import java.util.List;

import de.scoopgmbh.bms.db.model.Abschnitt;
import de.scoopgmbh.bms.db.model.RechenRegelNode;

public interface RechenRegelService {

	public List<RechenRegelNode> getRechenRegelForAbschnittId(long abschnittId);

	public RechenRegelNode getRechenRegelForAbschnittIdAsTree(long abschnittId)
			throws RuntimeException;

	void updateRechenRegeln(List<RechenRegelNode> rechenregelen,
			List<Abschnitt> abschnitte, long editorId);

}
