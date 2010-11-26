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
@Table
public class Rampenskizze implements Serializable {

	private static final long serialVersionUID = 5186036327406788564L;

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
	@Column(name = "SKIZZEN_ID")
	private Skizze skizzenId;
	@Column(name = "ABSCHNITTE_ID")
	private Abschnitt abschnittId;
	@Column(name = "BEZEICHNUNG")
	private String bezeichnung;
	@Column(name = "TYP")
	private String typ;
	@Column(name = "KOORDINATEN")
	private String koordinaten;

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

	public char getDeleted() {
		return deleted;
	}

	public void setDeleted(char deleted) {
		this.deleted = deleted;
	}

	public Skizze getSkizzenId() {
		return skizzenId;
	}

	public void setSkizzenId(Skizze skizzenId) {
		this.skizzenId = skizzenId;
	}

	public Abschnitt getAbschnittId() {
		return abschnittId;
	}

	public void setAbschnittId(Abschnitt abschnittId) {
		this.abschnittId = abschnittId;
	}

	public String getBezeichnung() {
		return bezeichnung;
	}

	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}

	public String getTyp() {
		return typ;
	}

	public void setTyp(String typ) {
		this.typ = typ;
	}

	public String getKoordinaten() {
		return koordinaten;
	}

	public void setKoordinaten(String koordinaten) {
		this.koordinaten = koordinaten;
	}

}
