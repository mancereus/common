package de.scoopgmbh.bms.db.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 * @author hirtech
 * @version 1.0
 * @created 06-Sep-2010 10:48:03
 */
@Entity
@Table(name = "EVENTS")
public class Event implements Serializable {

	private static final long serialVersionUID = 4503878067771672859L;

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
	@Column(name = "EVENT_KLASSEN_ID")
	private long idEventKlasse;
	@Column(name = "NAME")
	private String name;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATUM")
	private Date datum;
	@Column(name = "EXTEVID")
	private long externalId;
	@Column(name = "EXTFKID")
	private long externalFkId;

	@ManyToOne
	private EventKlasse eventKlasse;

	public Event() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getIdEventKlasse() {
		return idEventKlasse;
	}

	public void setIdEventKlasse(long idEventKlasse) {
		this.idEventKlasse = idEventKlasse;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public void setEventKlasse(EventKlasse eventKlasse) {
		this.eventKlasse = eventKlasse;
	}

	public EventKlasse getEventKlasse() {
		return eventKlasse;
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
		Event other = (Event) obj;
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

	public void setExternalId(long externalId) {
		this.externalId = externalId;
	}

	public long getExternalId() {
		return externalId;
	}

	public void setExternalFkId(long externalFkId) {
		this.externalFkId = externalFkId;
	}

	public long getExternalFkId() {
		return externalFkId;
	}

}