/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.dispatcher;

import javax.servlet.http.HttpServletRequest;

import com.opensymphony.webwork.dispatcher.mapper.ActionMapper;
import com.opensymphony.webwork.dispatcher.mapper.ActionMapping;

/**
 * ActionMapper for testing FilterDispatcher (used in FilterDispaatcherTest)
 * @author tm_jee
 * @version $Date$ $Id$
 */
public class NullActionMapper implements ActionMapper {

		private static ActionMapping _actionMapping;
	
		public NullActionMapper() {}
		
		public static void setActionMapping(ActionMapping actionMappingToBeRetrned) {
			_actionMapping = actionMappingToBeRetrned;
		}
		
		public ActionMapping getMapping(HttpServletRequest request) {
			return _actionMapping;
		}

		public String getUriFromActionMapping(ActionMapping mapping) {
			throw new UnsupportedOperationException("operation not supported");
		}
}
