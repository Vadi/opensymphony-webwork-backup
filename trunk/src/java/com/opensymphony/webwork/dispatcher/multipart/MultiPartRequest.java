/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.dispatcher.multipart;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;


/**
 * Abstract wrapper class HTTP requests to handle multi-part data. <p>
 *
 * @author <a href="mailto:matt@smallleap.com">Matt Baldree</a>
 * @author Patrick Lightbody
 * @author Bill Lynch (docs)
 */
public abstract class MultiPartRequest {
    //~ Static fields/initializers /////////////////////////////////////////////

    protected static Log log = LogFactory.getLog(MultiPartRequest.class);

    //~ Methods ////////////////////////////////////////////////////////////////

    /**
     * Returns <tt>true</tt> if the request is multipart form data, <tt>false</tt> otherwise.
     *
     * @param request the http servlet request.
     * @return <tt>true</tt> if the request is multipart form data, <tt>false</tt> otherwise.
     */
    public static boolean isMultiPart(HttpServletRequest request) {
        String content_type = request.getHeader("Content-Type");

        return ((content_type == null) || !content_type.startsWith("multipart/form-data")) ? false : true;
    }

    /**
     * Returns the content type of the specified file (as supplied by the client browser), or
     * <tt>null</tt> if the file was not included.
     *
     * @param name the name of the file to check
     * @return the file's content type or <tt>null</tt> if no content type was specified.
     */
    public abstract String getContentType(String name);

    /**
     * Returns a {@link java.io.File} object for the filename specified or <tt>null</tt> if the
     * file was not found.
     *
     * @param name the name of the file
     * @return the File object associated with the given name or <tt>null</tt> if it doesn't exist.
     */
    public abstract File getFile(String name);

    /**
     * Returns a String name list of all uploaded files.
     *
     * @return an enumeration of filenames (Strings).
     */
    public abstract Enumeration getFileNames();

    /**
     * Returns the file system name of the given file name.
     *
     * @param name the name of the file uploaded.
     * @return the file system name of the given file name.
     */
    public abstract String getFilesystemName(String name);

    /**
     * Returns the specified request parameter.
     *
     * @param name the name of the parameter to get
     * @return the parameter or <tt>null</tt> if it was not found.
     */
    public abstract String getParameter(String name);

    /**
     * Returns an enumeration of String parameter names.
     *
     * @return an enumeration of String parameter names.
     */
    public abstract Enumeration getParameterNames();

    /**
     * Returns a list of all parameter values associated with a parameter name. If there is only
     * one parameter value per name the resulting array will be of length 1.
     *
     * @param name the name of the parameter.
     * @return an array of all values associated with the parameter name.
     */
    public abstract String[] getParameterValues(String name);
}
