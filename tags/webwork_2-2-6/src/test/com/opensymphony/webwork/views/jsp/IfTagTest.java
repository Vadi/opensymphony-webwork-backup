/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp;

import com.mockobjects.servlet.MockJspWriter;
import com.mockobjects.servlet.MockPageContext;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.util.OgnlValueStack;
import com.opensymphony.webwork.ServletActionContext;
import junit.framework.TestCase;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.opensymphony.webwork.views.jsp.ElseIfTag;
import com.opensymphony.webwork.views.jsp.ElseTag;
import com.opensymphony.webwork.views.jsp.IfTag;


/**
 * @author $Author$
 * @version $Revision$
 */
public class IfTagTest extends TestCase {

    IfTag tag;
    MockPageContext pageContext;
    OgnlValueStack stack;


    public void testNonBooleanTest() {
        // set up the stack
        Foo foo = new Foo();
        foo.setNum(1);
        stack.push(foo);

        // set up the test
        tag.setTest("num");

        int result = 0;

        try {
            result = tag.doStartTag();
        } catch (JspException e) {
            e.printStackTrace();
            fail();
        }

        assertEquals(TagSupport.EVAL_BODY_INCLUDE, result);

        try {
            result = tag.doEndTag();
        } catch (JspException e) {
            e.printStackTrace();
            fail();
        }
    }

    public void testTestError() {
        // set up the stack
        Foo foo = new Foo();
        foo.setNum(2);
        stack.push(foo);

        // set up the test
        tag.setTest("nuuuuum == 2");

        int result = 0;

        try {
            result = tag.doStartTag();
        } catch (JspException e) {
            e.printStackTrace();
            fail();
        }

        assertEquals(TagSupport.SKIP_BODY, result);

        try {
            result = tag.doEndTag();
        } catch (JspException e) {
            e.printStackTrace();
            fail();
        }
    }

    public void testTestFalse() {
        // set up the stack
        Foo foo = new Foo();
        foo.setNum(2);
        stack.push(foo);

        // set up the test
        tag.setTest("num != 2");

        int result = 0;

        try {
            result = tag.doStartTag();
        } catch (JspException e) {
            e.printStackTrace();
            fail();
        }

        assertEquals(TagSupport.SKIP_BODY, result);

        try {
            result = tag.doEndTag();
        } catch (JspException e) {
            e.printStackTrace();
            fail();
        }
    }

    public void testTestTrue() {
        // set up the stack
        Foo foo = new Foo();
        foo.setNum(2);
        stack.push(foo);

        // set up the test
        tag.setTest("num == 2");

        int result = 0;
        //tag.setPageContext(pageContext);

        try {
            result = tag.doStartTag();
        } catch (JspException e) {
            e.printStackTrace();
            fail();
        }

        assertEquals(TagSupport.EVAL_BODY_INCLUDE, result);

        try {
            result = tag.doEndTag();
        } catch (JspException e) {
            e.printStackTrace();
            fail();
        }
    }
    
    public void testIfElse1() throws Exception {
    	IfTag ifTag = new IfTag();
    	ifTag.setPageContext(pageContext);
    	ifTag.setTest("true");
    	
    	ElseTag elseTag = new ElseTag();
    	elseTag.setPageContext(pageContext);
    	
    	int r1 = ifTag.doStartTag();
    	ifTag.doEndTag();
    	int r2 = elseTag.doStartTag();
    	elseTag.doEndTag();
    	
    	assertEquals(TagSupport.EVAL_BODY_INCLUDE, r1);
    	assertEquals(TagSupport.SKIP_BODY, r2);
    }
    
    public void testIfElse2() throws Exception {
    	IfTag ifTag = new IfTag();
    	ifTag.setPageContext(pageContext);
    	ifTag.setTest("false");
    	
    	ElseTag elseTag = new ElseTag();
    	elseTag.setPageContext(pageContext);
    	
    	int r1 = ifTag.doStartTag();
    	ifTag.doEndTag();
    	int r2 = elseTag.doStartTag();
    	elseTag.doEndTag();
    	
    	assertEquals(TagSupport.SKIP_BODY, r1);
    	assertEquals(TagSupport.EVAL_BODY_INCLUDE, r2);
    }
    
