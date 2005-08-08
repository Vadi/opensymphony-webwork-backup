package com.opensymphony.webwork.views.freemarker.tags;

import com.opensymphony.webwork.components.Component;
import com.opensymphony.xwork.util.OgnlValueStack;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateTransformModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * User: plightbo
 * Date: Jul 18, 2005
 * Time: 8:00:43 PM
 */
public abstract class TagModel implements TemplateTransformModel {
    protected OgnlValueStack stack;
    protected HttpServletRequest req;
    protected HttpServletResponse res;

    public TagModel(OgnlValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        this.stack = stack;
        this.req = req;
        this.res = res;
    }

    public Writer getWriter(Writer writer, Map params) throws TemplateModelException, IOException {
        Component bean = getBean();
        params = convertParams(params);
        bean.copyParams(params);
        //bean.addAllParameters(params);
        bean.start(writer);
        return new CallbackWriter(bean, writer);
    }

    protected abstract Component getBean();

    private Map convertParams(Map params) {
        HashMap map = new HashMap(params.size());
        for (Iterator iterator = params.entrySet().iterator(); iterator.hasNext();) {
            Map.Entry entry = (Map.Entry) iterator.next();
            map.put(entry.getKey(), entry.getValue().toString());
        }
        return map;
    }

}
