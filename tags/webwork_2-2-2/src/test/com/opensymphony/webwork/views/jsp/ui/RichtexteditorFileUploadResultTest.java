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
public class RichtexteditorFileUploadResultTest extends AbstractRichtexteditorTest {

	public void testExecuteResultWithFileChange() throws Exception {
		invocation.getStack().getContext().put("__richtexteditorFileUpload", 
				AbstractRichtexteditorConnector.FileUploadResult.uploadCompleteWithFilenamChanged("newFile.txt"));
		
		RichtexteditorFileUploadResult result = new RichtexteditorFileUploadResult();
		result.execute(invocation);
		
		verify(RichtexteditorFileUploadResultTest.class.getResourceAsStream("RichtexteditorFileUploadResultTest1.txt"));
	}
	
	public void testExecuteResultWithError() throws Exception {
		invocation.getStack().getContext().put("__richtexteditorFileUpload", 
				AbstractRichtexteditorConnector.FileUploadResult.invalidFile());
		
		RichtexteditorFileUploadResult result = new RichtexteditorFileUploadResult();
		result.execute(invocation);
		
		verify(RichtexteditorFileUploadResultTest.class.getResourceAsStream("RichtexteditorFileUploadResultTest2.txt"));
	}
	
	public void testExecuteResultWithSuccess() throws Exception {
		invocation.getStack().getContext().put("__richtexteditorFileUpload", 
				AbstractRichtexteditorConnector.FileUploadResult.uploadComplete());
		
		RichtexteditorFileUploadResult result = new RichtexteditorFileUploadResult();
		result.execute(invocation);
		
		verify(RichtexteditorFileUploadResultTest.class.getResourceAsStream("RichtexteditorFileUploadResultTest3.txt"));
	}
}
