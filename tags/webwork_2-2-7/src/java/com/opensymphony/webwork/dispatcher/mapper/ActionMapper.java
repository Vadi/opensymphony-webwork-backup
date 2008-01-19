/*
 * Copyright (c) 2002-2007 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.dispatcher.mapper;

import javax.servlet.http.HttpServletRequest;

/**
 * <!-- START SNIPPET: javadoc -->
 *
 * The ActionMapper is responsible for providing a mapping between HTTP requests and action invocation requests and
 * vice-versa. When given an HttpServletRequest, the ActionMapper may return null if no action invocation request maps,
 * or it may return an {@link ActionMapping} that describes an action invocation that WebWork should attempt to try. The
 * ActionMapper is not required to guarantee that the {@link ActionMapping} returned be a real action or otherwise
 * ensure a valid request. This means that most ActionMappers do not need to consult WebWork's configuration to
 * determine if a request should be mapped.
 *
 * <p/> Just as requests can be mapped from HTTP to an action invocation, the opposite is true as well. However, because
 * HTTP requests (when shown in HTTP responses) must be in String form, a String is returned rather than an actual
 * request object.
 *
 * <p/> ActionMapper should return null ({@link #getMapping(javax.servlet.http.HttpServletRequest)} and
 * {@link #getUriFromActionMapping(ActionMapping)} if it cannot handle the context.
 *
 * <!-- END SNIPPET: javadoc -->
 *
 * @author plightbo
 * @author tmjee
 *
 * @version $Date$ $Id$
 */
public interface ActionMapper {

    /**
     * Return the {@link ActionMapping} for the given {@link javax.servlet.http.HttpServletRequest}, the format of
     * {@link javax.servlet.http.HttpServletRequest} url depends on the implementation of {@link ActionMapper}, eg.
     * a {@link com.opensymphony.webwork.dispatcher.mapper.RestfulActionMapper} might handle it differently from
     * {@link com.opensymphony.webwork.dispatcher.mapper.DefaultActionMapper}.
     * <p/>
     * Implementation should return null if it cannot handle the format of request (eg. if it is bad etc.) such that
     * we could cascade {@link ActionMapping} together using
     * {@link com.opensymphony.webwork.dispatcher.mapper.CompositeActionMapper}
     *
     * @param request
     * @return ActionMapping
     */
    ActionMapping getMapping(HttpServletRequest request);

    /**
     * Return the uri of the {@link com.opensymphony.webwork.dispatcher.mapper.ActionMapping} passed in as the argument.
     * <p/>
     * Implementation should return null if it cannot handle the format of request (eg. if it is bad etc.) such that
     * we could cascade {@link ActionMapping} together using
     * {@link com.opensymphony.webwork.dispatcher.mapper.CompositeActionMapper}
     * <p/>
     * The parameter <code>mapping</code> is an instance of {@link ActionMappingEx}.
     *
     * @param mapping
     * @return String
     */
    String getUriFromActionMapping(ActionMapping mapping);
}
