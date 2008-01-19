/*
 * Copyright (c) 2002-2006 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.sitemesh;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.net.URL;
import java.util.Locale;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.opensymphony.module.sitemesh.Config;
import com.opensymphony.module.sitemesh.Decorator;
import com.opensymphony.module.sitemesh.Factory;
import com.opensymphony.module.sitemesh.HTMLPage;
import com.opensymphony.module.sitemesh.Page;
import com.opensymphony.module.sitemesh.PageParser;
import com.opensymphony.module.sitemesh.filter.Buffer;
import com.opensymphony.webwork.WebWorkException;
import com.opensymphony.webwork.components.Component;
import com.opensymphony.webwork.views.JspSupportServlet;
import com.opensymphony.webwork.views.freemarker.FreemarkerManager;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.LocaleProvider;
import com.opensymphony.xwork.util.OgnlValueStack;

import freemarker.template.Configuration;
import freemarker.template.ObjectWrapper;
import freemarker.template.SimpleHash;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * <!-- START SNIPPET: javadoc -->
 * 
 * This is the WebWork component that implements Freemarker's ApplyDecorator
 * Transform. To use this Freemarker Transform, it needs to be enabled in
 * webwork.properties (which is enabled by default)
 * 
 * <pre>
 * webwork.freemarker.sitemesh.applyDecoratorTransform = true
 * </pre>
 * 
 * An example of usage would be as follows:- <p/>
 * 
 * In Sitemesh's decorators.xml
 * <pre>
 *   &lt;decorators defaultdir="/WEB-INF/decorators"&gt;
 *   	....
 *      &lt;decorator name="panel" page="/panelDecorator.ftl" /&gt;
 *   &lt;/decorators*gt;
 * </pre>
 * 
 * Decorator (panelDecorator.ftl)<p/>
 * <pre>
 *  &lt;table border="1"&gt;
 *    &lt;tr&gt;
 *    	&lt;td&gt;${title}&lt;/td&gt;
 *    &lt;/tr&gt;
 *    &lt;tr&gt;
 *      &lt;td&gt;${body}&lt;/td&gt;
 *    &lt;/tr&gt;
 *  &lt;/table&gt;
 * </pre>
 * 
 * Freemarker page that uses decorator <p/>
 * <pre>
 * 	 &lt;html&gt; 
 *    &lt;head&gt;
 *       &lt;title&gt;some title&lt;/title&gt;
 *    &lt;/head&gt;
 *    &lt;body&gt;
 *      &lt;h1&gt;some body title&lt;/h1&gt;
 *      &lt;@sitemesh.applydecorator name=&quot;panel&quot; page=&quot;/pages/pageToBeDecorated.ftl&quot; /&gt;
 *    &lt;/body&gt;
 *   &lt;/html&gt;
 * </pre>
 * 
 * 
 * An example of pageToBeDecorated.ftl<p/>
 * <pre>
 *  &lt;html&gt;
 *    &lt;head&gt;
 *       &lt;title&gt;Panel Title&lt;/title&gt;
 *    &lt;/head&gt;
 *    &lt;body&gt;
 *       Panel Content
 *    &lt;/body&gt;
 *  &lt;/html&gt;
 * </pre>
 * 
 * 
 * The nett outcome would be:-
 * <pre>
 *   &lt;html&gt;
 *     &lt;title&gt;some title&l/title&gt;
 *   &lt;body&gt;
 *     &lt;h1&gt;some body title&lt;/h1&gt;
 *     &lt;table border="1"&gt;
 *       &lt;tr&gt;
 *       	&lt;td&gt;Panel Title&lt;/td&gt;
 *       &lt;/tr&gt;
 *       &lt;tr&gt;
 *          &lt;td&gt;Panel Content&lt;/td&gt;
 *       &lt;/tr&gt;
 *     &lt;/table&gt;
 *   &lt;/body&gt;
 *   &lt;/html&gt;
 * </pre>
 * 
 * The following are method hooks available to ApplyDecoratorBean and its subclass
 * <ul>
 * 	<li>getFreemarkerTemplate(String templatePath) - create a Freemarker Template based on the template path given </li>
 * 	<li>parsePageFromContent(String content) - returns a Sitemesh Page object based on the content as the to-be-decorated-page</li>
 * 	<li>parsePageFromAbsoluteUrl(String absoluteUrl) - returns a Sitemesh Page object using the absoluteUrl to get the content of the to-be-decorated-page</li>
 * 	<li>parsePageFromRelativeUrlPath(String relativeUrl) - returns a Sitemesh Page object using the relativeUrl to get the content of the to-be-decorated-page.</li>
 * 	<li>getSitemeshFactory() - returns a Sitemesh Factory object</li>
 * 	<li>getPageParser(String contentType) - returns a Sitemesh PageParser object</li>
 * 	<li>getDecorator(HttpServletRequest request, String decoratorName) - returns a Sitemesh Decorator object with the decoratorName supplied.</li>
 * 	<li>deduceLocale(ActionInvocation invocation, Configuration freemarkerConfiguration) - deduce the Locale from invocation else use the Locale supplied by Freemarker</li>
 * 	<li>createModel() - create a Freemarker model for the template</li>
 * </ul>
 * 
 * <!-- END SNIPPET: javadoc -->
 * 
 * @author tmjee
 * @version $Date$ $Id$
 * 
 * @see SitemeshModel
 * @see FreemarkerManager#buildTemplateModel(OgnlValueStack, Object,
 *      ServletContext, HttpServletRequest, HttpServletResponse, ObjectWrapper)
 */
