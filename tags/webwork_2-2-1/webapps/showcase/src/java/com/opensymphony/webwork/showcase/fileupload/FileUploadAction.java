/*
 * Copyright (c) 2006, Your Corporation. All Rights Reserved.
 */

package com.opensymphony.webwork.showcase.fileupload;

/**
 * <code>FileUploadAction</code>
 *
 * @author <a href="mailto:hermanns@aixcept.de">Rainer Hermanns</a>
 * @version $Id$
 */
public class FileUploadAction {


    private String contentType;
    private String fileName;
    private String caption;

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
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
