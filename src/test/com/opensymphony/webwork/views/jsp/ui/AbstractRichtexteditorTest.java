/*
 * Copyright (c) 2002-2006 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;

import org.codehaus.plexus.util.StringInputStream;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.Difference;
import org.custommonkey.xmlunit.DifferenceConstants;
import org.custommonkey.xmlunit.DifferenceListener;
import org.custommonkey.xmlunit.XMLTestCase;
import org.custommonkey.xmlunit.XMLUnit;
import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.mockobjects.servlet.MockHttpServletResponse;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.views.jsp.AbstractUITagTest;
import com.opensymphony.xwork.mock.MockActionInvocation;
import com.opensymphony.xwork.util.OgnlValueStack;

import junit.framework.TestCase;

/**
 * Abstract test case for all RichTextEditor based test case. (contains common
 * methods)
 * 
 * @author tm_jee
 * @version $Date$ $Id:
 *          AbstractRichtexteditorTest.java 2308 2006-03-04 15:07:50Z rainerh $
 */
public abstract class AbstractRichtexteditorTest extends TestCase {

	protected MockActionInvocation invocation;

	protected MockHttpServletResponse response;

	protected DocumentBuilder expectedParser;

	protected DocumentBuilder actualParser;

	protected void setUp() throws Exception {
		super.setUp();
		// ActionProxy actionProxy =
		// ActionProxyFactory.getFactory().createActionProxy("namespace",
		// "actionName", new HashMap());
		// ActionProxy actionProxy = new MockActionProxy();
		// invocation =
		// ActionProxyFactory.getFactory().createActionInvocation(actionProxy);
		invocation = new MockActionInvocation();
		invocation.setStack(new OgnlValueStack());
		response = new MockHttpServletResponse();
		ServletActionContext.setResponse(response);

		// rig up xmlunit
		XMLUnit.setIgnoreWhitespace(true);
		XMLUnit.getControlDocumentBuilderFactory().setIgnoringComments(true);
		XMLUnit.getTestDocumentBuilderFactory().setIgnoringComments(true);
		ErrorHandler errorHandler = new ErrorHandler() {
			public void error(SAXParseException exception) throws SAXException {
				throw exception;
			}

			public void fatalError(SAXParseException exception)
					throws SAXException {
				throw exception;
			}

			public void warning(SAXParseException exception) {
			}
		};

		expectedParser = XMLUnit.getControlParser();
		expectedParser.setErrorHandler(errorHandler);

		actualParser = XMLUnit.getTestParser();
		actualParser.setErrorHandler(errorHandler);

	}

	protected void tearDown() throws Exception {
		super.tearDown();
		invocation = null;
		response = null;
	}

	protected void verify(InputStream is) throws Exception {
		Document expectedDocument = XMLUnit.buildDocument(expectedParser,
				new InputSource(is));
		Document actualDocument = XMLUnit.buildDocument(actualParser,
				new InputSource(new StringInputStream(response.getOutputStreamContents())));

	       Diff diff = new Diff(expectedDocument, actualDocument) {
	            public int differenceFound(Difference difference) {
	                if (difference == DifferenceConstants.CHILD_NODELIST_SEQUENCE ||
	                        difference == DifferenceConstants.ATTR_SEQUENCE) {
	                    return DifferenceListener.RETURN_ACCEPT_DIFFERENCE;
	                }
	/*
	                if (difference == DifferenceConstants.DOCTYPE_NAME ||
	                        difference == DifferenceConstants.DOCTYPE_PUBLIC_ID ||
	                        difference == DifferenceConstants.DOCTYPE_SYSTEM_ID) {
	                    return DifferenceListener.RETURN_IGNORE_DIFFERENCE_NODES_SIMILAR;
	                }
	*/
	                return super.differenceFound(difference);
	            }
	        };
	        XMLTestCase xmlunit = new XMLTestCase(getName());

	        xmlunit.assertXMLIdentical(diff, true);	}
}
