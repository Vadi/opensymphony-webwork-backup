/*
 * Copyright (c) 2002-2007 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.components;

import java.net.URISyntaxException;

import org.springframework.mock.web.MockServletContext;

import junit.framework.TestCase;

/**
 * @author tmjee
 * @version $Date$ $Id$
 */
public class DefaultRichtexteditorConnectorTest extends TestCase {

	public void testCalculateActualServerPath() throws Exception {
		MockServletContext servletContext = new MockServletContext() {
			public String getRealPath(String path) {
				return "C:\\eclipse\\workspace\\myProject\\web"+path;
			}
		};
		DefaultRichtexteditorConnector connector = new DefaultRichtexteditorConnector() {
			private static final long serialVersionUID = -3436164274268111578L;

			protected boolean makeDirIfNotExists(String path) throws URISyntaxException {
				return true;
			}
		};
		connector.setServletContext(servletContext);
		String result = connector.calculateActualServerPath("\\com\\opensymphony\\webwork\\static\\richtexteditor\\data", "image", "/testing");
		assertEquals("file:////C:/eclipse/workspace/myProject/web/WEB-INF/classes/com/opensymphony/webwork/static/richtexteditor/data/image/testing", result);
	}
}
