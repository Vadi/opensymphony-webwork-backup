/*
 * Copyright (c) 2002-2007 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.interceptor;

import java.util.Map;

/**
 * @author tmjee
 * @author Matthew Payne
 * @version $Date$ $Id$
 */
public interface CookiesAware {
	void setCookiesMap(Map cookies);
}
