/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.dispatcher.multipart;

import com.opensymphony.webwork.config.Configuration;

import http.utils.multipartrequest.ServletMultipartRequest;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;


/**
 * Multipart form data request adapter for Jason Pell's multipart utils package.
 *
 * @author <a href="matt@smallleap.com">Matt Baldree</a> (modified for WW's use)
 * @author <a href="scott@atlassian.com">Scott Farquhar</a> (added i18n handling (WW-109))
 */
public class PellMultiPartRequest extends MultiPartRequest {
    //~ Instance fields ////////////////////////////////////////////////////////

    private ServletMultipartRequest multi;

    //~ Constructors ///////////////////////////////////////////////////////////

    /**
     * Creates a new request wrapper to handle multi-part data using methods adapted from Jason Pell's
     * multipart classes (see class description).
     *
     * @param maxSize        maximum size post allowed
     * @param saveDir        the directory to save off the file
     * @param servletRequest the request containing the multipart
     */
    public PellMultiPartRequest(HttpServletRequest servletRequest, String saveDir, int maxSize) throws IOException {
        //this needs to be synchronised, as we should not change the encoding at the same time as
        //calling the constructor.  See javadoc for MultipartRequest.setEncoding().
        synchronized (this) {
            setEncoding();
            multi = new ServletMultipartRequest(servletRequest, saveDir, maxSize);
        }
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    public String getContentType(String name) {
        return multi.getContentType(name);
    }

    public File getFile(String name) {
        return multi.getFile(name);
    }

    public Enumeration getFileNames() {
        return multi.getFileParameterNames();
    }

    public String getFilesystemName(String name) {
        return multi.getFileSystemName(name);
    }

    public String getParameter(String name) {
        return multi.getURLParameter(name);
    }

    public Enumeration getParameterNames() {
        return multi.getParameterNames();
    }

    public String[] getParameterValues(String name) {
        Enumeration enum = multi.getURLParameters(name);

        if (!enum.hasMoreElements()) {
            return null;
        }

        List values = new ArrayList();

        while (enum.hasMoreElements()) {
            values.add(enum.nextElement());
        }

        return (String[]) values.toArray(new String[values.size()]);
    }

    /**
     * Sets the encoding for the uploaded params.  This needs to be set if you are using character sets other than
     * ASCII.
     * <p/>
     * The encoding is looked up from the configuration setting 'webwork.i18n.encoding'.  This is usually set in
     * default.properties & webwork.properties.
     */
    private static void setEncoding() {
        String encoding = null;

        try {
            encoding = Configuration.getString("webwork.i18n.encoding");

            if (encoding != null) {
                //NB: This should never be called at the same time as the constructor for
                //ServletMultiPartRequest, as it can cause problems.
                //See javadoc for MultipartRequest.setEncoding()
                http.utils.multipartrequest.MultipartRequest.setEncoding(encoding);
            } else {
                http.utils.multipartrequest.MultipartRequest.setEncoding("UTF-8");
            }
        } catch (IllegalArgumentException e) {
            log.info("Could not get encoding property 'webwork.i18n.encoding' for file upload.  Using system default");
        } catch (UnsupportedEncodingException e) {
            log.error("Encoding " + encoding + " is not a valid encoding.  Please check your webwork.properties file.");
        }
    }
}
