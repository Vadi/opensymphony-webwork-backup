/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.velocity;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.config.Configuration;
import com.opensymphony.webwork.views.jsp.ParamTag;

import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.util.OgnlUtil;
import com.opensymphony.xwork.util.OgnlValueStack;

import ognl.Ognl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.velocity.app.event.EventCartridge;
import org.apache.velocity.context.Context;
import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.Node;
import org.apache.velocity.runtime.resource.Resource;
import org.apache.velocity.util.introspection.IntrospectionCacheData;

import java.io.IOException;
import java.io.Writer;

import java.util.*;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTag;
import javax.servlet.jsp.tagext.IterationTag;
import javax.servlet.jsp.tagext.Tag;


/**
 * Custom user Directive that enables the WebWork2 UI tags to be easily accessed from Velocity pages
 *
 * @author $author$
 * @version $id$
 */
public abstract class AbstractTagDirective extends Directive {
    //~ Static fields/initializers /////////////////////////////////////////////

    protected static Log log = LogFactory.getLog(AbstractTagDirective.class);

    /**
     * a params of tagname to tagclass that provides faster lookup that searching through the tagpath.  for example,
     * <pre>#tag( TextField )</pre>
     * would result in "TextField" and com.opensymphony.webwork.views.jsp.ui.TextFieldTag.class being stored in the
     * tagclassMap
     * todo enable this params to be reloaded or reset
     */
    protected static Map tagclassMap = new HashMap();

    //~ Methods ////////////////////////////////////////////////////////////////

    /**
     * the guts of this directive that indicates how this directive should be rendered.  Conceptually, this method is
     * a controller that delegates the work to other methods.  by convention, i'm using process* for the delegated
     * methods.  processRenderer and processTag respectively.
     */
    public boolean render(InternalContextAdapter contextAdapter, Writer writer, Node node) throws IOException, ResourceNotFoundException, ParseErrorException, MethodInvocationException {
        if (node.jjtGetNumChildren() < 1) {
            throw new ParseErrorException("no tag specified!  to use the #tag directive, you must specify at least the name of the tag to use");
        }

        // instantiate our tag
        Object object = this.createObject(node.jjtGetChild(0));

        /**
         * if this directive allows for a body, the last child Node will be the body.  we'll want to grab a handle to
         * this Node to allow the processTag method to use it
         */
        Node bodyNode = null;

        /**
         * if this Directive is a BLOCK directive, then we <b>know</b> we must have a body.  store the reference to
         * the directive's body in bodyNode
         */
        if (this.getType() == BLOCK) {
            bodyNode = node.jjtGetChild(node.jjtGetNumChildren() - 1);
        }

        /**
         * save the previous parent and tag if there are any
         */
        Object currentParent = contextAdapter.get(VelocityManager.PARENT);
        Object currentTag = contextAdapter.get(VelocityManager.TAG);

        try {
            // if we're already inside a tag, then make this tag the new parent
            contextAdapter.put(VelocityManager.PARENT, currentTag);
            contextAdapter.put(VelocityManager.TAG, object);

            InternalContextAdapter subContextAdapter = new WrappedInternalContextAdapter(contextAdapter);

            // populate our tag with all the user specified properties
            if (object instanceof ParamTag.Parametric) {
                Map params = ((ParamTag.Parametric) object).getParameters();
                if (params != null) {
                    params.clear();
                }
            }

            applyAttributes(contextAdapter, node, object);

            if (object instanceof Tag) {
                PageContext pageContext = ServletActionContext.getPageContext();

                if (currentTag instanceof Tag) {
                    ((Tag) object).setParent((Tag) currentTag);
                }

                try
                {
                    return this.processTag(pageContext, (Tag) object, subContextAdapter, writer, node, bodyNode);
                }
                catch (Exception e)
                {
                    log.error("Error processing tag: " + e, e);
                    return false;
                }
            } else {
                return true;
            }
        } finally {
            /**
             * replace the parent and/or child if there were any
             */
            if (currentParent != null) {
                contextAdapter.put(VelocityManager.PARENT, currentParent);
            } else {
                contextAdapter.remove(VelocityManager.PARENT);
            }

            if (currentTag != null) {
                contextAdapter.put(VelocityManager.TAG, currentTag);
            } else {
                contextAdapter.remove(VelocityManager.TAG);
            }
        }
    }

