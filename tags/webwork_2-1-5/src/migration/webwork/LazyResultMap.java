package webwork;

import com.opensymphony.webwork.config.Configuration;
import com.opensymphony.webwork.dispatcher.ServletDispatcherResult;
import com.opensymphony.xwork.ActionChainResult;
import com.opensymphony.xwork.config.entities.ResultConfig;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import webwork.dispatcher.ViewMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * LazyResultMap
 *
 * @author Jason Carreira
 *         Date: Nov 6, 2003 8:40:40 PM
 */
class LazyResultMap extends HashMap {
    private static String actionSuffix;
    private static Log LOG = LogFactory.getLog(LazyResultMap.class);

    static {
        try {
            actionSuffix = "." + Configuration.getString("webwork.action.extension");
        } catch (IllegalArgumentException iae) {
            actionSuffix = ".action";
            LOG.warn("Unable to find \'webwork.action.extension\' property setting. Defaulting to \'action\'");
        }
    }

    private String actionName;
    private ViewMapping views;

    public LazyResultMap(String actionName, ViewMapping views) {
        this.actionName = actionName;
        this.views = views;
    }

    public Object get(Object key) {
        ResultConfig resultConfig = (ResultConfig) super.get(key);
        if (resultConfig == null) {
            String resultStr = (String) views.getView(actionName, (String) key);
            if (resultStr != null) {
                if (resultStr.endsWith(actionSuffix)) {
                    String actionName = resultStr.substring(0, resultStr.lastIndexOf(actionSuffix));
                    Map params = new HashMap();
                    params.put(ActionChainResult.DEFAULT_PARAM, actionName);
                    resultConfig = new ResultConfig((String) key, ActionChainResult.class, params);
                } else {
                    Map params = new HashMap();
                    params.put(ServletDispatcherResult.DEFAULT_PARAM, actionName);
                    resultConfig = new ResultConfig((String) key, ServletDispatcherResult.class, params);
                }
                put(key, resultConfig);
            }
        }
        return resultConfig;
    }
}
