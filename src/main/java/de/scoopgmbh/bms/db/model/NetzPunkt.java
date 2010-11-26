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

import com.avaje.ebean.annotation.CacheStrategy;

/**
 * @author hirtech
 * @version 1.0
 * @created 06-Sep-2010 10:47:49
 */
@CacheStrategy(readOnly = true)
@Entity
@Table(name = "NETZPUNKTE")
public class NetzPunkt implements Serializable {

	private static final long serialVersionUID = -3354715517831796616L;

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

	@Column(name = "BEZEICHNUNG")
	private String bezeichnung;

	@Column(name = "BEZEICHNUNG2")
	private String bezeichnung2;

	@Column(name = "BEZEICHNUNG3")
	private String bezeichnung3;

	@Column(name = "BKM1", columnDefinition = "NUMBER(7,3)")
	private double bkm1;

	@Column(name = "BKM2", columnDefinition = "NUMBER(7,3)")
	private double bkm2;

	@Column(name = "STRASSE_ID")
	@ManyToOne
	private Strasse strassenId;

	@Column(name = "KNOTENTYP_ID")
	@ManyToOne
	private KnotenTyp knotenTyp;

	public NetzPunkt() {

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getBezeichnung() {
		return bezeichnung;
	}

	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}

	public void setKnotenTyp(KnotenTyp knotenTyp) {
		this.knotenTyp = knotenTyp;
	}

	public KnotenTyp getKnotenTyp() {
		return knotenTyp;
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
		NetzPunkt other = (NetzPunkt) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public void setBkm2(double bkm2) {
		this.bkm2 = bkm2;
	}

	public double getBkm2() {
		return bkm2;
	}

	public void setBkm1(double bkm1) {
		this.bkm1 = bkm1;
	}

	public double getBkm1() {
		return bkm1;
	}

	public void setBezeichnung3(String bezeichnung3) {
		this.bezeichnung3 = bezeichnung3;
	}

	public String getBezeichnung3() {
		return bezeichnung3;
	}

	public void setBezeichnung2(String bezeichnung2) {
		this.bezeichnung2 = bezeichnung2;
	}

	public String getBezeichnung2() {
		return bezeichnung2;
	}

	@Override
	public String toString() {
		return bezeichnung;
	}

	public void setDeleted(char deleted) {
		this.deleted = deleted;
	}

	public char getDeleted() {
		return deleted;
	}

	public void setStrassenId(Strasse strassenId) {
		this.strassenId = strassenId;
	}

	public Strasse getStrassenId() {
		return strassenId;
	}

}