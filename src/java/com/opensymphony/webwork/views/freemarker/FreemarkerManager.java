/*
 * Created on 18/04/2004
 */
package com.opensymphony.webwork.views.freemarker;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.GenericServlet;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.util.FileManager;
import com.opensymphony.webwork.config.Configuration;
import com.opensymphony.webwork.util.FreemarkerWebWorkUtil;
import com.opensymphony.webwork.views.jsp.ui.OgnlTool;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ObjectFactory;
import com.opensymphony.xwork.util.OgnlValueStack;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.cache.WebappTemplateLoader;
import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.jsp.TaglibFactory;
import freemarker.ext.servlet.HttpRequestHashModel;
import freemarker.ext.servlet.HttpSessionHashModel;
import freemarker.ext.servlet.ServletContextHashModel;
import freemarker.template.ObjectWrapper;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModel;


/**
 * 
 * Static Configuration Manager for the FreemarkerResult's configuration
 * 
 * 
 * @author CameronBraid
 */
public class FreemarkerManager {

    private static final Log log = LogFactory.getLog(FreemarkerManager.class);

    public static final String CONFIG_SERVLET_CONTEXT_KEY = "freemarker.Configuration";

    public static final String KEY_REQUEST = "req";
    public static final String KEY_RESPONSE = "res";
    public static final String KEY_STACK = "stack";
    public static final String KEY_OGNL = "ognl";
    public static final String KEY_UTIL = "wwUtil";
    public static final String KEY_WEBWORK = "webwork";
    public static final String KEY_EXCEPTION = "exception";
    public static final String KEY_ACTION = "action";

    // coppied from freemarker servlet - since they are private
    private static final String ATTR_APPLICATION_MODEL = ".freemarker.Application";
    private static final String ATTR_JSP_TAGLIBS_MODEL = ".freemarker.JspTaglibs";
    private static final String ATTR_SESSION_MODEL = ".freemarker.Session";
    private static final String ATTR_REQUEST_MODEL = ".freemarker.Request";

    // coppied from freemarker servlet - so that there is no dependency on it
    public static final String KEY_APPLICATION = "Application";
    public static final String KEY_REQUEST_MODEL = "Request";
    public static final String KEY_SESSION_MODEL = "Session";
    public static final String KEY_JSP_TAGLIBS = "JspTaglibs";

	private static FreemarkerManager instance = null;
	
	/**
	 * To allow for custom configuration of freemarker, sublcass this class "ConfigManager" and
	 * set the webwork configuration property
	 * <b>webwork.freemarker.configmanager.classname</b> to the fully qualified classname.
	 *
	 * This allows you to override the protected methods in the ConfigMangaer 
	 * to programatically create your own Configuration instance
	 *  
	 */
	public final static synchronized FreemarkerManager getInstance() {
	    if (instance == null) {
	        String classname = FreemarkerManager.class.getName();

	        if (Configuration.isSet("webwork.freemarker.manager.classname")) {
	            classname = Configuration.getString("webwork.freemarker.manager.classname").trim();
            }

            try {
                log.info("Instantiating Freemarker ConfigManager!, " + classname);
                instance = (FreemarkerManager) ObjectFactory.getObjectFactory().buildBean(Class.forName(classname));
            } catch (Exception e) {
                log.fatal("Fatal exception occurred while trying to instantiate a Freemarker ConfigManager instance, " + classname, e);
            }
        }

	    // if the instance creation failed, make sure there is a default instance
	    if (instance == null) {
	        instance = new FreemarkerManager();
	    }
	    return instance;
    }

	/**
	 * create the instance of the freemarker Configuration object
	 * 
	 * this implementation 
	 * <ul>
	 * 	<li>obtains the default configuration from Configuration.getDefaultConfiguration()
	 *  <li>sets up template loading from a ClassTemplateLoader and a WebappTemplateLoader
	 * 	<li>sets up the object wrapper to be the BeansWrapper
	 * 	<li>loads settings from the classpath file /freemarker.properties
	 * </ul>
	 * 
	 * @param servletContext
	 * @return
	 */
    protected freemarker.template.Configuration createConfiguration(ServletContext servletContext) throws TemplateException {

        freemarker.template.Configuration configuration = freemarker.template.Configuration.getDefaultConfiguration();
        
        configuration.setTemplateLoader(getTemplateLoader(servletContext));

        // do this before loading the settings, because the settings may override this
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
        configuration.setObjectWrapper(getObjectWrapper());	
        
		loadSettings(servletContext, configuration);
        
        return configuration;
    }
    
    /**
     * Load the settings from the /freemarker.properties file on the classpath
     * 
     * @see freemarker.template.Configurable#setSetting for the definition of valid settings
     */
    protected void loadSettings(ServletContext servletContext, freemarker.template.Configuration configuration) {
        try {
	        InputStream in = FileManager.loadFile("/freemarker.properties", FreemarkerManager.class);
	        if (in != null) {
	            configuration.setSettings(in);
	        }
        } catch (IOException e) {
            log.error("Error while loading freemarker settings from /freemarker.properties", e);
        } catch (TemplateException e) {
            log.error("Error while loading freemarker settings from /freemarker.properties", e);
        }
    }

