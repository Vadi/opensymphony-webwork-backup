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
 * Date: May 31, 2003
 * Time: 10:07:58 AM
 * To change this template use Options | File Templates.
 */
public interface IterationRenderer extends Renderer {
    //~ Instance fields ////////////////////////////////////////////////////////

    /**
     * value to be returned by doAfterRender if should perform another iteration
     */
    int RENDER_AGAIN = 2;

    /**
     * value to be returned by doAfterRender if we're done rendering
     */
    int RENDER_DONE = 0;

    //~ Methods ////////////////////////////////////////////////////////////////

    /**
     * allows the IterationRendererer to perform cleanup once the body content has been rendered.
     * @param context the current Velocity Context
     * @return
     */
    public int doAfterRender(Context context, Writer writer);

    /**
     * allows the IterationRenderer to modify Velocity's context prior to the contents being rendered
     * @param context the current Velocity Context
     */
    public void doBeforeRender(Context context, Writer writer);
}
