/*
 * Copyright (c) 2004 Manning Publications. All Rights Reserved.
 */
package org.hibernate.auction.dao;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.auction.exceptions.InfrastructureException;
import org.hibernate.auction.model.Comment;

import java.util.Collection;

/**
 * A typical DAO for comments using Hibernate
 *
 * @author Christian Bauer <christian@hibernate.org>
 */
public class CommentDAO extends AbstractDAO {

	// ********************************************************** //

	public Comment getCommentById(Long commentId, boolean lock)
			throws InfrastructureException {

		Session session = persistenceManager.getSession();
		Comment comment = null;
		try {
			if (lock) {
				comment = (Comment) session.load(Comment.class, commentId, LockMode.UPGRADE);
			} else {
				comment = (Comment) session.load(Comment.class, commentId);
			}
		}  catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}
		return comment;
	}

	// ********************************************************** //

	public Collection findAll()
			throws InfrastructureException {

		Collection comments;
		try {
			comments = persistenceManager.getSession().createCriteria(Comment.class).list();
		} catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}
		return comments;
	}

	// ********************************************************** //

	public Collection findByExample(Comment exampleComment)
			throws InfrastructureException {

		Collection comments;
		try {
			Criteria crit = persistenceManager.getSession().createCriteria(Comment.class);
			comments = crit.add(Example.create(exampleComment)).list();
		} catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}
		return comments;
	}

	// ********************************************************** //

	public void makePersistent(Comment comment)
			throws InfrastructureException {

		try {
			persistenceManager.getSession().saveOrUpdate(comment);
		} catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}
	}

	// ********************************************************** //

	public void makeTransient(Comment comment)
			throws InfrastructureException {

		try {
			persistenceManager.getSession().delete(comment);
		} catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}
	}


}
