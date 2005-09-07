package com.opensymphony.webwork.views.freemarker.tags;

import com.opensymphony.webwork.components.Component;
import com.opensymphony.xwork.util.OgnlValueStack;
import freemarker.ext.beans.CollectionModel;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateTransformModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
        Map basicParams = convertParams(params);
        bean.copyParams(basicParams);
        bean.addAllParameters(getComplexParams(params));
        bean.start(writer);
        return new CallbackWriter(bean, writer);
    }

    protected abstract Component getBean();

    private Map convertParams(Map params) {
        HashMap map = new HashMap(params.size());
        for (Iterator iterator = params.entrySet().iterator(); iterator.hasNext();) {
            Map.Entry entry = (Map.Entry) iterator.next();
            if (!(entry.getValue() instanceof CollectionModel)) {
                map.put(entry.getKey(), entry.getValue());
            }
        }
        return map;
    }

    private Map getComplexParams(Map params) {
        HashMap map = new HashMap(params.size());
        for (Iterator iterator = params.entrySet().iterator(); iterator.hasNext();) {
            Map.Entry entry = (Map.Entry) iterator.next();
            if (entry.getValue() instanceof CollectionModel) {
                map.put(entry.getKey(), entry.getValue());
            }
        }
        return map;
    }

}
