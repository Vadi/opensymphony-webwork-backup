package org.hibernate.auction.model;

public class Customer {

	private Long id = null;
	private CustomerLocation customerLocation;

	public Customer() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CustomerLocation getCustomerLocation() {
		return customerLocation;
	}

	public void setCustomerLocation(CustomerLocation customerLocation) {
		this.customerLocation = customerLocation;
	}
}
