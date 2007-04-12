package com.opensymphony.webwork.dispatcher.mapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.webwork.RequestUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * A custom action mapper using the following format:
 * <p/>
 * <p/>
 * <ul><tt>http://HOST/ACTION_NAME/PARAM_NAME1/PARAM_VALUE1/PARAM_NAME2/PARAM_VALUE2</tt></ul>
 * <p/>
 * You can have as many parameters you'd like to use. Alternatively the URL can be shortened to the following:
 * <p/>
 * <ul><tt>http://HOST/ACTION_NAME/PARAM_VALUE1/PARAM_NAME2/PARAM_VALUE2</tt></ul>
 * <p/>
 * This is the same as:
 * <p/>
 * <ul><tt>http://HOST/ACTION_NAME/ACTION_NAME + "Id"/PARAM_VALUE1/PARAM_NAME2/PARAM_VALUE2</tt></ul>
 * <p/>
 * Suppose for example we would like to display some articles by id at using the following URL sheme:
 * <p/>
 * <ul><tt>http://HOST/article/Id</tt></ul>
 * <p/>
 * <p/>
 * Your action just needs a setArticleId() method, and requests such as /article/1, /article/2, etc will all map
 * to that URL pattern.
 * <p/>
 * <b>Note: The RestfulActionMapper is not supported if you use the (deprecated) ServletDispatcher!</b>
 *
 * @author <a href="mailto:cameron@datacodex.net">Cameron Braid</a>
 * @author <a href="mailto:jerome.bernard@xtremejava.com">Jerome Bernard</a>
 * @author Patrick Lightbody
 */
public class RestfulActionMapper implements ActionMapper {
    protected static final Log LOG = LogFactory.getLog(RestfulActionMapper.class);

    public ActionMapping getMapping(HttpServletRequest request) {
        String uri = RequestUtils.getServletPath(request);

        int nextSlash = uri.indexOf('/', 1);
        if (nextSlash == -1) {
            return null;
        }

        String actionName = uri.substring(1, nextSlash);
        HashMap parameters = new HashMap();
        try {
            StringTokenizer st = new StringTokenizer(uri.substring(nextSlash), "/");
            boolean isNameTok = true;
            String paramName = null;
            String paramValue;

            // check if we have the first parameter name
            if ((st.countTokens() % 2) != 0) {
                isNameTok = false;
                paramName = actionName + "Id";
            }

            while (st.hasMoreTokens()) {
                if (isNameTok) {
                    paramName = URLDecoder.decode(st.nextToken(), "UTF-8");
                    isNameTok = false;
                } else {
                    paramValue = URLDecoder.decode(st.nextToken(), "UTF-8");

                    if ((paramName != null) && (paramName.length() > 0)) {
                        parameters.put(paramName, paramValue);
                    }

                    isNameTok = true;
                }
            }
        } catch (Exception e) {
            LOG.warn(e);
        }

        return new ActionMapping(actionName, "", "", parameters);
    }

    public String getUriFromActionMapping(ActionMapping mapping) {
        String base = mapping.getNamespace() + mapping.getName();
        for (Iterator iterator = mapping.getParams().entrySet().iterator(); iterator.hasNext();) {
            Map.Entry entry = (Map.Entry) iterator.next();
            String name = (String) entry.getKey();
            if (name.equals(mapping.getName() + "Id")) {
                base = base + "/" + entry.getValue();
                break;
            }
        }

        return base;
    }
}
