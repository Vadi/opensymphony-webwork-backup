package org.hibernate.auction.model;

import java.io.Serializable;

/**
 * The address of a User.
 *
 * An instance of this class is always associated with only
 * one <tt>User</tt> and depends on that parent objects lifecycle,
 * it is a component.
 *
 * @see User
 * @author Christian Bauer <christian@hibernate.org>
 */
public class Address implements Serializable {

	private String street;
	private String zipcode;
	private String city;

    // added for WebWork
    private String state;
    private String country;
    private boolean poBox;

	/**
	 * No-arg constructor for JavaBean tools.
	 */
	public Address() {}

	/**
	 * Full constructor.
	 */
	public Address(String street, String zipcode, String city) {
		this.street = street;
		this.zipcode = zipcode;
		this.city = city;
	}

	// ********************** Accessor Methods ********************** //

	public String getStreet() { return street; }
	public void setStreet(String street) { this.street = street; }

	public String getZipcode() { return zipcode; }
	public void setZipcode(String zipcode) { this.zipcode = zipcode; }

	public String getCity() { return city; }
	public void setCity(String city) { this.city = city; }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public boolean isPoBox() {
        return poBox;
    }

    public void setPoBox(boolean poBox) {
        this.poBox = poBox;
    }

	// ********************** Common Methods ********************** //

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;

        final Address address = (Address) o;

        if (city != null ? !city.equals(address.city) : address.city != null) return false;
        if (country != null ? !country.equals(address.country) : address.country != null) return false;
        if (state != null ? !state.equals(address.state) : address.state != null) return false;
        if (street != null ? !street.equals(address.street) : address.street != null) return false;
        if (zipcode != null ? !zipcode.equals(address.zipcode) : address.zipcode != null) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = (street != null ? street.hashCode() : 0);
        result = 29 * result + (zipcode != null ? zipcode.hashCode() : 0);
        result = 29 * result + (city != null ? city.hashCode() : 0);
        result = 29 * result + (state != null ? state.hashCode() : 0);
        result = 29 * result + (country != null ? country.hashCode() : 0);
        return result;
    }

    public String toString() {
		return  "Street: '" + getStreet() + "', " +
				"Zipcode: '" + getZipcode() + "', " +
				"City: '" + getCity() + "', " +
                "State: '" + getState() + "', " +
                "Country: '" + getCountry() + "'";
	}

	// ********************** Business Methods ********************** //

}
