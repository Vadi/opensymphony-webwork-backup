package org.hibernate.auction.model;

import java.io.Serializable;

public class CustomerLocation implements Serializable {

	private String one;
	private String two;

	public CustomerLocation() {}

	public String getOne() {
		return one;
	}

	public void setOne(String one) {
		this.one = one;
	}

	public String getTwo() {
		return two;
	}

	public void setTwo(String two) {
		this.two = two;
	}

	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof CustomerLocation)) return false;

		final CustomerLocation customerLocation = (CustomerLocation) o;

		if (one != null ? !one.equals(customerLocation.one) : customerLocation.one != null) return false;
		if (two != null ? !two.equals(customerLocation.two) : customerLocation.two != null) return false;

		return true;
	}

	public int hashCode() {
		int result;
		result = (one != null ? one.hashCode() : 0);
		result = 29 * result + (two != null ? two.hashCode() : 0);
		return result;
	}

}
