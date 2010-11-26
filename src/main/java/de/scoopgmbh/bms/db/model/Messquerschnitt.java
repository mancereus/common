package de.scoopgmbh.bms.db.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * @author hirtech
 * @version 1.0
 * @created 06-Sep-2010 10:47:39
 */
@Entity
@Table(name = "MESSQUERSCHNITTE")
public class Messquerschnitt implements Serializable {

	private static final long serialVersionUID = 566975727477484754L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GLOBAL_SEQUENCE")
	private long id;
	/**
	 * Id des Benutzer der den Datensatz angelegt hat.
	 */
	@Column(name = "LOGINS_ID")
	private long editorId;
	/**
	 * F�r optimistic locking.
	 */
	@Version
	@Column(name = "VERSION")
	private long version;
	@Column(name = "DELETED")
	private char deleted = DB.NO;

	@Column(name = "BEZEICHNUNG")
	private String bezeichnung;

	@OneToMany(cascade = { CascadeType.ALL })
	private List<ReferenzGangLinie> referenzGangLinien;

	@Column(name = "abschnitt_ID")
	@ManyToOne
	private Abschnitt autobahnAbschnitt;

	public Messquerschnitt() {

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getBezeichnung() {
		return bezeichnung;
	}

	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}

	public List<ReferenzGangLinie> getReferenzGangLinien() {
		return referenzGangLinien;
	}

	public void setReferenzGangLinien(List<ReferenzGangLinie> referenzGangLinien) {
		this.referenzGangLinien = referenzGangLinien;
	}

	public Abschnitt getAutobahnAbschnitt() {
		return autobahnAbschnitt;
	}

	public void setAutobahnAbschnitt(Abschnitt autobahnAbschnitt) {
		this.autobahnAbschnitt = autobahnAbschnitt;
	}

	public void setEditorId(long editorId) {
		this.editorId = editorId;
	}

	public long getEditorId() {
		return editorId;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public long getVersion() {
		return version;
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
		Messquerschnitt other = (Messquerschnitt) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public void setDeleted(char deleted) {
		this.deleted = deleted;
	}

	public char getDeleted() {
		return deleted;
	}

}