package org.hibernate.auction.model;

import java.io.Serializable;
import java.util.*;

/**
 * The CaveatEmptor Category can have child categories and each has Items.
 * <p>
 * Categories can be nested, this is expressed as a bidirectional one-to-many
 * relationship that references parent and child categories. Each Category
 * can have many Items (and an Item can be in many categories). This is a
 * true many-to-many relationship.
 * <p>
 * The optional class <tt>CategorizedItem</tt> can be used if additional
 * information has to be kept about the link between a Category and an
 * Item. The collection of <tt>items</tt> will then be mapped as a
 * collection of dependent objects in the mapping for <tt>Category</tt>.
 *
 * @see Item
 * @see CategorizedItem
 * @author Christian Bauer <christian@hibernate.org>
 */
public class Category implements Serializable, Comparable {

	private Long id = null;
	private int version;
	private String name;
	private Category parentCategory;
	private Set childCategories = new HashSet();
	private Set categorizedItems = new HashSet();
	private Date created = new Date();

	/**
	 * No-arg constructor for JavaBean tools.
	 */
	public Category() {}

	/**
	 * Full constructor.
	 */
	public Category(String name, Category parentCategory, Set childCategories, Set categorizedItems) {
		this.name = name;
		this.parentCategory = parentCategory;
		this.childCategories = childCategories;
		this.categorizedItems = categorizedItems;
	}

	/**
	 * Simple constructor.
	 */
	public Category(String name) {
		this.name = name;
	}

	// ********************** Accessor Methods ********************** //

	public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public Category getParentCategory() { return parentCategory; }
	public void setParentCategory(Category parentCategory) { this.parentCategory = parentCategory; }

	public Set getChildCategories() { return childCategories; }
	public void addChildCategory(Category category) {
		if (category == null)
			throw new IllegalArgumentException("Can't add a null Category as child.");
		// Remove from old parent category
		if (category.getParentCategory() != null)
			category.getParentCategory().getChildCategories()
										 .remove(category);
		// Set parent in child
		category.setParentCategory(this);
		// Set child in parent
		this.getChildCategories().add(category);
	}

	public Set getCategorizedItems() { return categorizedItems; }
	public void addCategorizedItem(CategorizedItem catItem) {
		if (catItem == null)
			throw new IllegalArgumentException("Can't add a null CategorizedItem.");
		this.getCategorizedItems().add(catItem);
	}

	public Date getCreated() { return created; }

	// ********************** Common Methods ********************** //

	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Category)) return false;

		final Category category = (Category) o;

		if (created != null ? !created.equals(category.created) : category.created != null) return false;
		if (name != null ? !name.equals(category.name) : category.name != null) return false;

		return true;
	}

	public int hashCode() {
		int result;
		result = (name != null ? name.hashCode() : 0);
		result = 29 * result + (created != null ? created.hashCode() : 0);
		return result;
	}

	public String toString() {
		return  "Category ('" + getId() + "'), " +
				"Name: '" + getName() + "'";
	}

	public int compareTo(Object o) {
		if (o instanceof Category) {
			return this.getName().compareTo( ((Category)o).getName() );
		}
		return 0;
	}

	// ********************** Business Methods ********************** //

    public String getFullName() {
        Category category = this;
        String fullName = "";
        while (category != null) {
            fullName = category.getName() + ": " + fullName;
            category = category.getParentCategory();
        }

        return fullName;

    }

    public int getDepth() {
        Category category = getParentCategory();
        int depth = 0;
        while (category != null) {
            depth++;
            category = category.getParentCategory();
        }

        return depth;
    }
}
