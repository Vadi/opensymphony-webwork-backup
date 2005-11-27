package com.opensymphony.webwork.components;

import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.LocaleProvider;
import com.opensymphony.xwork.TextProviderSupport;
import com.opensymphony.xwork.util.LocalizedTextUtil;
import com.opensymphony.xwork.util.OgnlValueStack;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Writer;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * <!-- START SNIPPET: javadoc -->
 * Gets a resource bundle and place it on the value stack. This allows
 * the text tag to access messages from any bundle, and not just the bundle
 * associated with the current action.
 * <!-- END SNIPPET: javadoc -->
 * 
 * <!-- START SNIPPET: params-->
 * <ul>
 * 		<li>name* - the resource bundle's name (eg foo/bar/customBundle)</li>
 * </ul>
 * <!-- END SNIPPET: params -->
 * 
 * <pre>
 * Example:
 * <!-- START SNIPPET: example -->
 * &lt;ww:i18n name="myCustomBundle"&gt;
 *    The i18n value for key aaa.bbb.ccc in myCustomBundle is &lt;ww:property value="text('aaa.bbb.ccc')" /&gt;
 * &lt;/ww:i18n&gt;
 * <!-- END SNIPPET: example -->
 * <pre>
 * 
 * @author Rickard �berg (rickard@dreambean.com)
 * @author Rene Gielen
 * @author tm_jee ( tm_jee (at) yahoo.co.uk )
 * @version $Revision$
 * @since 2.2
 *
 * @jsp.tag name="i18n" body-content="JSP"
 * description="Get a resource bundle and place it on the value stack"
 */
public class I18n extends Component {
    private static final Log LOG = LogFactory.getLog(I18n.class);

    protected boolean pushed;
    protected String name;

    public I18n(OgnlValueStack stack) {
        super(stack);
    }

    public void start(Writer writer) {
        super.start(writer);

        try {
            String name = this.findString(this.name, "name", "Resource bundle name is required. Example: foo or foo_en");
            ResourceBundle bundle = (ResourceBundle) findValue("texts('" + name + "')");

            if (bundle == null) {
                bundle = LocalizedTextUtil.findResourceBundle(name, (Locale) getStack().getContext().get(ActionContext.LOCALE));
            }

            if (bundle != null) {
                final Locale locale = (Locale) getStack().getContext().get(ActionContext.LOCALE);
                getStack().push(new TextProviderSupport(bundle, new LocaleProvider() {
                    public Locale getLocale() {
                        return locale;
                    }
                }));
                pushed = true;
            }
        } catch (Exception e) {
            String msg = "Could not find the bundle " + name;
            LOG.error(msg, e);
            throw new RuntimeException(msg);
        }
    }

    public void end(Writer writer, String body) {
        if (pushed) {
            getStack().pop();
        }

        super.end(writer, body);
    }

    /**
     * @jsp.attribute required="true"  rtexprvalue="true"
     * description="Name of ressource bundle to use"
     */
    public void setName(String name) {
        this.name = name;
    }
}
