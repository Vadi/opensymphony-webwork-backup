/*
 * WebWork, Web Application Framework
 *
 * Distributable under Apache license.
 * See terms of license at opensource.org
 */
package com.opensymphony.webwork.views.jsp;

import com.opensymphony.webwork.config.Configuration;
import com.opensymphony.webwork.util.FastByteArrayOutputStream;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.util.OgnlValueStack;
import org.apache.commons.logging.LogFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.*;

/**
 * Include a servlets output (e.g. result of servlet, or a JSP page).
 *
 * @author Rickard Öberg (rickard@dreambean.com)
 * @author <a href="mailto:scott@atlassian.com">Scott Farquhar</a>
 * @version $Revision$
 */
public class IncludeTag
        extends TagSupport
        implements ParameterizedTag {
    // Static --------------------------------------------------------
    public static void include(String aResult, PageContext aContext)
            throws ServletException, IOException {
        String resourcePath = getContextRelativePath(aContext.getRequest(), aResult);
        RequestDispatcher rd = aContext.getRequest().getRequestDispatcher(resourcePath);

        if (rd == null)
            throw new ServletException("Not a valid resource path:" + resourcePath);

        PageResponse pageResponse = new PageResponse((HttpServletResponse) aContext.getResponse());

        // Include the resource
        rd.include((HttpServletRequest) aContext.getRequest(), pageResponse);

        //write the response back to the JspWriter, using the correct encoding.
        String encoding = getEncoding();
        if (encoding != null) {
            //use the encoding specified in the property file
            pageResponse.getContent().writeTo(aContext.getOut(), encoding);
        } else {
            //use the platform specific encoding
            pageResponse.getContent().writeTo(aContext.getOut(), null);
        }
    }

    private static String encoding;
    private static boolean encodingDefined = true;

    /**
     * Get the encoding specified by the property 'webwork.i18n.encoding' in webwork.properties,
     * or return the default platform encoding if not specified.
     * <p>
     * Note that if the property is not initially defined, this will return the system default,
     * even if the property is later defined.  This is mainly for performance reasons.  Undefined
     * properties throw exceptions, which are a costly operation.
     * <p>
     * If the property is initially defined, it is read every time, until is is undefined, and then
     * the system default is used.
     * <p>
     * Why not cache it completely?  Some applications will wish to be able to dynamically set the
     * encoding at runtime.
     *
     * @return  The encoding to be used.
     */
    private static String getEncoding() {
        if (encodingDefined) {
            try {
                encoding = Configuration.getString("webwork.i18n.encoding");
            } catch (IllegalArgumentException e) {
                encoding = System.getProperty("file.encoding");
                encodingDefined = false;
            }
        }
        return encoding;

    }

    public static String getContextRelativePath(ServletRequest request,
                                                String relativePath) {
        String returnValue;

        if (relativePath.startsWith("/"))
            returnValue = relativePath;
        else if (!(request instanceof HttpServletRequest))
            returnValue = relativePath;
        else {
            HttpServletRequest hrequest = (HttpServletRequest) request;
            String uri = (String)
                    request.getAttribute("javax.servlet.include.servlet_path");
            if (uri == null)
                uri = hrequest.getServletPath();
            returnValue = uri.substring(0, uri.lastIndexOf('/')) + '/' + relativePath;
        }

        // .. is illegal in an absolute path according to the Servlet Spec and will cause
        // known problems on Orion application servers.
        if (returnValue.indexOf("..") != -1) {
            Stack stack = new Stack();
            StringTokenizer pathParts = new StringTokenizer(returnValue.replace('\\', '/'), "/");
            while (pathParts.hasMoreTokens()) {
                String part = pathParts.nextToken();
                if (!part.equals(".")) {
                    if (part.equals(".."))
                        stack.pop();
                    else
                        stack.push(part);
                }
            }

            StringBuffer flatPathBuffer = new StringBuffer();

            for (int i = 0; i < stack.size(); i++)
                flatPathBuffer.append("/" + stack.elementAt(i));

            returnValue = flatPathBuffer.toString();
        }

        return returnValue;
    }

    // Attributes ----------------------------------------------------
    protected String pageAttr;
    protected String valueAttr;
    protected Map params;

    // Public --------------------------------------------------------

    /**
     * Name of page/servlet to include.
     *
     * @param   aPage
     * @deprecated use value attribute instead
     */
    public void setPage(String aPage) {
        pageAttr = aPage;
    }

    /**
     * Name of property whose value is the name of the page/servlet to include.
     *
     * @param   aName
     */
    public void setValue(String aName) {
        valueAttr = aName;
    }

    // ParamTag.Parametric implementation ----------------------------
    /**
     * Add a parameter to the URL of the included page/servlet.
     *
     * @param   name
     * @param   value
     */
    public void addParam(String name, Object value) {
        if (value != null) {
            List currentValues = (List) params.get(name);
            if (currentValues == null) {
                currentValues = new ArrayList();
                params.put(name, currentValues);
            }
            currentValues.add(value);
        }
    }

    // BodyTag implementation ----------------------------------------
    public int doStartTag() throws JspException {
        // Init parameter map
        params = new HashMap();

        return super.doStartTag();
    }

    public int doEndTag() throws JspException {
        OgnlValueStack stack = ActionContext.getContext().getValueStack();


        String page;
        // If value is set, we resolve it to get the page name
        if (valueAttr != null) {
            page = (String) stack.findValue(valueAttr);
        } else {
            page = pageAttr;
        }

        StringBuffer urlBuf = new StringBuffer();

        // Add URL
        urlBuf.append(page);

        // Add request parameters
        if (params.size() > 0) {

            urlBuf.append('?');
            String concat = "";

            // Set parameters
            Iterator enum = params.entrySet().iterator();
            while (enum.hasNext()) {
                Map.Entry entry = (Map.Entry) enum.next();
                Object name = entry.getKey();
                List values = (List) entry.getValue();
                for (int i = 0; i < values.size(); i++) {
                    urlBuf.append(concat);
                    urlBuf.append(name);
                    urlBuf.append('=');

                    try {
                        urlBuf.append(URLEncoder.encode(values.get(i).toString(), "UTF-8"));
                    } catch (Exception e) {

                    }
                    concat = "&";
                }
            }
        }
        params = null;

        String result = urlBuf.toString();

        // Include
        try {
            include(result, pageContext);
        } catch (Exception e) {
            LogFactory.getLog(getClass()).warn("Exception thrown during include of " + result, e);
            throw new JspTagException(e.toString());
        }

        return EVAL_PAGE;
    }

    /* (non-Javadoc)
     * @see com.opensymphony.webwork.views.jsp.ParameterizedTag#getParams()
     */
    public Map getParams() {
        return params;
    }
}

