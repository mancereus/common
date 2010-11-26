package de.scoopgmbh.bms.db.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "ZUORDNUNG")
public class Zuordnung implements Serializable {

	private static final long serialVersionUID = -3946943719609268861L;

	@Id
	private long id;
	/**
	 * Id des Benutzer der den Datensatz angelegt hat.
	 */
	@Column(name = "LOGINS_ID")
	private long editorId;
	/**
	 * Für optimistic locking.
	 */
	@Version
	@Column(name = "VERSION")
	private long version;
	@Column(name = "DELETED")
	private char deleted = DB.NO;
	@Column(name = "AUTORITAETEN_ID")
	@ManyToOne
	private Autoritaet autoritaetenId;
	@Column(name = "ABSCHNITTE_ID")
	@ManyToOne
	private Abschnitt abschnitteId;
	@Column(name = "PKT1_KM_VON", columnDefinition = "NUMBER(7,3)")
	private double pkt1BkmVon;
	@Column(name = "PKT2_KM_BIS", columnDefinition = "NUMBER(7,3)")
	private double pkt2BkmBis;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getEditorId() {
		return editorId;
	}

	public void setEditorId(long editorId) {
		this.editorId = editorId;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public Autoritaet getAutoritaetenId() {
		return autoritaetenId;
	}

	public void setAutoritaetenId(Autoritaet autoritaetenId) {
		this.autoritaetenId = autoritaetenId;
	}

	public Abschnitt getAbschnitteId() {
		return abschnitteId;
	}

	public void setAbschnitteId(Abschnitt abschnitteId) {
		this.abschnitteId = abschnitteId;
	}

	public double getPkt1BkmVon() {
		return pkt1BkmVon;
	}

	public void setPkt1BkmVon(double pkt1BkmVon) {
		this.pkt1BkmVon = pkt1BkmVon;
	}

	public double getPkt2BkmBis() {
		return pkt2BkmBis;
	}

	public void setPkt2BkmBis(double pkt2BkmBis) {
		this.pkt2BkmBis = pkt2BkmBis;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Zuordnung other = (Zuordnung) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Zuordnung [id=" + id + ", autoritaetenId=" + autoritaetenId
				+ ", abschnitteId=" + abschnitteId + ", pkt1BkmVon="
				+ pkt1BkmVon + ", pkt2BkmBis=" + pkt2BkmBis + "]";
	}

	public void setDeleted(char deleted) {
		this.deleted = deleted;
	}

	public char getDeleted() {
		return deleted;
	}

}
