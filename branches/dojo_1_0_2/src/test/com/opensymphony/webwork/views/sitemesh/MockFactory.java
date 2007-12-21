/*
 * Copyright (c) 2002-2006 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.sitemesh;

import com.opensymphony.module.sitemesh.DecoratorMapper;
import com.opensymphony.module.sitemesh.Factory;
import com.opensymphony.module.sitemesh.PageParser;

/**
 * @author tmjee
 * @version $Date$ $Id$
 */
public class MockFactory extends Factory {
	
	private DecoratorMapper decoratorMapper;
	private PageParser pageParser;
	
	public void setDecoratorMapper(DecoratorMapper decoratorMapper) {
		this.decoratorMapper = decoratorMapper;
	}
	public DecoratorMapper getDecoratorMapper() {
		return this.decoratorMapper;
	}

	public void setPageParser(PageParser pageParser) {
		this.pageParser = pageParser;
	}
	public PageParser getPageParser(String arg0) {
		return this.pageParser;
	}

	public boolean isPathExcluded(String arg0) {
		return false;
	}

	public boolean shouldParsePage(String arg0) {
		return false;
	}
}
