/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.webwork.components.AbstractRichtexteditorConnector;


/**
 * 
 * @author tm_jee
 * @version $Date$ $Id$
 */
public class RichtexteditorCreateFolderResultTest extends AbstractRichtexteditorTest {

	public void testExecuteResult() throws Exception {
		invocation.getStack().getContext().put("__richtexteditorCreateFolder", 
				AbstractRichtexteditorConnector.CreateFolderResult.noErrors());
		invocation.getStack().getContext().put("__richtexteditorCommand", "CreateFolder");
		invocation.getStack().getContext().put("__richtexteditorType", "Image");
		invocation.getStack().getContext().put("__richtexteditorFolderPath", "/folder/path/");
		invocation.getStack().getContext().put("__richtexteditorServerPath", "/server/path/");
		
		RichtexteditorCreateFolderResult result = new RichtexteditorCreateFolderResult();
		result.execute(invocation);
		
		verify(RichtexteditorCreateFolderResultTest.class.getResourceAsStream("RichtexteditorCreateFolderResultTest1.txt"));
	}
}
