/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.velocity;

import com.opensymphony.webwork.views.velocity.ui.JSPTagAdapter;

import org.apache.velocity.Template;
import org.apache.velocity.context.Context;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.io.VelocityWriter;
import org.apache.velocity.runtime.RuntimeSingleton;
import org.apache.velocity.servlet.VelocityServlet;
import org.apache.velocity.util.SimplePool;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *
 *
 * @author $Author$
 * @version $Revision$
 */
public class WebWorkVelocityServlet extends VelocityServlet {
    //~ Static fields/initializers /////////////////////////////////////////////

    /**
    * The encoding to use when generating outputing.
    */
    private static String encoding = null;

    /**
    * Very simple object pool used by the VelocityServlet.  As it's marked as private, we need to create our own
    */
    private static SimplePool writerPool = new SimplePool(40);

    //~ Methods ////////////////////////////////////////////////////////////////

    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);

        // @todo look into converting this to using XWork/WebWork2 encoding rules
        encoding = RuntimeSingleton.getString(RuntimeSingleton.OUTPUT_ENCODING, DEFAULT_OUTPUT_ENCODING);

        // initialize our VelocityManager
        VelocityManager.init(servletConfig.getServletContext());
    }

    protected Template handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Context context) throws Exception {
        String servletPath = (String) httpServletRequest.getAttribute("javax.servlet.include.servlet_path");

        if (servletPath == null) {
            servletPath = httpServletRequest.getServletPath();
        }

        return getTemplate(servletPath);
    }

    /**
    * This method extends the VelocityServlet's loadConfiguration method by performing the following actions:
    * <ul>
    *  <li>invokes VelocityServlet.loadConfiguration to create a properties object</li>
    *  <li>alters the RESOURCE_LOADER to include a class loader</li>
    *  <li>configures the class loader using the WebWorkResourceLoader</li>
    * </ul>
    *
    * @see org.apache.velocity.servlet.VelocityServlet#loadConfiguration
    * @param servletConfig
    * @return
    * @throws IOException
    * @throws FileNotFoundException
    */
    protected Properties loadConfiguration(ServletConfig servletConfig) throws IOException, FileNotFoundException {
        return VelocityManager.loadConfiguration(servletConfig.getServletContext());
    }

    /**
    * <p>
    *  This method is intended to be <b>exactly</b> like the method in the VelocityServlet with one addition, after an
    * instance to a VelocityWriter is obtained, the this method pulls the current JSPTagAdapter out of the
    * VelocityContext and sets its writer to be the VelocityWriter.  In this way, we only have to deal with writing
    * to one writer.
    * </p>
    * <p>
    * Without this, an issue arises that the JSPTagAdapter is writing directly to the ServletResponse whereas the
    * WebWorkVelocityServiet is writing to its internal buffer, the WebWorkVelocityWriter
    * </p>
    *
    * @param template the current template
    * @param context the current VelocityContext (should be an instance of WebWorkVelocityContext)
    * @param response the current HttpServletResponse
    * @throws ResourceNotFoundException if the template was not found
    */
    protected void mergeTemplate(Template template, Context context, HttpServletResponse response) throws ResourceNotFoundException, ParseErrorException, MethodInvocationException, IOException, UnsupportedEncodingException, Exception {
        ServletOutputStream output = response.getOutputStream();
        VelocityWriter vw = null;

        try {
            vw = (VelocityWriter) writerPool.get();

            if (vw == null) {
                vw = new VelocityWriter(new OutputStreamWriter(output, encoding), 4 * 1024, true);
            } else {
                vw.recycle(new OutputStreamWriter(output, encoding));
            }

            /**
            * Grab the JSPTagAdapter out of the context and set its writer.  This writer will be written to directly
            * by all the tags that are instantiated by the JSPTagAdapter
            */
            JSPTagAdapter adapter = (JSPTagAdapter) context.get(VelocityManager.UI);

            if (adapter != null) {
                adapter.setWriter(vw);
            }

            template.merge(context, vw);
        } finally {
            try {
                if (vw != null) {
                    /*
                    *  flush and put back into the pool
                    *  don't close to allow us to play
                    *  nicely with others.
                    */
                    vw.flush();
                    writerPool.put(vw);
                }
            } catch (Exception e) {
                // do nothing
            }
        }
    }
}
