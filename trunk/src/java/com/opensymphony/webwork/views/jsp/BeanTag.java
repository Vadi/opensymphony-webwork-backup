/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp;

import com.opensymphony.xwork.ObjectFactory;
import com.opensymphony.xwork.util.OgnlUtil;
import com.opensymphony.xwork.util.OgnlValueStack;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import java.util.Map;


/**
 * <p>BeanTag will instantiate a class that conforms to the JavaBeans specification. This tag has a body which can contain
 * a number of ParamTag {@link ParamTag} elements to set any mutator methods on that class.</p>
 * <p>If the id attribute is set on the BeanTag, it will place the instantiated bean into the PageContext and the
 * ActionContext.</p>
 * <p>Examples:</p>
 * <pre>
 * <ww:bean name="'com.opensymphony.webwork.example.counter.SimpleCounter'" id="counter">
 *   <ww:param name="'foo'" value="'BAR'"/>
 * <p/>
 *   The value of foo is : <ww:property value="foo"/>, when inside the bean tag.<br />
 * </ww:bean>
 * </pre>
 * <p>This example instantiates a bean called SimpleCounter and sets the foo property (setFoo('BAR')). The
 * SimpleCounter object is then pushed onto the Valuestack, which means that we can called its accessor methods (getFoo())
 * with the Property tag and get their values.</p>
 * <p>In the above example, the id has been set to a value of <i>counter</i>. This means that the SimpleCounter class
 * will be placed into the PageContext and ActionContext. You can access theah SimpleCounter class using JSTL:</p>
 * <pre>
 * <c:out value="${counter.foo}"/>
 * </pre>
 * <p> or using the webwork Property tag:</p>
 * <pre>
 * <ww:property value="#counter.foo"/>
 * </pre>
 * <p>In the property tag example, the <i>#</i> tells Ognl to search the context for the SimpleCounter class which has
 * an id(key) of <i>counter</i></p>
 *
 * @author $author$
 * @author Rick Salsa (rsal@mb.sympatico.ca)
 * @author Brock Bulger
 * @version $Revision$
 */
public class BeanTag extends WebWorkTagSupport implements ParamTag.Parametric {
    //~ Static fields/initializers /////////////////////////////////////////////

    protected static Log log = LogFactory.getLog(BeanTag.class);

    //~ Instance fields ////////////////////////////////////////////////////////

    protected Object bean;
    protected String name;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setName(String name) {
        this.name = name;
    }

    public Map getParameters() {
        return null;
    }

    public void addParameter(String key, Object value) {
        OgnlUtil.setProperty(key, value, bean, getStack().getContext());
    }

    public int doEndTag() throws JspException {
        OgnlValueStack stack = getStack();
        stack.pop();

        return SKIP_BODY;
    }

    public int doStartTag() throws JspException {
        if (name == null) {
            throw new JspException("Bean name must be specified.");
        }

        try {
            bean = ObjectFactory.getObjectFactory().buildBean(Class.forName(findString(name)));
        } catch (Exception e) {
            log.error("Could not instantiate bean", e);

            return SKIP_PAGE;
        }

        OgnlValueStack stack = getStack();

        // push bean on stack
        stack.push(bean);

        // Store as attribute in page context and in the ActionContext for use with the property tag.
        if (getId() != null) {
            pageContext.setAttribute(getId(), bean);
            getStack().getContext().put(getId(), bean);
        }

        return EVAL_BODY_INCLUDE;
    }
}
