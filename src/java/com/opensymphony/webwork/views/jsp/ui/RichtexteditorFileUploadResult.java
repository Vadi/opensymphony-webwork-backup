/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.components.AbstractRichtexteditorConnector.FileUploadResult;
import com.opensymphony.xwork.ActionInvocation;

/**
 * @author tm_jee
 * @version $Date$ $Id$
 */
public class RichtexteditorFileUploadResult extends AbstractRichtexteditorResult {
	
	private static final long serialVersionUID = 4094812005581706392L;
	
	private static final Log _log = LogFactory.getLog(RichtexteditorFileUploadResult.class);
	
	
	public void execute(ActionInvocation invocation) throws Exception {
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html; charset=UTF-8");
		response.setHeader("Cache-Control","no-cache");
		PrintWriter out = response.getWriter();
		
		/**
		 <script type="text/javascript">
		 	window.parent.frames['frmUpload'].OnUploadCompleted(0) ;
        </script>
		 */
		
		FileUploadResult fileUploadResult = richtexteditorFileUploadResult(invocation);
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("<script type=\"text/javascript\">");
		if (fileUploadResult.getFilename() != null) {
			buffer.append("window.parent.frames['frmUpload'].OnUploadCompleted("+fileUploadResult.getCode()+", '"+fileUploadResult.getFilename()+"');");
		}
		else {
			buffer.append("window.parent.frames['frmUpload'].OnUploadCompleted("+fileUploadResult.getCode()+");");
		}
		buffer.append("</script>");
		
		String s = buffer.toString();
		if (_log.isDebugEnabled()) {
			_log.debug(s);
		}
		
		out.println(s);
		out.flush();
		out.close();
	}

}
