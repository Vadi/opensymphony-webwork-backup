/*
 *  Copyright (c) 2002-2006 by OpenSymphony
 *  All rights reserved.
 */
package com.opensymphony.webwork.components.template;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;

import com.mockobjects.servlet.MockServletContext;
import com.opensymphony.util.ClassLoaderUtil;
import com.opensymphony.webwork.components.template.BaseTemplateEngine;
import com.opensymphony.webwork.components.template.Template;
import com.opensymphony.webwork.components.template.TemplateEngine;
import com.opensymphony.webwork.components.template.TemplateRenderingContext;
import com.opensymphony.webwork.views.JspSupportServlet;

import junit.framework.TestCase;

/**
 * Test case for BaseTemplateEngine.
 * 
 * @author tm_jee
 * @version $Date$ $Id$
 */
public class BaseTemplateEngineTest extends TestCase {

	public void testGetThemePropsThroughFileSystem() throws Exception {
		
		URL dummyResourceUrl = getClass().getResource("dummy.properties");
		File dummyResourceFile = new File(dummyResourceUrl.getFile());
		String themePropertiesDir = dummyResourceFile.getParent();
		
		System.out.println("dummy resource url="+dummyResourceUrl);
		System.out.println("resource file="+dummyResourceFile);
		System.out.println("theme properties dir="+themePropertiesDir);
		
		assertTrue(dummyResourceFile.exists());
		assertNotNull(themePropertiesDir);
		
		Template template = new Template(themePropertiesDir, "theme1", "template1");
		
		TemplateEngine templateEngine = new InnerBaseTemplateEngine("themeThroughFileSystem.properties");
		Map propertiesMap = templateEngine.getThemeProps(template);
		
		assertNotNull(propertiesMap);
		assertTrue(propertiesMap.size() > 0);
		
	}
	
	public void testGetThemePropsThroughClasspath() throws Exception {
		
		Template template = new Template("com/opensymphony/webwork/components/template", "theme1", "template2");
		TemplateEngine templateEngine = new InnerBaseTemplateEngine("themeThroughClassPath.properties");
		Map propertiesMap = templateEngine.getThemeProps(template);
		
		assertNotNull(propertiesMap);
		assertTrue(propertiesMap.size() > 0);
	}
	
	
	public void testGetPropsThroughWebAppResource() throws Exception {
		
		
		final MockServletContext mockServletContext = new MockServletContext() {
			public InputStream getResourceAsStream(String resource) {
				if ("/template/myTheme/theme.properties".equals(resource)) {
					return ClassLoaderUtil.getResourceAsStream("com/opensymphony/webwork/components/template/dummyWithContents.properties", BaseTemplateEngineTest.class);
				}
				return null;
			}
		};
		
		JspSupportServlet.jspSupportServlet = new JspSupportServlet() {
			public ServletContext getServletContext() {
				return mockServletContext;
			}
		};
		
		
		Template template = new Template("template", "myTheme", "myComponent");
		TemplateEngine templateEngine = new InnerBaseTemplateEngine("theme.properties");
		Map propertiesMap = templateEngine.getThemeProps(template);
		assertNotNull(propertiesMap);
		assertEquals(propertiesMap.size(), 3);
		assertEquals(propertiesMap.get("one"), "one");
		assertEquals(propertiesMap.get("two"), "two");
		assertEquals(propertiesMap.get("parent"), "css_xhtml");
	}
	
	
	private boolean wentThroughWebAppPath = false;
	public void testGetPropsThroughWebAppResourceByPassingClassPathResource() throws Exception {
		
		final MockServletContext mockServletContext = new MockServletContext() {
			public InputStream getResourceAsStream(String resource) {
				if ("/template/myTheme/dummy.properties".equals(resource)) {
					wentThroughWebAppPath = true;
					return ClassLoaderUtil.getResourceAsStream("com/opensymphony/webwork/components/template/dummy.properties", BaseTemplateEngineTest.class);
				}
				return null;
			}
		};
		
		JspSupportServlet.jspSupportServlet = new JspSupportServlet() {
			public ServletContext getServletContext() {
				return mockServletContext;
			}
		};
		
		wentThroughWebAppPath = false;
		
		Template template = new Template("template", "myTheme", "myComponent");
		TemplateEngine templateEngine = new InnerBaseTemplateEngine("dummy.properties");
		Map propertiesMap = templateEngine.getThemeProps(template);
		
		assertNotNull(propertiesMap);
		assertTrue(wentThroughWebAppPath);
	}
	
	public void testGetPropsThroughClassPathResourceByPassingWebAppResource() throws Exception {
		JspSupportServlet.jspSupportServlet = null;
		wentThroughWebAppPath = false;
	
		Template template = new Template("template", "myTheme", "myComponent");
		TemplateEngine templateEngine = new InnerBaseTemplateEngine("dummy.properties");
		Map propertiesMap = templateEngine.getThemeProps(template);
		
		assertNotNull(propertiesMap);
		assertFalse(wentThroughWebAppPath);
	}
	
	protected void setUp() throws Exception {
	}
	
	protected void tearDown() throws Exception {
		JspSupportServlet.jspSupportServlet = null;
		wentThroughWebAppPath = false;
	}
	
	
	public class InnerBaseTemplateEngine extends BaseTemplateEngine {
		
		private String themePropertiesFileName;
		
		public InnerBaseTemplateEngine(String themePropertiesFileName) {
			this.themePropertiesFileName = themePropertiesFileName;
		}
		
		protected String getSuffix() {
			return "ftl";
		}

		public void renderTemplate(TemplateRenderingContext templateContext) throws Exception {
		}
		
		protected String getThemePropertiesFileName() {
			return this.themePropertiesFileName;
		}
	}
}
