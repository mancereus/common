package de.scoopgmbh.bms.db.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * @author hirtech
 * @version 1.0
 * @created 06-Sep-2010 10:47:35
 */
@Entity
@Table(name = "REFGANGLINIEN")
public class ReferenzGangLinie implements Serializable {

	private static final long serialVersionUID = -7380477133301539072L;

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
	@Column(name = "MESSSQUERSCHNITTE_ID")
	@ManyToOne
	private Messquerschnitt idMessquerschnitt;
	@Column(name = "EVENT_KLASSEN_ID")
	@ManyToOne
	private EventKlasse idEventKlasse;

	public ReferenzGangLinie() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public EventKlasse getIdEventKlasse() {
		return idEventKlasse;
	}

	public void setIdEventKlasse(EventKlasse idEventKlasse) {
		this.idEventKlasse = idEventKlasse;
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
		ReferenzGangLinie other = (ReferenzGangLinie) obj;
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

	public void setIdMessquerschnitt(Messquerschnitt idMessquerschnitt) {
		this.idMessquerschnitt = idMessquerschnitt;
	}

	public Messquerschnitt getIdMessquerschnitt() {
		return idMessquerschnitt;
	}

}