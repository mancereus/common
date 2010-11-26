package de.scoopgmbh.bms.db.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * @author hirtech
 * @version 1.0
 * @created 06-Sep-2010 10:48:01
 */
@Entity
@Table(name = "EVENT_KLASSEN")
public class EventKlasse implements Serializable {

	private static final long serialVersionUID = -2350675951853122779L;

	public static final String STANDARD = "Standard";

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
	/**
	 * NUMMER DES TAGES IN DER WOCHE AMERIKANISCH
	 * 0=Sonntag;1=Montag;...6=Samstag
	 */
	@Column(name = "TAG_IN_WOCHE")
	private int tagInWoche;

	/**
	 * PRIORITÄT DER EVENTKLASSE BEI GLEICHZEITIGEN AUFTRENTEN VON EVENTS. HOHE
	 * PRIORITÄT HAT VORANG
	 */
	@Column(name = "PRIO")
	private int prio;
	@Column(name = "BEZEICHNUNG")
	private String bezeichnung;
	@Column(name = "DATUMSBEZOGEN")
	private char istKlasseDatumsbezogen = DB.NO;
	@Column(name = "BESCHREIBUNG")
	private String beschreibung;
	@Column(name = "EXTEVTID")
	private long externalId;

	public EventKlasse() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getTagInWoche() {
		return tagInWoche;
	}

	public void setTagInWoche(int tagInWoche) {
		this.tagInWoche = tagInWoche;
	}

	public int getPrio() {
		return prio;
	}

	public void setPrio(int prio) {
		this.prio = prio;
	}

	public String getBezeichnung() {
		return bezeichnung;
	}

	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}

	public char getIstKlasseDatumsbezogen() {
		return istKlasseDatumsbezogen;
	}

	public void setIstKlasseDatumsbezogen(char istKlasseDatumsbezogen) {
		this.istKlasseDatumsbezogen = istKlasseDatumsbezogen;
	}

	public String getBeschreibung() {
		return beschreibung;
	}

	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
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
		EventKlasse other = (EventKlasse) obj;
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

}