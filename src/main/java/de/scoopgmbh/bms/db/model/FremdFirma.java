package de.scoopgmbh.bms.db.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

/**
 * @author hirtech
 * @version 1.0
 * @created 06-Sep-2010 10:48:29
 */
@Entity
@DiscriminatorValue("FREMDFIRMA")
public class FremdFirma extends Autoritaet implements Serializable {

	private static final long serialVersionUID = -5889817896120788599L;

	@ManyToMany(mappedBy = "fremdFirmen", targetEntity = AutobahnMeisterei.class, cascade = { CascadeType.ALL })
	private List<AutobahnMeisterei> autobahnMeistereien;

	public FremdFirma() {
		super();
	}

	public void setAutobahnMeistereien(
			List<AutobahnMeisterei> autobahnMeistereien) {
		this.autobahnMeistereien = autobahnMeistereien;
	}

	public List<AutobahnMeisterei> getAutobahnMeistereien() {
		return autobahnMeistereien;
	}

}
