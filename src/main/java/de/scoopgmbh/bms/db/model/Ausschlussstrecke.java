package de.scoopgmbh.bms.db.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * @author hirtech
 * @version 1.0
 * @created 06-Sep-2010 10:48:32
 */

@Entity
@Table(name = "AUSSCHLUSSSTRECKEN")
public class Ausschlussstrecke implements Serializable {

	private static final long serialVersionUID = 3027139983836332962L;

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

	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "AUSSCHLUSSSTRECKEN_ABSCHN", joinColumns = @JoinColumn(name = "AUSSCHLUSSSTRECKEN_ID"), inverseJoinColumns = @JoinColumn(name = "abschnitt_ID"))
	private List<Abschnitt> ausschluss;

	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "BAUSTRECKEN_ABSCHN", joinColumns = @JoinColumn(name = "AUSSCHLUSSSTRECKEN_ID"), inverseJoinColumns = @JoinColumn(name = "abschnitt_ID"))
	private List<Abschnitt> bebauungen;

	public Ausschlussstrecke() {
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setAusschluss(List<Abschnitt> ausschluss) {
		this.ausschluss = ausschluss;
	}

	public List<Abschnitt> getAusschluss() {
		return ausschluss;
	}

	public void setBebauungen(List<Abschnitt> bebauungen) {
		this.bebauungen = bebauungen;
	}

	public List<Abschnitt> getBebauungen() {
		return bebauungen;
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
		Ausschlussstrecke other = (Ausschlussstrecke) obj;
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