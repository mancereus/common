package de.scoopgmbh.bms.db.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

/**
 * @author mmr
 * 
 */
@Entity
@DiscriminatorValue("AUTOBAHNMEISTEREIEN")
public class AutobahnMeisterei extends Autoritaet implements Serializable {

	private static final long serialVersionUID = -6348797707655730959L;

	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "FREMDFIRMA_AM", joinColumns = @JoinColumn(name = "AUTHORITAET_ID_AM"), inverseJoinColumns = @JoinColumn(name = "AUTHORITAET_ID_FF"))
	private List<FremdFirma> fremdFirmen;

	@Transient
	private List<Zuordnung> autobahnabschnitte;

	public AutobahnMeisterei() {
		super();
	}

	public void setFremdFirmen(List<FremdFirma> fremdFirmen) {
		this.fremdFirmen = fremdFirmen;
	}

	public List<FremdFirma> getFremdFirmen() {
		return fremdFirmen;
	}

	public void setAutobahnabschnitte(List<Zuordnung> autobahnabschnitte) {
		this.autobahnabschnitte = autobahnabschnitte;
	}

	public List<Zuordnung> getAutobahnabschnitte() {
		return autobahnabschnitte;
	}

}
