/*
 *  Copyright (c) 2002-2006 by OpenSymphony
 *  All rights reserved.
 */
package com.opensymphony.webwork.components.template;

import java.io.File;
import java.net.URL;
import java.util.Map;

import com.opensymphony.webwork.components.template.BaseTemplateEngine;
import com.opensymphony.webwork.components.template.Template;
import com.opensymphony.webwork.components.template.TemplateEngine;
import com.opensymphony.webwork.components.template.TemplateRenderingContext;

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