    /**
     * todo it would be nice for the Configuration object to allow listeners to be registered so that they can be
     * notified of changes to the Configuration files
     *
     * @return an array of paths to search for our tag library
     */
    protected String[] getTagPath() throws ResourceNotFoundException {
        List pathList = new ArrayList();

        // let's add the webwork tags first
        pathList.add("com.opensymphony.webwork.views.jsp.ui");
        pathList.add("com.opensymphony.webwork.views.jsp");

        // now, if the user has defined a custom path, let's add that too
        if (Configuration.isSet("webwork.velocity.tag.path")) {
            StringTokenizer st = new StringTokenizer(Configuration.getString("webwork.velocity.tag.path"), ",");

            while (st.hasMoreTokens()) {
                String token = st.nextToken().trim();
                pathList.add(token);
            }
        }

        // allow fully qualified class names to be specified
        pathList.add("");

        String[] path = new String[pathList.size()];
        pathList.toArray(path);

        return path;
    }

    /**
     * create a new instance of our rendering object.  this will usually be a Tag, but I've left it as an Object just
     * in case we want to define more abitrary rendering mechanisms that are not JSP tags
     *
     * @param node the node that contains the label for our rendering object.  this will usually be something like
     *             TextField, Password, or Component
     * @return a new instance of the object specified by the Node
     * @throws org.apache.velocity.exception.ResourceNotFoundException
     *
     */
    protected Object createObject(Node node) throws ResourceNotFoundException {
        String tagname = node.getFirstToken().toString();
        Class clazz = (Class) tagclassMap.get(tagname);

        if (clazz == null) {
            clazz = this.findTagInPath(tagname);
            tagclassMap.put(tagname, clazz);
        }

        if (clazz == null) {
            throw new ResourceNotFoundException("No tag, '" + tagname + "', found in tag path");
        }

        try {
            return clazz.newInstance();
        } catch (Exception e) {
            throw new ResourceNotFoundException("unable to instantiate tag class, '" + clazz.getName() + "'");
        }
    }

    /**
     * create a Map of properties that the user has passed in.  for example,
     * <pre>
     * #tag( TextField "name=hello" "value=world" "template=foo" )
     * </pre>
     * would yield a params that contains {["name", "hello"], ["value", "world"], ["template", "foo"]}
     *
     * @param node the Node passed in to the render method
     * @return a Map of the user specified properties
     * @throws org.apache.velocity.exception.ParseErrorException
     *          if the was an error in the format of the property
     * @see #render
     */
    protected Map createPropertyMap(InternalContextAdapter contextAdapter, Node node) throws ParseErrorException, MethodInvocationException {
        Map propertyMap = new HashMap();

        for (int index = 1, length = node.jjtGetNumChildren(); index < length;
             index++) {
            this.putProperty(propertyMap, contextAdapter, node.jjtGetChild(index));
        }

        return propertyMap;
    }

