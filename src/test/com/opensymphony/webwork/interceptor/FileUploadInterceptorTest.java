/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.interceptor;

import java.io.File;
import java.util.Locale;

import com.opensymphony.xwork.ValidationAwareSupport;

import junit.framework.TestCase;

/**
 * @author tmjee
 */
public class FileUploadInterceptorTest extends TestCase {

    public void testAcceptFileWithEmptyAllowedTypes() throws Exception {
        // when allowed type is empty
        FileUploadInterceptor interceptor = new FileUploadInterceptor();
        ValidationAwareSupport validation = new ValidationAwareSupport();
        boolean ok = interceptor.acceptFile(new File(""), "text/plain", "inputName", validation, Locale.getDefault());
        
        assertTrue(ok);
        assertTrue(validation.getFieldErrors().isEmpty());
        assertFalse(validation.hasErrors());
    }
    
    public void testAcceptFileWithoutEmptyTypes() throws Exception {
        FileUploadInterceptor interceptor = new FileUploadInterceptor();
        interceptor.setAllowedTypes("text/plain");
        
        // when file is of allowed types
        ValidationAwareSupport validation = new ValidationAwareSupport();
        boolean ok = interceptor.acceptFile(new File(""), "text/plain", "inputName", validation, Locale.getDefault());

        assertTrue(ok);
        assertTrue(validation.getFieldErrors().isEmpty());
        assertFalse(validation.hasErrors());

        // when file is not of allowed types
        validation = new ValidationAwareSupport();
        boolean notOk = interceptor.acceptFile(new File(""), "text/html", "inputName", validation, Locale.getDefault());

        assertFalse(notOk);
        assertFalse(validation.getFieldErrors().isEmpty());
        assertTrue(validation.hasErrors());
    }
}
