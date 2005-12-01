package com.opensymphony.webwork.components;

import com.opensymphony.xwork.util.OgnlValueStack;

import java.io.Writer;

/**
 * <!-- START SNIPPET: javadoc -->
 * This tag can be used to parameterize other tags, who implement
 * the ParametricTag interface declared here.<p/>
 *
 * The include tag and bean tag are examples of such tags.<p/>
 *
 * The inner classes, Parametric and UnnamedParametric, are implemented by tags. They indicate that a
 * particular Tag may have embedded params. For example, if we were wanted to use the ComponentTag
 * and wanted to provide custom params to assist with the rendering, we could declare something like<p/>
 * <!-- END SNIPPET: javadoc -->
 *
 * <!-- START SNIPPET: params -->
 * <ul>
 * 		<li>name (String) - the name of the parameter</li>
 *      <li>value (Object) - the value of the parameter</li>
 * </ul>
 * <!-- END SNIPPET: params -->
 *
 *
 * <p/> <b>Examples</b>
 * <!-- START SNIPPET: example -->
 * &lt;ui:component&gt;
 *  &lt;ui:param name="key"     value="[0]"/&gt;
 *  &lt;ui:param name="value"   value="[1]"/&gt;
 *  &lt;ui:param name="context" value="[2]"/&gt;
 * &lt;/ui:component&gt;
 * <!-- END SNIPPET: example -->
 * </pre>
 * <p/>
 * <!-- START SNIPPET: exampledescription -->
 * where the key will be the identifier and the value the result of an OGNL expression run against the current
 * OgnlValueStack.<p/>
 * <!-- END SNIPPET: exampledescription -->
 * <pre>
 *
 * @author Rickard �berg (rickard@dreambean.com)
 * @author Rene Gielen
 * @author tm_jee
 * @version $Revision$
 * @since 2.2
 *
 * @see Include
 * @see Bean
 *
 * @ww.tag name="param" tld-body-content="JSP" tld-tag-class="com.opensymphony.webwork.views.jsp.ParamTag"
 * description="Parametrize other tags"
 */
public class Param extends Component {
    protected String name;
    protected String value;

    public Param(OgnlValueStack stack) {
        super(stack);
    }

    public void end(Writer writer, String body) {
        Component component = findAncestor(Component.class);
        if (value != null) {
            if (component instanceof UnnamedParametric) {
                ((UnnamedParametric) component).addParameter(findValue(value));
            } else {
                String name = findString(this.name);

                if (name == null) {
                    throw new RuntimeException("No name found for following expression: " + name);
                }

                Object value = findValue(this.value);
                component.addParameter(name, value);
            }
        } else {
            if (component instanceof UnnamedParametric) {
                ((UnnamedParametric) component).addParameter(body);
            } else {
                component.addParameter(findString(name), body);
            }
        }

        super.end(writer, "");
    }

    public boolean usesBody() {
        return true;
    }

    /**
     * @ww.tagattribute required="true" type="String"
     * description="Name of Parameter to set"
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @ww.tagattribute required="false" default="The value of evaluating provided name against stack"
     * description="Value expression for Parameter to set"
     */
    public void setValue(String value) {
        this.value = value;
    }

    public interface UnnamedParametric {
        public void addParameter(Object value);
    }
}
