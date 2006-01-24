/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.components;

import java.util.Iterator;
import java.util.Stack;

import com.opensymphony.webwork.views.jsp.AbstractUITagTest;

/**
 * Test case for method findAncestor(Class) in Component.
 * 
 * @author tm_jee
 * @version $Date$ $Id$
 */
public class ComponentTest extends AbstractUITagTest {

	public void testFindAncestorTest() throws Exception {
    	Property property = new Property(stack);
    	Form form = new Form(stack, request, response);
    	ActionComponent actionComponent = new ActionComponent(stack, request, response);
    	Anchor anchor = new Anchor(stack, request, response);
    	Form form2 = new Form(stack, request, response);
    	TextField textField = new TextField(stack, request, response);
    	
    	
    	Stack stack = property.getComponentStack();
		Iterator i = stack.iterator();

    	
    	try {
    		// component stack
    		assertEquals(property.getComponentStack().size(), 6);
    		assertEquals(i.next(), property);
    		assertEquals(i.next(), form);
    		assertEquals(i.next(), actionComponent);
    		assertEquals(i.next(), anchor);
    		assertEquals(i.next(), form2);
    		assertEquals(i.next(), textField);
    		
    		
    		// property
    		assertNull(property.findAncestor(Component.class));
    		
    		// form
    		assertEquals(form.findAncestor(Component.class), property);
    		assertEquals(form.findAncestor(Property.class), property);
    		
    		// action
    		assertEquals(actionComponent.findAncestor(Component.class), form);
    		assertEquals(actionComponent.findAncestor(Property.class), property);
    		assertEquals(actionComponent.findAncestor(Form.class), form);
    		
    		// anchor
    		assertEquals(anchor.findAncestor(Component.class), actionComponent);
    		assertEquals(anchor.findAncestor(ActionComponent.class), actionComponent);
    		assertEquals(anchor.findAncestor(Form.class), form);
    		assertEquals(anchor.findAncestor(Property.class), property);
    		
    		// form2
    		assertEquals(form2.findAncestor(Component.class), anchor);
    		assertEquals(form2.findAncestor(Anchor.class), anchor);
    		assertEquals(form2.findAncestor(ActionComponent.class), actionComponent);
    		assertEquals(form2.findAncestor(Form.class), form);
    		assertEquals(form2.findAncestor(Property.class), property);
    		
    		// textField
    		assertEquals(textField.findAncestor(Component.class), form2);
    		assertEquals(textField.findAncestor(Form.class), form2);
    		assertEquals(textField.findAncestor(Anchor.class), anchor);
    		assertEquals(textField.findAncestor(ActionComponent.class), actionComponent);
    		assertEquals(textField.findAncestor(Property.class), property);
    	}
    	finally {
    		property.getComponentStack().pop();
    		property.getComponentStack().pop();
    		property.getComponentStack().pop();
    		property.getComponentStack().pop();
    		property.getComponentStack().pop();
    	}
    }
}
