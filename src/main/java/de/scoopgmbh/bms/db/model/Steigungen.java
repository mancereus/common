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
 * @created 06-Sep-2010 10:46:42
 */
@Entity
@Table(name = "STEIGUNGEN")
public class Steigungen implements Serializable {

	private static final long serialVersionUID = 5917271110154845045L;

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
	@Column(name = "strasse_ID")
	@ManyToOne
	private Strasse strasse;

	@Column(name = "BKM_VON", columnDefinition = "NUMBER(7,3)")
	private float betriebKiloMeterVon;

	@Column(name = "BKM_BIS", columnDefinition = "NUMBER(7,3)")
	private float betriebKiloMeterBis;

	@Column(name = "LAENGE", columnDefinition = "NUMBER(7,3)")
	private float laenge;

	/**
	 * Steigungsklasse in Richtung Süd-Osten
	 */
	@Column(name = "STEIGKL_DIR_NO")
	private int steigungsKlasseRichtungNordOst;

	/**
	 * Steigungsklasse in Richtung Nord-Westen
	 */
	@Column(name = "STEIGKL_DIR_SW")
	private int steigungsKlasseRichtungSuedWest;
	/**
	 * Anzahl Fahrstreifen Richtung Nord-Osten
	 */
	@Column(name = "FS_CNT_NO")
	private int anzahlFahrspurenRichtungNordOst;
	@Column(name = "FS_CNT_SW")
	private int anzahlFahrspurenRichtungSuedWest;

	public Steigungen() {

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public float getBetriebKiloMeterVon() {
		return betriebKiloMeterVon;
	}

	public void setBetriebKiloMeterVon(float betriebKiloMeterVon) {
		this.betriebKiloMeterVon = betriebKiloMeterVon;
	}

	public float getBetriebKiloMeterBis() {
		return betriebKiloMeterBis;
	}

	public void setBetriebKiloMeterBis(float betriebKiloMeterBis) {
		this.betriebKiloMeterBis = betriebKiloMeterBis;
	}

	public float getLaenge() {
		return laenge;
	}

	public void setLaenge(float laenge) {
		this.laenge = laenge;
	}

	public int getSteigungsKlasseRichtungNordOst() {
		return steigungsKlasseRichtungNordOst;
	}

	public void setSteigungsKlasseRichtungNordOst(
			int steigungsKlasseRichtungNordOst) {
		this.steigungsKlasseRichtungNordOst = steigungsKlasseRichtungNordOst;
	}

	public int getSteigungsKlasseRichtungSuedWest() {
		return steigungsKlasseRichtungSuedWest;
	}

	public void setSteigungsKlasseRichtungSuedWest(
			int steigungsKlasseRichtungSuedWest) {
		this.steigungsKlasseRichtungSuedWest = steigungsKlasseRichtungSuedWest;
	}

	public int getAnzahlFahrspurenRichtungNordOst() {
		return anzahlFahrspurenRichtungNordOst;
	}

	public void setAnzahlFahrspurenRichtungNordOst(
			int anzahlFahrspurenRichtungNordOst) {
		this.anzahlFahrspurenRichtungNordOst = anzahlFahrspurenRichtungNordOst;
	}

	public int getAnzahlFahrspurenRichtungSuedWest() {
		return anzahlFahrspurenRichtungSuedWest;
	}

	public void setAnzahlFahrspurenRichtungSuedWest(
			int anzahlFahrspurenRichtungSuedWest) {
		this.anzahlFahrspurenRichtungSuedWest = anzahlFahrspurenRichtungSuedWest;
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
		Steigungen other = (Steigungen) obj;
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

	public void setStrasse(Strasse strasse) {
		this.strasse = strasse;
	}

	public Strasse getStrasse() {
		return strasse;
	}

}