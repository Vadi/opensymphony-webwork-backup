/*
 * Copyright (c) 2005 Manning Publications. All Rights Reserved.
 */
package org.hibernate.auction.web.actions.test;

import com.mockobjects.dynamic.C;
import com.mockobjects.dynamic.Mock;
import junit.framework.TestCase;
import org.hibernate.auction.dao.UserDAO;
import org.hibernate.auction.model.User;
import org.hibernate.auction.web.filters.AuthenticationFilter;

import java.util.HashMap;
import java.util.Map;

/**
 * AbstractUpdateUserTest
 *
 * @author Jason Carreira <jcarreira@eplus.com>
 */
public abstract class AbstractUpdateUserTest extends TestCase {
    protected static Long userId = new Long(1);
    protected TestUser user;
    protected Mock userDaoMock;
    protected UserDAO userDAO;
    protected Map session;

    protected void setUp() throws Exception {
        super.setUp();
        user = new TestUser();
        user.setId(userId);
        userDaoMock = new Mock(UserDAO.class);
        userDaoMock.matchAndReturn("getUserById",
                C.args(C.eq(userId), C.IS_FALSE), user);
        userDAO = (UserDAO) userDaoMock.proxy();
        session = new HashMap();
        session.put(AuthenticationFilter.USER, user);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        userDaoMock.verify();
    }

    class TestUser extends User {
        public void setId(Long id) {
            this.id = id;
        }
    }
}
