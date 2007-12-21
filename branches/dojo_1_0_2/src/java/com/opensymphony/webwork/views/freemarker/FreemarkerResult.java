/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
/*
 * Created on 15/04/2004
 */
package com.opensymphony.webwork.views.freemarker;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.dispatcher.WebWorkResultSupport;
import com.opensymphony.webwork.views.util.ResourceUtil;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.LocaleProvider;
import com.opensymphony.xwork.util.OgnlValueStack;
import freemarker.template.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Locale;


/**
 * <!-- START SNIPPET: description -->
 * 
 * Renders a view using the Freemarker template engine. Alternatively, the
 * {@link com.opensymphony.webwork.dispatcher.ServletDispatcherResult
 * dispatcher} result type can be used in conjunction Webwork's {@link
 * FreemarkerServlet}.
 * <p>
 * The FreemarkarManager class configures the template loaders so that the
 * template location can be either
 * </p>
 * 
 * <ul>
 * 
 * <li>relative to the web root folder. eg <code>/WEB-INF/views/home.ftl</code>
 * </li>
 * 
 * <li>a classpath resuorce. eg <code>com/company/web/views/home.ftl</code></li>
 * 
 * </ul>
 * 
 * <p>
 * <b>NOTE (bufferOutput attribute):</b><br/>
 * Allow customization of either (when true) to write result to response
 * stream/writer only when everything is ok (without exception) or otherwise.
 * This is usefull when using Freemarker's "rethrow" exception handler, where we
 * don't want partial of the page to be writen and then exception occurred and
 * we have freemarker's "rethrow" exception handler to take over but its too late
 * since part of the response has already been 'commited' to the stream/writer.
 * </p>
 * 
 * <!-- END SNIPPET: description -->
 * 
 * <b>This result type takes the following parameters:</b>
 * 
 * <!-- START SNIPPET: params -->
 * 
 * <ul>
 * 
 * <li><b>location (default)</b> - the location of the template to process.</li>
 * 
 * <li><b>parse</b> - true by default. If set to false, the location param
 * will not be parsed for Ognl expressions.</li>
 * 
 * <li><b>contentType</b> - defaults to "text/html" unless specified.</li>
 * 
 * <li><b>bufferOutput</b> - default to false. If true, will only write to
 * the response if the whole freemarker page could be rendered ok. </li>
 * 
 * </ul>
 * 
 * <!-- END SNIPPET: params -->
 * 
 * <b>Example:</b>
 * 
 * <pre>
 * &lt;!-- START SNIPPET: example --&gt;
 *  &lt;result name=&quot;success&quot; type=&quot;freemarker&quot;&gt;foo.ftl&lt;/result&gt;
 *  &lt;!-- END SNIPPET: example --&gt;
 * </pre>
 * 
 * @author CameronBraid
 */
public class FreemarkerResult extends WebWorkResultSupport {

	private static final long serialVersionUID = -3778230771704661631L;
	
	protected ActionInvocation invocation;
    protected Configuration configuration;
    protected ObjectWrapper wrapper;

    protected boolean bufferOutput = false;
    
    /*
     * webwork results are constructed for each result execeution
     *
     * the current context is availible to subclasses via these protected fields
     */
    protected String location;
    private String pContentType = "text/html";


    public void setContentType(String aContentType) {
        pContentType = aContentType;
    }

    /**
     * allow parameterization of the contentType
     * the default being text/html
     */
    public String getContentType() {
        return pContentType;
    }
    
    public void setBufferOutput(boolean bufferedOutput) {
    	this.bufferOutput = bufferedOutput;
    }
    
    /**
     * Allow customization of either (when true) to write result to response stream/writer
     * only when everything is ok (without exception) or otherwise. This is usefull 
     * when using Freemarker's "rethrow" exception handler, where we don't want
     * partial of the page to be writen and then exception occurred and we have 
     * freemarker's "rethrow" exception handler to take over but its too late since 
     * part of the response has already been 'commited' to the stream/writer.
     * 
     * @return boolean
     */
    public boolean getBufferOutput() {
    	return bufferOutput;
    }
    

