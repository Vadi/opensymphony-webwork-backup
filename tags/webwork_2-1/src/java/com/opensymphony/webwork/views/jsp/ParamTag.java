/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp;

import java.util.Map;

import javax.servlet.jsp.JspException;


/**
 * This tag can be used to parameterize other tags, who implement
 * the ParametricTag interface declared here.
 * <p/>
 * The IncludeTag and BeanTag are examples of such tags.
 * <p/>
 * The inner classes, Parametric and UnnamedParametric, are implemented by tags. They indicate that a
 * particular Tag may have embedded params.  For example, if we were wanted to use the ComponentTag
 * and wanted to provide custom params to assist with the rendering, we could declare something like
 * <p/>
 * <pre>
 * &lt;ui:component&gt;
 *  &lt;ui:param name="key"     value="[0]"/&gt;
 *  &lt;ui:param name="value"   value="[1]"/&gt;
 *  &lt;ui:param name="context" value="[2]"/&gt;
 * &lt;/ui:component&gt;
 * </pre>
 * <p/>
 * where the key will be the identifier and the value the result of an OGNL expression run against the current
 * OgnlValueStack
 *
 * @author Rickard Öberg (rickard@dreambean.com)
 * @see com.opensymphony.webwork.views.jsp.IncludeTag
 * @see com.opensymphony.webwork.views.jsp.BeanTag
 */
public class ParamTag extends WebWorkBodyTagSupport {
    //~ Instance fields ////////////////////////////////////////////////////////

    // Attributes ----------------------------------------------------
    protected String nameAttr;
    protected String valueAttr;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setName(String aName) {
        nameAttr = aName;
    }

    public void setValue(String aName) {
        valueAttr = aName;
    }

    // BodyTag implementation ----------------------------------------
    public int doEndTag() throws JspException {
        Parametric parametricTag = (Parametric) findAncestorWithClass(this, Parametric.class);

        if (parametricTag != null) {
            if (valueAttr != null) {
                if (parametricTag instanceof UnnamedParametric) {
                    ((UnnamedParametric) parametricTag).addParameter(findValue(valueAttr));
                } else {
                    Object name = findValue(nameAttr);

                    if (name == null) {
                        throw new JspException("No name found for following expression: " + nameAttr);
                    }

                    Object value = findValue(valueAttr);
                    parametricTag.addParameter(name.toString(), value);
                }
            } else {
                String content = null;

                if (!((bodyContent != null) && ((content = bodyContent.getString()).length() != 0))) {
                    content = null; // No value
                }

                if (parametricTag instanceof UnnamedParametric) {
                    ((UnnamedParametric) parametricTag).addParameter(content);
                } else {
                    parametricTag.addParameter(findString(nameAttr), content);
                }
            }
        }

        return EVAL_PAGE;
    }

    //~ Inner Interfaces ///////////////////////////////////////////////////////

    // Inner classes -------------------------------------------------
    public interface Parametric {
        public Map getParameters();

        public void addParameter(String name, Object value);
    }

    public interface UnnamedParametric extends Parametric {
        public void addParameter(Object value);
    }
}
