package de.scoopgmbh.bms.db.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

/**
 * @author hirtech
 * @version 1.0
 * @created 06-Sep-2010 10:48:09
 */
@Entity
@Table(name = "BAUSTELLEN")
public class Baustelle implements Serializable {

	private static final long serialVersionUID = 7746800815171833373L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GLOBAL_SEQUENCE")
	public long id;
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
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CHANGED")
	private Date change;
	@Column(name = "STRASSENNR")
	private String strassenNr;
	@Column(name = "RICHTUNG_FERNZIEL")
	private String richtungFernziel;
	@Column(name = "KNOTENPKT_ID_VON")
	private long idKnotenpunktVon;
	@Column(name = "KNOTENPKT_ID_BIS")
	private long idKnotenpunktBis;
	@Column(name = "BKM_VON", columnDefinition = "NUMBER(7,3)")
	private double betriebsKilometerVon;
	@Column(name = "BKM_BIS", columnDefinition = "NUMBER(7,3)")
	private double betriebsKilometerBis;
	@Column(name = "LAENGE", columnDefinition = "NUMBER(7,3)")
	private double laenge;
	@Column(name = "BAUSTELLENARTEN_ID")
	private long idBaustellenArt;
	@Column(name = "ARBEITENART")
	private String arbeitsArt;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATUM_VON")
	private Date datumVon;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATUM_BIS")
	private Date datumBis;
	@Column(name = "FS_VORHANDEN")
	private int anzahlFahrspurenVorhanden;
	@Column(name = "FS_VERBLEIBEND")
	private int anzahlFahrspurenVerbleibend;
	@Column(name = "SPERRUNG_LINKS")
	private char istLinksGesperrt;
	@Column(name = "INSELBAUSTELLE")
	private char istInselBaustelle;
	@Column(name = "STATUS_ID")
	private long idStatus;
	@Column(name = "GEFAHR_IM_VERZUG")
	private char istGefahrImVerzug;
	/**
	 * Anordnende Dienststelle
	 */
	@Column(name = "AM_ID")
	private long idAnordnendeDienststelle;
	/**
	 * beantragende autorit�t
	 */
	@Column(name = "AUTHORITAET_ID")
	private long idBeantragendeStelle;

	@Column(name = "MAX_BREITE_BS_RICHTUNG", columnDefinition = "NUMBER(4,2)")
	private double maxSpurBreiteInRichtungBaustelle;

	@Column(name = "MAX_HOEHE_BS_RICHTUNG", columnDefinition = "NUMBER(4,2)")
	private double maxDurchfahrtsHoeheInRichtungBaustelle;

	@Column(name = "MAX_BREITE_GEGENRICHTUNG", columnDefinition = "NUMBER(4,2)")
	private double maxSpurBreiteInGegenrichtung;

	@Column(name = "MAX_HOEHE_GEGENRICHTUNG", columnDefinition = "NUMBER(4,2)")
	private double maxDurchfahrtsHoeheInGegenrichtung;

	@Column(name = "KOMMENTAR")
	private String kommentar;

	@Transient
	private BaustellenArt baustellenart;

	@Transient
	private NetzPunkt knotenPunktVon;

	@Transient
	private NetzPunkt knotenPunktBis;

	@Transient
	private Status status;