    /**
     * @return
     */
    protected BeansWrapper getObjectWrapper() {
        BeansWrapper beansWrapper = new BeansWrapper();
		beansWrapper.setSimpleMapWrapper(true);
        return beansWrapper;
    }

    /**
     * the default template loader is a MultiTemplateLoader which includes
     * a ClassTemplateLoader and a WebappTemplateLoader
     * 
     * The ClassTemplateLoader will resolve fully qualified template includes
     * that begin with a slash. for example /com/company/template/common.ftl
     * 
     * The WebappTemplateLoader attempts to resolve templates relative to the web root folder
     */
    protected TemplateLoader getTemplateLoader(ServletContext servletContext) {
        // presume that most apps will require the class and webapp template loader
        // if people wish to 
        TemplateLoader multiLoader = new MultiTemplateLoader(
            new TemplateLoader[] {
                    new WebappTemplateLoader(servletContext),
                    new ClassTemplateLoader(FreemarkerResult.class, "/")
            });
        return multiLoader;
    }

    public final synchronized freemarker.template.Configuration getConfigruation(ServletContext servletContext) throws TemplateException {
        freemarker.template.Configuration config = (freemarker.template.Configuration)servletContext.getAttribute(CONFIG_SERVLET_CONTEXT_KEY);
        if (config == null) {
            config = createConfiguration(servletContext);
    		// store this configuration in the servlet context 
            servletContext.setAttribute(CONFIG_SERVLET_CONTEXT_KEY, config);
        }
        return config;
    }
    
    public void populateContext(ScopesHashModel model, OgnlValueStack stack, Action action, HttpServletRequest request, HttpServletResponse response) {
        // put the same objects into the context that the velocity result uses
        model.put(KEY_REQUEST, request);
        model.put(KEY_RESPONSE, response);
        model.put(KEY_STACK, stack);
        model.put(KEY_OGNL, OgnlTool.getInstance());
        model.put(KEY_WEBWORK, new FreemarkerWebWorkUtil(stack, request, response));
        model.put(KEY_ACTION, action);

        // support for JSP exception pages, exposing the servlet or JSP exception
        Throwable exception = (Throwable) request.getAttribute("javax.servlet.error.exception");
        if (exception == null) {
            exception = (Throwable) request.getAttribute("javax.servlet.error.JspException");
        }
        if (exception != null) {
            model.put(KEY_EXCEPTION, exception);
        }

    }
    
    public ScopesHashModel buildScopesHashModel(ServletContext servletContext, HttpServletRequest request, HttpServletResponse response, ObjectWrapper wrapper) {
        ScopesHashModel model = new ScopesHashModel(
                wrapper, 
                servletContext, 
                request);

        // Create hash model wrapper for servlet context (the application)
        // only need one thread to do this once, per servlet context
        synchronized(servletContext) {
	        ServletContextHashModel servletContextModel = (ServletContextHashModel) servletContext.getAttribute(ATTR_APPLICATION_MODEL);
	        if (servletContextModel == null && servletContext.getAttribute("webwork.servlet") != null) {
	            servletContextModel = new ServletContextHashModel((GenericServlet)servletContext.getAttribute("webwork.servlet"), wrapper);
	            servletContext.setAttribute(ATTR_APPLICATION_MODEL,servletContextModel);
	            TaglibFactory taglibs = new TaglibFactory(servletContext);
	            servletContext.setAttribute(ATTR_JSP_TAGLIBS_MODEL,taglibs);
	        }
	        model.put(KEY_APPLICATION, servletContextModel);
	        model.put(KEY_JSP_TAGLIBS, (TemplateModel)servletContext.getAttribute(ATTR_JSP_TAGLIBS_MODEL));
        }
        
        // Create hash model wrapper for session
        TemplateHashModel sessionModel;
        if(request.getSession(false) != null) {
            HttpSession session = request.getSession();
            synchronized(session) {
	            sessionModel = (HttpSessionHashModel) session.getAttribute(ATTR_SESSION_MODEL);
	            if (sessionModel == null) {
	                sessionModel = new HttpSessionHashModel(session, wrapper);
	                session.setAttribute(ATTR_SESSION_MODEL, sessionModel);
	            }
            }
            model.put(KEY_SESSION_MODEL, sessionModel);
        }
        else {
            // no session means no attributes ???
//            model.put(KEY_SESSION_MODEL, new SimpleHash());
        }
        
        // Create hash model wrapper for the request
        HttpRequestHashModel requestModel = (HttpRequestHashModel) request.getAttribute(ATTR_REQUEST_MODEL);
        if (requestModel == null || requestModel.getRequest() != request)
        {
            requestModel = new HttpRequestHashModel(request, response, wrapper);
            request.setAttribute(ATTR_REQUEST_MODEL, requestModel);
        }
        model.put(KEY_REQUEST_MODEL, requestModel);
        return model;
    }
}
