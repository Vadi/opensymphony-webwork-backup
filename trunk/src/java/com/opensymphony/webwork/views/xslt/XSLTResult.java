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
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
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
 * @author <a href="mailto:meier@meisterbohne.de">Philipp Meier</a>
 */
public class XSLTResult implements Result {
    //~ Static fields/initializers /////////////////////////////////////////////

    private static final Log log = LogFactory.getLog(XSLTResult.class);
    public static final String DEFAULT_PARAM = "location";

    //~ Instance fields ////////////////////////////////////////////////////////

    private String location;
    private boolean parse;
    private Map templatesCache;

    //~ Protected config ///////////////////////////////////////////////////////
    protected boolean noCache = false;


    //~ Constructors ///////////////////////////////////////////////////////////
    public XSLTResult() {
        templatesCache = new HashMap();
        noCache = Configuration.getString("webwork.xslt.nocache").trim().equalsIgnoreCase("true");
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setLocation(String location) {
        this.location = location;
    }

    public void setParse(boolean parse) {
        this.parse = parse;
    }

    public void execute(ActionInvocation invocation) throws Exception {
        long startTime = System.currentTimeMillis();

        if (parse) {
            OgnlValueStack stack = ActionContext.getContext().getValueStack();
            location = TextParseUtil.translateVariables(location, stack);
        }

        try {
            HttpServletResponse response = ServletActionContext.getResponse();

            Writer writer = response.getWriter();

            // Create a transformer for the stylesheet.
            Templates templates = getTemplates(location);
            Transformer transformer = templates.newTransformer();

            String mimeType = templates.getOutputProperties().getProperty(OutputKeys.MEDIA_TYPE);
            if (mimeType == null) {
                // guess (this is a servlet, so text/html might be the best guess)
                mimeType = "text/html";
            }
            response.setContentType(mimeType);

            Source xmlSource = getTraxSourceForStack(invocation.getAction());

            // Transform the source XML to System.out.
            PrintWriter out = response.getWriter();

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

    private Source getTraxSourceForStack(Object action) throws IllegalAccessException, InstantiationException {
        return new DOMSource(new DOMAdapter().adapt(action));
    }

    private Templates getTemplates(String path) throws TransformerException, IOException {
        String pathFromRequest = ServletActionContext.getRequest().getParameter("xslt.location");
        if (pathFromRequest != null) {
            path = pathFromRequest;
        }
        if (path == null) {
            throw new TransformerException("Stylesheet path is null");
        }
        Templates templates = (Templates) templatesCache.get(path);
        if (noCache || templates == null) {
            synchronized (templatesCache) {
                URL resource = ServletActionContext.getServletContext().getResource(path);
                if (resource == null) {
                    throw new TransformerException("Stylesheet " + path + " not found in resources.");
                }

                // This may result in the template being put into the cache multiple times
                // if concurrent requests are made, but that's ok.
                log.debug("Preparing new XSLT stylesheet: " + path);
                TransformerFactory factory = TransformerFactory.newInstance();
                log.trace("Uri-Resolver is: " + factory.getURIResolver());
                factory.setURIResolver(new ServletURIResolver(ServletActionContext.getServletContext()));
                log.trace("Uri-Resolver is: " + factory.getURIResolver());
                templates = factory.newTemplates(new StreamSource(resource.openStream()));
                templatesCache.put(path, templates);
            }
        }
        return templates;

    }
}
