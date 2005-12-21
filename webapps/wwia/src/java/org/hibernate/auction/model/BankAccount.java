package org.hibernate.auction.model;

/**
 * This billing strategy uses a simple bank account.
 *
 * @author Christian Bauer <christian@hibernate.org>
 */
public class BankAccount extends BillingDetails {

	private String number;
	private String bankName;
	private String bankSwift;

	/**
	 * No-arg constructor for JavaBean tools.
	 */
	BankAccount() { super(); }

	/**
	 * Full constructor.
	 *
	 * @param ownerName
	 * @param user
	 * @param number
	 * @param bankName
	 * @param bankSwift
	 */
	public BankAccount(String ownerName, User user, String number, String bankName, String bankSwift) {
		super(ownerName, user);
		this.number = number;
		this.bankName = bankName;
		this.bankSwift = bankSwift;
	}

	// ********************** Accessor Methods ********************** //

	public String getNumber() { return number; }
	public void setNumber(String number) { this.number = number; }

	public String getBankName() { return bankName; }
	public void setBankName(String bankName) { this.bankName = bankName; }

	public String getBankSwift() { return bankSwift; }
	public void setBankSwift(String bankSwift) { this.bankSwift = bankSwift; }

	// ********************** Common Methods ********************** //

	public String toString() {
		return  "BankAccount ('" + getId() + "'), " +
				"Number: '" + getNumber() + "'";
	}

	// ********************** Business Methods ********************** //

	public boolean isValid() {
		// TODO: Validate bank account syntax.
		return true;
	}

}
