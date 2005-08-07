package com.opensymphony.webwork.views.freemarker.tags;

import com.opensymphony.webwork.components.Component;
import freemarker.template.TemplateModelException;
import freemarker.template.TransformControl;

import java.io.IOException;
import java.io.Writer;

/**
 * User: plightbo
 * Date: Jul 18, 2005
 * Time: 8:00:24 PM
 */
public class CallbackWriter extends Writer implements TransformControl {
    private Component bean;
    private Writer writer;

    public CallbackWriter(Component bean, Writer writer) {
        this.bean = bean;
        this.writer = writer;
    }

    public void close() throws IOException {
    }

    public void flush() throws IOException {
        writer.flush();
    }

    public void write(char cbuf[], int off, int len) throws IOException {
        writer.write(cbuf, off, len);
    }

    public int onStart() throws TemplateModelException, IOException {
        return EVALUATE_BODY;
    }

    public int afterBody() throws TemplateModelException, IOException {
        bean.end(this);

        return END_EVALUATION;
    }

    public void onError(Throwable throwable) throws Throwable {
        throw throwable;
    }

    public Component getBean() {
        return bean;
    }
}
