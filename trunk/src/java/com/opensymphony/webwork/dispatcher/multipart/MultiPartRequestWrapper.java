/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.dispatcher.multipart;

import com.opensymphony.webwork.config.Configuration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.IOException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;


/**
 * <code>MultiPartRequestWrapper</code> will parse a multipart request and
 * provide a wrapper around the request. The parse it uses depends on
 * the "webwork.multipart.parser" setting. It should be set to a class which
 * extends com.opensymphony.webwork.dispatcher.multipart.MultiPartRequest.
 * Webwork ships with two implementations,
 * com.opensymphony.webwork.dispatcher.multipart.PellMultiPartRequest and
 * com.opensymphony.webwork.dispatcher.multipart.CosMultiPartRequest. Pell
 * is the default.
 * "pell" for Jason Pell and "cos" for Jason Hunter.
 *
 * The files are uploaded when the object is instantiated. If there are
 * any errors they are logged using addError. An action handling a
 * multipart form should first check <code>hasErrors()</code> before doing
 * any other processing.
 *
 * Currently the max file size check just checks the ful
 *
 * @author Matt Baldree
 * @version $Revision$
 */
public class MultiPartRequestWrapper extends HttpServletRequestWrapper {
    //~ Static fields/initializers /////////////////////////////////////////////

    protected static final Log log = LogFactory.getLog(MultiPartRequestWrapper.class);

    //~ Instance fields ////////////////////////////////////////////////////////

    Collection errors;
    MultiPartRequest multi;

    //~ Constructors ///////////////////////////////////////////////////////////

    /**
     * Constructor. Creates the appropriate MultiPartRequest object and processes
     * the data.
     *
     * @param request the servlet request object
     * @param saveDir directory to save the file(s) to
     * @param maxSize maximum file size allowed
     */
    public MultiPartRequestWrapper(HttpServletRequest request, String saveDir, int maxSize) throws IOException {
        super(request);

        if (request instanceof MultiPartRequest) {
            multi = (MultiPartRequest) request;
        } else {
            String parser = "";

            parser = Configuration.getString("webwork.multipart.parser");

            // If it's not set, use Pell
            if (parser.equals("")) {
                log.warn("Property webwork.multipart.parser not set." + " Using com.opensymphony.webwork.dispatcher.multipart.PellMultiPartRequest");
                parser = "com.opensymphony.webwork.dispatcher.multipart.PellMultiPartRequest";
            }

            // legacy support for old style property values
            if (parser.equals("pell")) {
                parser = "com.opensymphony.webwork.dispatcher.multipart.PellMultiPartRequest";
            } else if (parser.equals("cos")) {
                parser = "com.opensymphony.webwork.dispatcher.multipart.CosMultiPartRequest";
            }

            try {
                Class baseClazz = com.opensymphony.webwork.dispatcher.multipart.MultiPartRequest.class;

                Class clazz = Class.forName(parser);

                // make sure it extends MultiPartRequest
                if (!baseClazz.isAssignableFrom(clazz)) {
                    addError("Class '" + parser + "' does not extend MultiPartRequest");

                    return;
                }

                // get the constructor
                Constructor ctor = clazz.getDeclaredConstructor(new Class[]{
                    Class.forName("javax.servlet.http.HttpServletRequest"),
                    java.lang.String.class, int.class
                });

                // build the parameter list
                Object[] parms = new Object[]{
                    request, saveDir, new Integer(maxSize)
                };

                // instantiate it
                multi = (MultiPartRequest) ctor.newInstance(parms);
            } catch (ClassNotFoundException e) {
                addError("Class: " + parser + " not found.");
            } catch (NoSuchMethodException e) {
                addError("Constructor error for " + parser + ": " + e);
            } catch (InstantiationException e) {
                addError("Error instantiating " + parser + ": " + e);
            } catch (IllegalAccessException e) {
                addError("Access errror for " + parser + ": " + e);
            } catch (InvocationTargetException e) {
                // This is a wrapper for any exceptions thrown by the ctor called from newInstance
                addError(e.getTargetException().toString());
            }
        }
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    /**
     * Get the content encoding type for the file 'name'. Name is the name field
     * on the input tag.
     *
     * @param name uploaded filename.
     * @return content encoding for file 'name'
     */
    public String getContentType(String name) {
        if (multi == null) {
            return null;
        }

        return multi.getContentType(name);
    }

    /**
     * Returns the error Collection.
     *
     * @return the error Collection.
     */
    public Collection getErrors() {
        return errors;
    }

    /**
     * Get a java.io.File for the file 'name'. Name is the name field on the
     * input tag.
     *
     * @param name uploaded filename
     * @return File object for filename
     */
    public File getFile(String name) {
        if (multi == null) {
            return null;
        }

        return multi.getFile(name);
    }

    // Methods only in MultipartRequest

    /**
     * Get an enumeration of the filenames uploaded
     *
     * @return enumeration of filenames
     */
    public Enumeration getFileNames() {
        if (multi == null) {
            return null;
        }

        return multi.getFileNames();
    }

    /**
     * Get the filename of the file uploaded for the given input field name.
     *
     * @param name uploaded filename
     * @return null if name not found.
     */
    public String getFilesystemName(String name) {
        if (multi == null) {
            return null;
        }

        return multi.getFilesystemName(name);
    }

    /**
     * The value of the url parameter 'name'.
     *
     * @param name to lookup
     * @return url parameter value of name
     */
    public String getParameter(String name) {
        return ((multi == null) || (multi.getParameter(name) == null)) ? super.getParameter(name) : multi.getParameter(name);
    }

    /**
     * An map of all URL Parameters for the current HTTP Request.
     *
     * @return map of params
     */
    public Map getParameterMap() {
        Map map = new HashMap();
        Enumeration enum = getParameterNames();

        while (enum.hasMoreElements()) {
            String name = (String) enum.nextElement();
            map.put(name, this.getParameterValues(name));
        }

        return map;
    }

    /**
     * An enumeration of all URL Parameter names for the current HTTP Request.
     *
     * @return enumeration of names
     */
    public Enumeration getParameterNames() {
        if (multi == null) {
            return super.getParameterNames();
        } else {
            return mergeParams(multi.getParameterNames(), super.getParameterNames());
        }
    }

    /**
     * An array of all URL Parameter values for the current HTTP Request for name.
     *
     * @param name key
     * @return array of values associated with name
     */
    public String[] getParameterValues(String name) {
        return ((multi == null) || (multi.getParameterValues(name) == null)) ? super.getParameterValues(name) : multi.getParameterValues(name);
    }

    /**
     * If any errors have been logged return true.
     *
     * @return true if errors have occured
     */
    public boolean hasErrors() {
        if ((errors == null) || errors.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    protected void addError(String anErrorMessage) {
        if (errors == null) {
            errors = new ArrayList();
        }

        errors.add(anErrorMessage);
    }

    //private
    protected Enumeration mergeParams(Enumeration params1, Enumeration params2) {
        Vector temp = new Vector();

        while (params1.hasMoreElements()) {
            temp.add(params1.nextElement());
        }

        while (params2.hasMoreElements()) {
            temp.add(params2.nextElement());
        }

        return temp.elements();
    }
}
