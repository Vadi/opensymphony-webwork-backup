package com.opensymphony.webwork.components;

import com.opensymphony.xwork.util.OgnlValueStack;

import java.io.Writer;

/**
 * <!-- START SNIPPET: javadoc -->
 * Push value on stack for simplified usage.</p>
 * <!-- END SNIPPET: javadoc -->
 *
 * <!-- START SNIPPET: params -->
 * <ul>
 * 		<li>value* (Object) - value to be pushed into the top of the stack</li>
 * </ul>
 * <!-- END SNIPPET: params -->
 *
 *
 * <p/> <b>Examples</b>
 * <!-- START SNIPPET: example -->
 * &lt;ww:push value="user"&gt;
 *     &lt;ww:propery value="firstName" /&gt;
 *     &lt;ww:propery value="lastName" /&gt;
 * &lt;/ww:push&gt;
 * <!-- END SNIPPET: example -->
 * </pre>
 *
 * @author Patrick Lightbody
 * @author Rene Gielen
 * @author tm_jee
 * @version $Revision$
 * @since 2.2
 *
 * @jsp.tag name="push" body-content="JSP"
 * description="Push value on stack for simplified usage."
 */
public class Push extends Component {
    protected String value;
    protected boolean pushed;

    public Push(OgnlValueStack stack) {
        super(stack);
    }

    public void start(Writer writer) {
        super.start(writer);

        OgnlValueStack stack = getStack();

        if (stack != null) {
            stack.push(findValue(value, "value", "You must specify a value to push on the stack. Example: person"));
            pushed = true;
        } else {
            pushed = false; // need to ensure push is assigned, otherwise we may have a leftover value
        }
    }

    public void end(Writer writer, String body) {
        OgnlValueStack stack = getStack();

        if (pushed && (stack != null)) {
            stack.pop();
        }

        super.end(writer, body);
    }

    /**
     * @jsp.attribute required="true"  rtexprvalue="true"
     * description="Value to push on stack"
     */
    public void setValue(String value) {
        this.value = value;
    }
}
