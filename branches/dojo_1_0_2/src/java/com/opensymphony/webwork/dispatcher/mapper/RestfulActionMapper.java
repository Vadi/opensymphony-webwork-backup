/*
 * Copyright (c) 2002-2007 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.dispatcher.mapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.webwork.RequestUtils;
import com.opensymphony.webwork.WebWorkConstants;
import com.opensymphony.webwork.config.Configuration;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * A custom action mapper using the following format:
 * <p/>
 * <p/>
 * <ul><tt>http://HOST/ACTION_NAME/PARAM_NAME1/PARAM_VALUE1/PARAM_NAME2/PARAM_VALUE2</tt></ul>
 * <p/>
 * You can have as many parameters you'd like to use. Alternatively the URL can be shortened to the following:
 * <p/>
 * <ul><tt>http://HOST/ACTION_NAME/PARAM_VALUE1/PARAM_NAME2/PARAM_VALUE2</tt></ul>
 * <p/>
 * This is the same as:
 * <p/>
 * <ul><tt>http://HOST/ACTION_NAME/ACTION_NAME + "Id"/PARAM_VALUE1/PARAM_NAME2/PARAM_VALUE2</tt></ul>
 * <p/>
 * Suppose for example we would like to display some articles by id at using the following URL sheme:
 * <p/>
 * <ul><tt>http://HOST/article/Id</tt></ul>
 * <p/>
 * <p/>
 * Your action just needs a setArticleId() method, and requests such as /article/1, /article/2, etc will all map
 * to that URL pattern.
 * <p/>
 * To invoke an action without any parameters, use:
 * <ul>
 * <tt>http://HOST/article/
 * </ul>
 * with a slash at the back, assuming 'article' is the action name.
 * <p/>
 * <b>Note: The RestfulActionMapper is not supported if you use the (deprecated) ServletDispatcher!</b>
 * <b>Note: The RestfulActionMapper doesn't takes into account the concept of namespace, hence actions
 *          defined for RestfulActionMapper will have to resides in WebWork 'default' namespace.
 *
 * @author <a href="mailto:cameron@datacodex.net">Cameron Braid</a>
 * @author <a href="mailto:jerome.bernard@xtremejava.com">Jerome Bernard</a>
 * @author Patrick Lightbody
 * @author tmjee
 *
 * @version $Date$ $Id$
 */
public class RestfulActionMapper implements ActionMapper {
    protected static final Log LOG = LogFactory.getLog(RestfulActionMapper.class);

    /**
     * Attempt to return an {@link ActionMapping} for the supplied {@link javax.servlet.http.HttpServletRequest}.
     * <p/>
     * Since {@link com.opensymphony.webwork.dispatcher.mapper.RestfulActionMapper} doesn't take into
     * account namespace, this method will not try to recognized the namespace from {@link javax.servlet.http.HttpServletRequest}
     * but only the action name itself and its parameters as described in the javadoc of this {@link RestfulActionMapper}.
     * <p/>
     * This method will return null, if the {@link javax.servlet.http.HttpServletRequest} contains an extension eg.
     * http://localhost:8080/context/someAction.action etc, because {@link com.opensymphony.webwork.dispatcher.mapper.RestfulActionMapper}'s
     * url should not contains extension.
     * <p/>
     * These are such that {@link com.opensymphony.webwork.dispatcher.mapper.RestfulActionMapper} could fallback to other
     * {@link com.opensymphony.webwork.dispatcher.mapper.ActionMapper} when used in conjuection with say a
     * {@link com.opensymphony.webwork.dispatcher.mapper.CompositeActionMapper}.
     *
     * @param request
     * @return ActionMapping
     * @See {@link ActionMapper#getMapping(javax.servlet.http.HttpServletRequest)} 
     */
    public ActionMapping getMapping(HttpServletRequest request) {

        String uri = RequestUtils.getServletPath(request);
        if (uri.indexOf(".") != -1) {
            // there's an extension eg. http://localhost:8080/context/someAction.action
            // RestfulActionMapper doesn't deal with such situation, we'll return null and let the
            // next ActionMapper eg. if a CompositeActionMapper is used, to kick in
            return null;
        }

        if (LOG.isDebugEnabled())
            LOG.debug("request (servlet path)="+uri);

        int nextSlash = uri.indexOf('/', 1);
        if (nextSlash == -1) {
            return null;
        }

        String actionName = uri.substring(1, nextSlash);
        HashMap parameters = new HashMap();
        try {
            StringTokenizer st = new StringTokenizer(uri.substring(nextSlash), "/");
            boolean isNameTok = true;
            String paramName = null;
            String paramValue;

            // check if we have the first parameter name
            if ((st.countTokens() % 2) != 0) {
                isNameTok = false;
                paramName = actionName + "Id";
            }

            while (st.hasMoreTokens()) {
                if (isNameTok) {
                    paramName = URLDecoder.decode(st.nextToken(), "UTF-8");
                    isNameTok = false;
                } else {
                    paramValue = URLDecoder.decode(st.nextToken(), "UTF-8");

                    if ((paramName != null) && (paramName.length() > 0)) {
                        parameters.put(paramName, paramValue);
                    }

                    isNameTok = true;
                }
            }
        } catch (Exception e) {
            LOG.warn(e);
        }

        LOG.debug("actionName="+actionName);
        LOG.debug("params="+parameters.size());

        return new ActionMapping(actionName, "", "", parameters);
    }

    /**
     * Attempt to return a url, for the given {@link com.opensymphony.webwork.dispatcher.mapper.ActionMapping} passed in
     * as its argument.
     * <p/>
     * This method wll return null if {@link ActionMapping} contains a non-null or non-empty namespace as
     * {@link com.opensymphony.webwork.dispatcher.mapper.RestfulActionMapper} doesn't deal with namespace.
     * <p/>
     * These are such that {@link com.opensymphony.webwork.dispatcher.mapper.RestfulActionMapper} could fallback to other
     * {@link com.opensymphony.webwork.dispatcher.mapper.ActionMapper} when used in conjuection with say a
     * {@link com.opensymphony.webwork.dispatcher.mapper.CompositeActionMapper}.
     *
     * @param mapping
     * @return String
     */
    public String getUriFromActionMapping(ActionMapping mapping) {
        // RestfulActionMapper doesn't deals with mapping with namespace, if there's one, we'll
        // return null, and let the next ActionMapper kicks in if say if a CompositeActionMapper
        // is being used.
        if ((mapping.getNamespace() == null) || (mapping.getNamespace().trim().length() <= 0)) {
            String base = "/"+mapping.getName();

            // let's see if we have the <actionName>Id first, if so this should go first,
            // cause in {#link #getMapping(HttpServletRequest) the <actionName>Id is expected to be
            // first if the / separated element is odd
            Map parameters = mapping.getParams();
            if (parameters.containsKey(mapping.getName()+"Id")) {
                base = base + "/" + parameters.get(mapping.getName()+"Id");
            }

            for (Iterator iterator = parameters.entrySet().iterator(); iterator.hasNext();) {
                Map.Entry entry = (Map.Entry) iterator.next();
                String name = (String) entry.getKey();
                if (! name.equals(mapping.getName() + "Id")) {
                    base = base + "/" + entry.getKey() + "/" + entry.getValue();
                }
            }
            return base+"/";
        }
        return null;
    }
}
