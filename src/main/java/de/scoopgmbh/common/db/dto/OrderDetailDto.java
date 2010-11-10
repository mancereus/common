package de.scoopgmbh.common.db.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class OrderDetailDto implements Serializable {

	Integer id;

	Integer orderQty;

	Integer shipQty;

	Timestamp cretime;

	Timestamp updtime;

	OrderDto order;

	ProductDto product;

	public OrderDetailDto() {
	}

	public OrderDetailDto(ProductDto product, Integer orderQty) {
		this.product = product;
		this.orderQty = orderQty;
	}

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
	 * Return order qty.
	 */
	public Integer getOrderQty() {
		return orderQty;
	}

	/**
	 * Set order qty.
	 */
	public void setOrderQty(Integer orderQty) {
		this.orderQty = orderQty;
	}

	/**
	 * Return ship qty.
	 */
	public Integer getShipQty() {
		return shipQty;
	}

	/**
	 * Set ship qty.
	 */
	public void setShipQty(Integer shipQty) {
		this.shipQty = shipQty;
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

	/**
	 * Return order.
	 */
	public OrderDto getOrder() {
		return order;
	}

	/**
	 * Set order.
	 */
	public void setOrder(OrderDto order) {
		this.order = order;
	}

	/**
	 * Return product.
	 */
	public ProductDto getProduct() {
		return product;
	}

	/**
	 * Set product.
	 */
	public void setProduct(ProductDto product) {
		this.product = product;
	}

}
