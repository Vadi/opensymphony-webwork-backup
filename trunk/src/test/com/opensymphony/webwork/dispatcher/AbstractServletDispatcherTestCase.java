/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
/*
 * Created on 2/10/2003
 *
 */
package com.opensymphony.webwork.dispatcher;

import com.mockobjects.dynamic.C;
import com.mockobjects.dynamic.Mock;

import com.mockobjects.servlet.MockHttpServletRequest;
import com.mockobjects.servlet.MockHttpServletResponse;
import com.mockobjects.servlet.MockHttpSession;
import com.mockobjects.servlet.MockServletConfig;
import com.mockobjects.servlet.MockServletOutputStream;

import com.opensymphony.xwork.config.ConfigurationManager;
import com.opensymphony.xwork.config.providers.XmlConfigurationProvider;

import junit.framework.TestCase;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;


/**
 * @author CameronBraid
 *
 */
public abstract class AbstractServletDispatcherTestCase extends TestCase {
    //~ Constructors ///////////////////////////////////////////////////////////

    /**
     *
     */
    public AbstractServletDispatcherTestCase(String name) {
        super(name);
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    public String getConfigFilename() {
        return "xwork.xml";
    }

    public abstract ServletDispatcher getServletDispatcher();

    public abstract String getServletPath();

    public void testServletDispatcher() throws ServletException, IOException {
        loadConfig();

        service(getServletDispatcher());
    }

    protected Map getParameterMap() {
        return new HashMap();
    }

    protected void loadConfig() {
        XmlConfigurationProvider c = new XmlConfigurationProvider(getConfigFilename());
        ConfigurationManager.destroyConfiguration();
        ConfigurationManager.addConfigurationProvider(c);
        ConfigurationManager.getConfiguration();
    }

    private void service(HttpServlet servlet) throws ServletException, IOException {
        Mock dispatcherMock = new Mock(RequestDispatcher.class);
        dispatcherMock.expect("include", C.ANY_ARGS);

        MockHttpSession session = new MockHttpSession();

        MockHttpServletRequestExt request = new MockHttpServletRequestExt();
        request.setSession(session);
        request.setupAddHeader("Content-Type", "dunno what this should be... just not multipart !");
        request.setupGetParameterMap(getParameterMap());
        request.setupGetServletPath(getServletPath());
        request.setupGetPathInfo(getServletPath());

        request.setupGetAttribute("javax.servlet.include.servlet_path", null);
        request.setupGetAttribute("DefaultComponentManager", null);

        MockHttpServletResponse response = new MockHttpServletResponse();
        response.setupOutputStream(new MockServletOutputStream());

        Mock servletContextDMock = new Mock(ServletContext.class);
        servletContextDMock.matchAndReturn("getRealPath", C.args(C.eq("")), "");
        servletContextDMock.matchAndReturn("getRealPath", C.args(C.eq("velocity.properties")), null);
        servletContextDMock.matchAndReturn("getRealPath", C.args(C.eq("/WEB-INF/velocity.properties")), null);
        servletContextDMock.matchAndReturn("log", C.ANY_ARGS, null);

        ServletContext servletContextMock = (ServletContext) servletContextDMock.proxy();

        MockServletConfig servletConfigMock = new MockServletConfig();
        servletConfigMock.setServletContext(servletContextMock);

        servlet.init(servletConfigMock);
        servlet.service(request, response);
    }

    //~ Inner Classes //////////////////////////////////////////////////////////

    class MockHttpServletRequestExt extends MockHttpServletRequest {
        public Map attributes = new HashMap();
        private String pathInfo;

        public Object getAttribute(String name) {
            return attributes.get(name);
        }

        public String getPathInfo() {
            return pathInfo;
        }

        public void setupGetAttribute(String name, Object value) {
            this.attributes.put(name, value);
        }

        public void setupGetAttributes(Map attributes) {
            this.attributes = attributes;
        }

        public void setupGetPathInfo(String pathInfo) {
            this.pathInfo = pathInfo;
        }

        //		public Map parameters = new HashMap();
        //		public void setupGetParameters(Map parameters)
        //		{
        //			this.parameters = parameters;
        //		}
        //		
        //		public void setupGetParameter(String name, String[] value)
        //		{
        //			this.parameters.put(name, value);
        //		}
        //
        //		public String getParameter(String name)
        //		{
        //			return (String)parameters.get(name);
        //		}
        //
        //		public Map getParameterMap()
        //		{
        //			return parameters;
        //		}
    }
}
