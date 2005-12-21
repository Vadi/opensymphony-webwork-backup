/*
 * Copyright (c) 2005 Manning Publications. All Rights Reserved.
 */
package org.hibernate.auction.web.actions;

import org.hibernate.auction.model.User;

/**
 * UserAware
 *
 * @author Jason Carreira <jcarreira@eplus.com>
 */
public interface UserAware {
    void setUser(User user);
}
