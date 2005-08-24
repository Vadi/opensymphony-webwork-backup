/*
 * Copyright (c) 2002-2005 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.sandbox;

import com.opensymphony.xwork.ActionSupport;

import java.io.File;

/**
 * TestAction for the FileUploadInterceptor.
 */
public class FileUploadTestAction extends ActionSupport {

    private File uploadedItem;
    private String uploadedItemContentType;
    private String uploadedItemFileName;

    // Properties -------------------------------------------------

    public void setUploadedItem(File uploadedItem) {
        this.uploadedItem = uploadedItem;
    }

    public void setUploadedItemContentType(String uploadedItemContentType) {
        this.uploadedItemContentType = uploadedItemContentType;
    }

    public void setUploadedItemFileName(String uploadedItemFileName) {
        this.uploadedItemFileName = uploadedItemFileName;
    }

    // Methods -------------------------------------------------

    public String execute() throws Exception {
        return SUCCESS;
    }

    public String attemptUpload() throws Exception {
        if (uploadedItem != null) {
            addActionMessage("Uploaded File: " + uploadedItemFileName +
                    ", with a contentType of " + uploadedItemContentType);
        } else {
            addActionError("File upload unsuccessful. Please verify that the file is allowed and within the size limits.");
        }

        return SUCCESS;
    }
}
