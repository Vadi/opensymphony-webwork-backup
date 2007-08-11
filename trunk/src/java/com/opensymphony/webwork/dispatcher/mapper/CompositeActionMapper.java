/*
 * Copyright (c) 2002-2007 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.dispatcher.mapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collections;

import com.opensymphony.webwork.config.Configuration;
import com.opensymphony.webwork.WebWorkConstants;
import com.opensymphony.xwork.ObjectFactory;


/**
 * <!-- SNIPPET: javadoc -->
 * This is an {@link ActionMapper} that could take in {@link ActionMapper}s and consult each {@link ActionMapper}
 * in the order they are given where {@link ActionMapper} with higher order will take precedence.
 * <p/>
 * The pre-defined {@link ActionMapper}s could be configured in 2 ways :-
 *
 * <ul>
 *    <li>Through webwork.properties - {@link #CompositeActionMapper()} </li>
 *    <li>Through parameters passed in to the constructor of {@link CompositeActionMapper} -
 *        {@link #CompositeActionMapper(ActionMapper[])}</li>
 * </ul>
 * <p/>
 * <b>Through webwork.properties</b>
 * <p/>
 * {@link CompositeActionMapper} does this by reading in the pre-configured {@link ActionMapper}s
 * through webwork.properties file eg. with
 * <pre>
 *   webwork.mapper.class=com.opensymphony.webwork.dispatcher.mapper.CompositeActionMapper
 *   webwork.compositeActionMapper.1=com.opensymphony.webwork.dispatcher.mapper.RestfulActionMapper
 *   webwork.compositeActionMapper.2=com.opensymphony.webwork.dispatcher.mapper.DefaultActionMapper
 * </pre>
 * We would have configured a CompositeActionMapper, with 2 pre-configured {@link ActionMapper}s, namely
 * <ul>
 *      <li>com.opensymphony.webwork.dispatcher.mapper.RestfulActionMapper</li>
 *      <li>com.opensymphony.webwork.dispatcher.mapper.DefaultActionMapper</li>
 * </ul>
 * where RestfulActionMapper will be consulted first before DefaultActionMapper as it has a higher order.
 * {@link ActionMapper} with a lower order will be consulted first.
 *
 * <b>Through constructor (normally for unit testing)</b>
 * <p/>
 * {@link CompositeActionMapper} does this with the pre-configured {@link ActionMapper}s
 * passed in as an array. The order of the {@link ActionMapper}s is important as they will be consulted
 * in order.
 * <!-- SNIPPET: javadoc -->
 *
 *
 * @author tmjee
 * @version $Date$ $Id$
 */
public class CompositeActionMapper implements ActionMapper {

    private static final Log LOG = LogFactory.getLog(CompositeActionMapper.class);

    /** Contains array of {@link com.opensymphony.webwork.dispatcher.mapper.CompositeActionMapper.ActionMapperInfo} objects. */
    private List actionMappers = new ArrayList();


