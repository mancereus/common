package de.scoopgmbh.bms.db.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import com.avaje.ebean.annotation.CacheStrategy;

@CacheStrategy(readOnly = true)
@Entity
@Table(name = "STRASSEN")
public class Strasse implements Serializable {

	private static final long serialVersionUID = -4144427515531618014L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GLOBAL_SEQUENCE")
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

	@Column(name = "NUMMER")
	private int nummer;

	@Column(name = "FERNZIEL_NO")
	private String fernzielNO;

	@Column(name = "FERNZIEL_SW")
	private String fernzielSW;

	@Column(name = "TYP")
	private char typ;

	public Strasse() {
	}

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

	public void setFernzielNO(String fernziel1) {
		this.fernzielNO = fernziel1;
	}

	public String getFernzielNO() {
		return fernzielNO;
	}

	public void setFernzielSW(String fernziel2) {
		this.fernzielSW = fernziel2;
	}

	public String getFernzielSW() {
		return fernzielSW;
	}

	public void setTyp(char typ) {
		this.typ = typ;
	}

	public char getTyp() {
		return typ;
	}

	public void setNummer(int nummer) {
		this.nummer = nummer;
	}

	public int getNummer() {
		return nummer;
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
		Strasse other = (Strasse) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Strasse [id=" + id + ", nummer=" + nummer + ", typ=" + typ
				+ "]";
	}

	public void setDeleted(char deleted) {
		this.deleted = deleted;
	}

	public char getDeleted() {
		return deleted;
	}

}
