package de.scoopgmbh.bms.db.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * @author hirtech
 * @version 1.0
 * @created 06-Sep-2010 10:47:21
 */
@Entity
@Table(name = "BAUSTELLENANTRAGPOSITIONEN")
public class BaustellenAntragPosition implements Serializable {

	private static final long serialVersionUID = 4151741465707752926L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GLOBAL_SEQUENCE")
	private long id;

	@Column(name = "baustellenantrag_ID")
	@ManyToOne
	private BaustellenAntrag idBaustellenantrag;

	/**
	 * Id des Benutzer der den Datensatz angelegt hat.
	 */
	@Column(name = "LOGINS_ID")
	private long editorId;

	/**
	 * For optimistic locking.
	 */
	@Version
	@Column(name = "VERSION")
	private long version;
	@Column(name = "DELETED")
	private char deleted = DB.NO;
	@Column(name = "BKM_VON", columnDefinition = "NUMBER(7,3)")
	private float betriebsKiloMeterVon;
	@Column(name = "BKM_BIS", columnDefinition = "NUMBER(7,3)")
	private float betriebsKiloMeterBis;
	@Column(name = "LAENGE", columnDefinition = "NUMBER(7,3)")
	private float laenge;
	@Column(name = "DATUM_VON")
	private Date datumVon;
	@Column(name = "DATUM_BIS")
	private Date datumBis;
	@Column(name = "SPERRUNG")
	private char sperrung;
	@Column(name = "GESPERRTE_FS")
	private int gesperrteFahrstreifen;
	@Column(name = "GEFAHR_IM_VERZUG")
	private char istGefahrImVerzug;
	@Column(name = "ID_KNOTENPUNKT_VON")
	private NetzPunkt idKnotenpunktVon;
	@Column(name = "ID_KNOTENPUNKT_BIS")
	private NetzPunkt idKnotenpunktBis;
	@Lob
	@Column(name = "KOMMENTAR")
	private String kommentar;
	@Column(name = "ID_RSA_REGEL")
	private long idRsaRegel;
	@Column(name = "WANDERBAUSTELLE")
	private char istWanderbaustelle;
	private String richtung;

	// @ManyToMany(fetch=FetchType.EAGER,cascade={CascadeType.PERSIST,CascadeType.MERGE})
	// @JoinTable(name="BABABS_SPERRANTRAEGE",
	// joinColumns=@JoinColumn(name="SPERRANTRAEGE_ID"),
	// inverseJoinColumns=@JoinColumn(name="BABABS_ID"))
	// private List<Abschnitt> autobahnAbschnitte;

	public BaustellenAntragPosition() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BaustellenAntrag getBaustellenAntrag() {
		return idBaustellenantrag;
	}

	public void setBaustellenAntrag(BaustellenAntrag idBaustellenantrag) {
		this.idBaustellenantrag = idBaustellenantrag;
	}

	public NetzPunkt getIdKnotenpunktBis() {
		return idKnotenpunktBis;
	}

	public void setIdKnotenpunktBis(NetzPunkt idKnotenpunktBis) {
		this.idKnotenpunktBis = idKnotenpunktBis;
	}

	public NetzPunkt getIdKnotenpunktVon() {
		return idKnotenpunktVon;
	}

	public void setIdKnotenpunktVon(NetzPunkt idKnotenpunktVon) {
		this.idKnotenpunktVon = idKnotenpunktVon;
	}

	public float getBetriebsKiloMeterVon() {
		return betriebsKiloMeterVon;
	}

	public void setBetriebsKiloMeterVon(float betriebsKiloMeterVon) {
		this.betriebsKiloMeterVon = betriebsKiloMeterVon;
	}

	public float getBetriebsKiloMeterBis() {
		return betriebsKiloMeterBis;
	}

	public void setBetriebsKiloMeterBis(float betriebsKiloMeterBis) {
		this.betriebsKiloMeterBis = betriebsKiloMeterBis;
	}

	public float getLaenge() {
		return laenge;
	}

	public void setLaenge(float laenge) {
		this.laenge = laenge;
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

	public char getSperrung() {
		return sperrung;
	}

	public void setSperrung(char sperrung) {
		this.sperrung = sperrung;
	}

	public int getGesperrteFahrstreifen() {
		return gesperrteFahrstreifen;
	}

	public void setGesperrteFahrstreifen(int gesperrteFahrstreifen) {
		this.gesperrteFahrstreifen = gesperrteFahrstreifen;
	}

	public char getIstGefahrImVerzug() {
		return istGefahrImVerzug;
	}

	public void setIstGefahrImVerzug(char istGefahrImVerzug) {
		this.istGefahrImVerzug = istGefahrImVerzug;
	}

	// public void setAutobahnAbschnitte(List<Abschnitt> autobahnAbschnitte) {
	// this.autobahnAbschnitte = autobahnAbschnitte;
	// }
	//
	// public List<Abschnitt> getAutobahnAbschnitte() {
	// return autobahnAbschnitte;
	// }

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
		BaustellenAntragPosition other = (BaustellenAntragPosition) obj;
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

	public String getKommentar() {
		return kommentar;
	}

	public void setKommentar(String kommentar) {
		this.kommentar = kommentar;
	}

	public long getIdRsaRegel() {
		return idRsaRegel;
	}

	public void setIdRsaRegel(long idRsaRegel) {
		this.idRsaRegel = idRsaRegel;
	}

	public void setRichtung(String richtung) {
		if (!richtung.equals(Abschnitt.NORDOST)
				&& !richtung.equals(Abschnitt.SUEDWEST))
			throw new RuntimeException(
					"Richtung fehlerhaft. F�r Richtung muss entweder "
							+ Abschnitt.NORDOST + " oder " + Abschnitt.SUEDWEST
							+ " angegeben werden.");
		this.richtung = richtung;
	}

	public String getRichtung() {
		return richtung;
	}

	public void setIstWanderbaustelle(char istWanderbaustelle) {
		this.istWanderbaustelle = istWanderbaustelle;
	}

	public char getIstWanderbaustelle() {
		return istWanderbaustelle;
	}
}
