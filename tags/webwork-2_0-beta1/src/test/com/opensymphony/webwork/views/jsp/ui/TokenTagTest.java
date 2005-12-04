/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.util.TokenHelper;
import com.opensymphony.webwork.views.jsp.AbstractJspTest;

import com.opensymphony.xwork.ActionContext;

import javax.servlet.jsp.JspException;


/**
 * TokenTagTest
 * @author Jason Carreira
 * Created Apr 10, 2003 7:12:12 PM
 */
public class TokenTagTest extends AbstractJspTest {
    //~ Methods ////////////////////////////////////////////////////////////////

    public void testDefaultName() {
        String tokenName = TokenHelper.DEFAULT_TOKEN_NAME;
        TokenTag tag = new TokenTag();
        doTokenTest(tokenName, tag);
    }

    public void testMultipleTagsWithSameName() {
        String tokenName = "sameName";
        TokenTag tag = new TokenTag();
        tag.setName(tokenName);

        String token = doTokenTest(tokenName, tag);

        TokenTag anotherTag = new TokenTag();
        anotherTag.setName(tokenName);

        String anotherToken = doTokenTest(tokenName, anotherTag);
        assertEquals(token, anotherToken);
    }

    public void testSuppliedName() {
        String tokenName = "my.very.long.token.name";
        TokenTag tag = new TokenTag();
        tag.setName(tokenName);
        doTokenTest(tokenName, tag);
    }

    public void testWithoutSession() {
        String tokenName = TokenHelper.DEFAULT_TOKEN_NAME;
        TokenTag tag = new TokenTag();
        ServletActionContext.getContext().setSession(null);
        doTokenTest(tokenName, tag);
    }

    private String doTokenTest(String tokenName, TokenTag tag) {
        tag.setPageContext(pageContext);

        String token = null;

        try {
            tag.doEndTag();

            token = (String) pageContext.getAttribute(tokenName);
            assertNotNull(token);
            assertEquals(token, ActionContext.getContext().getSession().get(tokenName));
        } catch (JspException e) {
            e.printStackTrace();
            fail();
        }

        return token;
    }
}