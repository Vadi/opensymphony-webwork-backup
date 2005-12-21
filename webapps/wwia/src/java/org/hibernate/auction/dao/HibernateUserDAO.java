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
import org.hibernate.auction.model.User;

import java.util.Collection;

/**
 * A typical DAO for users using Hibernate.
 *
 * @author Christian Bauer <christian@hibernate.org>
 */
public class HibernateUserDAO extends AbstractDAO implements UserDAO {

	// ********************************************************** //

	public User getUserById(Long userId, boolean lock)
			throws InfrastructureException {

		Session session = persistenceManager.getSession();
		User user = null;
		try {
			if (lock) {
				user = (User) session.load(User.class, userId, LockMode.UPGRADE);
			} else {
				user = (User) session.load(User.class, userId);
			}
		}  catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}
		return user;
	}

	// ********************************************************** //

	public Collection findAll()
			throws InfrastructureException {

		Collection users;
		try {
			users = persistenceManager.getSession().createCriteria(User.class).list();
		} catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}
		return users;
	}

	// ********************************************************** //

	public Collection findByExample(User exampleUser)
			throws InfrastructureException {

		Collection users;
		try {
			Criteria crit = persistenceManager.getSession().createCriteria(User.class);
			users = crit.add(Example.create(exampleUser)).list();
		} catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}
		return users;
	}

    public User findByCredentials(String username, String password) {
        try {
            Criteria crit = persistenceManager.getSession().createCriteria(User.class);
            crit.add(Expression.and(Expression.eq("username", username), Expression.eq("password", password)));
            return (User) crit.uniqueResult();
        } catch (HibernateException e) {
            throw new InfrastructureException(e);
        }
    }

    public User findByUsername(String username) {
        try {
            Criteria crit = persistenceManager.getSession().createCriteria(User.class);
            crit.add(Expression.eq("username", username));
            return (User) crit.uniqueResult();
        } catch (HibernateException e) {
            throw new InfrastructureException(e);
        }
    }

	// ********************************************************** //

	public void makePersistent(User user)
			throws InfrastructureException {

		try {
            persistenceManager.begin();
			persistenceManager.getSession().saveOrUpdate(user);
            persistenceManager.commit();
		} catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}
	}

	// ********************************************************** //

	public void makeTransient(User user)
			throws InfrastructureException {

		try {
			persistenceManager.getSession().delete(user);
		} catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}
	}


}
