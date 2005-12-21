package org.hibernate.auction.model;

import java.io.Serializable;
import java.util.*;

public class ItemState implements Serializable {

	private char state;

	public static final ItemState DRAFT = new ItemState('D');
	public static final ItemState PENDING = new ItemState('P');
	public static final ItemState ACTIVE = new ItemState('A');

	private static final Map INSTANCES = new HashMap();

	static {
		INSTANCES.put(DRAFT.toString(), DRAFT);
		INSTANCES.put(PENDING.toString(), PENDING);
		INSTANCES.put(ACTIVE.toString(), ACTIVE);
	}

	/**
	 * Prevent instantiation and subclassing with a private constructor.
	 */
	private ItemState(char state) {
		this.state = state;
	}

	// ********************** Common Methods ********************** //

	public String toString() {
		return Character.toString(state);
	}

	Object readResolve() {
		return getInstance(state);
	}

	public static ItemState getInstance(char name) {
		return (ItemState) INSTANCES.get(Character.toString(name));
	}

}