package org.hibernate.auction.model;

import org.hibernate.auction.exceptions.BusinessException;

import java.io.Serializable;
import java.util.*;

/**
 * A user of the CaveatEmptor auction application.
 * <p>
 * This class represents the user entity of CaveatEmptor business.
 * The associations are: a <tt>Set</tt> of <tt>Item</tt>s the user
 * is selling, a <tt>Set</tt> of <tt>Bid</tt>s the user has made,
 * and an <tt>Address</tt> component. Also a <tt>Set</tt> of
 * <tt>BuyNow</tt>s, that is, immediate buys made for an item.
 * <p>
 * The <tt>billingDetails</tt> are used to calculate and bill the
 * user for his activities on our system. The <tt>username</tt>
 * and <tt>password</tt> are used as login credentials. The
 * <tt>ranking</tt> is a number that is increased by each successful
 * transaction, but may also be manually increased (or decreased) by
 * the system administrators.
 *
 *
 * @author Christian Bauer <christian@hibernate.org>
 */
public class User implements Serializable, Comparable {

	protected Long id = null;
	private int version;
	private String firstname;
	private String lastname;
	private String username;
	private String password;
	private Email email;
	private int ranking = 0;
	private Date created = new Date();
	private Address address;
	private Set items = new HashSet();
	private Set billingDetails = new HashSet();
	private BillingDetails defaultBillingDetails;
	private boolean admin = false;

    // added for WebWork
    private int gender;

	/**
	 * No-arg constructor for JavaBean tools.
	 */
	public User() {}

    public User(String username) {
        this.username = username;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

	/**
	 * Full constructor.
	 */
	public User(String firstname, String lastname, String username,
				String password, Email email,
				Address address, Set items,
				Set billingDetails) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.username = username;
		this.password = password;
		this.email = email;
		this.address = address;
		this.items = items;
		this.billingDetails = billingDetails;
	}

	/**
	 * Simple constructor.
	 */
	public User(String firstname, String lastname,
	            String username, String password, Email email) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.username = username;
		this.password = password;
		this.email = email;
	}

	// ********************** Accessor Methods ********************** //

	public Long getId() { return id; }

	public String getFirstname() { return firstname; }
	public void setFirstname(String firstname) { this.firstname = firstname; }

	public String getLastname() { return lastname; }
	public void setLastname(String lastname) { this.lastname = lastname; }

	public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

	public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password; }

	public Email getEmail() { return email; }
	public void setEmail(Email email) { this.email = email; }

	public int getRanking() { return ranking; }
	public void setRanking(int ranking) { this.ranking = ranking; }

	public Address getAddress() { return address; }
	public void setAddress(Address address) { this.address = address; }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

	public Set getItems() { return items; }
	public void addItem(Item item) {
		if (item == null)
			throw new IllegalArgumentException("Can't add a null Item.");
		this.getItems().add(item);
	}

	public Set getBillingDetails() { return billingDetails; }
	/**
	  * Adds a <tt>BillingDetails</tt> to the set.
	  * <p>
	  * This method checks if there is only one billing method
	  * in the set, then makes this the default.
	  *
	  * @param billingDetails
	  */
	 public void addBillingDetails(BillingDetails billingDetails) {
		 if (billingDetails == null)
			 throw new IllegalArgumentException("Can't add a null BillingDetails.");
		 this.getBillingDetails().add(billingDetails);

		 if (getBillingDetails().size() == 1) {
			 setDefaultBillingDetails(billingDetails);
		 }
	}
	/**
 	 * Removes a <tt>BillingDetails</tt> from the set.
	 * <p>
	 * This method checks if the removed is the default element,
	 * and will throw a BusinessException if there is more than one
	 * left to chose from. This might actually not be the best way
	 * to handle this situation.
	 *
	 * @param billingDetails
	 * @throws BusinessException
	 */
	public void removeBillingDetails(BillingDetails billingDetails)
		throws BusinessException {
		if (billingDetails == null)
			throw new IllegalArgumentException("Can't add a null BillingDetails.");

		if (getBillingDetails().size() >= 2) {
			getBillingDetails().remove(billingDetails);
			setDefaultBillingDetails((BillingDetails)getBillingDetails().iterator().next());
		} else {
			throw new BusinessException("Please set new default BillingDetails first");
		}
	}

	public BillingDetails getDefaultBillingDetails() { return defaultBillingDetails; }
	public void setDefaultBillingDetails(BillingDetails defaultBillingDetails) {
		this.defaultBillingDetails = defaultBillingDetails;
	}

	public Date getCreated() { return created; }

	public boolean isAdmin() { return admin; }
	public void setAdmin(boolean admin) { this.admin = admin; }

	// ********************** Common Methods ********************** //

	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof User)) return false;
		final User user = (User) o;
		if (!username.equals(user.username)) return false;
		return true;
	}

	public int hashCode() {
		return username.hashCode();
	}

	public String toString() {
		return  "User ('" + getId() + "'), " +
				"Username: '" + getUsername() + "'";
	}

	public int compareTo(Object o) {
		if (o instanceof User)
			return this.getCreated().compareTo( ((User)o).getCreated() );
		return 0;
	}

	// ********************** Business Methods ********************** //

	public void increaseRanking() {
		setRanking(getRanking() + 1);
	}

}