public class ApplyDecoratorBean extends Component {

	private HttpServletRequest request;
	private HttpServletResponse response;
	
	private String name;
	private String page;
	private String contentType;
	private String encoding;
	
	/**
	 * @param stack
	 */
	public ApplyDecoratorBean(OgnlValueStack stack, 
			HttpServletRequest request, 
			HttpServletResponse response) {
		super(stack);
		this.request = request;
		this.response = response;
	}
	
	/**
	 * Set the name of the Sitemesh decorator to be used.
	 * @return String
	 */
	public String getName() { return this.name; }
	public void setName(String name) { this.name = name; }

	/**
	 * Set the page to be decorated. This could be either an
	 * <ul>
	 * 	<li>absolute url eg. www.google.com</li>
	 *     <li>relative url eg /page/pageToBeDecorated.ftl</li>
	 * </ul>
	 * @return String
	 */
	public String getPage() { return this.page; }
	public void setPage(String page) { this.page = page; }
	
	/**
	 * The content type, default to "text/html".
	 * @return String
	 */
	public String getContentType() { return this.contentType; }
	public void setContentType(String contentType) { this.contentType = contentType; }
	
	/**
	 * Set the encoding eg. "UTF-8".
	 * @return String
	 */
	public String getEncoding() { return this.encoding; }
	public void setEncoding(String encoding) { this.encoding = encoding; }
	
	
	/**
	 * @see com.opensymphony.webwork.components.Component#start(java.io.Writer)
	 */
	public boolean start(Writer writer) {
		return true;
	}
	
	/**
	 * @see com.opensymphony.webwork.components.Component#end(java.io.Writer, java.lang.String, boolean)
	 */
	protected boolean end(Writer writer, String body, boolean popComponentStack) {
		try {
			endAllowingExceptionThrowing(writer, body);
		}
		catch(Exception e) {
			throw new WebWorkException("failed when trying to end the parsing of component ["+this+"]", e);
		}
		
		super.end(writer, body, popComponentStack);
		return false;
	}
	
	/**
	 * Render the <code>page</page> specified with the <code>decorator</page>
	 * 
	 * @param writer
	 * @param body
	 * @throws IOException
	 * @throws ServletException
	 * @throws TemplateException
	 */
	protected void endAllowingExceptionThrowing(Writer writer, String body) throws IOException, ServletException, TemplateException {
		
		if (JspSupportServlet.jspSupportServlet == null) {
			throw new IllegalStateException("JspSupportServlet needs to be configured and loaded on start up");
		}
		
		
		Page pageObject = null;
		if (page == null) {
			pageObject = parsePageObjectFromContent(body);
		}
		else if (page.startsWith("http://") || page.startsWith("https://")) {
			pageObject = parsePageObjectFromAbsoluteUrl(page);
		}
		else {
			pageObject = parsePageObjectFromRelativeUrlPath(page);
		}
		Decorator decorator = getDecorator(request, name);
		
		try {
			Template template = getFreemarkerTemplate(decorator.getPage());
			SimpleHash model = createModel();
			model.put("page", pageObject);
            if (pageObject instanceof HTMLPage) 
            	model.put("head", ((HTMLPage)pageObject).getHead());
            model.put("body", pageObject.getBody());
            model.put("title", pageObject.getTitle());
            model.put("page.properties", pageObject.getProperties());
			template.process(model, writer);
		}
		finally {
		}
	}

	
	/**
	 * Method Hook: <p/>
	 * Returns a Freemarker's Template bsaed on the <code>templatePath</code>
	 * supplied.
	 * 
	 * @param templatePath
	 * @return
	 * @throws IOException
	 * @throws TemplateException
	 */
	protected Template getFreemarkerTemplate(String templatePath) throws IOException, TemplateException {
		FreemarkerManager freemarkerManager = FreemarkerManager.getInstance();
		Configuration configuration = freemarkerManager.getConfiguration(JspSupportServlet.jspSupportServlet.getServletContext());
		
		return configuration.getTemplate(templatePath);
	}

	/**
	 * Returns back a Sitemesh Page object assuming that the <code>content</code> is 
	 * the content of the "to-be-decorated" page.
	 * 
	 * @param content
	 * @return
	 * @throws IOException
	 */
	protected Page parsePageObjectFromContent(String content) throws IOException {
		PageParser pageParser = getPageParser(contentType);
		return pageParser.parse(content.toCharArray());
	}
	