	public Baustelle() {

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getChange() {
		return change;
	}

	public void setChange(Date change) {
		this.change = change;
	}

	public String getStrassenNr() {
		return strassenNr;
	}

	public void setStrassenNr(String strassenNr) {
		this.strassenNr = strassenNr;
	}

	public String getRichtungFernziel() {
		return richtungFernziel;
	}

	public void setRichtungFernziel(String richtungFernziel) {
		this.richtungFernziel = richtungFernziel;
	}

	public long getIdKnotenpunktVon() {
		return idKnotenpunktVon;
	}

	public void setIdKnotenpunktVon(long idKnotenpunktVon) {
		this.idKnotenpunktVon = idKnotenpunktVon;
	}

	public long getIdKnotenpunktBis() {
		return idKnotenpunktBis;
	}

	public void setIdKnotenpunktBis(long idKnotenpunktBis) {
		this.idKnotenpunktBis = idKnotenpunktBis;
	}

	public double getBetriebsKilometerVon() {
		return betriebsKilometerVon;
	}

	public void setBetriebsKilometerVon(double betriebsKilometerVon) {
		this.betriebsKilometerVon = betriebsKilometerVon;
	}

	public double getBetriebsKilometerBis() {
		return betriebsKilometerBis;
	}

	public void setBetriebsKilometerBis(double betriebsKilometerBis) {
		this.betriebsKilometerBis = betriebsKilometerBis;
	}

	public double getLaenge() {
		return laenge;
	}

	public void setLaenge(double laenge) {
		this.laenge = laenge;
	}

	public long getIdBaustellenArt() {
		return idBaustellenArt;
	}

	public void setIdBaustellenArt(long idBaustellenArt) {
		this.idBaustellenArt = idBaustellenArt;
	}

	public String getArbeitsArt() {
		return arbeitsArt;
	}

	public void setArbeitsArt(String arbeitsArt) {
		this.arbeitsArt = arbeitsArt;
	}

	public Date getDatumVon() {
		return datumVon;
	}

	public void setDatumVon(Date datumVon) {
		this.datumVon = datumVon;
	}

	public Date getDatumBis() {
		return datumBis;
	}

	public void setDatumBis(Date datumBis) {
		this.datumBis = datumBis;
	}

	public int getAnzahlFahrspurenVorhanden() {
		return anzahlFahrspurenVorhanden;
	}

	public void setAnzahlFahrspurenVorhanden(int anzahlFahrspurenVorhanden) {
		this.anzahlFahrspurenVorhanden = anzahlFahrspurenVorhanden;
	}

	public int getAnzahlFahrspurenVerbleibend() {
		return anzahlFahrspurenVerbleibend;
	}

	public void setAnzahlFahrspurenVerbleibend(int anzahlFahrspurenVerbleibend) {
		this.anzahlFahrspurenVerbleibend = anzahlFahrspurenVerbleibend;
	}

	public char getIstLinksGesperrt() {
		return istLinksGesperrt;
	}

	public void setIstLinksGesperrt(char istLinksGesperrt) {
		this.istLinksGesperrt = istLinksGesperrt;
	}

	public char getIstInselBaustelle() {
		return istInselBaustelle;
	}

	public void setIstInselBaustelle(char istInselBaustelle) {
		this.istInselBaustelle = istInselBaustelle;
	}

	public long getIdStatus() {
		return idStatus;
	}

	public void setIdStatus(long idStatus) {
		this.idStatus = idStatus;
	}

	public char getIstGefahrImVerzug() {
		return istGefahrImVerzug;
	}

	public void setIstGefahrImVerzug(char istGefahrImVerzug) {
		this.istGefahrImVerzug = istGefahrImVerzug;
	}

	public long getIdAnordnendeDienststelle() {
		return idAnordnendeDienststelle;
	}

	public void setIdAnordnendeDienststelle(long idAnordnendeDienststelle) {
		this.idAnordnendeDienststelle = idAnordnendeDienststelle;
	}

	public long getIdBeantragendeStelle() {
		return idBeantragendeStelle;
	}

	public void setIdBeantragendeStelle(long idBeantragendeStelle) {
		this.idBeantragendeStelle = idBeantragendeStelle;
	}

	public double getMaxSpurBreiteInRichtungBaustelle() {
		return maxSpurBreiteInRichtungBaustelle;
	}

	public void setMaxSpurBreiteInRichtungBaustelle(
			double maxSpurBreiteInRichtungBaustelle) {
		this.maxSpurBreiteInRichtungBaustelle = maxSpurBreiteInRichtungBaustelle;
	}

	public double getMaxDurchfahrtsHoeheInRichtungBaustelle() {
		return maxDurchfahrtsHoeheInRichtungBaustelle;
	}

	public void setMaxDurchfahrtsHoeheInRichtungBaustelle(
			float maxDurchfahrtsHoeheInRichtungBaustelle) {
		this.maxDurchfahrtsHoeheInRichtungBaustelle = maxDurchfahrtsHoeheInRichtungBaustelle;
	}

	public double getMaxSpurBreiteInGegenrichtung() {
		return maxSpurBreiteInGegenrichtung;
	}

	public void setMaxSpurBreiteInGegenrichtung(
			double maxSpurBreiteInGegenrichtung) {
		this.maxSpurBreiteInGegenrichtung = maxSpurBreiteInGegenrichtung;
	}

	public double getMaxDurchfahrtsHoeheInGegenrichtung() {
		return maxDurchfahrtsHoeheInGegenrichtung;
	}

	public void setMaxDurchfahrtsHoeheInGegenrichtung(
			double maxDurchfahrtsHoeheInGegenrichtung) {
		this.maxDurchfahrtsHoeheInGegenrichtung = maxDurchfahrtsHoeheInGegenrichtung;
	}

	public String getKommentar() {
		return kommentar;
	}

	public void setKommentar(String kommentar) {
		this.kommentar = kommentar;
	}

	public BaustellenArt getBaustellenart() {
		return baustellenart;
	}

	public void setBaustellenart(BaustellenArt baustellenart) {
		this.baustellenart = baustellenart;
	}

	public NetzPunkt getKnotenPunktVon() {
		return knotenPunktVon;
	}

	public void setKnotenPunktVon(NetzPunkt knotenPunktVon) {
		this.knotenPunktVon = knotenPunktVon;
	}

	public NetzPunkt getKnotenPunktBis() {
		return knotenPunktBis;
	}

	public void setKnotenPunktBis(NetzPunkt knotenPunktBis) {
		this.knotenPunktBis = knotenPunktBis;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
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
		Baustelle other = (Baustelle) obj;
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