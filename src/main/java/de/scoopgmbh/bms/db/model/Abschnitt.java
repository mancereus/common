package de.scoopgmbh.bms.db.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import com.avaje.ebean.annotation.CacheStrategy;

/**
 * @author hirtech
 * @version 1.0
 * @created 06-Sep-2010 10:48:21
 */
@CacheStrategy(readOnly = true)
@Entity
@Table(name = "ABSCHNITTE")
public class Abschnitt implements Serializable {

	private static final long serialVersionUID = -7263664143193022035L;

	public static final String AUTOBAHN = "Autobahn"; // Meint eine Autobahn
	public static final String BUNDESSTRASSE = "Bundeststrasse"; // Meint eine
																	// Bundesstrasse
	public static final String RAMPE = "Rampe"; // Meint eine Rampe innerhalb
												// eines Netzknoten

	public static final String NORDOST = "NO"; // abs
	public static final String SUEDWEST = "SW"; // abs

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

	@Column(name = "STRASSEN_ID")
	@ManyToOne
	private Strasse strasse;

	@Column(name = "POS_IN_LIST")
	private long posInList;

	@Column(name = "KNOTENPKTE_ID1")
	@OneToOne
	private NetzPunkt idKnotenPunkt1;

	@Column(name = "KNOTENPKTE_ID2")
	@OneToOne
	private long idKnotenPunkt2;

	@Column(name = "LAENGE", columnDefinition = "NUMBER(7,3)")
	private double laenge;

	@Column(name = "FS_CNT")
	private long anzahlFahrstreifen;
	@Column(name = "HAT_STANDSTREIFEN")
	private char hatStandstreifen;

	@Column(name = "RECHENREGEL_ID")
	@ManyToOne
	private RechenRegelNode rechenRegelId;

	@Column(name = "RICHTUNG")
	private String richtung;

	@Column(name = "TYP")
	private String typ;

	@Column(name = "BLOCK")
	private int blockNr;

	@Column(name = "STRECKENNR")
	private int streckenNr = 1;

	@OneToMany
	private List<Messquerschnitt> messquerschnitte;

	public Abschnitt() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getPosInList() {
		return posInList;
	}

	public void setPosInList(long posInList) {
		this.posInList = posInList;
	}

	public NetzPunkt getIdKnotenPunkt1() {
		return idKnotenPunkt1;
	}

	public void setIdKnotenPunkt1(NetzPunkt idKnotenPunkt1) {
		this.idKnotenPunkt1 = idKnotenPunkt1;
	}

	public long getIdKnotenPunkt2() {
		return idKnotenPunkt2;
	}

	public void setIdKnotenPunkt2(long idKnotenPunkt2) {
		this.idKnotenPunkt2 = idKnotenPunkt2;
	}

	public long getAnzahlFahrstreifen() {
		return anzahlFahrstreifen;
	}

	public void setAnzahlFahrstreifen(long anzahlFahrstreifen) {
		this.anzahlFahrstreifen = anzahlFahrstreifen;
	}

	public char getHatStandstreifen() {
		return hatStandstreifen;
	}

	public void setHatStandstreifen(char hatStandstreifen) {
		this.hatStandstreifen = hatStandstreifen;
	}

	public List<Messquerschnitt> getMessstellen() {
		return messquerschnitte;
	}

	public void setMessstellen(List<Messquerschnitt> messstellen) {
		this.messquerschnitte = messstellen;
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
		Abschnitt other = (Abschnitt) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public void setRechenRegelId(RechenRegelNode rechenRegelId) {
		this.rechenRegelId = rechenRegelId;
	}

	public RechenRegelNode getRechenRegelId() {
		return rechenRegelId;
	}

	public void setRichtung(String richtung) {
		this.richtung = richtung;
	}

	public String getRichtung() {
		return richtung;
	}

	public void setTyp(String typ) {
		this.typ = typ;
	}

	public String getTyp() {
		return typ;
	}

	public void setLaenge(double laenge) {
		this.laenge = laenge;
	}

	public double getLaenge() {
		return laenge;
	}

	public void setDeleted(char deleted) {
		this.deleted = deleted;
	}

	public char getDeleted() {
		return deleted;
	}

	@Override
	public String toString() {
		return "Abschnitt [idKnotenPunkt1=" + idKnotenPunkt1
				+ ", idKnotenPunkt2=" + idKnotenPunkt2 + "]";
	}

	public void setBlocknr(int blocknr) {
		this.blockNr = blocknr;
	}

	public int getBlocknr() {
		return blockNr;
	}

	public void setStreckenNr(int streckenNr) {
		this.streckenNr = streckenNr;
	}

	public int getStreckenNr() {
		return streckenNr;
	}

	public void setStrasse(Strasse strasse) {
		this.strasse = strasse;
	}

	public Strasse getStrasse() {
		return strasse;
	}

}