/**
 * Implementation of ServletOutputStream that stores all data written
 * to it in a temporary buffer accessible from {@link #getBuffer()} .
 *
 * @author <a href="joe@truemesh.com">Joe Walnes</a>
 * @author <a href="mailto:scott@atlassian.com">Scott Farquhar</a>
 */
final class PageOutputStream extends ServletOutputStream {
    private FastByteArrayOutputStream buffer;

    public PageOutputStream() {
        buffer = new FastByteArrayOutputStream();
    }

    public void write(byte[] b, int o, int l) throws IOException {
        buffer.write(b, o, l);
    }

    public void write(int i) throws IOException {
        buffer.write(i);
    }

    public void flush() throws IOException {
        buffer.flush();
    }

    public void close() throws IOException {
        buffer.close();
    }

    public void write(byte b[]) throws IOException {
        buffer.write(b);
    }

    /** Return all data that has been written to this OutputStream. */
    public FastByteArrayOutputStream getBuffer() throws IOException {
        flush();
        return buffer;
    }

}


/**
 * Simple wrapper to HTTPServletResponse that will allow getWriter()
 * and getResponse() to be called as many times as needed without
 * causing conflicts.
 * <p>
 * The underlying outputStream is a wrapper around
 * {@link com.opensymphony.webwork.views.jsp.PageOutputStream} which will store
 * the written content to a buffer.
 * <p>
 * This buffer can later be retrieved by calling {@link #getContent}.
 *
 * @author <a href="mailto:joe@truemesh.com">Joe Walnes</a>
 * @author <a href="mailto:scott@atlassian.com">Scott Farquhar</a>
 */
final class PageResponse extends HttpServletResponseWrapper {
    protected PrintWriter pagePrintWriter;
    protected ServletOutputStream outputStream;
    private PageOutputStream pageOutputStream = null;

    /** Create PageResponse wrapped around an existing HttpServletResponse. */
    public PageResponse(HttpServletResponse response) {
        super(response);
    }

    /** Return PrintWriter wrapper around PageOutputStream. */
    public PrintWriter getWriter() throws IOException {
        if (pagePrintWriter == null)
            pagePrintWriter = new PrintWriter(new OutputStreamWriter(getOutputStream(), getCharacterEncoding()));
        return pagePrintWriter;
    }

    /**
     * Return instance of {@link com.opensymphony.webwork.views.jsp.PageOutputStream}
     * allowing all data written to stream to be stored in temporary buffer.
     */
    public ServletOutputStream getOutputStream() throws IOException {
        if (pageOutputStream == null) {
            pageOutputStream = new PageOutputStream();
        }
        return pageOutputStream;
    }

    /**
     * Return the content buffered inside the {@link com.opensymphony.webwork.views.jsp.PageOutputStream}.
     * @return
     * @throws IOException
     */
    public FastByteArrayOutputStream getContent() throws IOException {
        //if we are using a writer, we need to flush the
        //data to the underlying outputstream.
        //most containers do this - but it seems Jetty 4.0.5 doesn't
        if (pagePrintWriter != null)
            pagePrintWriter.flush();

        return ((PageOutputStream) getOutputStream()).getBuffer();
    }
}
