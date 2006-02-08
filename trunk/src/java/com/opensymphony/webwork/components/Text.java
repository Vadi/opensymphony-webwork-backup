package com.opensymphony.webwork.components;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.util.TextUtils;
import com.opensymphony.xwork.TextProvider;
import com.opensymphony.xwork.util.OgnlValueStack;

/**
 * <!-- START SNIPPET: javadoc -->
 * Render a I18n text message. 
 * 
 * <p/>
 *
 * The message must be in a resource bundle
 * with the same name as the action that it is associated with. In practice
 * this means that you should create a properties file in the same package
 * as your Java class with the same name as your class, but with .properties
 * extension.
 *
 * <p/>
 *
 * If the named message is not found, then the body of the tag will be used as default message.
 * If no body is used, then the name of the message will be used.
 * 
 * <!-- END SNIPPET: javadoc -->
 *
 *
 *
 * <!-- START SNIPPET: params -->
 * 
 * <ul>
 *      <li>name* (String) - the i18n message key</li>
 * </ul>
 * 
 * <!-- END SNIPPET: params -->
 *
 * <p/>
 *
 * Example:
 * <pre>
 * <!-- START SNIPPET: exdescription -->
 * 
 * Accessing messages from a given bundle (the i18n Shop example bundle in the first example) and using bundle defined through ww in the second example.</p>
 * 
 * <!-- END SNIPPET: exdescription -->
 * </pre>
 *
 * <pre>
 * <!-- START SNIPPET: example -->
 * 
 * &lt;!-- First Example --&gt;
 * &lt;ww:i18n name="webwork.action.test.i18n.Shop"&gt;
 *     &lt;ww:text name="main.title"/&gt;
 * &lt;/ww:i18n&gt;
 *
 * &lt;!-- Second Example --&gt;
 * &lt;ww:text name="main.title" /&gt;
 * 
 * <!-- END SNIPPET: example -->
 * </pre>
 * 
 * 
 * <pre>
 * <!-- START SNIPPET: i18nExample -->
 * 
 * &lt;ww:text name="some.key" /&gt;
 * 
 * <!-- END SNIPPET: i18nExample -->
 * </pre>
 * 
 *
 * @author Jason Carreira
 * @author Patrick Lightbody
 * @author Rene Gielen
 * @author tm_jee
 * @version $Revision$
 * @since 2.2
 *
 * @see Param
 *
 * @ww.tag name="text" tld-body-content="JSP" tld-tag-class="com.opensymphony.webwork.views.jsp.TextTag"
 * description="Render a I18n text message."
 */
public class Text extends Component implements Param.UnnamedParametric {
    private static final Log LOG = LogFactory.getLog(Text.class);

    protected List values = Collections.EMPTY_LIST;
    protected String actualName;
    protected String name;

    public Text(OgnlValueStack stack) {
        super(stack);
    }

    /**
     * @ww.tagattribute required="true"
     * description=" Name of resource property to fetch"
     */
    public void setName(String name) {
        this.name = name;
    }

    public boolean end(Writer writer, String body) {
        actualName = findString(name, "name", "You must specify the i18n key. Example: welcome.header");
        String defaultMessage;
        if (TextUtils.stringSet(body)) {
            defaultMessage = body;
        } else {
            defaultMessage = actualName;
        }
        String msg = null;
        OgnlValueStack stack = getStack();

        for (Iterator iterator = getStack().getRoot().iterator();
             iterator.hasNext();) {
            Object o = iterator.next();

            if (o instanceof TextProvider) {
                TextProvider tp = (TextProvider) o;
                msg = tp.getText(actualName, defaultMessage, values, stack);

                break;
            }
        }

        if (msg != null) {
            try {
                if (getId() == null) {
                    writer.write(msg);
                } else {
                    stack.getContext().put(getId(), msg);
                }
            } catch (IOException e) {
                LOG.error("Could not write out Text tag", e);
            }
        }

        return super.end(writer, "");
    }

    public void addParameter(String key, Object value) {
        addParameter(value);
    }

    public void addParameter(Object value) {
        if (values.isEmpty()) {
            values = new ArrayList(4);
        }

        values.add(value);
    }
}
