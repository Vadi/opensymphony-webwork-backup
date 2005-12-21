/*
 * Copyright (c) 2004 Manning Publications. All Rights Reserved.
 */
package org.hibernate.auction.dao;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Expression;
import org.hibernate.auction.exceptions.InfrastructureException;
import org.hibernate.auction.model.Category;

import java.util.Collection;
import java.util.List;

/**
 * A typical DAO for categories using Hibernate.
 * 
 * @author Christian Bauer <christian@hibernate.org>
 */ 
public class CategoryDAO extends AbstractDAO {

    // ********************************************************** //

	public Category getCategoryById(Long categoryId, boolean lock)
			throws InfrastructureException {

		Session session = persistenceManager.getSession();
		Category cat = null;
		try {
			if (lock) {
				cat = (Category) session.load(Category.class, categoryId, LockMode.UPGRADE);
			} else {
				cat = (Category) session.load(Category.class, categoryId);
			}
		}  catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}
		return cat;
	}

	// ********************************************************** //

	public List findAll(boolean onlyRootCategories)
			throws InfrastructureException {

		List categories;
		try {
			if (onlyRootCategories) {
				Criteria crit = persistenceManager.getSession().createCriteria(Category.class);
				categories = crit.add(Expression.isNull("parentCategory")).list();
			} else {
				categories = persistenceManager.getSession().createCriteria(Category.class).list();
			}
		} catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}
		return categories;
	}

	// ********************************************************** //

	public Collection findByExample(Category exampleCategory)
			throws InfrastructureException {

		Collection categories;
		try {
			Criteria crit = persistenceManager.getSession().createCriteria(Category.class);
			categories = crit.add(Example.create(exampleCategory)).list();
		} catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}
		return categories;
	}

	// ********************************************************** //

	public void makePersistent(Category category)
			throws InfrastructureException {

		try {
			persistenceManager.getSession().saveOrUpdate(category);
		} catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}
	}

	// ********************************************************** //

	public void makeTransient(Category category)
			throws InfrastructureException {

		try {
			persistenceManager.getSession().delete(category);
		} catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}
	}

    public List search(String query) {
        try {
            query = "%" + query + "%";
            Criteria crit = persistenceManager.getSession().createCriteria(Category.class);
            crit.add(Expression.like("name", query));
            return crit.list();
        } catch (HibernateException e) {
            throw new InfrastructureException(e);
        }
    }
}
