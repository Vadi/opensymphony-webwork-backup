/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.dispatcher;

import com.opensymphony.webwork.WebWorkStatics;

import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionProxy;
import com.opensymphony.xwork.ActionProxyFactory;
import com.opensymphony.xwork.config.ConfigurationManager;
import com.opensymphony.xwork.config.entities.ActionConfig;
import com.opensymphony.xwork.config.entities.ResultConfig;
import com.opensymphony.xwork.util.LocalizedTextUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *
 *
 * @author $Author$
 * @version $Revision$
 */
public class FilterDispatcher implements Filter, WebWorkStatics {
    //~ Static fields/initializers /////////////////////////////////////////////

    private static final Log log = LogFactory.getLog(FilterDispatcher.class);

    //~ Instance fields ////////////////////////////////////////////////////////

    private FilterConfig filterConfig;
    private Map config;

    //~ Methods ////////////////////////////////////////////////////////////////

    /**
     * getter for {@link #filterConfig}
     * <p>
     * Since {@link #filterConfig} is declared private, this getter is
     * needed for subclasses to retrieve it.  Subclasses need this
     * to make this filter work for Weblogic 6.1.
     *
     * @return the filter configuration.
     */
    public FilterConfig getFilterConfig() {
        return filterConfig;
    }

    public void destroy() {
    }

    /**
    * @param servletRequest
    * @param servletResponse
    * @param filterChain
    * @throws IOException
    * @throws ServletException
    */
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //todo verify that this works with namepaces
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // check to see if maybe the ServletDispatcher already fielded this?
        boolean invoked = false;

        if (request.getAttribute("webwork.valueStack") == null) {
            Map namespaceAction = (Map) config.get(request.getServletPath());

            if (namespaceAction != null) {
                Map.Entry entry = (Map.Entry) namespaceAction.entrySet().iterator().next();
                String namespace = (String) entry.getKey();
                String actionName = (String) entry.getValue();
                HashMap extraContext = new HashMap();
                extraContext.put(ActionContext.PARAMETERS, request.getParameterMap());
                extraContext.put(HTTP_REQUEST, request);
                extraContext.put(HTTP_RESPONSE, response);
                extraContext.put(ActionContext.SESSION, new SessionMap(request.getSession()));
                extraContext.put(ActionContext.APPLICATION, new ApplicationMap(filterConfig.getServletContext()));
                extraContext.put(COMPONENT_MANAGER, request.getAttribute("DefaultComponentManager"));

                try {
                    ActionProxy proxy = ActionProxyFactory.getFactory().createActionProxy(namespace, actionName, extraContext);
                    request.setAttribute("webwork.valueStack", proxy.getInvocation().getStack());
                    proxy.execute();
                } catch (Exception e) {
                    try {
                        response.getWriter().write("Unknown error executing action: " + e.getMessage());
                        log.error("Could not execute action", e);
                    } catch (IOException e1) {
                    }
                }

                invoked = true;
            }
        }

        if (!invoked) {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    /**
    * @param filterConfig
    * @throws ServletException
    */
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;

        LocalizedTextUtil.addDefaultResourceBundle("com/opensymphony/webwork/webwork-messages");

        Map namespaceActionConfigs = ConfigurationManager.getConfiguration().getRuntimeConfiguration().getActionConfigs();
        HashMap newConfig = new HashMap();

        for (Iterator iterator = namespaceActionConfigs.entrySet().iterator();
                iterator.hasNext();) {
            Map.Entry entry = (Map.Entry) iterator.next();
            String namespace = (String) entry.getKey();
            Map actionConfigs = (Map) entry.getValue();

            for (Iterator configIterator = actionConfigs.entrySet().iterator();
                    configIterator.hasNext();) {
                Map.Entry entry2 = (Map.Entry) configIterator.next();
                String actionName = (String) entry2.getKey();
                ActionConfig actionConfig = (ActionConfig) entry2.getValue();
                Map results = actionConfig.getResults();
                String view = null;

                // try success first
                String result = "success";
                view = getActionResultConfig(result, actionConfig);

                // next try input
                if (view == null) {
                    result = "input";
                    view = getActionResultConfig(result, actionConfig);
                }

                // we give up, try the first one you can find
                if (view == null) {
                    Map.Entry firstResult = (Map.Entry) results.entrySet().iterator().next();
                    result = (String) firstResult.getKey();

                    view = getActionResultConfig(result, actionConfig);
                }

                if (view != null) {
                    Map namespaceAction = new HashMap();
                    namespaceAction.put(namespace, actionName);
                    newConfig.put(view, namespaceAction);
                }
            }
        }

        this.config = newConfig;
    }

    /**
    * <p>
    * getActionResultConfig returns the value of the location associated with a specified result and ActionConfig.  If
    * there is no associated view or the Result has been configured as something besides dispatcher, this method will
    * return null.
    * </p>
    *
    * <p>
    * Consider the following example xwork.xml configuration:
    * </p>
    *
    * <pre>
    *
    * &lt;action name="SimpleCounter" class="com.opensymphony.webwork.example.counter.SimpleCounter"&gt;
    *   &lt;result name="success" type="dispatcher"&gt;
    *     &lt;param name="location"&gt;/success.jsp&lt;/param&gt;
    *   &lt;/result&gt;
    *   &lt;result name="input" type="chain"&gt;
    *     &lt;param name="actionName"&gt;home&lt;/param&gt;
    *   &lt;/result&gt;
    *   ...
    * &lt;/action&gt;
    * </pre>
    *
    * <p>
    * Assuming actionConfig references the above configuration
    * <ul>
    *   <li>getActionConfig("success", actionConfig) will return /success.jsp</li>
    *   <li>getActionConfig("input", actionConfig) will return null</li>
    *   <li>and getActionConfig("failure", actionConfig) will also return null</li>
    * </ul>
    * </p>
    *
    * @param result the name of the target we're testing against
    * @param actionConfig the action to be introspected
    * @return the path to the view is a view exists or null otherwise
    */
    private String getActionResultConfig(String result, ActionConfig actionConfig) {
        Map results = actionConfig.getResults();

        ResultConfig resultConfig = (ResultConfig) results.get(result);
        Class clazz = resultConfig.getClazz();

        /**
        * getActionResultConfig is _only_ valid for ServletDispatchResults
        */
        if ((clazz == null) || !clazz.equals(ServletDispatcherResult.class)) {
            return null;
        }

        Map map = resultConfig.getParams();

        if (map == null) {
            return null;
        }

        String view = (String) map.get("location");

        return view;
    }
}
