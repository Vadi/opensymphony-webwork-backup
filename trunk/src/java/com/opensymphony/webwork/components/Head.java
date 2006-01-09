package com.opensymphony.webwork.components;

import com.opensymphony.xwork.util.OgnlValueStack;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.webwork.config.Configuration;
import com.opensymphony.webwork.WebWorkConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <!-- START SNIPPET: javadoc -->
 *
 * Renders parts of the HEAD section for an HTML file. This is useful as some themes require certain CSS and JavaScript
 * includes.<p/>
 *
 * If, for example, your page has ajax components integrated, without having the default theme set to ajax, you might
 * want to use the head tag with <b>theme="ajax"</b> so that the typical ajax header setup will be included in the
 * page.<p/>
 *
 * The tag also includes the option to set a custom datepicker theme if needed. See calendarcss parameter for
 * description for details.<p/>
 *
 * <!-- END SNIPPET: javadoc -->
 *
 * <p/> <b>Examples</b>
 *
 * <pre>
 * <!-- START SNIPPET: example1 -->
 * &lt;head&gt;
 *   &lt;title&gt;My page&lt;/title&gt;
 *   &lt;ww:head/&gt;
 * &lt;/head&gt;
 * <!-- END SNIPPET: example1 -->
 * </pre>
 *
 * <pre>
 * <!-- START SNIPPET: example2 -->
 * &lt;head&gt;
 *   &lt;title&gt;My page&lt;/title&gt;
 *   &lt;ww:head theme="ajax" calendarcss="calendar-green"/&gt;
 * &lt;/head&gt;
 * <!-- END SNIPPET: example2 -->
 * </pre>
 *
 * @author Patrick Lightbody
 * @author Rene Gielen
 * @ww.tag name="head" tld-body-content="empty" tld-tag-class="com.opensymphony.webwork.views.jsp.ui.HeadTag"
 * description="Render a chunk of HEAD for your HTML file"
 * @since 2.2
 */
public class Head extends UIBean {
    final public static String TEMPLATE = "head";

    private String calendarcss = "calendar-blue.css";

    public Head(OgnlValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

    protected String getDefaultTemplate() {
        return TEMPLATE;
    }

    public void evaluateParams() {
        super.evaluateParams();

        if (calendarcss != null) {
            String css = findString(calendarcss);
            if (css != null && css.trim().length() > 0) {
                if (css.lastIndexOf(".css") < 0) {
                    addParameter("calendarcss", css + ".css");
                } else {
                    addParameter("calendarcss", css);
                }
            }
        }

        addParameter("encoding", Configuration.get(WebWorkConstants.WEBWORK_I18N_ENCODING));
    }

    public String getCalendarcss() {
        return calendarcss;
    }

    /**
     * @ww.tagattribute required="false" description="The jscalendar css theme to use" default="calendar-blue.css"
     */
    public void setCalendarcss(String calendarcss) {
        this.calendarcss = calendarcss;
    }
}
