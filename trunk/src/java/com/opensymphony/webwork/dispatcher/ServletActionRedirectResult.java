package com.opensymphony.webwork.dispatcher;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.opensymphony.webwork.dispatcher.mapper.ActionMapper;
import com.opensymphony.webwork.dispatcher.mapper.ActionMapperFactory;
import com.opensymphony.webwork.dispatcher.mapper.ActionMapping;
import com.opensymphony.webwork.views.util.UrlHelper;

import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.config.entities.ActionConfig;
import com.opensymphony.xwork.config.entities.ResultConfig;

/**
 * <!-- START SNIPPET: description -->
 *
 * This result uses the {@link ActionMapper} provided by the {@link ActionMapperFactory} to redirect the browser to a
 * URL that invokes the specified action and (optional) namespace. This is better than the {@link ServletRedirectResult}
 * because it does not require you to encode the URL patterns processed by the {@link ActionMapper} in to your xwork.xml
 * configuration files. This means you can change your URL patterns at any point and your application will still work.
 * It is strongly recommended that if you are redirecting to another action, you use this result rather than the
 * standard redirect result.
 * 
 * <p/>
 * 
 * To pass parameters, the &lt;param&gt; ... &lt;/param&gt; tag. The following parameters will not be 
 * passable becuase they are part of the config param for this particular result.
 * 
 * <ul>
 * 	<li>actionName</li>
 *  <li>namespace</li>
 *  <li>method</li>
 *  <li>encode</li>
 *  <li>parse</li>
 *  <li>location</li>
 *  <li>prependServletContext</li>
 * </ul>
 * 
 * See examples below for an example of how request parameters could be passed in.
 *
 * <!-- END SNIPPET: description -->
 *
 * <b>This result type takes the following parameters:</b>
 *
 * <!-- START SNIPPET: params -->
 *
 * <ul>
 *
 * <li><b>actionName (default)</b> - the name of the action that will be redirect to</li>
 *
 * <li><b>namespace</b> - used to determine which namespace the action is in that we're redirecting to . If namespace is
 * null, this defaults to the current namespace</li>
 *
 * </ul>
 *
 * <!-- END SNIPPET: params -->
 *
 * <b>Example:</b>
 *
 * <pre><!-- START SNIPPET: example -->
 * &lt;package name="public" extends="webwork-default"&gt;
 *     &lt;action name="login" class="..."&gt;
 *         &lt;!-- Redirect to another namespace --&gt;
 *         &lt;result type="redirect-action"&gt;
 *             &lt;param name="actionName"&gt;dashboard&lt;/param&gt;
 *             &lt;param name="namespace"&gt;/secure&lt;/param&gt;
 *         &lt;/result&gt;
 *     &lt;/action&gt;
 * &lt;/package&gt;
 *
 * &lt;package name="secure" extends="webwork-default" namespace="/secure"&gt;
 *     &lt;-- Redirect to an action in the same namespace --&gt;
 *     &lt;action name="dashboard" class="..."&gt;
 *         &lt;result&gt;dashboard.jsp&lt;/result&gt;
 *         &lt;result name="error" type="redirect-action&gt;error&lt;/result&gt;
 *     &lt;/action&gt;
 *
 *     &lt;action name="error" class="..."&gt;
 *         &lt;result&gt;error.jsp&lt;/result&gt;
 *     &lt;/action&gt;
 * &lt;/package&gt;
 * 
 * &lt;package name="passingRequestParameters" extends="webwork-default" namespace="/passingRequestParameters"&gt;
 * 	  &lt;-- Pass parameters (reportType, width and height) --&gt;
 *    &lt;!-- 
 *    The redirect-action url generated will be : 
 *    /genReport/generateReport.action?reportType=pie&width=100&height=100
 *    --&gt;
 *    &lt;action name="gatherReportInfo" class="..."&gt;
 *       &lt;result name="showReportResult" type="redirect-action"&gt;
 *       	&lt;param name="actionName"&gt;generateReport&lt;/param&gt;
 *          &lt;param name="namespace="&gt;/genReport&lt;/param&gt;
 *          &lt;param name="reportType"&gt;pie&lt;/param&gt;
 *          &lt;param name="width"&gt;100&lt;/param&gt;
 *          &lt;param name="height"&gt;100&lt;/param&gt;
 *       &lt;/result&gt;
 *    &lt;/action&gt;
 * &lt;/package&gt;
 * 
 * <!-- END SNIPPET: example --></pre>
 *
 * @see ActionMapper
 */
public class ServletActionRedirectResult extends ServletRedirectResult {
	
	private static final long serialVersionUID = -6126889518132056284L;

	public static final String DEFAULT_PARAM = "actionName";

    protected String actionName;
    protected String namespace;
    protected String method;
    
    protected List prohibitedResultParam = Arrays.asList(new String[] { 
    		DEFAULT_PARAM, "namespace", "method", "encode", "parse", "location", 
    		"prependServletContext" });

    public void execute(ActionInvocation invocation) throws Exception {
        actionName = conditionalParse(actionName, invocation);
        if (namespace == null) {
            namespace = invocation.getProxy().getNamespace();
        } else {
            namespace = conditionalParse(namespace, invocation);
        }
        if (method == null) {
        	method = "";
        }
        else {
        	method = conditionalParse(method, invocation);
        }
        
        Map requestParameters = new HashMap();
        ResultConfig resultConfig = (ResultConfig) invocation.getProxy().getConfig().getResults().get(
        		invocation.getResultCode());
        Map resultConfigParams = resultConfig.getParams();
        for (Iterator i = resultConfigParams.entrySet().iterator(); i.hasNext(); ) {
        	Map.Entry e = (Map.Entry) i.next();
        	if (! prohibitedResultParam.contains(e.getKey())) {
        		requestParameters.put(e.getKey().toString(), 
        				e.getValue() == null ? "": 
        					conditionalParse(e.getValue().toString(), invocation));
        	}
        }
        
        ActionMapper mapper = ActionMapperFactory.getMapper();
        StringBuffer tmpLocation = new StringBuffer(mapper.getUriFromActionMapping(new ActionMapping(actionName, namespace, method, null)));
        UrlHelper.buildParametersString(requestParameters, tmpLocation, "&");
        
        location = tmpLocation.toString();

        super.execute(invocation);
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public void setMethod(String method) {
    	this.method = method;
    }
}