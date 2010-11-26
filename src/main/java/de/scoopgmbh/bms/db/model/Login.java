package de.scoopgmbh.bms.db.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import com.avaje.ebean.annotation.CacheStrategy;

/**
 * Represents a login with username and password for a autobahn meisterei.
 * 
 * @author hirtech
 * @version 1.0
 * @created 06-Sep-2010 10:47:42
 */
@CacheStrategy(readOnly = true)
@Entity
@Table(name = "LOGINS")
public class Login implements Serializable {

	private static final long serialVersionUID = -1126588697167708378L;

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
	@Column(name = "ANGESTELLTE_ID")
	private long angestellteId;

	@Column(name = "LOGIN")
	private String login;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "ENABLED")
	private char enabled;

	@ManyToMany
	@JoinTable(name = "ROLLEN_LOGINS", joinColumns = @JoinColumn(name = "LOGINS_ID"), inverseJoinColumns = @JoinColumn(name = "ROLLEN_ID"))
	private List<Rolle> rollen;

	public Login() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public char getEnabled() {
		return enabled;
	}

	public void setEnabled(char enabled) {
		this.enabled = enabled;
	}

	public List<Rolle> getRollen() {
		return rollen;
	}

	public void setRollen(List<Rolle> rollen) {
		this.rollen = rollen;
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
		Login other = (Login) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public void setAngestellteId(long angestellteId) {
		this.angestellteId = angestellteId;
	}

	public long getAngestellteId() {
		return angestellteId;
	}

	public void setDeleted(char deleted) {
		this.deleted = deleted;
	}

	public char getDeleted() {
		return deleted;
	}

}