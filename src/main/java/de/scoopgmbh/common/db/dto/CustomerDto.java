package de.scoopgmbh.common.db.dto;

import java.io.Serializable;
import java.util.List;

public class CustomerDto extends DomainDto implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * EnumMapping is an Ebean specific mapping for enums.
	 */
	public enum Status {
		NEW, ACTIVE, INACTIVE
	}

	Status status;

	String name;

	AddressDto billingAddress;

	AddressDto shippingAddress;

	List<OrderDto> orders;

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
	 * Return billing address.
	 */
	public AddressDto getBillingAddress() {
		return billingAddress;
	}

	/**
	 * Set billing address.
	 */
	public void setBillingAddress(AddressDto billingAddress) {
		this.billingAddress = billingAddress;
	}

	/**
	 * Return status.
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * Set status.
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * Return shipping address.
	 */
	public AddressDto getShippingAddress() {
		return shippingAddress;
	}

	/**
	 * Set shipping address.
	 */
	public void setShippingAddress(AddressDto shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	/**
	 * Return orders.
	 */
	public List<OrderDto> getOrders() {
		return orders;
	}

	/**
	 * Set orders.
	 */
	public void setOrders(List<OrderDto> orders) {
		this.orders = orders;
	}

}
