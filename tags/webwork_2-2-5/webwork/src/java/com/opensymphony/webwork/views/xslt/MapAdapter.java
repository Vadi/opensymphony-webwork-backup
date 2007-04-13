/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.xslt;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.*;

/**
 * MapAdapter adapters a java.util.Map type to an XML DOM with the following
 * structure:
 * <pre>
 * 	<myMap>
 * 		<entry>
 * 			<key>...</key>
 * 			<value>...</value>
 * 		</entry>
 * 		...
 * 	</myMap>
 * </pre>
 *
 * @author Pat Niemeyer (pat@pat.net)
 */
public class MapAdapter extends AbstractAdapterElement
{
	private Log log = LogFactory.getLog(this.getClass());

	public MapAdapter() { }

	public MapAdapter(AdapterFactory adapterFactory, AdapterNode parent, String propertyName, Map value) {
		setContext( adapterFactory, parent, propertyName, value );
	}

	public Map map() { return (Map)getPropertyValue(); }

	protected List buildChildAdapters()
	{
		List children = new ArrayList(map().entrySet().size());

		for (Iterator i = map().entrySet().iterator(); i.hasNext();)
		{
			Map.Entry entry = (Map.Entry)i.next();
			Object key = entry.getKey();
			Object value = entry.getValue();
			EntryElement child = new EntryElement(
				getAdapterFactory(), this, "entry", key, value );
			children.add( child );
		}

		return children;
	}

	class EntryElement extends AbstractAdapterElement
	{
		Object key, value;

		public EntryElement(  AdapterFactory adapterFactory,
			AdapterNode parent, String propertyName, Object key, Object value )
		{
			setContext( adapterFactory, parent, propertyName, null/*we have two values*/ );
			this.key = key;
			this.value = value;
		}

		protected List buildChildAdapters()
		{
			List children = new ArrayList();
			children.add( getAdapterFactory().adaptNode( this, "key", key ) );
			children.add( getAdapterFactory().adaptNode( this, "value", value ) );
			return children;
		}
	}
}
