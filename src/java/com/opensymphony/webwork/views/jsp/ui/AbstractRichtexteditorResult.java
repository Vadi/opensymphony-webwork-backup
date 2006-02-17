/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import java.io.OutputStream;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.opensymphony.webwork.components.AbstractRichtexteditorConnector.CreateFolderResult;
import com.opensymphony.webwork.components.AbstractRichtexteditorConnector.FileUploadResult;
import com.opensymphony.webwork.components.AbstractRichtexteditorConnector.Folder;
import com.opensymphony.webwork.components.AbstractRichtexteditorConnector.FoldersAndFiles;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.Result;

/**
 * Abstrac test case for RichTextEditor's Result 
 * 
 * @author tm_jee
 * @version $Date$ $Id$
 */
public abstract class AbstractRichtexteditorResult implements Result {

	protected Document buildDocument() throws ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.newDocument();
		
		return document;
	}
	
	protected Element buildCommonResponseXml(Document document, String command, String type, String folderPath, String serverPath)  {
		Element connectorElement = document.createElement("Connector");
		connectorElement.setAttribute("command",command);
		connectorElement.setAttribute("resourceType",type);
		document.appendChild(connectorElement);
		
		Element myEl=document.createElement("CurrentFolder");
		myEl.setAttribute("path", folderPath);
		myEl.setAttribute("url", serverPath);
		connectorElement.appendChild(myEl);
		
		return connectorElement;
	}
	
	protected String stringFromDocument(Document document) throws TransformerConfigurationException, TransformerException {
		document.normalizeDocument();
		StringWriter writer = new StringWriter();
		String result = null;
		try {
			writer = new StringWriter();
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer();
			transformer.transform(new DOMSource(document), new StreamResult(writer));
			result = writer.getBuffer().toString();

		}
		finally {
			if (writer != null) 
				try {
					writer.close();
				}
				catch(Exception e) {
				}
				writer = null;
		}
		return result;
	}
	
	protected void writeDocumentToStream(Document document, OutputStream out) throws TransformerConfigurationException, TransformerException {
		document.normalizeDocument();
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer = factory.newTransformer();
		transformer.transform(new DOMSource(document), new StreamResult(out));
	}
	
	protected String getCommand(ActionInvocation invocation) {
		return (String) invocation.getStack().getContext().get("__richtexteditorCommand");
	}
	
	protected String getType(ActionInvocation invocation) {
		return (String) invocation.getStack().getContext().get("__richtexteditorType");
	}
	
	protected String getFolderPath(ActionInvocation invocation) {
		return (String) invocation.getStack().getContext().get("__richtexteditorFolderPath");
	}
	
	protected String getServerPath(ActionInvocation invocation) {
		return (String) invocation.getStack().getContext().get("__richtexteditorServerPath");
	}
	
	protected Folder[] richtexteditorFolders(ActionInvocation invocation) {
		return (Folder[]) invocation.getStack().getContext().get("__richtexteditorGetFolders");
	}
	
	protected FoldersAndFiles richtexteditorFoldersAndFiles(ActionInvocation invocation) {
		return (FoldersAndFiles) invocation.getStack().getContext().get("__richtexteditorGetFoldersAndFiles");
	}
	
	protected CreateFolderResult richtexteditorCreateFolderResult(ActionInvocation invocation) {
		return (CreateFolderResult) invocation.getStack().getContext().get("__richtexteditorCreateFolder");
	}
	
	protected FileUploadResult richtexteditorFileUploadResult(ActionInvocation invocation) {
		return (FileUploadResult) invocation.getStack().getContext().get("__richtexteditorFileUpload");
	}
}
