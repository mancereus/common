package de.db12.common.db.entity;

import java.io.Serializable;

public class Profile implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String profilename;
	private int level;

	public Profile() {
	}

	public void setProfilename(String profilename) {
		this.profilename = profilename;
	}

	public String getProfilename() {
		return profilename;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getLevel() {
		return level;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}
}
