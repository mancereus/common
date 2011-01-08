package de.db12.common.db.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class ProductDto implements Serializable {

	Integer id;

	String sku;

	String name;

	Timestamp cretime;

	Timestamp updtime;

	/**
	 * Return id.
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Set id.
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Return sku.
	 */
	public String getSku() {
		return sku;
	}

	/**
	 * Set sku.
	 */
	public void setSku(String sku) {
		this.sku = sku;
	}

	/**
	 * Return name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set name.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Return cretime.
	 */
	public Timestamp getCretime() {
		return cretime;
	}

	/**
	 * Set cretime.
	 */
	public void setCretime(Timestamp cretime) {
		this.cretime = cretime;
	}

	/**
	 * Return updtime.
	 */
	public Timestamp getUpdtime() {
		return updtime;
	}

	/**
	 * Set updtime.
	 */
	public void setUpdtime(Timestamp updtime) {
		this.updtime = updtime;
	}

}
