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
import com.opensymphony.webwork.components.AbstractRichtexteditorConnector.Folder;
import com.opensymphony.xwork.ActionInvocation;

/**
 * @author tm_jee
 * @version $Date$ $Id$
 */
public class RichtexteditorGetFoldersResult extends AbstractRichtexteditorResult {

	private static final Log _log = LogFactory.getLog(RichtexteditorGetFoldersResult.class);
	
	private static final long serialVersionUID = -6414969434944547862L;

	public void execute(ActionInvocation invocation) throws Exception {
		
		Folder[] folders = richtexteditorFolders(invocation);
		HttpServletResponse response = ServletActionContext.getResponse();
		OutputStream os = response.getOutputStream();

		Document document = buildDocument();
		Element root = buildCommonResponseXml(document, 
				getCommand(invocation), getType(invocation), 
				getFolderPath(invocation), getServerPath(invocation));
		
		Element foldersElement = document.createElement("Folders");
		if (folders != null) {
			for (int a=0; a< folders.length; a++) {
				Element folderElement = document.createElement("Folder");
				folderElement.setAttribute("name", folders[a].getFoldername());
				foldersElement.appendChild(folderElement);
			}
		}
		root.appendChild(foldersElement);
		
		if (_log.isDebugEnabled()) {
			String result = stringFromDocument(document);
			_log.debug(result);
		}
		
		writeDocumentToStream(document, os);
		os.flush();
		os.close();
	}
}
