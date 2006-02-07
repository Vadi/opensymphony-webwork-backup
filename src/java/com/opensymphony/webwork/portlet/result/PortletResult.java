/*
 * Copyright (c) 2002-2006 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.portlet.result;

import java.io.IOException;
import java.util.StringTokenizer;

import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.dispatcher.WebWorkResultSupport;
import com.opensymphony.webwork.portlet.PortletActionConstants;
import com.opensymphony.webwork.portlet.context.PortletActionContext;
import com.opensymphony.xwork.ActionInvocation;

/**
 * Result type that includes a JSP to render.
 * 
 * @author Nils-Helge Garli
 * @version $Revision$
 *  
 */
public class PortletResult extends WebWorkResultSupport {

    /**
     * Logger instance.
     */
    private static final Log LOG = LogFactory.getLog(PortletResult.class);

    private String contentType = "text/html";

    private String title;

    /**
     * Execute the result. Obtains the
     * {@link javax.portlet.PortletRequestDispatcher}from the
     * {@link PortletActionContext}and includes the JSP.
     * 
     * @see com.opensymphony.xwork.Result#execute(com.opensymphony.xwork.ActionInvocation)
     */
    public void doExecute(String finalLocation,
            ActionInvocation actionInvocation) throws Exception {

        if (PortletActionContext.isRender()) {
            executeRenderResult(finalLocation);
        } else if (PortletActionContext.isEvent()) {
            executeActionResult(finalLocation, actionInvocation);
        } else {
            executeRegularServletResult(finalLocation, actionInvocation);
        }
    }

    /**
     * @param finalLocation
     * @param actionInvocation
     */
    private void executeRegularServletResult(String finalLocation,
            ActionInvocation actionInvocation) throws ServletException, IOException {
        ServletContext ctx = ServletActionContext.getServletContext();
        HttpServletRequest req = ServletActionContext.getRequest();
        HttpServletResponse res = ServletActionContext.getResponse();
        try {
            ctx.getRequestDispatcher(finalLocation).include(req, res);
        } catch (ServletException e) {
            LOG.error("ServletException including " + finalLocation, e);
            throw e;
        } catch (IOException e) {
            LOG.error("IOException while including result '" + finalLocation + "'", e);
            throw e;
        }
    }

    /**
     *  
     */
    protected void executeActionResult(String finalLocation,
            ActionInvocation invocation) {
        LOG.debug("Executing result in Event phase");
        ActionResponse res = PortletActionContext.getActionResponse();
        LOG.debug("Setting event render parameter: " + finalLocation);
        if (finalLocation.indexOf('?') != -1) {
            convertQueryParamsToRenderParams(res, finalLocation
                    .substring(finalLocation.indexOf('?') + 1));
            finalLocation = finalLocation.substring(0, finalLocation
                    .indexOf('?'));
        }
        if (finalLocation.endsWith(".action")) {
            // View is rendered with a view action...luckily...
            finalLocation = finalLocation.substring(0, finalLocation
                    .lastIndexOf("."));
            res.setRenderParameter(PortletActionConstants.ACTION_PARAM, finalLocation);
        } else {
            // View is rendered outside an action...uh oh...
            res.setRenderParameter(PortletActionConstants.ACTION_PARAM, "renderDirect");
            res.setRenderParameter("location", finalLocation);
        }
        res.setRenderParameter(PortletActionConstants.MODE_PARAM, PortletActionContext
                .getRequest().getPortletMode().toString());
    }

    /**
     * @param string
     */
    protected static void convertQueryParamsToRenderParams(
            ActionResponse response, String queryParams) {
        StringTokenizer tok = new StringTokenizer(queryParams, "&");
        while (tok.hasMoreTokens()) {
            String token = tok.nextToken();
            String key = token.substring(0, token.indexOf('='));
            String value = token.substring(token.indexOf('=') + 1);
            response.setRenderParameter(key, value);
        }
    }

    /**
     *  
     */
    protected void executeRenderResult(final String finalLocation) throws PortletException, IOException {
        LOG.debug("Executing result in Render phase");
        PortletConfig cfg = PortletActionContext.getPortletConfig();
        RenderRequest req = PortletActionContext.getRenderRequest();
        RenderResponse res = PortletActionContext.getRenderResponse();
        LOG.debug("PortletConfig: " + cfg);
        LOG.debug("RenderRequest: " + req);
        LOG.debug("RenderResponse: " + res);
        res.setContentType(contentType);
        if (StringUtils.isNotEmpty(title)) {
            res.setTitle(title);
        }
        LOG.debug("Location: " + finalLocation);
        PortletRequestDispatcher preparator = cfg.getPortletContext()
                .getNamedDispatcher("preparator");
        if(preparator == null) {
            throw new PortletException("Cannot look up 'preparator' servlet. Make sure that you" +
            		"have configured it correctly in the web.xml file.");
        }
        new IncludeTemplate() {
            protected void when(PortletException e) {
                LOG.error("PortletException while dispatching to 'preparator' servlet", e);
            }
            protected void when(IOException e) {
                LOG.error("IOException while dispatching to 'preparator' servlet", e);
            }
        }.include(preparator, req, res);
        PortletRequestDispatcher dispatcher = cfg.getPortletContext().getRequestDispatcher(finalLocation);
        if(dispatcher == null) {
            throw new PortletException("Could not locate dispatcher for '" + finalLocation + "'");
        }
        new IncludeTemplate() {
            protected void when(PortletException e) {
                LOG.error("PortletException while dispatching to '" + finalLocation + "'");
            }
            protected void when(IOException e) {
                LOG.error("IOException while dispatching to '" + finalLocation + "'");
            }
        }.include(dispatcher, req, res);
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * @param title The title to set.
     */
    public void setTitle(String title) {
        this.title = title;
    }
    
    static class IncludeTemplate {
        protected void include(PortletRequestDispatcher dispatcher, RenderRequest req, RenderResponse res) throws PortletException, IOException{
            try {
                dispatcher.include(req, res);
            }
            catch(PortletException e) {
                when(e);
                throw e;
            }
            catch(IOException e) {
                when(e);
                throw e;
            }
        }
        
        protected void when(PortletException e) {}
        
        protected void when(IOException e) {}
    }
}