package com.opensymphony.webwork.dispatcher;

import com.opensymphony.webwork.dispatcher.mapper.ActionMapper;
import com.opensymphony.webwork.dispatcher.mapper.ActionMapperFactory;
import com.opensymphony.webwork.dispatcher.mapper.ActionMapping;
import com.opensymphony.xwork.ActionInvocation;

/**
 * User: patrick
 * Date: Sep 2, 2005
 * Time: 9:53:50 AM
 */
public class ServletActionRedirectResult extends ServletRedirectResult {
    public static final String DEFAULT_PARAM = "actionName";

    protected String actionName;
    protected String namespace;

    public void execute(ActionInvocation invocation) throws Exception {
        actionName = conditionalParse(actionName, invocation);
        if (namespace == null) {
            namespace = invocation.getProxy().getNamespace();
        } else {
            namespace = conditionalParse(namespace, invocation);
        }

        ActionMapper mapper = ActionMapperFactory.getMapper();
        location = mapper.getUriFromActionMapping(new ActionMapping(actionName, namespace, "", null));

        super.execute(invocation);
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }
}
