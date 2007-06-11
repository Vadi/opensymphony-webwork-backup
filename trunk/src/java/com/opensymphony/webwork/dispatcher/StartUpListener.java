/*
 * Copyright (c) 2002-2007 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.dispatcher;

/**
 * <!-- START SNIPPET: javadoc -->
 * 
 * An interface to be implemented if require to get informed when WebWork
 * starts up, It's being hooked up through {@link FilterDispatcher#init(javax.servlet.FilterConfig)} 
 * -> {@link DispatcherUtils#init(javax.servlet.ServletContext)} methods calls.
 * <p/>
 * It can be configured through <code>webwork.properties</code> that 
 * resides in the classpath, typically in <code>/WEB-INF/classes</code> directory
 * through the following entry
 * <p/>
 * 
 * <pre>
 * webwork.dispatcher.startUpListener=foo.bar.StartupListener1, foo.bar.StartupListener2
 * </pre>
 * 
 * The value of the properties is the FQN (Fully Quantified class name of the start up listeners)
 *  It must implements this interface, else a ClassCastException will be registered in the log.
 *  Multiple class names could be specified, but they must be comma separated.
 * 
 * <!-- END SNIPPET: javadoc -->
 * 
 * @author tmjee
 * @version $Date$ $Id$
 */
// START SNIPPET: code
public interface StartUpListener {
	void startup();
}
// END SNIPPET: code
