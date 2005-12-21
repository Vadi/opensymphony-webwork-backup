package com.opensymphony.webwork.components;

import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * DoubleListUIBean is the standard superclass of all webwork double list handling components.
 *
 * @author Patrick Lightbody
 * @author Rene Gielen
 * @version $Revision$
 * @since 2.2
 */
public abstract class DoubleListUIBean extends ListUIBean {
    protected String doubleList;
    protected String doubleListKey;
    protected String doubleListValue;
    protected String doubleName;
    protected String doubleValue;
    protected String formName;

    public DoubleListUIBean(OgnlValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

    public void evaluateExtraParams() {
        super.evaluateExtraParams();

        Object doubleName = null;

        if (this.doubleName != null) {
            addParameter("doubleName", findString(this.doubleName));
        }

        if (doubleList != null) {
            addParameter("doubleList", doubleList);
        }

        if (doubleListKey != null) {
            addParameter("doubleListKey", doubleListKey);
        }

        if (doubleListValue != null) {
            addParameter("doubleListValue", doubleListValue);
        }

        if (formName != null) {
            addParameter("formName", findString(formName));
        } else {
            // ok, let's look it up
            Component form = findAncestor(Form.class);
            if (form != null) {
                addParameter("formName", form.getParameters().get("name"));
            }
        }

        Class valueClazz = getValueClassType();

        if (valueClazz != null) {
            if (doubleValue != null) {
                addParameter("doubleNameValue", findValue(doubleValue, valueClazz));
            } else if (doubleName != null) {
                addParameter("doubleNameValue", findValue(doubleName.toString(), valueClazz));
            }
        } else {
            if (doubleValue != null) {
                addParameter("doubleNameValue", findValue(doubleValue));
            } else if (doubleName != null) {
                addParameter("doubleNameValue", findValue(doubleName.toString()));
            }
        }
    }

    /**
     * @ww.tagattribute required="true"
     * description="The second iterable source to populate from."
     */
    public void setDoubleList(String doubleList) {
        this.doubleList = doubleList;
    }

    /**
     * @ww.tagattribute required="false"
     * description="The key expression to use for second list"
     */
    public void setDoubleListKey(String doubleListKey) {
        this.doubleListKey = doubleListKey;
    }

    /**
     * @ww.tagattribute required="false"
     * description="The value expression to use for second list"
     */
    public void setDoubleListValue(String doubleListValue) {
        this.doubleListValue = doubleListValue;
    }

    /**
     * @ww.tagattribute required="true"
     * description="The name for complete component"
     */
    public void setDoubleName(String doubleName) {
        this.doubleName = doubleName;
    }

    /**
     * @ww.tagattribute required="false"
     * description="The value expression for complete component"
     */
    public void setDoubleValue(String doubleValue) {
        this.doubleValue = doubleValue;
    }

    /**
     * @ww.tagattribute required="false"
     * description="The form name this component resides in and populates to"
     */
    public void setFormName(String formName) {
        this.formName = formName;
    }
}
