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
import com.avaje.ebean.annotation.NamedUpdate;
import com.avaje.ebean.annotation.NamedUpdates;

/**
 * @author hirtech
 * @version 1.0
 * @created 06-Sep-2010 10:47:46
 */

@NamedUpdates(value = { @NamedUpdate(name = "deleteAll", notifyCache = true, update = "delete from Knotentypen") })
@CacheStrategy(readOnly = true)
@Entity
@Table(name = "KNOTENTYPEN")
public class KnotenTyp implements Serializable {

	private static final long serialVersionUID = -2726653982559360805L;

	public static final String AUTOBAHNDREIECK = "AD";
	public static final String ANSCHLUSSSTELLE = "AS";
	public static final String AUTOBAHNANSCHLUSS = "AN";
	public static final String AUTOBAHNKREUZ = "AK";
	public static final String LANDESGRENZE = "LG";
	public static final String BUNDESSTRASSENPUNKT = "BP";
	public static final String RAMPENPUNKT = "RP";
	public static final String BAUAMTSGRENZE = "BAG";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GLOBAL_SEQUENCE")
	private long id;
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

	@Column(name = "KUERZEL")
	private String kuerzel;

	@Column(name = "BEZEICHNUNG")
	private String bezeichnung;

	public KnotenTyp() {
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
		KnotenTyp other = (KnotenTyp) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public void setKuerzel(String kuerzel) {
		this.kuerzel = kuerzel;
	}

	public String getKuerzel() {
		return kuerzel;
	}

}