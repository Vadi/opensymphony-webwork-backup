package com.opensymphony.webwork.validators;

import com.opensymphony.util.TextUtils;
import com.opensymphony.webwork.dispatcher.ServletDispatcher;
import com.opensymphony.webwork.views.util.TextUtil;
import com.opensymphony.xwork.*;
import com.opensymphony.xwork.config.entities.ActionConfig;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * User: plightbo
 * Date: Dec 11, 2004
 * Time: 6:17:58 PM
 */
public class ValidationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {
            String action = null;
            String namespace = null;
            HashMap params = null;
            res.setContentType("text/xml");
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = db.parse(req.getInputStream());

            Element r = doc.getDocumentElement();
            action = r.getAttribute("a");
            namespace = r.getAttribute("ns");
            params = new HashMap();
            NodeList ps = r.getElementsByTagName("p");
            for (int i = 0; i < ps.getLength(); i++) {
                Node n = ps.item(i);
                if (n.getNodeType() == Node.ELEMENT_NODE) {
                    Element p = (Element) n;
                    String name = p.getAttribute("n");
                    Node child = p.getFirstChild();
                    String value;
                    if (child != null) {
                        value = child.getNodeValue();
                        params.put(name, value);
                    }
                }
            }

            Action a = null;
            HashMap ctx = new HashMap();
            ctx.put(ActionContext.PARAMETERS, params);
            ctx.put(ServletDispatcher.SERVLET_CONFIG, getServletConfig());

            ValidatorActionProxy proxy = new ValidatorActionProxy(namespace, action, ctx);
            proxy.execute();
            a = proxy.getAction();

            PrintWriter out = res.getWriter();
            out.println("<?xml version=\"1.0\"?>");
            out.println("<errors>");

            if (a instanceof ValidationAware) {
                ValidationAware va = (ValidationAware) a;
                out.println("<actionErrors>");
                for (Iterator iter = va.getActionErrors().iterator(); iter.hasNext(); ) {
					String ae = (String) iter.next();
					// TODO xmlencode the error strings
					out.println("<errorMessage>" + ae + "</errorMessage>");
				}
                out.println("</actionErrors>");
				
                Map fe = va.getFieldErrors();
                for (Iterator iterator = fe.entrySet().iterator(); iterator.hasNext();) {
                    Map.Entry entry = (Map.Entry) iterator.next();
                    String name = (String) entry.getKey();
                    List errors = (List) entry.getValue();
                    out.println("<fieldErrors fieldName=\"" + name + "\">");
                    for (Iterator iterator1 = errors.iterator(); iterator1.hasNext();) {
                        String error = (String) iterator1.next();
    					// TODO xmlencode the error strings and field names
                        out.println("<errorMessage>" + error + "</errorMessage>");
                    }
                    out.println("</fieldErrors>");
                }
            }

            out.println("</errors>");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class ValidatorActionInvocation extends DefaultActionInvocation {
        protected ValidatorActionInvocation(ActionProxy proxy, Map extraContext) throws Exception {
            super(proxy, extraContext, true);
        }

        protected String invokeAction(Action action, ActionConfig actionConfig) throws Exception {
            return Action.NONE; // don't actually execute the action
        }
    }

    public static class ValidatorActionProxy extends DefaultActionProxy {
        protected ValidatorActionProxy(String namespace, String actionName, Map extraContext) throws Exception {
            super(namespace, actionName,extraContext,false);
        }

        protected void prepare() throws Exception {
            invocation = new ValidatorActionInvocation(this, extraContext);
        }
    }
}
