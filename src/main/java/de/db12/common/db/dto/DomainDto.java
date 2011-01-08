package de.db12.common.db.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class DomainDto implements Serializable {

	private Integer id;

	private Timestamp cretime;

	private Timestamp updtime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Timestamp getCretime() {
		return cretime;
	}

	public void setCretime(Timestamp cretime) {
		this.cretime = cretime;
	}

	public Timestamp getUpdtime() {
		return updtime;
	}

	public void setUpdtime(Timestamp updtime) {
		this.updtime = updtime;
	}

}
