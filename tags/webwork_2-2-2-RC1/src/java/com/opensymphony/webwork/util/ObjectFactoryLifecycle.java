/*
 * Copyright (c) 2002-2006 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.util;

/**
 * An interface indicating the lifecycle of an ObjectFactory implementation.
 * 
 * @author tm_jee
 * @version $Date$ $Id$
 * 
 * @see ObjectFactoryLifecycle
 * @see com.opensymphony.xwork.ObjectFactory
 * @see com.opensymphony.webwork.util.ObjectFactoryInitializable
 * @see com.opensymphony.webwork.util.ObjectFactoryDestroyable
 */
public interface ObjectFactoryLifecycle extends ObjectFactoryInitializable, ObjectFactoryDestroyable {
	
}
