/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.components.AbstractRichtexteditorConnector.CreateFolderResult;
import com.opensymphony.xwork.ActionInvocation;


/**
 * 
 * @author tm_jee
 * @version $Date$ $Id$
 */
public class RichtexteditorCreateFolderResult extends AbstractRichtexteditorResult {
	
	private static final Log _log = LogFactory.getLog(RichtexteditorCreateFolderResult.class);

	private static final long serialVersionUID = 9024490340530057673L;

	public void execute(ActionInvocation invocation) throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		OutputStream os = response.getOutputStream();
		
		CreateFolderResult createFolderResult = richtexteditorCreateFolderResult(invocation);
		
		Document document = buildDocument();
		Element root = buildCommonResponseXml(document, 
				getCommand(invocation), getType(invocation), 
				getFolderPath(invocation), getServerPath(invocation));
		
		Element errorElement = document.createElement("Error");
		errorElement.setAttribute("number", createFolderResult.getCode());
		root.appendChild(errorElement);
		
		if (_log.isDebugEnabled()) {
			String result = stringFromDocument(document);
			_log.debug(result);
		}
		
		writeDocumentToStream(document, os);
		os.flush();
		os.close();
	}
}
