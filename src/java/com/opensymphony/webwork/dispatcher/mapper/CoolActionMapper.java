package com.opensymphony.webwork.dispatcher.mapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.HashMap;
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
 * <ul><tt>http://HOST/ACTION_NAME/ACTION_NAME/PARAM_VALUE1/PARAM_NAME2/PARAM_VALUE2</tt></ul>
 * <p/>
 * Suppose for example we would like to display some articles by id at using the following URL sheme:
 * <p/>
 * <ul><tt>http://HOST/article/ID</tt></ul>
 * <p/>
 * All we would have to do is to map the <tt>/article/*</tt> to this servlet and declare in WebWork an
 * action named <tt>article</tt>. This action would set its <tt>article</tt> parameter <tt>ID</tt>.
 *
 * @author <a href="mailto:cameron@datacodex.net">Cameron Braid</a>
 * @author <a href="mailto:jerome.bernard@xtremejava.com">Jerome Bernard</a>
 * @author Patrick Lightbody
 */
public class CoolActionMapper implements ActionMapper {
    protected static final Log LOG = LogFactory.getLog(CoolActionMapper.class);

    public ActionMapping getMapping(HttpServletRequest request) {
        String uri = request.getServletPath();

        String actionName = uri.substring(1, uri.indexOf('/', 1));
        HashMap parameters = new HashMap();
        try {
            StringTokenizer st = new StringTokenizer(uri.substring(uri.indexOf('/', 1)), "/");
            boolean isNameTok = true;
            String paramName = null;
            String paramValue = null;

            // check if we have the first parameter name
            if ((st.countTokens() % 2) != 0) {
                isNameTok = false;
                paramName = actionName;
            }

            while (st.hasMoreTokens()) {
                if (isNameTok) {
                    paramName = URLDecoder.decode(st.nextToken());
                    isNameTok = false;
                } else {
                    paramValue = URLDecoder.decode(st.nextToken());

                    if ((paramName != null) && (paramName.length() > 0)) {
                        parameters.put(paramName, paramValue);
                    }

                    isNameTok = true;
                }
            }
        } catch (Exception e) {
            LOG.warn(e);
        }

        return new ActionMapping(actionName, "", parameters);
    }

    public String getUriFromActionMapping(ActionMapping mapping) {
        return "/" + mapping.getName();
    }
}
