/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.velocity;

import com.opensymphony.webwork.config.Configuration;

import com.opensymphony.xwork.util.OgnlUtil;

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
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.jsp.tagext.Tag;


/**
 * Custom user Directive that enables the WebWork2 UI tags to be easily accessed from Velocity pages
 *
 * @author $author$
 * @version $id$
 */
public abstract class AbstractTagDirective extends Directive {
    //~ Static fields/initializers /////////////////////////////////////////////

    /**
     * a params of tagname to tagclass that provides faster lookup that searching through the tagpath.  for example,
     * <pre>#tag( TextField )</pre>
     * would result in "TextField" and com.opensymphony.webwork.views.jsp.ui.TextFieldTag.class being stored in the
     * tagclassMap
     * @todo enable this params to be reloaded or reset
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

        // create a Map of the properties that the user has passed in
        Map propertyMap = this.createPropertyMap(contextAdapter, node);

        // assigned the properties to our tag
        OgnlUtil.setProperties(propertyMap, object);

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
            if (currentTag != null) {
                contextAdapter.put(VelocityManager.PARENT, currentTag);
            }

            contextAdapter.put(VelocityManager.TAG, object);

            InternalContextAdapter subContextAdapter = new WrappedInternalContextAdapter(contextAdapter);

            if (object instanceof Renderer) {
                return this.processRenderer((Renderer) object, subContextAdapter, writer, bodyNode);
            } else if (object instanceof Tag) {
                return this.processTag((Tag) object, subContextAdapter, writer, bodyNode);
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
     * @todo it would be nice for the Configuration object to allow listeners to be registered so that they can be
     * notified of changes to the Configuration files
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
     * TextField, Password, or Component
     * @return a new instance of the object specified by the Node
     * @throws org.apache.velocity.exception.ResourceNotFoundException
     */
    protected Object createObject(Node node) throws ResourceNotFoundException {
        String tagname = node.getFirstToken().toString();
        Class clazz = (Class) tagclassMap.get(tagname);

        if (clazz == null) {
            clazz = this.findTagInPath(tagname);
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
     * @throws org.apache.velocity.exception.ParseErrorException if the was an error in the format of the property
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
     *   <li>append the tagname + 'Tag' to the path and see if a class exists and is a Renderer or Tag</li>
     *   <li>append the tagname to the path and see if a class exists and is a Renderer or Tag</li>
     * </ul>
     * For example, let us say that we're search for a custom tag, Foobar.  Assuming our webwork.velocity.tag.path is
     * the default ("com.opensymphony.webwork.views.jsp.ui", "com.opensymphony.webwork.views.jsp", ""), then we will search
     * for our tag in the following locations:
     * <ul>
     *   <li>com.opensymphony.webwork.views.jsp.ui.FoobarTag</li>
     *   <li>com.opensymphony.webwork.views.jsp.ui.Foobar</li>
     *   <li>com.opensymphony.webwork.views.jsp.FoobarTag</li>
     *   <li>com.opensymphony.webwork.views.jsp.Foobar</li>
     *   <li>FoobarTag</li>
     *   <li>Foobar</li>
     * </ul>
     * @param tagname
     * @return
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
                    clazz = Class.forName(tagpath[index] + "." + tagname + "Tag");
                } catch (ClassNotFoundException e) {
                }
            }
        }

        return clazz;
    }

    /**
     * <p>
     * display tags that are an instanceof Renderer.  Renderers have a lifecycle similar to JSPs, but much simpler.
     * The intent is to provide Velocity users a way to access and create their own tags without the burden and
     * overhead of having to write a full blown tag library.
     * </p>
     * <p>
     * the lifecycle for the render is as follows:
     * </p>
     * <ol>
     *  <li>instantiate the Renderer instance</li>
     *  <li>use Ognl to set parameters passed in through the directive</li>
     *  <li>if this Renderer is an instance of IterationRenderer, call doBeforeRender()</li>
     *  <li>render the body content of the userdirective, if there is any</li>
     *  <li>ask the Renderer to render the tag</li>
     *  <li>if this Renderer is an instance of IterationRenderer, call doAfterRender()</li>
     *  <li>if this Renderer is an instance of IterationRenderer, and doAfterRender() returned
     * IterationRenderer.RENDER_AGAIN, go back to step 1, add more shampoo, and rinse til clean</li>
     * </ol>
     *
     * <p>
     * the render method (which called us) should have already added the renderer and it's parent tag (if applicable)
     * to the context under the labels tag and parent respectively
     * </p>
     *
     * @param renderer the instance of the Renderer we're working with
     * @param context the context the renderer will be provided
     * @param writer the writer the renderer will be provided
     * @param node the body of the userdirective, if there is any, null otherwise
     * @return true if the rendering was successful
     * @throws IOException if there was an error in rendering the tag
     *
     * @see com.opensymphony.webwork.views.velocity.Renderer
     * @see com.opensymphony.webwork.views.velocity.IterationRenderer
     */
    protected boolean processRenderer(Renderer renderer, InternalContextAdapter context, Writer writer, Node node) throws IOException {
        /**
         * we need to do a few things different for IterationRenderers.  rather than write another method that does
         * something so similar, we'll just do checks along the way to see if this method is an instance of
         * IterationRenderer
         */
        IterationRenderer iterationRenderer = null;

        if (renderer instanceof IterationRenderer) {
            iterationRenderer = (IterationRenderer) renderer;
        }

        try {
            boolean doRender = true;

            while (doRender) {
                if (iterationRenderer != null) {
                    iterationRenderer.doBeforeRender(context, writer);
                }

                // if body content exists, render it first!
                if (node != null) {
                    node.render(context, writer);
                }

                // here's the real work, just render the freakin' tag
                renderer.render(context, writer);

                if (iterationRenderer != null) {
                    int status = iterationRenderer.doAfterRender(context, writer);
                    doRender = (status == IterationRenderer.RENDER_AGAIN);
                } else {
                    doRender = false;
                }
            }
        } catch (Exception e) {
            StringWriter w = new StringWriter();
            PrintWriter pw = new PrintWriter(w);
            e.printStackTrace(pw);
            throw new IOException("unable to render tag! --\n" + w.getBuffer());
        }

        return true;
    }

    /**
     * @todo this is a placeholder for future code.
     */
    protected boolean processTag(Tag tag, InternalContextAdapter contextAdapter, Writer writer, Node bodyNode) throws ParseErrorException, IOException, MethodInvocationException, ResourceNotFoundException {
        throw new UnsupportedOperationException("processing JSP Tags is not currently supported");
    }

    /**
     * adds a given Node's key/value pair to the propertyMap.  For example, if this Node contained the value "rows=20",
     * then the key, rows, would be added to the propertyMap with the String value, 20.

     * @param propertyMap a params containing all the properties that we wish to set
     * @param node the parameter to set expressed in "name=value" format
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
