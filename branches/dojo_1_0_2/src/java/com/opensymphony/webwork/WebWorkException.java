/*
 * Copyright (c) 2002-2006 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork;

import com.opensymphony.xwork.XworkException;

/**
 * <code>WebWorkException</code>
 * 
 * A generic runtime exception that optionally contains Location information
 *
 * @author <a href="mailto:hermanns@aixcept.de">Rainer Hermanns</a>
 * @version $Id$
 */
public class WebWorkException extends XworkException {

    /**
      * Constructs a <code>WebWorkException</code> with no detail message.
      */
     public WebWorkException() {
     }

     /**
      * Constructs a <code>WebWorkException</code> with the specified
      * detail message.
      *
      * @param s the detail message.
      */
     public WebWorkException(String s) {
         this(s, null, null);
     }

     /**
      * Constructs a <code>WebWorkException</code> with the specified
      * detail message and target.
      *
      * @param s the detail message.
      * @param target the target of the exception.
      */
     public WebWorkException(String s, Object target) {
         this(s, (Throwable) null, target);
     }

     /**
      * Constructs a <code>WebWorkException</code> with the root cause
      *
      * @param cause The wrapped exception
      */
     public WebWorkException(Throwable cause) {
         this(null, cause, null);
     }

     /**
      * Constructs a <code>WebWorkException</code> with the root cause and target
      *
      * @param cause The wrapped exception
      * @param target The target of the exception
      */
     public WebWorkException(Throwable cause, Object target) {
         this(null, cause, target);
     }

     /**
      * Constructs a <code>WebWorkException</code> with the specified
      * detail message and exception cause.
      *
      * @param s the detail message.
      * @param cause the wrapped exception
      */
     public WebWorkException(String s, Throwable cause) {
         this(s, cause, null);
     }


      /**
      * Constructs a <code>WebWorkException</code> with the specified
      * detail message, cause, and target
      *
      * @param s the detail message.
      * @param cause The wrapped exception
      * @param target The target of the exception
      */
     public WebWorkException(String s, Throwable cause, Object target) {
         super(s, cause, target);
     }

}
