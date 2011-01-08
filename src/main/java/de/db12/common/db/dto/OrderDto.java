package de.db12.common.db.dto;

import java.sql.Date;
import java.util.List;

public class OrderDto extends DomainDto {

	private static final long serialVersionUID = 1L;

	public enum Status {
		NEW, APPROVED, SHIPPED, COMPLETE
	}

	public OrderDto() {

	}

	Status status = Status.NEW;

	Date orderDate = new Date(System.currentTimeMillis());

	Date shipDate;

	CustomerDto customer;

	List<OrderDetailDto> details;

	/**
	 * Return order date.
	 */
	public Date getOrderDate() {
		return orderDate;
	}

	/**
	 * Set order date.
	 */
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	/**
	 * Return ship date.
	 */
	public Date getShipDate() {
		return shipDate;
	}

	/**
	 * Set ship date.
	 */
	public void setShipDate(Date shipDate) {
		this.shipDate = shipDate;
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
	 * Return customer.
	 */
	public CustomerDto getCustomer() {
		return customer;
	}

	/**
	 * Set customer.
	 */
	public void setCustomer(CustomerDto customer) {
		this.customer = customer;
	}

	/**
	 * Return details.
	 */
	public List<OrderDetailDto> getDetails() {
		return details;
	}

	/**
	 * Set details.
	 */
	public void setDetails(List<OrderDetailDto> details) {
		this.details = details;
	}

}
