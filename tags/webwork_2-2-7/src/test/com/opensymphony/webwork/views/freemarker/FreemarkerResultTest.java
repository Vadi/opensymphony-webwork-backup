/*
 * Copyright (c) 2002-2007 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.freemarker;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Locale;

import com.opensymphony.webwork.WebWorkTestCase;
import com.opensymphony.xwork.mock.MockActionInvocation;

import freemarker.template.Configuration;
import freemarker.template.SimpleHash;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

/**
 * @author tmjee
 * @version $Date$ $Id$
 */
public class FreemarkerResultTest extends WebWorkTestCase {

	private boolean postTemplateProcessCalled = false;
	private boolean preTemplateProcessCalled = false;
	
	/**
	 * This is just a very simple sanity test just to make sure that with 
	 * writeCompleted=true, we still get the normal output writen.
	 * @throws Exception
	 */
	public void testFreemarkerResultMakeSureWeGetTheDesiredResultWhenWriteCompleteIsTrue() throws Exception {
		preTemplateProcessCalled = false;
		postTemplateProcessCalled = false;
		final StringWriter writer = new StringWriter();
		
		FreemarkerResult result = new FreemarkerResult() {
			protected Configuration getConfiguration() throws TemplateException {
				return new Configuration() {
					public Template getTemplate(String arg0, Locale locale) throws IOException {
						return new Template("test.ftl", new StringReader("testing")) {
						};
					}
				};
			}
			
			protected Writer getWriter() throws IOException {
				return writer;
			}
			
			protected boolean preTemplateProcess(Template template, TemplateModel model) throws IOException, TemplateException {
				preTemplateProcessCalled = true;
				return true;
			}
			
			protected void postTemplateProcess(Template template, TemplateModel data) throws IOException {
				postTemplateProcessCalled = true;
			}
			
			protected TemplateModel createModel() throws TemplateModelException {
				return new SimpleHash();
			}
		};
		
		result.setBufferOutput(true);
		result.doExecute("/test.ftl", new MockActionInvocation());
		
		assertEquals(writer.getBuffer().toString(), "testing");
		assertTrue(preTemplateProcessCalled);
		assertTrue(postTemplateProcessCalled);
	}
	
	/**
	 * This is just a very simple sanity test just to make sure that with 
	 * writeCompleted=false, we still get the normal output writen.
	 * @throws Exception
	 */
	public void testFreemarkerResultMakeSureWeGetTheDesiredResultWhenWriteCompleteIsFalse() throws Exception {
		preTemplateProcessCalled = false;
		postTemplateProcessCalled = false;
		final StringWriter writer = new StringWriter();
		
		FreemarkerResult result = new FreemarkerResult() {
			protected Configuration getConfiguration() throws TemplateException {
				return new Configuration() {
					public Template getTemplate(String arg0, Locale locale) throws IOException {
						return new Template("test.ftl", new StringReader("testing")) {
						};
					}
				};
			}
			
			protected Writer getWriter() throws IOException {
				return writer;
			}
			
			protected boolean preTemplateProcess(Template template, TemplateModel model) throws IOException, TemplateException {
				preTemplateProcessCalled = true;
				return true;
			}
			
			protected void postTemplateProcess(Template template, TemplateModel data) throws IOException {
				postTemplateProcessCalled = true;
			}
			
			protected TemplateModel createModel() throws TemplateModelException {
				return new SimpleHash();
			}
		};
		
		result.setBufferOutput(false);
		result.doExecute("/test.ftl", new MockActionInvocation());
		
		assertEquals(writer.getBuffer().toString(), "testing");
		assertTrue(preTemplateProcessCalled);
		assertTrue(postTemplateProcessCalled);
	}
}