    /**
     * Searches for tags (class that are instances of Renderers or Tags) in the webwork.velocity.tag.path using the
     * following rules:
     * <ul>
     * <li>append the tagname + 'Tag' to the path and see if a class exists and is a Renderer or Tag</li>
     * <li>append the tagname to the path and see if a class exists and is a Renderer or Tag</li>
     * </ul>
     * For example, let us say that we're search for a custom tag, Foobar.  Assuming our webwork.velocity.tag.path is
     * the default ("com.opensymphony.webwork.views.jsp.ui", "com.opensymphony.webwork.views.jsp", ""), then we will search
     * for our tag in the following locations:
     * <ul>
     * <li>com.opensymphony.webwork.views.jsp.ui.FoobarTag</li>
     * <li>com.opensymphony.webwork.views.jsp.ui.Foobar</li>
     * <li>com.opensymphony.webwork.views.jsp.FoobarTag</li>
     * <li>com.opensymphony.webwork.views.jsp.Foobar</li>
     * <li>FoobarTag</li>
     * <li>Foobar</li>
     * </ul>
     *
     * @param tagname
     * @see #getTagPath
     */
    protected Class findTagInPath(String tagname) throws ResourceNotFoundException {
        String[] tagpath = this.getTagPath();

        Class clazz = null;

        for (int index = 0; (clazz == null) && (index < tagpath.length);
             index++) {
            try {
                clazz = Class.forName(tagpath[index] + "." + tagname + "Tag");
            } catch (ClassNotFoundException e) {
            }

            if (clazz == null) {
                try {
                    clazz = Class.forName(tagpath[index] + "." + tagname);
                } catch (ClassNotFoundException e) {
                }
            }
        }

        return clazz;
    }

    /**
     *
     */
    protected boolean processTag(PageContext pageContext, Tag tag, InternalContextAdapter context, Writer writer, Node node, Node bodyNode) throws ParseErrorException, IOException, MethodInvocationException, ResourceNotFoundException {
        tag.setPageContext(pageContext);
        writer = pageContext.getOut();

        try {
            Map paramMap = null;
            ParamTag.Parametric parameterizedTag = null;

            if (tag instanceof ParamTag.Parametric) {
                parameterizedTag = (ParamTag.Parametric) tag;
                paramMap = parameterizedTag.getParameters();
            }

            int result = tag.doStartTag();

            if (paramMap != null) {
                parameterizedTag.getParameters().putAll(paramMap);
            }

            if (result != Tag.SKIP_BODY) {
                if (tag instanceof BodyTag) {
                    BodyTag bodyTag = (BodyTag) tag;

                    if (result == BodyTag.EVAL_BODY_BUFFERED) {
                        BodyContent bodyContent = pageContext.pushBody();
                        writer = bodyContent.getEnclosingWriter();
                        bodyTag.setBodyContent(bodyContent);
                    }

                    bodyTag.doInitBody();
                }

                for (boolean done = false; !done;) {
                    // if body content exists, render it first!
                    if (bodyNode != null) {
                        bodyNode.render(context, writer);
                    }

                    if (tag instanceof IterationTag) {
                        IterationTag iterationTag = (IterationTag) tag;
                        done = (iterationTag.doAfterBody() == BodyTag.EVAL_BODY_AGAIN) ? false : true;
                    } else {
                        done = true;
                    }
                }

                if (tag instanceof BodyTag) {
                    if (result == BodyTag.EVAL_BODY_BUFFERED) {
                        writer = pageContext.popBody();
                    } else {
                        ((BodyTag) tag).setBodyContent(null);
                    }
                }
            }

            tag.doEndTag();
        } catch (JspException e) {
            String gripe = "Fatal exception caught while processing tag,  " + tag.getClass().getName();
            log.warn(gripe, e);

            String methodName = "-";
            throw new MethodInvocationException(gripe, e, methodName);
        }

        return true;
    }

    /**
     * adds a given Node's key/value pair to the propertyMap.  For example, if this Node contained the value "rows=20",
     * then the key, rows, would be added to the propertyMap with the String value, 20.
     *
     * @param propertyMap a params containing all the properties that we wish to set
     * @param node        the parameter to set expressed in "name=value" format
     */
    protected void putProperty(Map propertyMap, InternalContextAdapter contextAdapter, Node node) throws ParseErrorException, MethodInvocationException {
        // node.value uses the WebWorkValueStack to evaluate the directive's value parameter
        String param = node.value(contextAdapter).toString();

        int idx = param.indexOf("=");

        if (idx != -1) {
            String property = param.substring(0, idx);

            String value = param.substring(idx + 1);
            propertyMap.put(property, value);
        } else {
            throw new ParseErrorException("#" + this.getName() + " arguments must include an assignment operator!  For example #tag( Component \"template=mytemplate\" ).  #tag( TextField \"mytemplate\" ) is illegal!");
        }
    }

