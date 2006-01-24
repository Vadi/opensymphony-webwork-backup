/*
 * Copyright 2004 BEKK Consulting
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.opensymphony.webwork.portlet;

/**
 * Interface defining some constants used in the WebWork2 portlet implementation
 * 
 * @author <a href="mailto:nils-helge.garli@bekk.no">Nils-Helge Garli</a>
 *  
 */
public interface PortletActionConstants {
	/**
	 * Default action name to use when no default action has been configured in the portlet
	 * init parameters.
	 */
	String DEFAULT_ACTION_NAME = "default";
    /**
     * Key used for looking up and storing the portlet phase
     */
    String PHASE = PortletActionConstants.class.getName() + ".phase";

    /**
     * Constant used for the render phase (
     * {@link javax.portlet.Portlet#render(javax.portlet.RenderRequest, javax.portlet.RenderResponse)})
     */
    Integer RENDER_PHASE = new Integer(1);

    /**
     * Constant used for the event phase (
     * {@link javax.portlet.Portlet#processAction(javax.portlet.ActionRequest, javax.portlet.ActionResponse)})
     */
    Integer EVENT_PHASE = new Integer(2);

    /**
     * Key used for looking up and storing the
     * {@link javax.portlet.PortletRequest}
     */
    String REQUEST = "no.bekk.portletwork.request";

    /**
     * Key used for looking up and storing the
     * {@link javax.portlet.PortletResponse}
     */
    String RESPONSE = "no.bekk.portletwork.response";
    
    /**
     * Key used for looking up and storing the action that was invoked in the event phase.
     */
    String EVENT_ACTION = PortletActionConstants.class.getName() + ".eventAction";

    /**
     * Key used for looking up and storing the
     * {@link javax.portlet.PortletConfig}
     */
    String PORTLET_CONFIG = "no.bekk.portletwork.config";

    /**
     * Name of the action used as error handler
     */
    String ERROR_ACTION = "errorHandler";

    /**
     * Key used to store the exception used in the error handler
     */
    String EXCEPTION_KEY = "no.bekk.portletwork.exception";
    
    String PORTLET_NAMESPACE = PortletActionConstants.class.getName() + ".portletNamespace";
    
    String MODE_NAMESPACE_MAP = PortletActionConstants.class.getName() + ".modeNamespaceMap";
    
    String DEFAULT_ACTION_FOR_MODE = PortletActionConstants.class.getName() + ".defaultActionForMode";
}
