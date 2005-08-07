package com.opensymphony.webwork.views.freemarker.tags;

import com.opensymphony.webwork.components.Component;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateTransformModel;
import freemarker.template.TransformControl;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

/**
 * User: plightbo
 * Date: Aug 7, 2005
 * Time: 3:18:57 PM
 */
public class ParamModel implements TemplateTransformModel {
    public Writer getWriter(Writer writer, Map map) throws TemplateModelException, IOException {
        String name = map.get("name").toString();
        String value = null;
        if (map.containsKey("value")) {
            value = map.get("value").toString();
        }

        if (writer instanceof CallbackWriter) {
            CallbackWriter cw = (CallbackWriter) writer;
            Component bean = cw.getBean();
            return new ParamWriter(bean, name, value);
        } else {
            throw new RuntimeException("Param tag not used inside of valid element");
        }
    }

    public static class ParamWriter extends Writer implements TransformControl {
        StringWriter writer = new StringWriter();

        Component bean;
        String name;
        String value;

        public ParamWriter(Component bean, String name, String value) {
            this.bean = bean;
            this.name = name;
            this.value = value;
        }

        public void write(char cbuf[], int off, int len) throws IOException {
            writer.write(cbuf, off, len);
        }

        public void flush() throws IOException {
            writer.flush();
        }

        public void close() throws IOException {
            writer.close();
        }

        public int onStart() throws TemplateModelException, IOException {
            return EVALUATE_BODY;
        }

        public int afterBody() throws TemplateModelException, IOException {
            if (value != null) {
                bean.addParameter(name, value);
            } else {
                bean.addParameter(name, writer.toString());
            }

            return END_EVALUATION;
        }

        public void onError(Throwable throwable) throws Throwable {
            throw throwable;
        }
    }
}
