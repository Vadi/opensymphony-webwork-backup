package org.hibernate.auction.model;

import java.io.Serializable;
import java.util.Date;

/**
 * An immutable class representing a comment from users.
 * <p>
 * Comments are always made in the context of an auction, that
 * is, a comment has a reference to an Item. The comment has
 * a free text field and a rating (using a typesafe enumeration).
 *
 * @see Item
 * @see User
 * @see Rating
 * @author Christian Bauer <christian@hibernate.org>
 */
public class Comment implements Serializable, Comparable {

	private Long id = null;
	private int version;
	private Rating rating;
	private String text;
	private User fromUser;
	private Item item;
	private Date created = new Date();

	/**
	 * No-arg constructor for JavaBean tools.
	 */
	Comment() {
	}

	/**
	 * Full constructor.
	 *
	 * @param rating
	 * @param text
	 * @param fromUser
	 * @param item
	 */
	public Comment(Rating rating, String text, User fromUser, Item item) {
		this.rating = rating;
		this.text = text;
		this.fromUser = fromUser;
		this.item = item;
	}

	// ********************** Accessor Methods ********************** //

	public Long getId() { return id; }

	public Rating getRating() { return rating; }

	public String getText() { return text; }
	public void setText(String text) {
		this.text = text;
	}

	public User getFromUser() { return fromUser; }

	public Item getItem() { return item; }

	public Date getCreated() { return created; }

	// ********************** Common Methods ********************** //

	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Comment)) return false;

		final Comment comment = (Comment) o;

		if (!created.equals(comment.created)) return false;
		if (!rating.equals(comment.rating)) return false;
		if (text != null ? !text.equals(comment.text) : comment.text != null) return false;

		return true;
	}

	public int hashCode() {
		int result;
		result = rating.hashCode();
		result = 29 * result + (text != null ? text.hashCode() : 0);
		result = 29 * result + created.hashCode();
		return result;
	}

	public String toString() {
		return "Comment ('" + getId() + "'), " +
				"Rating: '" + getRating() + "'";
	}

	public int compareTo(Object o) {
		if (o instanceof Comment) {
			return this.getCreated().compareTo(((Comment) o).getCreated());
		}
		return 0;
	}

	// ********************** Business Methods ********************** //

}
