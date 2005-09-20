/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.interceptor;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.dispatcher.multipart.MultiPartRequestWrapper;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.ActionProxy;
import com.opensymphony.xwork.ValidationAware;
import com.opensymphony.xwork.interceptor.Interceptor;
import com.opensymphony.xwork.util.LocalizedTextUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.util.*;

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

    private static final String DEFAULT_DELIMITER = ",";

//~ Instance fields ////////////////////////////////////////////////////////

    protected Long maximumSize;
    protected String allowedTypes;
    protected String disallowedTypes;
    protected Set allowedTypesSet = Collections.EMPTY_SET;
    protected Set disallowedTypesSet = Collections.EMPTY_SET;
    static final String DEFAULT_MESSAGE = "no.message.found";

//~ Methods ////////////////////////////////////////////////////////////////

    public void setAllowedTypes(String allowedTypes) {
        this.allowedTypes = allowedTypes;

        // set the allowedTypes as a collection for easier access later
        allowedTypesSet = getDelimitedValues(allowedTypes);
    }

    public void setDisallowedTypes(String disallowedTypes) {
        this.disallowedTypes = disallowedTypes;

        // set the disallowedTypes as a collection for easier access later
        disallowedTypesSet = getDelimitedValues(disallowedTypes);
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
                ActionProxy proxy = invocation.getProxy();
                log.debug(getTextMessage("webwork.messages.bypass.request", new Object[]{proxy.getNamespace(), proxy.getActionName()}));
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
            for (Iterator errorIter = multiWrapper.getErrors().iterator(); errorIter.hasNext();) {
                String error = (String) errorIter.next();

                if (validation != null) {
                    validation.addActionError(error);
                }

                log.error(error);
            }
        }

        Map parameters = invocation.getInvocationContext().getParameters();

        // Bind allowed Files
        Enumeration fileParameterNames = multiWrapper.getFileParameterNames();
        while (fileParameterNames != null && fileParameterNames.hasMoreElements()) {
            // get the value of this input tag
            String inputName = (String) fileParameterNames.nextElement();

            // get the content type
            String[] contentType = multiWrapper.getContentTypes(inputName);

            if (isNonEmpty(contentType)) {
                // get the name of the file from the input tag
                String[] fileName = multiWrapper.getFileNames(inputName);

                if (isNonEmpty(fileName)) {
                    // Get a File object for the uploaded File
                    File[] files = multiWrapper.getFiles(inputName);
                    if (files != null) {
                        for (int index = 0; index < files.length; index++) {
                            getTextMessage("webwork.messages.current.file", new Object[]{inputName, contentType[index], fileName[index], files[index]});

                            if (acceptFile(files[0], contentType[0], inputName, validation)) {
                                parameters.put(inputName, files);
                                parameters.put(inputName + "ContentType", contentType);
                                parameters.put(inputName + "FileName", fileName);
                            }
                        }
                    }
                } else {
                    log.error(getTextMessage("webwork.messages.invalid.file", new Object[]{inputName}));
                }
            } else {
                log.error(getTextMessage("webwork.messages.invalid.content.type", new Object[]{inputName}));
            }
        }

        // invoke action
        String result = invocation.invoke();

        // cleanup
        fileParameterNames = multiWrapper.getFileParameterNames();
        while (fileParameterNames != null && fileParameterNames.hasMoreElements()) {
            String inputValue = (String) fileParameterNames.nextElement();
            File[] file = multiWrapper.getFiles(inputValue);
            for (int index = 0; index < file.length; index++) {
                File currentFile = file[index];
                log.info(getTextMessage("webwork.messages.removing.file", new Object[]{inputValue, currentFile}));

                if ((currentFile != null) && currentFile.isFile()) {
                    currentFile.delete();
                }
            }
        }

        return result;
    }

    /**
     * Override for added functionality. Checks if the proposed file is acceptable by contentType and size.
     *
     * @param file        - proposed upload file.
     * @param contentType - contentType of the file.
     * @param inputName   - inputName of the file.
     * @param validation  - Non-null ValidationAware if the action implements ValidationAware, allowing for better logging.
     * @return true if the proposed file is acceptable by contentType and size.
     */
    protected boolean acceptFile(File file, String contentType, String inputName, ValidationAware validation) {
        boolean fileIsAcceptable = false;

        // If it's null the upload failed
        if (file == null) {
            if (validation != null) {
                validation.addFieldError(inputName, "Could not upload file.");
            }

            log.error(getTextMessage("webwork.messages.error.uploading", new Object[]{inputName}));

        } else if (maximumSize != null && maximumSize.longValue() < file.length()) {
            String errMsg = getTextMessage("webwork.messages.error.file.too.large", new Object[]{inputName, file.getName(), "" + file.length()});
            if (validation != null) {
                validation.addFieldError(inputName, errMsg);
            }

            log.error(errMsg);

        } else if (containsItem(disallowedTypesSet, contentType)) {
            String errMsg = getTextMessage("webwork.messages.error.content.type.disallowed", new Object[]{inputName, file.getName(), contentType});
            if (validation != null) {
                validation.addFieldError(inputName, errMsg);
            }

            log.error(errMsg);

        } else if (!containsItem(allowedTypesSet, contentType)) {
            String errMsg = getTextMessage("webwork.messages.error.content.type.not.allowed", new Object[]{inputName, file.getName(), contentType});
            if (validation != null) {
                validation.addFieldError(inputName, errMsg);
            }

            log.error(errMsg);

        } else {
            fileIsAcceptable = true;
        }

        return fileIsAcceptable;
    }

    /**
     * @param itemCollection - Collection of string items (all lowercase).
     * @param key            - Key to search for.
     * @return true if itemCollection contains the key, false otherwise.
     */
    private static boolean containsItem(Collection itemCollection, String key) {
        return itemCollection.contains(key.toLowerCase());
    }

    private static Set getDelimitedValues(String delimitedString) {
        Set delimitedValues = new HashSet();
        if (delimitedString != null) {
            StringTokenizer stringTokenizer = new StringTokenizer(delimitedString, DEFAULT_DELIMITER);
            while (stringTokenizer.hasMoreTokens()) {
                String nextToken = stringTokenizer.nextToken().toLowerCase().trim();
                if (nextToken.length() > 0) {
                    delimitedValues.add(nextToken);
                }
            }
        }
        return delimitedValues;
    }

    private static boolean isNonEmpty(Object[] objArray) {
        boolean result = false;
        for (int index = 0; index < objArray.length && !result; index++) {
            if (objArray[index] != null) {
                result = true;
            }
        }
        return result;
    }

    private String getTextMessage(String messageKey, Object[] args) {
        if (args == null || args.length == 0) {
            return LocalizedTextUtil.findText(this.getClass(), messageKey, ActionContext.getContext().getLocale());
        } else {
            return LocalizedTextUtil.findText(this.getClass(), messageKey, ActionContext.getContext().getLocale(), DEFAULT_MESSAGE, args);
        }
    }
}
