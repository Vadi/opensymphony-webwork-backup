/*
 * Copyright (c) 2006, Your Corporation. All Rights Reserved.
 */

package com.opensymphony.webwork.showcase.fileupload;

import java.io.File;

/**
 * <code>FileUploadAction</code>
 *
 * @author <a href="mailto:hermanns@aixcept.de">Rainer Hermanns</a>
 * @version $Id$
 */
public class FileUploadAction {


    private String contentType;
    private File upload;
    private String fileName;
    private String caption;
    
    
    public String getUploadFileName() {
    	return fileName;
    }
    public void setUploadFileName(String fileName) {
    	this.fileName = fileName;
    }

    public String getUploadContentType() {
        return contentType;
    }

    public void setUploadContentType(String contentType) {
        this.contentType = contentType;
    }

    public File getUpload() {
        return upload;
    }

    public void setUpload(File upload) {
        this.upload = upload;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String input() {
        return "success";
    }

    public String upload() {
        return "success";
    }

}
