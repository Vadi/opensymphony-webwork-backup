/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.velocity;

import org.apache.velocity.context.Context;

import java.io.Writer;


/**
 * Created by IntelliJ IDEA.
 * User: matt
 * Date: May 30, 2003
 * Time: 12:35:53 PM
 * To change this template use Options | File Templates.
 */
public interface Renderer {
    //~ Methods ////////////////////////////////////////////////////////////////

    public void render(Context context, Writer writer) throws Exception;
}
