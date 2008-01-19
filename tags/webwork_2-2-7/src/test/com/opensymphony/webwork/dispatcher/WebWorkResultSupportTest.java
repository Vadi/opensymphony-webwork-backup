/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.dispatcher;

import com.opensymphony.webwork.dispatcher.WebWorkResultSupport;
import org.easymock.MockControl;

import com.opensymphony.webwork.WebWorkTestCase;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.ActionSupport;
import com.opensymphony.xwork.util.OgnlValueStack;

/**
 * Test case for WebWorkResultSupport.
 * 
 * @author tm_jee
 * @version $Date$ $Id$
 */
public class WebWorkResultSupportTest extends WebWorkTestCase {

	public void testParse() throws Exception {
		OgnlValueStack stack = new OgnlValueStack();
		stack.push(new ActionSupport() {
			private static final long serialVersionUID = 2482793811882036901L;

			public String getMyLocation() {
				return "ThisIsMyLocation";
			}
		});
		MockControl mockControlActionInvocation = MockControl.createNiceControl(ActionInvocation.class);
		ActionInvocation mockActionInvocation = (ActionInvocation) mockControlActionInvocation.getMock();
		mockActionInvocation.getStack();
		mockControlActionInvocation.setDefaultReturnValue(stack);
		mockControlActionInvocation.replay();
		
		/*ActionInvocation mockActionInvocation = EasyMock.createNiceMock(ActionInvocation.class);
		mockActionInvocation.getStack();
		EasyMock.expectLastCall().andReturn(stack);
		EasyMock.replay(mockActionInvocation);*/
		
		
		InternalWebWorkResultSupport result = new InternalWebWorkResultSupport();
		result.setParse(true);
		result.setEncode(false);
		result.setLocation("/pages/myJsp.jsp?location=${myLocation}");
		
		result.execute(mockActionInvocation);
		
		assertNotNull(result.getInternalLocation());
		assertEquals("/pages/myJsp.jsp?location=ThisIsMyLocation", result.getInternalLocation());
		
		mockControlActionInvocation.verify();
		//EasyMock.verify(mockActionInvocation);
	}
	
	public void testParseAndEncode() throws Exception {
		OgnlValueStack stack = new OgnlValueStack();
		stack.push(new ActionSupport() {
			public String getMyLocation() {
				return "/myPage?param=value&param1=value1";
			}
		});
		
		MockControl mockControlActionInvocation = MockControl.createNiceControl(ActionInvocation.class);
		ActionInvocation mockActionInvocation = (ActionInvocation) mockControlActionInvocation.getMock();
		mockActionInvocation.getStack();
		mockControlActionInvocation.setDefaultReturnValue(stack);
		mockControlActionInvocation.replay();
		
		/*ActionInvocation mockActionInvocation = EasyMock.createNiceMock(ActionInvocation.class);
		mockActionInvocation.getStack();
		EasyMock.expectLastCall().andReturn(stack);
		EasyMock.replay(mockActionInvocation);*/
		
		InternalWebWorkResultSupport result = new InternalWebWorkResultSupport();
		result.setParse(true);
		result.setEncode(true);
		result.setLocation("/pages/myJsp.jsp?location=${myLocation}");
		
		result.execute(mockActionInvocation);
		
		assertNotNull(result.getInternalLocation());
		assertEquals("/pages/myJsp.jsp?location=%2FmyPage%3Fparam%3Dvalue%26param1%3Dvalue1", result.getInternalLocation());
		
		mockControlActionInvocation.verify();
		//EasyMock.verify(mockActionInvocation);
	}
	
	
	public void testNoParseAndEncode() throws Exception {
		OgnlValueStack stack = new OgnlValueStack();
		stack.push(new ActionSupport() {
			public String getMyLocation() {
				return "myLocation.jsp";
			}
		});
		
		MockControl mockControlActionInvocaton = MockControl.createNiceControl(ActionInvocation.class);
		ActionInvocation mockActionInvocation = (ActionInvocation) mockControlActionInvocaton.getMock();
		mockControlActionInvocaton.replay();
		
		/*ActionInvocation mockActionInvocation = EasyMock.createNiceMock(ActionInvocation.class);
		EasyMock.replay(mockActionInvocation);*/
		
		InternalWebWorkResultSupport result = new InternalWebWorkResultSupport();
		result.setParse(false);
		result.setEncode(false); // don't really need this, as encode is only valid when parse is true.
		result.setLocation("/pages/myJsp.jsp?location=${myLocation}");
		
		result.execute(mockActionInvocation);
		
		assertNotNull(result.getInternalLocation());
		assertEquals("/pages/myJsp.jsp?location=${myLocation}", result.getInternalLocation());
		
		mockControlActionInvocaton.verify();
		//EasyMock.verify(mockActionInvocation);
	}
	
	
	public static class InternalWebWorkResultSupport extends WebWorkResultSupport {
		
		private static final long serialVersionUID = 7910355535690184631L;
		
		private String _internalLocation = null;
		
		protected void doExecute(String finalLocation, ActionInvocation invocation) throws Exception {
			_internalLocation = finalLocation;
		}
		
		public String getInternalLocation() {
			return _internalLocation;
		}
	}
}
