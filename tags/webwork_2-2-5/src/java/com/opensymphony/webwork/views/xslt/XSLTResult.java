/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.xslt;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.config.Configuration;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.Result;
import com.opensymphony.xwork.util.OgnlValueStack;
import com.opensymphony.xwork.util.TextParseUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


/**
 * XSLTResult provides support for XSLT generated views of WebWork actions.
 * XSLTResult dynamically creates a DOM representation of the invoking WebWork
 * action object and applies a specified stylesheet to it.
 *
 * The DOM representation of the WebWork action is produced by JavaBeans
 * style introspection of the properties of the action using an
 * extensible AdapterFactory.  Common Java types and collection are
 * supported as well as arbitrary beans composed of these types.  (See
 * AdapterFactory).  Action properties may also return their own DOMs
 * (Object implementing Node, Document, or other DOM types) which will then
 * appear directly as part of the the result DOM for the stylesheet.
 *
 * The contents of the result tag normally specify the location of an XSL
 * stylesheet, relative to the servlet context. e.g.:
 *
 *  <result-type name="xslt" class="com.opensymphony.webwork.views.xslt.XSLTResult"/>
 *  ...
 *  <result name="success" type="xslt">/foo.xsl</result>
 *
 * If the stylesheet location is absent, the raw XML is returned.
 *
 * By default, stylesheets are cached for performance.  This can be disabled
 * by setting the webwork property webwork.xslt.nocache to true.
 *
 * XSLTResult utilizes a servlet context sensitive URIResolve
 *
 * @see AdapterFactory
 * @author <a href="mailto:meier@meisterbohne.de">Philipp Meier</a>
 * @author Pat Niemeyer (pat@pat.net
 */
public class XSLTResult implements Result {
    //~ Static fields/initializers /////////////////////////////////////////////

    private static final Log log = LogFactory.getLog(XSLTResult.class);
    public static final String DEFAULT_PARAM = "stylesheetLocation";

    //~ Instance fields ////////////////////////////////////////////////////////

    protected boolean noCache = false;
    private Map templatesCache;
    private String stylesheetLocation;
    private boolean parse;
    private AdapterFactory adapterFactory;

    //~ Constructors ///////////////////////////////////////////////////////////

    public XSLTResult() {
        templatesCache = new HashMap();
        noCache = Configuration.getString("webwork.xslt.nocache").trim().equalsIgnoreCase("true");
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    /**
     * @deprecated Use #setStylesheetLocation(String)
     */
    public void setLocation(String location) {
        setStylesheetLocation(location);
    }

    public void setStylesheetLocation(String location) {
        if (location == null)
            throw new IllegalArgumentException("Null location");
        System.out.println("location = " + location);
        this.stylesheetLocation = location;
    }

    public String getStylesheetLocation() {
        return stylesheetLocation;
    }

    /**
     * If true, parse the stylesheet location for OGNL expressions.
     *
     * @param parse
     */
    public void setParse(boolean parse) {
        this.parse = parse;
    }

    public void execute(ActionInvocation invocation) throws Exception {
        long startTime = System.currentTimeMillis();
        String location = getStylesheetLocation();

        if (parse) {
            OgnlValueStack stack = ActionContext.getContext().getValueStack();
            location = TextParseUtil.translateVariables(location, stack);
        }

        try {
            HttpServletResponse response = ServletActionContext.getResponse();

            Writer writer = response.getWriter();

            // Create a transformer for the stylesheet.
            Templates templates = null;
            Transformer transformer;
            if (location != null) {
                templates = getTemplates(location);
                transformer = templates.newTransformer();
            } else
                transformer = TransformerFactory.newInstance().newTransformer();

            transformer.setURIResolver(getURIResolver());

            // debug xslt
            //PrintTraceListener ptl = new PrintTraceListener(new PrintWriter(System.out, true));
            //ptl.m_traceElements = true;
            //ptl.m_traceSelection = true;
            //ptl.m_traceSelection = true;
            //TransformerImpl ti = (TransformerImpl)transformer;
            //ti.getTraceManager().addTraceListener( ptl );

            String mimeType = null;
            if (templates == null)
                mimeType = "text/xml"; // no stylesheet, raw xml
            else
                mimeType = templates.getOutputProperties().getProperty(OutputKeys.MEDIA_TYPE);
            if (mimeType == null) {
                // guess (this is a servlet, so text/html might be the best guess)
                mimeType = "text/html";
            }

            response.setContentType(mimeType);

            Source xmlSource = getDOMSourceForStack(invocation.getAction());

            // Transform the source XML to System.out.
            PrintWriter out = response.getWriter();

            log.debug("xmlSource = " + xmlSource);
            transformer.transform(xmlSource, new StreamResult(out));

            out.close(); // ...and flush...

            if (log.isDebugEnabled()) {
                log.debug("Time:" + (System.currentTimeMillis() - startTime) + "ms");
            }

            writer.flush();
        } catch (Exception e) {
            log.error("Unable to render XSLT Template, '" + location + "'", e);
            throw e;
        }
    }

    protected AdapterFactory getAdapterFactory() {
        if (adapterFactory == null)
            adapterFactory = new AdapterFactory();
        return adapterFactory;
    }

    protected void setAdapterFactory(AdapterFactory adapterFactory) {
        this.adapterFactory = adapterFactory;
    }

    /**
     * Get the URI Resolver to be called by the processor when it encounters an xsl:include, xsl:import, or document()
     * function. The default is an instance of ServletURIResolver, which operates relative to the servlet context.
     */
    protected URIResolver getURIResolver() {
        return new ServletURIResolver(
                ServletActionContext.getServletContext());
    }

    protected Templates getTemplates(String path) throws TransformerException, IOException {
        String pathFromRequest = ServletActionContext.getRequest().getParameter("xslt.location");

        if (pathFromRequest != null)
            path = pathFromRequest;

        if (path == null)
            throw new TransformerException("Stylesheet path is null");

        Templates templates = (Templates) templatesCache.get(path);

        if (noCache || (templates == null)) {
            synchronized (templatesCache) {
                URL resource = ServletActionContext.getServletContext().getResource(path);

                if (resource == null) {
                    throw new TransformerException("Stylesheet " + path + " not found in resources.");
                }

                log.debug("Preparing XSLT stylesheet templates: " + path);

                TransformerFactory factory = TransformerFactory.newInstance();
                templates = factory.newTemplates(new StreamSource(resource.openStream()));
                templatesCache.put(path, templates);
            }
        }

        return templates;
    }

    protected Source getDOMSourceForStack(Object action)
            throws IllegalAccessException, InstantiationException {
        return new DOMSource(getAdapterFactory().adaptDocument("result", action) );
	}
}
