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
 * Multipart Form Data request.
 * <p>
 * This class decodes the multipart/form-data stream sent by
 * a HTML form that uses a file input item.
 *
 * @version $Id$
 * @author  Matt Baldree (matt@smallleap.com) modified for WW's use
 */
public abstract class MultiPartRequest {
    //~ Static fields/initializers /////////////////////////////////////////////

    protected static Log log = LogFactory.getLog(MultiPartRequest.class);

    //~ Methods ////////////////////////////////////////////////////////////////

    /**
     * Is request a multipart request
     */
    public static boolean isMultiPart(HttpServletRequest request) {
        String content_type = request.getHeader("Content-Type");

        return ((content_type == null) || !content_type.startsWith("multipart/form-data")) ? false : true;
    }

    public abstract String getContentType(String name);

    public abstract File getFile(String name);

    // Methods only in MultipartRequest
    public abstract Enumeration getFileNames();

    public abstract String getFilesystemName(String name);

    public abstract String getParameter(String name);

    public abstract Enumeration getParameterNames();

    public abstract String[] getParameterValues(String name);
}
