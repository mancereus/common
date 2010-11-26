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
 * Stellt einen Angestellten einer Autorit�t dar.
 * 
 * 
 * @author hirtech
 * @version 1.0
 * @created 06-Sep-2010 10:48:34
 */

@CacheStrategy(readOnly = true)
@Entity
@Table(name = "ANGESTELLTE")
public class Angestellte implements Serializable {

	private static final long serialVersionUID = 7519219084984479745L;

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
	@Column(name = "DELETED")
	private char deleted = DB.NO;
	/**
	 * Die Authorit�t zu welcher
	 */
	@Column(name = "AUTORITAETEN_ID")
	@ManyToOne
	private Autoritaet autoritaeten;

	@Column(name = "NAME")
	private String name;
	@Column(name = "VORNAME")
	private String vorname;
	@Column(name = "POSITION")
	private String position;
	@Column(name = "EMAIL")
	private String email;
	@Column(name = "TELEFON")
	private String telefon;
	@Column(name = "TELEFAX")
	private String telefax;

	public Angestellte() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	public void setAutoritaeten(Autoritaet authoritaetenId) {
		this.autoritaeten = authoritaetenId;
	}

	public Autoritaet getAutoritaeten() {
		return autoritaeten;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public long getVersion() {
		return version;
	}

	public void setEditorId(long editorId) {
		this.editorId = editorId;
	}

	public long getEditorId() {
		return editorId;
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
		Angestellte other = (Angestellte) obj;
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

	public String getTelefax() {
		return telefax;
	}

	public void setTelefax(String telefax) {
		this.telefax = telefax;
	}
}