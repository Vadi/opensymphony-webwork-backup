package org.hibernate.auction.model;

import java.util.Date;
import java.io.Serializable;

/**
 * A single item in a single category, with additional information.
 * <p>
 * This is really a very special mapping. The CategorizedItem class
 * represents an association table. The ER model for this is really
 * a many-to-many association, but instead of two entities and two
 * collections, we mapped this as two one-to-many associations between
 * three entities. One of the motivation for this are the additional
 * attributes on the association table (not only two FKs): username
 * and creation date.
 *
 * @see Category
 * @see Item
 * @author Christian Bauer <christian@hibernate.org>
 */
public class CategorizedItem implements Serializable, Comparable {

	// ******************* Begin Inner composite Id class ******************* //
	public static class Id implements Serializable {
		private Long categoryId;
		private Long itemId;

		public Id() {}

		public Id(Long categoryId, Long itemId) {
			this.categoryId = categoryId;
			this.itemId = itemId;
		}

		public boolean equals(Object o) {
			if (o instanceof Id) {
				Id that = (Id)o;
				return this.categoryId.equals(that.categoryId) &&
					   this.itemId.equals(that.itemId);
			} else {
				return false;
			}
		}

		public int hashCode() {
			return categoryId.hashCode() + itemId.hashCode();
		}
	}
	// ******************* End Inner composite Id class ******************* //

	private Id id = new Id();

	private String username; // This could also be an association to User
	private Date dateAdded = new Date();

	private Item item;
	private Category category;

	/**
	 * No-arg constructor for JavaBean tools.
	 */
	CategorizedItem() {}

	/**
	 * Full constructor;
	 */
	public CategorizedItem(String username, Category category, Item item) {
		this.username = username;

		this.category = category;
		this.item = item;

		// Set key values
		this.id.categoryId = category.getId();
		this.id.itemId = item.getId();

		// Guarantee referential integrity
		category.getCategorizedItems().add(this);
		item.getCategorizedItems().add(this);
	}

	// ********************** Accessor Methods ********************** //

	public Id getId() { return id; }

	public String getUsername() { return username; }
	public Date getDateAdded() { return dateAdded; }

	public Category getCategory() { return category; }
	public Item getItem() { return item; }

	// ********************** Common Methods ********************** //

	public int compareTo(Object o) {
		// CategorizedItems are sorted by date
		if (o instanceof CategorizedItem)
			return getDateAdded().compareTo( ((CategorizedItem)o).getDateAdded() );
		return 0;
	}

	public String toString() {
		return  "Added by: '" + getUsername() + "', " +
				"On Date: '" + getDateAdded();
	}

	// ********************** Business Methods ********************** //

}
