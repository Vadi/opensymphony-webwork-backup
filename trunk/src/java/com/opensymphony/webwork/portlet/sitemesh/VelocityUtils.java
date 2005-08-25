/*
 * Copyright (c) 2005 Opensymphony. All Rights Reserved.
 */
package com.opensymphony.webwork.portlet.sitemesh;

import com.opensymphony.webwork.portlet.velocity.VelocityManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;

import java.io.StringWriter;
import java.util.Map;

/**
 * @author <a href="mailto:hu_pengfei@yahoo.com.cn">Henry Hu </a>
 * @since 2005-7-18
 */
public class VelocityUtils {

    private static final Log log = LogFactory.getLog(com.opensymphony.webwork.portlet.sitemesh.VelocityUtils.class);

    public VelocityUtils() {
    }

    public static String getRenderedTemplate(String templateName, Map contextMap) {
        return getRenderedTemplate(templateName, ((Context) (new VelocityContext(contextMap))));
    }

    public static String getRenderedTemplate(String templateName, Context context) {
        try {
            return getRenderedTemplateWithoutSwallowingErrors(templateName, context);
        } catch (Exception e) {
            log.error("Error occurred rendering template: " + templateName, e);
            return "";
        }
    }

    public static String getRenderedTemplateWithoutSwallowingErrors(String templateName, Context context) throws Exception {
        Template template = getTemplate(templateName);
        StringWriter tempWriter = new StringWriter();
        template.merge(context, tempWriter);
        return tempWriter.toString();
    }

    public static Template getTemplate(String templateName) throws Exception {
        VelocityEngine velocityEngine = VelocityManager.getInstance().getVelocityEngine();

        /*
         * if (velocityEngine == null) { velocityEngine = new VelocityEngine();
         * Properties props = new Properties();
         * props.load(ClassLoaderUtils.getResourceAsStream("test-velocity.properties",
         * com.opensymphony.webwork.portlet.sitemesh.VelocityUtils.class));
         * props.list(System.out); velocityEngine.init(props); }
         */
        String encoding = "UTF-8";

        Template template = velocityEngine.getTemplate(templateName, encoding);
        return template;
    }

    public static String getRenderedContent(String templateContent, Map contextMap) {
        try {
            StringWriter tempWriter = new StringWriter();
            VelocityContext context = new VelocityContext(contextMap);

            VelocityManager.getInstance().getVelocityEngine().evaluate(context, tempWriter, "getRenderedContent", templateContent);
            return tempWriter.toString();
        } catch (Exception e) {
            log.error("Error occurred rendering template content", e);
            throw new InfrastructureException("Error occurred rendering template content", e);
        }
    }

}