package org.hibernate.auction.model;

import java.io.Serializable;
import java.util.*;

/**
 * Typesafe enumeration class for credit card types.
 *
 * @author Christian Bauer <christian@hibernate.org>
 */
public class CreditCardType implements Serializable {

	private final int code;
	private final String debugName;

	public static final CreditCardType MASTERCARD = new CreditCardType(1, "Mastercard");
	public static final CreditCardType VISA = new CreditCardType(2, "Visa");
	public static final CreditCardType AMEX = new CreditCardType(3, "American Express");

	private static final Map INSTANCES = new HashMap();

	static {
		INSTANCES.put(MASTERCARD.toInteger(), MASTERCARD);
		INSTANCES.put(VISA.toInteger(), VISA);
		INSTANCES.put(AMEX.toInteger(), AMEX);
	}

	/**
	 * Prevent instantiation and subclassing with a private constructor.
	 */
	private CreditCardType(int code, String debugName) {
		this.debugName = debugName;
		this.code = code;
	}

	// ********************** Common Methods ********************** //

	public String toString() {
	    return debugName;
	}

	public Integer toInteger() {
		return new Integer(code);
	}

	Object readResolve() {
		return getInstance(code);
	}

	public static CreditCardType getInstance(int code) {
		return (CreditCardType) INSTANCES.get(new Integer(code));
	}

	// ********************** Business Methods ********************** //

	public boolean isValid(CreditCard cc) {
		// TODO: Implement syntactical validation of credit card information.
		return true;
	}

}