    /**
     * Creates an instance of {@link CompositeActionMapper}, reading in the pre-configured {@link ActionMapper}s
     * through webwork.properties file eg. with
     * <pre>
     *   webwork.mapper.class=com.opensymphony.webwork.dispatcher.mapper.CompositeActionMapper
     *   webwork.compositeActionMapper.1=com.opensymphony.webwork.dispatcher.mapper.RestfulActionMapper
     *   webwork.compositeActionMapper.2=com.opensymphony.webwork.dispatcher.mapper.DefaultActionMapper
     * </pre>
     * We would have configured a CompositeActionMapper, with 2 pre-configured {@link ActionMapper}s, namely
     * <ul>
     *      <li>com.opensymphony.webwork.dispatcher.mapper.RestfulActionMapper</li>
     *      <li>com.opensymphony.webwork.dispatcher.mapper.DefaultActionMapper</li>
     * </ul>
     * where RestfulActionMapper will be consulted first before DefaultActionMapper as it has a higher order.
     * {@link ActionMapper} with a lower order will be consulted first.
     */
    public CompositeActionMapper() {
        for (Iterator i = Configuration.list(); i.hasNext(); ) {
            String propertyName = (String) i.next();
            if (propertyName.startsWith(WebWorkConstants.WEBWORK_COMPOSITE_ACTION_MAPPER)) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("found CompositeActionMapper order property ["+propertyName+"]");
                }
                String orderAsString = propertyName.substring(WebWorkConstants.WEBWORK_COMPOSITE_ACTION_MAPPER.length());
                try {
                    int order = Integer.parseInt(orderAsString);
                    String actionMapperClassName = Configuration.getString(propertyName);
                    if (actionMapperClassName == null || (actionMapperClassName.trim().length() <= 0)) {
                        LOG.warn("property ["+propertyName+"] doesn't contains an ActionMapper class name, it will be ignored");
                    }
                    else {
                        try {
                            actionMappers.add(
                                new ActionMapperInfo(
                                        order,
                                        (ActionMapper) ObjectFactory.getObjectFactory().buildBean(actionMapperClassName.trim(), Collections.EMPTY_MAP)));
                        }
                        catch(Exception e) {
                            LOG.warn("failed to create ActionMapper ["+actionMapperClassName.trim()+"] defined in property ["+propertyName+"]", e);
                        }
                    }
                }
                catch(NumberFormatException e) {
                    LOG.warn("unable to recognize the order from property ["+propertyName+"], this property configuration will be ignored");
                }
            }
        }

        Collections.sort(actionMappers);
    }


    /**
     * Creates an instance of {@link CompositeActionMapper} with the pre-configured {@link ActionMapper}s
     * passed in as an array. The order of the {@link ActionMapper}s is important as they will be consulted
     * in order.
     *
     * @param actionMappers Array of {@link ActionMapper}s
     */
    public CompositeActionMapper(ActionMapper[] actionMappers) {
        for (int a=0; a< actionMappers.length; a++) {
            if (actionMappers[a] != null) {
                this.actionMappers.add(new ActionMapperInfo(a, actionMappers[a]));
            }
            else {
                LOG.warn("ActionMapper given at index["+a+"] is null, it will be ignored");
            }
        }
    }


     /**
     * This method will go through all the pre-configured {@link ActionMapper}s in the order defined, and return
     * the {@link ActionMapping} of the first {@link ActionMapper} that returns a valid (non-null) {@link ActionMapping}
     *
     * @param request {@link HttpServletRequest}
     * @return ActionMapping
     */
    public ActionMapping getMapping(HttpServletRequest request) {
        for (Iterator i = actionMappers.iterator(); i.hasNext(); ) {
            ActionMapperInfo info = (ActionMapperInfo) i.next();
            ActionMapper actionMapper = info.getActionMapper();
            if (LOG.isDebugEnabled()) {
                LOG.debug("trying to get ActionMapping from ActionMapper ["+actionMapper+"] with order ["+info.getOrder()+"] in CompositeActionMapper");
            }
            ActionMapping actionMapping = actionMapper.getMapping(request);
            if (actionMapping != null) {
                return actionMapping;
            }
        }
        LOG.info("failed to find a valid (non-null) ActionMapping from all preconfigured ActionMappers");
        return null;
    }

    /**
     * This method will go through all the pre-configured {@link ActionMapper}s in the order defined, and return
     * the URI of the first {@Link ActionMapper} that returns a valid (non-null) URI.
     * @param mapping {@link ActionMapping} 
     * @return String
     */
    public String getUriFromActionMapping(ActionMapping mapping) {
        for (Iterator i = actionMappers.iterator(); i.hasNext(); ) {
            ActionMapperInfo info = (ActionMapperInfo) i.next();
            ActionMapper actionMapper = info.getActionMapper();
            if (LOG.isDebugEnabled()) {
                LOG.debug("trying to get Uri from ActionMapper ["+actionMapper+"] with order ["+info.getOrder()+"] in CompositeActionMapper");
            }
            String uri = actionMapper.getUriFromActionMapping(mapping);
            if (uri != null && (uri.trim().length() > 0)) {
                return uri;
            }
        }
        LOG.info("failed to find a valid (non-null) URI from all preconfigured ActionMappers");
        return null;
    }

    /**
     * Returns a list of {@link ActionMapperInfo}, preconfigured in this {@link CompositeActionMapper}.
     * 
     * @return List of {@link com.opensymphony.webwork.dispatcher.mapper.CompositeActionMapper.ActionMapperInfo}
     */
    protected List getActionMapperInfos() {
        return actionMappers;
    }


    /**
     * Encapsulate the {@link ActionMapper} and its order in this {@link CompositeActionMapper}.
     */
    protected class ActionMapperInfo implements Comparable {

        private int order;
        private ActionMapper actionMapper;

        /**
         * Creates an instance of {@link ActionMapperInfo} that encapsulates the order and the
         * {@link ActionMapper} used.
         * 
         * @param order order in this {@link CompositeActionMapper}
         * @param actionMapper the {@link ActionMapper} encapsulated
         */
        private ActionMapperInfo(int order, ActionMapper actionMapper) {
            this.order = order;
            this.actionMapper = actionMapper;
        }

        /**
         * returns the order.
         * @return int
         */
        public int getOrder() { return order; }
        
        /**
         * returns the {@link ActionMapper}.
         * @return an {@link ActionMapper}
         */
        public ActionMapper getActionMapper() { return actionMapper; }

        /**
         * Return the order compared to <code>o</code> as defined in
         * {@link Comparable#compareTo(Object)}
         * 
         * @param o the object ({@link com.opensymphony.webwork.dispatcher.mapper.CompositeActionMapper.ActionMapperInfo}
         *          to compare to.
         * @return int
         */
        public int compareTo(Object o) {
            ActionMapperInfo _tmp = (ActionMapperInfo) o;
            return (order - _tmp.order);
        }
    }
}
