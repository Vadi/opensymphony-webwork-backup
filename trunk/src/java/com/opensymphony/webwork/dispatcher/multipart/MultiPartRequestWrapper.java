/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.dispatcher.multipart;

import com.opensymphony.webwork.config.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;


/**
 * Parses a multipart request and provides a wrapper around the request. The parsing implementation used
 * depends on the <tt>webwork.multipart.parser</tt> setting. It should be set to a class which
 * extends {@link com.opensymphony.webwork.dispatcher.multipart.MultiPartRequest}. <p>
 * <p/>
 * Webwork ships with two implementations,
 * {@link com.opensymphony.webwork.dispatcher.multipart.PellMultiPartRequest} and
 * {@link com.opensymphony.webwork.dispatcher.multipart.CosMultiPartRequest}. The Pell implementation
 * is the default. The <tt>webwork.multipart.parser</tt> property should be set to <tt>pell</tt> for
 * the Pell implementation and <tt>cos</tt> for the Jason Hunter implementation. <p>
 * <p/>
 * The files are uploaded when the object is instantiated. If there are any errors they are logged using
 * {@link #addError(String)}. An action handling a multipart form should first check {@link #hasErrors()}
 * before doing any other processing. <p>
 *
 * @author Matt Baldree
 */
public class MultiPartRequestWrapper extends HttpServletRequestWrapper {
    //~ Static fields/initializers /////////////////////////////////////////////

    protected static final Log log = LogFactory.getLog(MultiPartRequestWrapper.class);

    //~ Instance fields ////////////////////////////////////////////////////////

    Collection errors;
    MultiPartRequest multi;

    //~ Constructors ///////////////////////////////////////////////////////////

    /**
     * Instantiates the appropriate MultiPartRequest parser implementation and processes the data.
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
                parser = com.opensymphony.webwork.dispatcher.multipart.PellMultiPartRequest.class.getName();
            }

            // legacy support for old style property values
            if (parser.equals("pell")) {
                parser = com.opensymphony.webwork.dispatcher.multipart.PellMultiPartRequest.class.getName();
            } else if (parser.equals("cos")) {
                parser = com.opensymphony.webwork.dispatcher.multipart.CosMultiPartRequest.class.getName();
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
     * Get the content encoding type for the given file name. Name is the name field on the input tag.
     *
     * @param name uploaded filename.
     * @return content encoding for file name
     */
    public String getContentType(String name) {
        if (multi == null) {
            return null;
        }

        return multi.getContentType(name);
    }

    /**
     * Returns a collection of any errors generated when parsing the multipart request.
     *
     * @return the error Collection.
     */
    public Collection getErrors() {
        return errors;
    }

    /**
     * Get a {@link java.io.File} for the give file name. Name is the name field on the input tag.
     *
     * @param name uploaded filename
     * @return File object for file name
     */
    public File getFile(String name) {
        if (multi == null) {
            return null;
        }

        return multi.getFile(name);
    }

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
     * Get the filename of the file uploaded for the given input field name. Returns <tt>null</tt> if the
     * file is not found.
     *
     * @param name uploaded filename
     * @return the file system name of the given file or <tt>null</tt> if name not found.
     */
    public String getFilesystemName(String name) {
        if (multi == null) {
            return null;
        }

        return multi.getFilesystemName(name);
    }

    /**
     * @see javax.servlet.http.HttpServletRequest#getParameter(String)
     */
    public String getParameter(String name) {
        return ((multi == null) || (multi.getParameter(name) == null)) ? super.getParameter(name) : multi.getParameter(name);
    }

    /**
     * @see javax.servlet.http.HttpServletRequest#getParameterMap()
     */
    public Map getParameterMap() {
        Map map = new HashMap();
        Enumeration enumeration = getParameterNames();

        while (enumeration.hasMoreElements()) {
            String name = (String) enumeration.nextElement();
            map.put(name, this.getParameterValues(name));
        }

        return map;
    }

    /**
     * @see javax.servlet.http.HttpServletRequest#getParameterNames()
     */
    public Enumeration getParameterNames() {
        if (multi == null) {
            return super.getParameterNames();
        } else {
            return mergeParams(multi.getParameterNames(), super.getParameterNames());
        }
    }

    /**
     * @see javax.servlet.http.HttpServletRequest#getParameterValues(String)
     */
    public String[] getParameterValues(String name) {
        return ((multi == null) || (multi.getParameterValues(name) == null)) ? super.getParameterValues(name) : multi.getParameterValues(name);
    }

    /**
     * Returns <tt>true</tt> if any errors occured when parsing the HTTP multipart request, <tt>false</tt> otherwise.
     *
     * @return <tt>true</tt> if any errors occured when parsing the HTTP multipart request, <tt>false</tt> otherwise.
     */
    public boolean hasErrors() {
        if ((errors == null) || errors.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Adds an error message.
     *
     * @param anErrorMessage the error message to report.
     */
    protected void addError(String anErrorMessage) {
        if (errors == null) {
            errors = new ArrayList();
        }

        errors.add(anErrorMessage);
    }

    /**
     * Merges 2 enumeration of parameters as one.
     *
     * @param params1 the first enumeration.
     * @param params2 the second enumeration.
     * @return a single Enumeration of all elements from both Enumerations.
     */
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
