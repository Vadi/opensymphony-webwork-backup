/*
 * Copyright (c) 2002-2004 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.dispatcher.multipart;

import org.apache.commons.fileupload.DefaultFileItem;
import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Multipart form data request adapter for Jakarta's file upload package.
 *
 * @author Bruce Ritchie
 */
public class JakartaMultiPartRequest extends MultiPartRequest {
    //~ Instance fields ////////////////////////////////////////////////////////

    // maps parameter name -> List of FileItem objects
    private Map files = new HashMap();
    // maps parameter name -> List of param values
    private Map params = new HashMap();

    //~ Constructors ///////////////////////////////////////////////////////////

    /**
     * Creates a new request wrapper to handle multi-part data using methods adapted from Jason Pell's
     * multipart classes (see class description).
     *
     * @param maxSize        maximum size post allowed
     * @param saveDir        the directory to save off the file
     * @param servletRequest the request containing the multipart
     */
    public JakartaMultiPartRequest(HttpServletRequest servletRequest, String saveDir, int maxSize)
            throws IOException {
        DiskFileUpload upload = new DiskFileUpload();
        // we must store all uploads on disk because the ww multipart API is missing streaming
        // capabilities
        upload.setSizeThreshold(0);
        upload.setSizeMax(maxSize);
        if (saveDir != null) {
            upload.setRepositoryPath(saveDir);
        }

        // Parse the request
        try {
            List items = upload.parseRequest(servletRequest);

            for (int i = 0; i < items.size(); i++) {
                FileItem item = (FileItem) items.get(i);
                log.debug("Found item " + item.getFieldName());
                if (item.isFormField()) {
                    log.debug("Item is a normal form field");
                    List values = null;
                    if (params.get(item.getFieldName()) != null) {
                        values = (List) params.get(item.getFieldName());
                    } else {
                        values = new ArrayList();
                    }

                    // note: see http://jira.opensymphony.com/browse/WW-633
                    // basically, in some cases the charset may be null, so
                    // we're just going to try to "other" method (no idea if this
                    // will work)
                    String charset = servletRequest.getCharacterEncoding();
                    if (charset != null) {
                        values.add(item.getString(charset));
                    } else {
                        values.add(item.getString());
                    }
                    params.put(item.getFieldName(), values);
                } else if (item.getSize() == 0) {
                    log.debug("Item is a file upload of 0 size, ignoring");
                } else {
                    log.debug("Item is a file upload");

                    List values = null;
                    if (files.get(item.getFieldName()) != null) {
                        values = (List) files.get(item.getFieldName());
                    } else {
                        values = new ArrayList();
                    }

                    values.add(item);
                    files.put(item.getFieldName(), values);
                }
            }
        } catch (FileUploadException e) {
            log.error(e);
        }
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    public Enumeration getFileParameterNames() {
        return Collections.enumeration(files.keySet());
    }

    public String[] getContentType(String fieldName) {
        List items = (List) files.get(fieldName);

        if (items == null) {
            return null;
        }

        List contentTypes = new ArrayList(items.size());
        for (int i = 0; i < items.size(); i++) {
            FileItem fileItem = (FileItem) items.get(i);
            contentTypes.add(fileItem.getContentType());
        }

        return (String[]) contentTypes.toArray(new String[contentTypes.size()]);
    }

    public File[] getFile(String fieldName) {
        List items = (List) files.get(fieldName);

        if (items == null) {
            return null;
        }

        List fileList = new ArrayList(items.size());
        for (int i = 0; i < items.size(); i++) {
            DefaultFileItem fileItem = (DefaultFileItem) items.get(i);
            fileList.add(fileItem.getStoreLocation());
        }

        return (File[]) fileList.toArray(new File[fileList.size()]);
    }

    /**
     * Returns the canonical name of the given file
     */
    private String getCanonicalName(String filename) {
        int forwardSlash = filename.lastIndexOf("/");
        int backwardSlash = filename.lastIndexOf("\\");
        if (forwardSlash != -1 && forwardSlash > backwardSlash) {
            filename = filename.substring(forwardSlash + 1, filename.length());
        } else if (backwardSlash != -1 && backwardSlash >= forwardSlash) {
            filename = filename.substring(backwardSlash + 1, filename.length());
        }

        return filename;
    }

    public String[] getFileNames(String fieldName) {
        List items = (List) files.get(fieldName);

        if (items == null) {
            return null;
        }

        List fileNames = new ArrayList(items.size());
        for (int i = 0; i < items.size(); i++) {
            DefaultFileItem fileItem = (DefaultFileItem) items.get(i);
            fileNames.add(getCanonicalName(fileItem.getName()));
        }

        return (String[]) fileNames.toArray(new String[fileNames.size()]);
    }

    public String[] getFilesystemName(String fieldName) {
        List items = (List) files.get(fieldName);

        if (items == null) {
            return null;
        }

        List fileNames = new ArrayList(items.size());
        for (int i = 0; i < items.size(); i++) {
            DefaultFileItem fileItem = (DefaultFileItem) items.get(i);
            fileNames.add(fileItem.getStoreLocation().getName());
        }

        return (String[]) fileNames.toArray(new String[fileNames.size()]);
    }

    public String getParameter(String name) {
        List v = (List) params.get(name);
        if (v != null && v.size() > 0) {
            return (String) v.get(0);
        }

        return null;
    }

    public Enumeration getParameterNames() {
        return Collections.enumeration(params.keySet());
    }

    public String[] getParameterValues(String name) {
        List v = (List) params.get(name);
        if (v != null && v.size() > 0) {
            return (String[]) v.toArray(new String[v.size()]);
        }

        return null;
    }
}
