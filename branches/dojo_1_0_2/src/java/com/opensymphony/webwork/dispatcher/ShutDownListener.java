/*
 * Copyright (c) 2002-2007 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.dispatcher;

/**
 * <!-- START SNIPPET: javadoc_snippet -->
 * 
 * An interface to be implemented if require to get informed when WebWork
 * shuts down, It's being hooked up through {@link FilterDispatcher#destroy()} 
 * -> {@link DispatcherUtils#cleanup()} methods calls.
 * <p/>
 * It can be configured through <code>webwork.properties</code> that 
 * resides in the classpath, typically in <code>/WEB-INF/classes</code> directory
 * through the following entry
 * <p/>
 * 
 * <pre>
 * webwork.dispatcher.shutDownListener=foo.bar.ShutdownListener1, foo.bar.ShutdownListener2
 * </pre>
 * 
 * The value of the properties is the FQN (Fully Quantified class name of the shut down listeners)
 *  It must implements this interface, else a ClassCastException will be registered in the log.
 *  Multiple class names could be specified, but they must be comma separated.
 * 
 * <!-- END SNIPPET: javadoc_snippet -->
 * 
 * @author tmjee
 * @version $Date$ $Id$
 */
// START SNIPPET: code
public interface ShutDownListener {
	void shutdown();
}
// END SNIPPET: code
