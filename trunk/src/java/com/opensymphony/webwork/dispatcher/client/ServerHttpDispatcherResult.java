/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.dispatcher.client;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.WebWorkStatics;

import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.Result;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Server side transmitter of {@link RemoteResult RemoteResult}s to the
 * {@link TransportHttp TransportHttp}. Pulls the
 * <code>HttpServletResponse</code> object from the action context, and
 * sends back a serialised {@link RemoteResult RemoteResult} to the
 * remote {@link TransportHttp TransportHttp}. No params are required.<BR><BR>
 *
 * You must configure this as a result in your <code>xwork.xml</code>.
 * For example:<BR><BR>
 *
 * <code>
 * &lt;result-types&gt;<BR>
 *   &lt;result-type name="client" class="com.opensymphony.webwork.dispatcher.client.ServerHttpDispatcherResult"/&gt;<BR>
 * &lt;/result-types&gt;<BR><BR>
 * </code>
 *
 * You must also ensure your <code>xwork.xml</code> is correctly setup to
 * reference the new <code>result-type</code>. An example mapping might be:<BR><BR>
 *
 * <code>&lt;action name="remoteActionName"
 * class="com.some.company.class"&gt;<BR>
 * &lt;result name="success" type="client"/&gt;<BR>
 * .....
 * </code>
 * <BR><BR>
 *
 * @version $Id$
 * @author Ben Alex (<a href="mailto:ben.alex@acegi.com.au">ben.alex@acegi.com.au</a>)
 * @author Matt Ho <a href="mailto:matt@indigoegg.com">&lt;matt@indigoegg.com&gt;</a>
 */
public class ServerHttpDispatcherResult implements Result, WebWorkStatics {
    //~ Static fields/initializers /////////////////////////////////////////////

    private static final Log log = LogFactory.getLog(ServerHttpDispatcherResult.class);

    //~ Methods ////////////////////////////////////////////////////////////////

    public void execute(ActionInvocation invocation) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("Trying to get response");
        }

        HttpServletResponse response = ServletActionContext.getResponse();
        HttpServletRequest request = ServletActionContext.getRequest();
        response.setContentType("application/octet-stream");

        String resultCode = invocation.getResultCode();
        Action action = invocation.getAction();
        RemoteResult remoteResult = new RemoteResult(action, resultCode, request.getSession().getId());

        ObjectOutputStream oos;

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        oos = new ObjectOutputStream(baos);
        oos.writeObject(remoteResult);
        oos.flush();
        oos.close();
        response.setIntHeader(TransportHttp.REPLY_CONTENT_SIZE, baos.size());

        try {
            // try removing the buffered layer
            oos = new ObjectOutputStream(new BufferedOutputStream(response.getOutputStream()));

            if (log.isDebugEnabled()) {
                log.debug("Sending result object");
            }

            oos.writeObject(remoteResult);
            oos.flush();
            oos.close();
        } catch (IOException ioe) {
            String message = "Could not send RemoteResult!";
            log.error(message, ioe);
            throw new Exception(message, ioe);
        }

        log.info("Finished");
    }
}
