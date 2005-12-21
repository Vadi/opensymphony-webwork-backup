/*
 * Copyright (c) 2005 Manning Publications. All Rights Reserved.
 */
package org.hibernate.auction.web.actions.test;

import org.hibernate.auction.web.actions.users.UpdateUser;


/**
 * UpdateUserTest
 *
 * @author Jason Carreira <jcarreira@eplus.com>
 */
public class UpdateUserTest extends AbstractUpdateUserTest {
    protected UpdateUser action;

    protected void setUp() throws Exception {
        super.setUp();
        action = new UpdateUser();
        action.setSession(session);
        action.setUserDAO(userDAO);
    }

    public void testPrepareLoadsUserFromUserDAO() throws Exception {
        // loads the user
        action.prepare();
        assertEquals(user, action.getModel());
    }

}
