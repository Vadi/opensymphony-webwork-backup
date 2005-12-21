/*
 * Copyright (c) 2004 Manning Publications. All Rights Reserved.
 */
package org.hibernate.auction.dao;

/**
 * CommentDAOAware
 *
 * @author Jason Carreira <jason@zenfrog.com>
 */
public interface CommentDAOAware {
    void setCommentDAO(CommentDAO dao);
}