    /**
     * apply the attributes requested to the specified object
     * @param context
     * @param node
     * @param object the object the tags should be applied to
     * @throws ParseErrorException
     * @throws MethodInvocationException
     */
    private void applyAttributes(InternalContextAdapter context, Node node, Object object) throws ParseErrorException, MethodInvocationException {
        Map propertyMap = this.createPropertyMap(context, node);

        // if there's nothing to do, don't bother creating an OgnlContext and the Iterator
        if ((propertyMap == null) || (propertyMap.size() == 0)) {
            return;
        }

        OgnlValueStack stack = ActionContext.getContext().getValueStack();
        Map ognlContext = Ognl.createDefaultContext(object);

        for (Iterator iterator = propertyMap.entrySet().iterator();
             iterator.hasNext();) {
            Map.Entry entry = (Map.Entry) iterator.next();
            String key = entry.getKey().toString();
            Object value = entry.getValue();

            if (object instanceof ParamTag.Parametric && key.startsWith("params.")) {
                value = stack.findValue(value.toString());
            }

            OgnlUtil.setProperty(key, value, object, ognlContext);
        }
    }

    //~ Inner Classes //////////////////////////////////////////////////////////

    /**
     * the WrappedInternalContextAdapter is a simple wrapper around the InternalContextAdapter that allows us to
     * effectively create local variables within each custom directive that don't bleed into the main context.
     */
    class WrappedInternalContextAdapter implements InternalContextAdapter {
        private HashMap params = new HashMap();
        private InternalContextAdapter contextAdapter;

        public WrappedInternalContextAdapter(InternalContextAdapter contextAdapter) {
            this.contextAdapter = contextAdapter;
        }

        public InternalContextAdapter getBaseContext() {
            return contextAdapter.getBaseContext();
        }

        public void setCurrentResource(Resource resource) {
            contextAdapter.setCurrentResource(resource);
        }

        public Resource getCurrentResource() {
            return contextAdapter.getCurrentResource();
        }

        public String getCurrentTemplateName() {
            return contextAdapter.getCurrentTemplateName();
        }

        public EventCartridge getEventCartridge() {
            return contextAdapter.getEventCartridge();
        }

        public Context getInternalUserContext() {
            return contextAdapter.getInternalUserContext();
        }

        public Object[] getKeys() {
            Set keySet = params.keySet();

            if (keySet == null) {
                return contextAdapter.getKeys();
            }

            Object[] objects = new Object[keySet.size()];
            keySet.toArray(objects);

            return objects;
        }

        public Object[] getTemplateNameStack() {
            return contextAdapter.getTemplateNameStack();
        }

        public EventCartridge attachEventCartridge(EventCartridge eventCartridge) {
            return contextAdapter.attachEventCartridge(eventCartridge);
        }

        public boolean containsKey(Object o) {
            if (params.containsKey(o)) {
                return true;
            }

            return contextAdapter.containsKey(o);
        }

        public Object get(String s) {
            Object obj = params.get(s);

            if (obj == null) {
                obj = contextAdapter.get(s);
            }

            return obj;
        }

        public IntrospectionCacheData icacheGet(Object o) {
            return contextAdapter.icacheGet(o);
        }

        public void icachePut(Object o, IntrospectionCacheData introspectionCacheData) {
            contextAdapter.icachePut(o, introspectionCacheData);
        }

        public void popCurrentTemplateName() {
            contextAdapter.popCurrentTemplateName();
        }

        public void pushCurrentTemplateName(String s) {
            contextAdapter.pushCurrentTemplateName(s);
        }

        public Object put(String s, Object o) {
            return params.put(s, o);
        }

        public Object remove(Object o) {
            Object obj = params.remove(o);

            if (obj == null) {
                obj = contextAdapter.remove(o);
            }

            return obj;
        }
    }
}
