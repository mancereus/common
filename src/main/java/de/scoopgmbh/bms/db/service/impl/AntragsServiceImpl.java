package de.scoopgmbh.bms.db.service.impl;

import java.util.List;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.Expr;
import com.google.inject.Inject;

import de.scoopgmbh.bms.db.model.BaustellenAntrag;
import de.scoopgmbh.bms.db.model.BaustellenAntragPosition;
import de.scoopgmbh.bms.db.model.DB;
import de.scoopgmbh.bms.db.service.AntragsService;

public class AntragsServiceImpl extends EbeanDao<BaustellenAntrag> implements
		AntragsService {

	@Inject
	public AntragsServiceImpl(EbeanServer ebean) {
		super(ebean);
	}

	public void addFormular(BaustellenAntrag sf, long loginId) {
		sf.setEditorId(loginId);
		if (sf.getSperrAntraege() != null) {
			for (BaustellenAntragPosition pos : sf.getSperrAntraege()) {
				pos.setEditorId(loginId);
			}
		}
		ebean.save(sf);
	}

	public void deleteFormular(BaustellenAntrag sf, long loginId) {
		sf.setDeleted(DB.YES);
		if (sf.getSperrAntraege() != null) {
			for (BaustellenAntragPosition pos : sf.getSperrAntraege()) {
				pos.setDeleted(DB.YES);
			}
		}
		ebean.save(sf);
	}

	public void updateFormular(BaustellenAntrag sf, long loginId) {
		ebean.save(sf);
	}

	public BaustellenAntrag getFormular(long formularId) {
		return ebean.find(BaustellenAntrag.class, formularId);
	}

	public List<BaustellenAntrag> getFormularList() {
		return ebean.find(BaustellenAntrag.class)
				.where(Expr.eq("deleted", DB.NO)).findList();
	}

	public List<BaustellenAntragPosition> getAntragsPositionen(
			long baustellenAntragId) {
		return ebean.find(BaustellenAntragPosition.class)
				.where(Expr.eq("", baustellenAntragId)).findList();
	}
}