    public void testIfElseIf() throws Exception {
    	IfTag ifTag = new IfTag();
    	ifTag.setPageContext(pageContext);
    	ifTag.setTest("false");
    	
    	ElseIfTag elseIfTag1 = new ElseIfTag();
    	elseIfTag1.setPageContext(pageContext);
    	elseIfTag1.setTest("false");
    	
    	ElseIfTag elseIfTag2 = new ElseIfTag();
    	elseIfTag2.setPageContext(pageContext);
    	elseIfTag2.setTest("true");
    	
    	ElseIfTag elseIfTag3 = new ElseIfTag();
    	elseIfTag3.setPageContext(pageContext);
    	elseIfTag3.setTest("true");
    	
    	int r1 = ifTag.doStartTag();
    	ifTag.doEndTag();
    	int r2 = elseIfTag1.doStartTag();
    	elseIfTag1.doEndTag();
    	int r3 = elseIfTag2.doStartTag();
    	elseIfTag2.doEndTag();
    	int r4 = elseIfTag3.doStartTag();
    	elseIfTag3.doEndTag();
    	
    	assertEquals(TagSupport.SKIP_BODY, r1);
    	assertEquals(TagSupport.SKIP_BODY, r2);
    	assertEquals(TagSupport.EVAL_BODY_INCLUDE, r3);
    	assertEquals(TagSupport.SKIP_BODY, r4);
    }
    
    public void testIfElseIfElse() throws Exception {
    	IfTag ifTag = new IfTag();
    	ifTag.setPageContext(pageContext);
    	ifTag.setTest("false");
    	
    	ElseIfTag elseIfTag1 = new ElseIfTag();
    	elseIfTag1.setPageContext(pageContext);
    	elseIfTag1.setTest("false");
    	
    	ElseIfTag elseIfTag2 = new ElseIfTag();
    	elseIfTag2.setPageContext(pageContext);
    	elseIfTag2.setTest("false");
    	
    	ElseIfTag elseIfTag3 = new ElseIfTag();
    	elseIfTag3.setPageContext(pageContext);
    	elseIfTag3.setTest("false");
    	
    	ElseTag elseTag = new ElseTag();
    	elseTag.setPageContext(pageContext);
    	
    	int r1 = ifTag.doStartTag();
    	ifTag.doEndTag();
    	int r2 = elseIfTag1.doStartTag();
    	elseIfTag1.doEndTag();
    	int r3 = elseIfTag2.doStartTag();
    	elseIfTag2.doEndTag();
    	int r4 = elseIfTag3.doStartTag();
    	elseIfTag3.doEndTag();
    	int r5 = elseTag.doStartTag();
    	elseTag.doEndTag();
    	
    	assertEquals(TagSupport.SKIP_BODY, r1);
    	assertEquals(TagSupport.SKIP_BODY, r2);
    	assertEquals(TagSupport.SKIP_BODY, r3);
    	assertEquals(TagSupport.SKIP_BODY, r4);
    	assertEquals(TagSupport.EVAL_BODY_INCLUDE, r5);
    }

    protected void setUp() throws Exception {
        // create the needed objects
        tag = new IfTag();
        stack = new OgnlValueStack();

        // create the mock http servlet request
        WebWorkMockHttpServletRequest request = new WebWorkMockHttpServletRequest();
        ActionContext.getContext().setValueStack(stack);
        request.setAttribute(ServletActionContext.WEBWORK_VALUESTACK_KEY, stack);

        // create the mock page context
        pageContext = new MockPageContext();
        pageContext.setRequest(request);
        pageContext.setJspWriter(new MockJspWriter());
        
        // associate the tag with the mock page request
        tag.setPageContext(pageContext);
    }


    class Foo {
        int num;

        public void setNum(int num) {
            this.num = num;
        }

        public int getNum() {
            return num;
        }
    }
}