	/**
	 * Returns back a Sitemesh Page object assuming that the <code>absoluteUrl</code> is 
	 * the content of the "to-be-decorated" page.
	 * 
	 * @param absoluteUrl
	 * @return
	 * @throws IOException
	 */
	protected Page parsePageObjectFromAbsoluteUrl(String absoluteUrl) throws IOException {
		StringBuffer content = new StringBuffer(1024);
		URL url = new URL(page);
		BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
		
		int read = -1;
		do {
			char[] buffer = new char[1024];
			read = reader.read(buffer);
			if (read > -1)
				content.append(buffer, 0, read);
		} while (read != -1);
		
		PageParser pageParser = getPageParser(contentType);
		return pageParser.parse(content.toString().toCharArray());
	}
	
	/**
	 * Returns a Sitemesh Page object assuming the <code>relativeUrl</code> is
	 * the freemarker page that is "to-be-decorated".
	 * 
	 * @param relativeUrl
	 * @return
	 * @throws TemplateException
	 * @throws IOException
	 */
	protected Page parsePageObjectFromRelativeUrlPath(String relativeUrl) throws TemplateException, IOException {
        String fullPath = page;
		if (fullPath.length() > 0 && fullPath.charAt(0) != '/') {

            // find absolute path if relative supplied
            String thisPath = request.getServletPath();

            // check if it did not return null (could occur when the servlet container
            // does not use a servlet to serve the requested resouce)
            if (thisPath == null) {
                String requestURI = request.getRequestURI();
                if (request.getPathInfo() != null) {
                    // strip the pathInfo from the requestURI
                    thisPath = requestURI.substring(0, requestURI.indexOf(request.getPathInfo()));
                }
                else {
                    thisPath = requestURI;
                }
            }

            fullPath = thisPath.substring(0, thisPath.lastIndexOf('/') + 1) + fullPath;
            int dotdot;
            while ((dotdot = fullPath.indexOf("..")) > -1) {
                int prevSlash = fullPath.lastIndexOf('/', dotdot - 2);
                fullPath = fullPath.substring(0, prevSlash) + fullPath.substring(dotdot + 2);
            }
		}
		
		contentType = contentType == null ? "text/html" : contentType;
		if (encoding != null)
			contentType = contentType + ";charset="+encoding;
		
        Template decoratorTemplate = getFreemarkerTemplate(fullPath);
        
        Factory factory = getSitemeshFactory();
        Buffer buffer = new Buffer(factory, contentType, encoding);
        SimpleHash model = createModel();
        decoratorTemplate.process(model, buffer.getWriter());
        Page pageObject = buffer.parse();
        return pageObject;
	}
	
	/**
	 * Returns a Sitemesh Factory object.
	 * @return
	 */
	protected Factory getSitemeshFactory() {
		ServletConfig servletConfig = JspSupportServlet.jspSupportServlet.getServletConfig();
		Config config = new Config(servletConfig);
		Factory factory = Factory.getInstance(config);
		return factory;
	}
	
	/**
	 * Returns a Sitemesh PageParser object with the <code>contentType</code>
	 * specified, which by default is "text/html".
	 * 
	 * @param contentType
	 * @return
	 */
	protected PageParser getPageParser(String contentType) {
		Factory factory = getSitemeshFactory();
		return factory.getPageParser(contentType == null ? "text/html" : contentType);
	}
	
	
	/**
	 * Returns a Sitemesh Decorator object with the <code>decoratorName</code> specified.
	 * 
	 * @param request
	 * @param decoratorName
	 * @return
	 */
	protected Decorator getDecorator(HttpServletRequest request, String decoratorName) {
		return getSitemeshFactory().getDecoratorMapper().getNamedDecorator(request, decoratorName);
	}
	
	/**
	 * Attempt to deduce the Locale, based on <code>invocation</code> and 
	 * Freemarker's <code>configuration</code>.
	 * 
	 * @param invocation
	 * @param configuration
	 * @return
	 */
	protected Locale deduceLocale(ActionInvocation invocation, Configuration configuration) {
        if (invocation.getAction() instanceof LocaleProvider) {
            return ((LocaleProvider) invocation.getAction()).getLocale();
        } else {
            return configuration.getLocale();
        }
    }
	
	/**
	 * Create a Freemarker's Model through {@link FreemarkerManager#buildTemplateModel(OgnlValueStack, Object, ServletContext, HttpServletRequest, HttpServletResponse, ObjectWrapper)}.
	 * 
	 * @return
	 * @throws TemplateException
	 */
	protected SimpleHash createModel() throws TemplateException {
		FreemarkerManager freemarkerManager = FreemarkerManager.getInstance();
		ActionInvocation invocation = ActionContext.getContext().getActionInvocation();
		ServletContext servletContext = JspSupportServlet.jspSupportServlet.getServletContext();
		OgnlValueStack stack = ActionContext.getContext().getValueStack();
		ObjectWrapper wrapper = freemarkerManager.getConfiguration(servletContext).getObjectWrapper();
		
		Object action = null;
        if(invocation!= null ) 
        	action = invocation.getAction(); //Added for NullPointException
        
		return freemarkerManager.buildTemplateModel(stack, action, servletContext, request, response, wrapper);
	}
}
