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
import com.opensymphony.webwork.components.AbstractRichtexteditorConnector.File;
import com.opensymphony.webwork.components.AbstractRichtexteditorConnector.Folder;
import com.opensymphony.webwork.components.AbstractRichtexteditorConnector.FoldersAndFiles;
import com.opensymphony.xwork.ActionInvocation;

/**
 * @author tm_jee
 * @version $Date$ $Id$
 */
public class RichtexteditorGetFoldersAndFilesResult extends AbstractRichtexteditorResult {

	private static final Log _log = LogFactory.getLog(RichtexteditorGetFoldersAndFilesResult.class);
	
	private static final long serialVersionUID = -8405656868125936172L;

	public void execute(ActionInvocation invocation) throws Exception {
		
		HttpServletResponse response = ServletActionContext.getResponse();
		OutputStream os = response.getOutputStream();
		
		Document document = buildDocument();
		Element root = buildCommonResponseXml(document, 
				getCommand(invocation), getType(invocation), 
				getFolderPath(invocation), getServerPath(invocation));
		
		FoldersAndFiles foldersAndFiles = richtexteditorFoldersAndFiles(invocation);
		
		Folder[] folders = foldersAndFiles.getFolders();
		File[] files = foldersAndFiles.getFiles();
		
		Element foldersElement = document.createElement("Folders");
		if (folders != null) {
			for (int a=0; a< folders.length; a++) {
				Element folderElement = document.createElement("Folder");
				folderElement.setAttribute("name", folders[a].getFoldername());
				foldersElement.appendChild(folderElement);
			}
		}
		root.appendChild(foldersElement);
		
		Element filesElement = document.createElement("Files");
		if (files != null) {
			for (int a=0; a< files.length; a++) {
				Element fileElement = document.createElement("File");
				fileElement.setAttribute("name", files[a].getFilename());
				fileElement.setAttribute("size", String.valueOf(files[a].getSizeInKb()));
				filesElement.appendChild(fileElement);
			}
		}
		root.appendChild(filesElement);
		
		if (_log.isDebugEnabled()) {
			String result = stringFromDocument(document);
			_log.debug(result);
		}
		
		writeDocumentToStream(document, os);
		os.flush();
		os.close();
	}
}
