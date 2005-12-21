package org.hibernate.auction.dao;

import org.hibernate.auction.model.User;
import org.hibernate.auction.exceptions.InfrastructureException;

import java.util.Collection;

/**
 * UserDAO
 *
 * @author Jason Carreira <jcarreira@eplus.com>
 */
public interface UserDAO {
    User getUserById(Long userId, boolean lock)
			throws InfrastructureException;

    Collection findAll()
			throws InfrastructureException;

    Collection findByExample(User exampleUser)
			throws InfrastructureException;

    User findByCredentials(String username, String password);

    User findByUsername(String username);

    void makePersistent(User user)
			throws InfrastructureException;

    void makeTransient(User user)
			throws InfrastructureException;
}
