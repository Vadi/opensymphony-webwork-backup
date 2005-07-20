package com.opensymphony.webwork.components.ajax;

import com.opensymphony.webwork.components.ClosingUIBean;
import com.opensymphony.xwork.util.OgnlValueStack;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

/**
 * User: plightbo
 * Date: Jul 20, 2005
 * Time: 7:23:22 AM
 */
public class TopicScope extends ClosingUIBean {
    private static final Log LOG = LogFactory.getLog(TopicScope.class);

    final public static String OPEN_TEMPLATE = "topicscope";
    final public static String TEMPLATE = "topicscope-close";

    protected Map jsEmitters = new HashMap();
    protected List jsInstances = new ArrayList();

    public TopicScope(OgnlValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

    public String getDefaultOpenTemplate() {
        return OPEN_TEMPLATE;
    }

    protected String getDefaultTemplate() {
        return TEMPLATE;
    }

    /**
     * @param emitter emitter adds a component emitter to the list emitters contained within this tag
     */
    public void addEmitter(JavascriptEmitter emitter) {
        jsEmitters.put(emitter.getComponentName(), emitter);
        jsInstances.add(emitter);
    }

    public void end(Writer writer) {

        try {
            writer.write("<script language=\"JavaScript\" type=\"text/javascript\">\n");
            writer.write("<!--\n");

            writer.write("// component JS\n");
            for (Iterator i = (jsEmitters.values()).iterator(); i.hasNext();) {
                JavascriptEmitter emitter = (JavascriptEmitter) i.next();
                emitter.emittJavascript(writer);
            }

            writer.write("// instance JS\n");
            for (int i = 0; i < jsInstances.size(); i++) {
                ((JavascriptEmitter) jsInstances.get(i)).emittInstanceConfigurationJavascript(writer);
            }

            writer.write("-->\n");
            writer.write("</script>\n");
        } catch (IOException e) {
            LOG.error("Error writting JS", e);
        }

        super.end(writer);
    }
}
