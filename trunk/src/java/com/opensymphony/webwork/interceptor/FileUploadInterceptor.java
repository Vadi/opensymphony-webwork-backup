/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.interceptor;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.dispatcher.multipart.MultiPartRequestWrapper;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.ValidationAware;
import com.opensymphony.xwork.interceptor.Interceptor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;


/**
 * Interceptor that is based off of {@link MultiPartRequestWrapper}. It adds the following
 * parameters, where [File Name] is the name given to the file uploaded by the HTML form:
 * <ul>
 * <li>[File Name] : File - the actual File</li>
 * <li>[File Name]ContentType : String - the content type of the file</li>
 * <li>[File Name]FileName : String - the actual name of the file uploaded (not the HTML name)</li>
 * </ul>
 * <p/>
 * You can get access to these files by merely providing setters in your action that correspond to any
 * of the three patterns above, such as setDocument(File document), setDocumentContentType(String contentType), etc.
 */
public class FileUploadInterceptor implements Interceptor {
    //~ Static fields/initializers /////////////////////////////////////////////

    protected static final Log log = LogFactory.getLog(FileUploadInterceptor.class);

    //~ Instance fields ////////////////////////////////////////////////////////

    protected Long maximumSize;
    protected String allowedTypes;
    protected String disallowedTypes;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setAllowedTypes(String allowedTypes) {
        this.allowedTypes = allowedTypes;
    }

    public void setDisallowedTypes(String disallowedTypes) {
        this.disallowedTypes = disallowedTypes;
    }

    public void setMaximumSize(Long maximumSize) {
        this.maximumSize = maximumSize;
    }

    public void destroy() {
    }

    public void init() {
    }

    public String intercept(ActionInvocation invocation) throws Exception {
        if (!(ServletActionContext.getRequest() instanceof MultiPartRequestWrapper)) {
            if (log.isDebugEnabled()) {
                log.debug("bypass " + invocation.getProxy().getNamespace() + "/" + invocation.getProxy().getActionName());
            }

            return invocation.invoke();
        }

        final Object action = invocation.getAction();
        ValidationAware validation = null;

        if (action instanceof ValidationAware) {
            validation = (ValidationAware) action;
        }

        MultiPartRequestWrapper multiWrapper = (MultiPartRequestWrapper) ServletActionContext.getRequest();

        if (multiWrapper.hasErrors()) {
            Collection errors = multiWrapper.getErrors();
            Iterator i = errors.iterator();

            while (i.hasNext()) {
                String error = (String) i.next();

                if (validation != null) {
                    validation.addActionError(error);
                }

                log.error(error);
            }
        }

        Enumeration e = multiWrapper.getFileParameterNames();

        // Bind allowed Files
        while (e != null && e.hasMoreElements()) {
            // get the value of this input tag
            String inputName = (String) e.nextElement();

            // get the content type
            String[] contentType = multiWrapper.getContentTypes(inputName);

            // get the name of the file from the input tag
            String[] fileName = multiWrapper.getFileNames(inputName);

            // Get a File object for the uploaded File
            File[] file = multiWrapper.getFiles(inputName);

            if (file != null) {
                for (int i = 0; i < file.length; i++) {
                    log.info("file " + inputName + " " + contentType[i] + " " + fileName[i] + " " + file[i]);
                }
            }

            // If it's null the upload failed
            if (file == null) {
                if (validation != null) {
                    validation.addFieldError(inputName, "Could not upload file(s). Perhaps it is too large?");
                }

                log.error("Error uploading: " + fileName);
            } else {
                invocation.getInvocationContext().getParameters().put(inputName, file);
                invocation.getInvocationContext().getParameters().put(inputName + "ContentType", contentType);
                invocation.getInvocationContext().getParameters().put(inputName + "FileName", fileName);
            }
        }

        // invoke action
        String result = invocation.invoke();

        // cleanup
        e = multiWrapper.getFileParameterNames();

        while (e != null && e.hasMoreElements()) {
            String inputValue = (String) e.nextElement();
            File[] file = multiWrapper.getFiles(inputValue);
            for (int i = 0; i < file.length; i++) {
                File f = file[i];
                log.info("removing file " + inputValue + " " + f);

                if ((f != null) && f.isFile()) {
                    f.delete();
                }
            }
        }

        return result;
    }
}
