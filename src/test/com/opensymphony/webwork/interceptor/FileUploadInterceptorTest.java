/*
 * Copyright (c) 2002-2006 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.interceptor;

import com.opensymphony.webwork.WebWorkTestCase;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ValidationAwareSupport;
import com.opensymphony.xwork.mock.MockActionInvocation;

import java.io.File;
import java.util.List;
import java.util.Locale;

/**
 * Test case for FileUploadInterceptor.
 *
 * @author tmjee
 * @version $Date$ $Id$
 */
public class FileUploadInterceptorTest extends WebWorkTestCase {

    private FileUploadInterceptor interceptor;

    public void testAcceptFileWithEmptyAllowedTypes() throws Exception {
        // when allowed type is empty
        ValidationAwareSupport validation = new ValidationAwareSupport();
        boolean ok = interceptor.acceptFile(new File(""), "text/plain", "inputName", validation, Locale.getDefault());

        assertTrue(ok);
        assertTrue(validation.getFieldErrors().isEmpty());
        assertFalse(validation.hasErrors());
    }

    public void testAcceptFileWithoutEmptyTypes() throws Exception {
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

    public void testAcceptFileWithNoFile() throws Exception {
        FileUploadInterceptor interceptor = new FileUploadInterceptor();
        interceptor.setAllowedTypes("text/plain");

        // when file is not of allowed types
        ValidationAwareSupport validation = new ValidationAwareSupport();
        boolean notOk = interceptor.acceptFile(null, "text/html", "inputName", validation, Locale.getDefault());

        assertFalse(notOk);
        assertFalse(validation.getFieldErrors().isEmpty());
        assertTrue(validation.hasErrors());
        List errors = (List) validation.getFieldErrors().get("inputName");
        assertEquals(1, errors.size());
        assertEquals("Could not upload file.", errors.get(0));
    }

    public void testAcceptFileWithMaxSize() throws Exception {
        interceptor.setAllowedTypes("text/plain");
        interceptor.setMaximumSize(new Long(10));

        // when file is not of allowed types
        ValidationAwareSupport validation = new ValidationAwareSupport();
        File file = new File("build\\\\test\\\\log4j.properties"); // use log4j.properties in build/test folder
        assertTrue("log4j.properties should be in src/test folder", file.exists());
        boolean notOk = interceptor.acceptFile(file, "text/html", "inputName", validation, Locale.getDefault());

        assertFalse(notOk);
        assertFalse(validation.getFieldErrors().isEmpty());
        assertTrue(validation.hasErrors());
        List errors = (List) validation.getFieldErrors().get("inputName");
        assertEquals(1, errors.size());
        String msg = (String) errors.get(0);
        // the error message shoul contain at least this test
        assertTrue(msg.startsWith("The file is to large to be uploaded"));
        assertTrue(msg.indexOf("inputName") > 0);
        assertTrue(msg.indexOf("log4j.properties") > 0);
    }

    public void testNoMultipartRequest() throws Exception {
        MockActionInvocation mai = new MockActionInvocation();
        mai.setResultCode("NoMultipart");
        mai.setInvocationContext(ActionContext.getContext());

        // if no multipart request it will bypass and execute it
        assertEquals("NoMultipart", interceptor.intercept(mai));
    }

    protected void setUp() throws Exception {
        super.setUp();
        interceptor = new FileUploadInterceptor();
    }

    protected void tearDown() throws Exception {
        interceptor.destroy();
        super.tearDown();
    }

}