    /**
     * Execute this result, using the specified template location.
     * <p/>
     * The template location has already been interoplated for any variable substitutions
     * <p/>
     * this method obtains the freemarker configuration and the object wrapper from the provided hooks.
     * It them implements the template processing workflow by calling the hooks for
     * preTemplateProcess and postTemplateProcess
     */
    public void doExecute(String location, ActionInvocation invocation) throws IOException, TemplateException {
        this.location = location;
        this.invocation = invocation;
        this.configuration = getConfiguration();
        this.wrapper = getObjectWrapper();
        
        if (!location.startsWith("/")) {
            ActionContext ctx = invocation.getInvocationContext();
            HttpServletRequest req = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
            String base = ResourceUtil.getResourceBase(req);
            location = base + "/" + location;
        }

        Template template = configuration.getTemplate(location, deduceLocale());
        TemplateModel model = createModel();

        // Give subclasses a chance to hook into preprocessing
        if (preTemplateProcess(template, model)) {
            try {
                // Process the template
                // First, get the writer
                Writer writer = null;
                boolean useOutputStream = false;
                try {
                    writer = getWriter();
                }
                catch (IllegalStateException ise) {
                    // Getting the writer failed, try using getOutputStream()
                    // This can happen on some application servers such as WebLogic 8.1
                    useOutputStream = true;
                }
                if (useOutputStream) { 
                	// If we are here, we don't have the issue of WW-1458, since 
                	// we are already writing through a temporary buffer.
                	
                    // Use a StringWriter as a buffer to write the template output to
                    writer = new java.io.StringWriter();
                    template.process(model, writer);
                    writer.flush();
                    
                    // Then write the contents of the writer to the OutputStream
                    java.io.OutputStream os = ServletActionContext.getResponse().getOutputStream();
                    os.write(writer.toString().getBytes());
                }
                else {
                    // Process the template with the normal writer since it was available
                	
                	// WW-1458
                	// Allow customization of either (when true) to write result to response stream/writer
                    // only when everything is ok (without exception) or otherwise. This is usefull 
                    // when using Freemarker's "rethrow" exception handler, where we don't want
                    // partial of the page to be writen and then exception occurred and we have 
                    // freemarker's "rethrow" exception handler to take over but its too late since 
                    // part of the response has already been 'commited' to the stream/writer.
                	if (configuration.getTemplateExceptionHandler() == TemplateExceptionHandler.RETHROW_HANDLER || 
                			getBufferOutput()) {
                		CharArrayWriter tempBuffer = new CharArrayWriter();
                		template.process(model, tempBuffer);
                		tempBuffer.flush();
                		
                		tempBuffer.writeTo(writer);
                	}
                	else {
                		template.process(model, writer);
                	}
                }
            } finally {
                // Give subclasses a chance to hook into postprocessing
                postTemplateProcess(template, model);
            }
        }
    }

    /**
     * This method is called from {@link #doExecute(String, ActionInvocation)} to obtain the
     * FreeMarker configuration object that this result will use for template loading. This is a
     * hook that allows you to custom-configure the configuration object in a subclass, or to fetch
     * it from an IoC container.
     * <p/>
     * <b>
     * The default implementation obtains the configuration from the ConfigurationManager instance.
     * </b>
     */
    protected Configuration getConfiguration() throws TemplateException {
        return FreemarkerManager.getInstance().getConfiguration(ServletActionContext.getServletContext());
    }

    /**
     * This method is called from {@link #doExecute(String, ActionInvocation)}  to obtain the
     * FreeMarker object wrapper object that this result will use for adapting objects into template
     * models. This is a hook that allows you to custom-configure the wrapper object in a subclass.
     * <p/>
     * <b>
     * The default implementation returns {@link Configuration#getObjectWrapper()}
     * </b>
     */
    protected ObjectWrapper getObjectWrapper() {
        return configuration.getObjectWrapper();
    }

    /**
     * The default writer writes directly to the response writer.
     */
    protected Writer getWriter() throws IOException {
        return ServletActionContext.getResponse().getWriter();
    }

    /**
     * Build the instance of the ScopesHashModel, including JspTagLib support
     * <p/>
     * Objects added to the model are
     * <p/>
     * <ul>
     * <li>Application - servlet context attributes hash model
     * <li>JspTaglibs - jsp tag lib factory model
     * <li>Request - request attributes hash model
     * <li>Session - session attributes hash model
     * <li>req - the HttpServletRequst object for direct access
     * <li>res - the HttpServletResponse object for direct access
     * <li>stack - the OgnLValueStack instance for direct access
     * <li>ognl - the instance of the OgnlTool
     * <li>action - the action itself
     * <li>exception - optional : the JSP or Servlet exception as per the servlet spec (for JSP Exception pages)
     * <li>webwork - instance of the WebWorkUtil class
     * </ul>
     */
    protected TemplateModel createModel() throws TemplateModelException {
        ServletContext servletContext = ServletActionContext.getServletContext();
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        OgnlValueStack stack = ServletActionContext.getContext().getValueStack();

        Object action = null;
        if(invocation!= null ) action = invocation.getAction(); //Added for NullPointException
        return FreemarkerManager.getInstance().buildTemplateModel(stack, action, servletContext, request, response, wrapper);
    }

    /**
     * Returns the locale used for the {@link Configuration#getTemplate(String, Locale)} call. The base implementation
     * simply returns the locale setting of the action (assuming the action implements {@link LocaleProvider}) or, if
     * the action does not the configuration's locale is returned. Override this method to provide different behaviour,
     */
    protected Locale deduceLocale() {
        if (invocation.getAction() instanceof LocaleProvider) {
            return ((LocaleProvider) invocation.getAction()).getLocale();
        } else {
            return configuration.getLocale();
        }
    }

    /**
     * the default implementation of postTemplateProcess applies the contentType parameter
     */
    protected void postTemplateProcess(Template template, TemplateModel data) throws IOException {
    }

    /**
     * Called before the execution is passed to template.process().
     * This is a generic hook you might use in subclasses to perform a specific
     * action before the template is processed. By default does nothing.
     * A typical action to perform here is to inject application-specific
     * objects into the model root
     *
     * @return true to process the template, false to suppress template processing.
     */
    protected boolean preTemplateProcess(Template template, TemplateModel model) throws IOException, TemplateException {
        Object attrContentType = template.getCustomAttribute("content_type");

        if (attrContentType != null) {
            ServletActionContext.getResponse().setContentType(attrContentType.toString());
        } else {
            String contentType = getContentType();

            if (contentType == null) {
                contentType = "text/html";
            }

            String encoding = template.getEncoding();

            if (encoding != null) {
                contentType = contentType + "; charset=" + encoding;
            }

            ServletActionContext.getResponse().setContentType(contentType);
        }

        return true;
    }
}
