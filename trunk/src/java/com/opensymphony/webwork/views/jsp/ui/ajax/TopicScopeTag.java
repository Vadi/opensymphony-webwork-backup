package com.opensymphony.webwork.views.jsp.ui.ajax;

import com.opensymphony.webwork.views.jsp.ui.AbstractUITag;
import com.opensymphony.webwork.components.ajax.JavascriptEmitter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import java.util.*;
import java.io.IOException;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

/**
 * This is a body tag that surrounds other Ajax components/tags that need to generate javascript on
 * a per-component (class) or per-instance basis.  This will be common for components/tags that
 * utilize the dojo event framework.
 *
 * @author		Ian Roughley
 * @version		$Id$
 */
public class TopicScopeTag extends AbstractUITag {

    final public static String OPEN_TEMPLATE = "topicscope";
    final public static String TEMPLATE = "topicscope-close";

    private Map jsEmitters = new HashMap();
    private List jsInstances = new ArrayList();

    private static final Log LOG = LogFactory.getLog(TopicScopeTag.class);

    protected String getDefaultTemplate() {
        return TEMPLATE;
    }

    public String getDefaultOpenTemplate() {
        return OPEN_TEMPLATE;
    }

    /**
     * @param emitter emitter adds a component emitter to the list emitters contained within this tag
     */
    public void addEmitter( JavascriptEmitter emitter ) {
        jsEmitters.put( emitter.getComponentName(), emitter );
        jsInstances.add( emitter );
    }

    public int doEndTag() throws JspException {

        JspWriter out = pageContext.getOut();
        try {
            out.println("<script language=\"JavaScript\" type=\"text/javascript\">");
            out.println("<!--");

            out.println("// component JS");
            for( Iterator i=((Collection)jsEmitters.values()).iterator(); i.hasNext(); ) {
                JavascriptEmitter emitter = (JavascriptEmitter)i.next();
                emitter.emittJavascript(pageContext);
            }

            out.println("// instance JS");
            for( int i=0; i<jsInstances.size(); i++ ) {
                ((JavascriptEmitter)jsInstances.get(i)).emittInstanceConfigurationJavascript(pageContext);
            }

            out.println("-->");
            out.println("</script>");

        } catch (IOException e) {
            LOG.error( "Error writting JS to pageContext.out", e );
        }
        return super.doEndTag();
    }

}
