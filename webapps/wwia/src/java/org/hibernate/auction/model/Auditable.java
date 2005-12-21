package org.hibernate.auction.model;

/**
 * A marker interface for auditable persistent domain classes.
 *
 * @author Christian Bauer <christian@hibernate.org>
 */
public interface Auditable {

	public Long getId();
}
