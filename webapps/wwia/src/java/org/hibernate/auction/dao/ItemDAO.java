/*
 * Copyright (c) 2004 Manning Publications. All Rights Reserved.
 */
package org.hibernate.auction.dao;

import org.hibernate.*;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Expression;
import org.hibernate.auction.exceptions.InfrastructureException;
import org.hibernate.auction.model.Bid;
import org.hibernate.auction.model.Item;

import java.util.Collection;
import java.util.List;

/**
 * A typical DAO for auction items using Hibernate.
 *
 * @author Christian Bauer <christian@hibernate.org>
 */
public class ItemDAO extends AbstractDAO {

	// ********************************************************** //

	public Item getItemById(Long itemId, boolean lock)
			throws InfrastructureException {

		Session session = persistenceManager.getSession();
		Item item = null;
		try {
			if (lock) {
				item = (Item) session.load(Item.class, itemId, LockMode.UPGRADE);
			} else {
				item = (Item) session.load(Item.class, itemId);
			}
		}  catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}
		return item;
	}

	// ********************************************************** //

	public Bid getMaxBid(Long itemId)
			throws InfrastructureException {

		Bid maxBidAmount = null;
		try {
			// Note the creative where-clause subselect expression...
			Query q = persistenceManager.getSession().getNamedQuery("maxBid");
			q.setLong("itemid", itemId.longValue());
			maxBidAmount = (Bid) q.uniqueResult();
		}
		catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}
		return maxBidAmount;
	}

	// ********************************************************** //

	public Bid getMinBid(Long itemId)
			throws InfrastructureException {

		Bid maxBidAmount = null;
		try {
			// Note the creative where-clause subselect expression..
			Query q = persistenceManager.getSession().getNamedQuery("minBid");
			q.setLong("itemid", itemId.longValue());
			maxBidAmount = (Bid) q.uniqueResult();
		}
		catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}
		return maxBidAmount;
	}

	// ********************************************************** //

	public Collection findAll()
			throws InfrastructureException {

		Collection items;
		try {
			items = persistenceManager.getSession().createCriteria(Item.class).list();
		} catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}
		return items;
	}

	// ********************************************************** //

	public Collection findByExample(Item exampleItem)
			throws InfrastructureException {

		Collection items;
		try {
			Criteria crit = persistenceManager.getSession().createCriteria(Item.class);
			items = crit.add(Example.create(exampleItem)).list();
		} catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}
		return items;
	}

	// ********************************************************** //

	public void makePersistent(Item item)
			throws InfrastructureException {

		try {
			persistenceManager.getSession().saveOrUpdate(item);
		} catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}
	}

	// ********************************************************** //

	public void makeTransient(Item item)
			throws InfrastructureException {

		try {
			persistenceManager.getSession().delete(item);
		} catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}
	}

    public List search(String query) {
        try {
            query = "%" + query + "%";
            Criteria crit = persistenceManager.getSession().createCriteria(Item.class);
            crit.add(Expression.or(Expression.like("name", query), Expression.like("description", query)));
            return crit.list();
        } catch (HibernateException e) {
            throw new InfrastructureException(e);
        }
    }
}
