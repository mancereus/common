package de.scoopgmbh.bms.db.model;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 * @author hirtech
 * @version 1.0
 * @created 06-Sep-2010 10:47:15
 */
@Entity
@Table(name = "BAUSTELLENANTRAEGE")
public class BaustellenAntrag implements Serializable {

	private static final long serialVersionUID = -1831165848389695755L;

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
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATUM")
	private Date datum;
	@Column(name = "FREMDFIRMA_ID")
	@ManyToOne
	private FremdFirma idFremdfirma; // Autorität, die als Fremdfirma auftritt

	@Column(name = "AUTHORITAET_ID")
	@ManyToOne
	private Autoritaet idAntragsstellerAut; // Autorität, die als
											// Antragssteller
											// auftritt
	@Column(name = "AUTOBAHNMEISTEREI_ID")
	@ManyToOne
	private AutobahnMeisterei idAutobahnMeisterei; // Autorität, die als
													// Autobahnmeisterei
	// auftritt
	@Column(name = "BAULEITER_ID")
	@ManyToOne
	private Angestellte idBauleiter; // Angestellter, der als Bauleiter auftritt

	@Column(name = "SICHERUNG_ID")
	@ManyToOne
	private Angestellte idSicherung; // Angestellter, der als Sicherungsmann
										// auftritt
	@Column(name = "ANTRAGSST_ID")
	@ManyToOne
	private Angestellte idAntragssteller; // Angestellter, der den Antrag zur
											// Baustelle
	// stellt (redundant zu editorId)
	@Column(name = "ANSPRECHPARTNER_ID")
	private Angestellte idAnsprechpartner; // Angestellter, der von der
											// FremdFirma als
											// AP benannt wurde

	@Column(name = "KOPIEHESSEN")
	private char kopieHessen = DB.NO;
	@Column(name = "KOPIEPAST")
	private char kopiePast = DB.NO;
	@Column(name = "KOPIEASV")
	private char kopieASV = DB.NO;

	@Column(name = "KOPIESONST1_ID")
	private long idKopieSonstige1;
	@Column(name = "KOPIESONST2_ID")
	private long idKopieSonstige2;

	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "idBaustellenantrag")
	private List<BaustellenAntragPosition> sperrAntraege;

	public BaustellenAntrag() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public AutobahnMeisterei getIdAutobahnMeisterei() {
		return idAutobahnMeisterei;
	}

	public void setIdAutobahnMeisterei(AutobahnMeisterei idAutobahnMeisterei) {
		this.idAutobahnMeisterei = idAutobahnMeisterei;
	}

	public List<BaustellenAntragPosition> getSperrAntraege() {
		return sperrAntraege;
	}

	public void setSperrAntraege(List<BaustellenAntragPosition> sperrAntraege) {
		this.sperrAntraege = sperrAntraege;
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

	public void setDeleted(char deleted) {
		this.deleted = deleted;
	}

	public char getDeleted() {
		return deleted;
	}

	public void setIdAntragsstellerAut(Autoritaet idAntragsstellerAutoritaet) {
		this.idAntragsstellerAut = idAntragsstellerAutoritaet;
	}

	public Autoritaet getIdAntragsstellerAut() {
		return idAntragsstellerAut;
	}

	public void setIdFremdfirma(FremdFirma idFremdfirma) {
		this.idFremdfirma = idFremdfirma;
	}

	public FremdFirma getIdFremdfirma() {
		return idFremdfirma;
	}

	public Angestellte getIdBauleiter() {
		return idBauleiter;
	}

	public void setIdBauleiter(Angestellte idBauleiter) {
		this.idBauleiter = idBauleiter;
	}

	public Angestellte getIdSicherung() {
		return idSicherung;
	}

	public void setIdSicherung(Angestellte idSicherung) {
		this.idSicherung = idSicherung;
	}

	public Angestellte getIdAntragssteller() {
		return idAntragssteller;
	}

	public void setIdAntragssteller(Angestellte idAntragssteller) {
		this.idAntragssteller = idAntragssteller;
	}

	public Angestellte getIdAnsprechpartner() {
		return idAnsprechpartner;
	}

	public void setIdAnsprechpartner(Angestellte idAnsprechpartner) {
		this.idAnsprechpartner = idAnsprechpartner;
	}

	public char getKopieHessen() {
		return kopieHessen;
	}

	public void setKopieHessen(char kopieHessen) {
		this.kopieHessen = kopieHessen;
	}

	public char getKopiePast() {
		return kopiePast;
	}

	public void setKopiePast(char kopiePast) {
		this.kopiePast = kopiePast;
	}

	public char getKopieASV() {
		return kopieASV;
	}

	public void setKopieASV(char kopieASV) {
		this.kopieASV = kopieASV;
	}

	public long getIdKopieSonstige1() {
		return idKopieSonstige1;
	}

	public void setIdKopieSonstige1(long idKopieSonstige1) {
		this.idKopieSonstige1 = idKopieSonstige1;
	}

	public long getIdKopieSonstige2() {
		return idKopieSonstige2;
	}

	public void setIdKopieSonstige2(long idKopieSonstige2) {
		this.idKopieSonstige2 = idKopieSonstige2;
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
		BaustellenAntrag other = (BaustellenAntrag) obj;
		if (id != other.id)
			return false;
		return true;
	}

}