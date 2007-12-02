package com.opensymphony.webwork.interceptor;

import com.opensymphony.webwork.WebWorkTestCase;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.mock.MockActionInvocation;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tmjee
 * @version $Date$ $Id$
 */
public class BooleanTweakerTest extends WebWorkTestCase {

    public void test() throws Exception {

        ActionContext actionContext = ActionContext.getContext();
        actionContext.setParameters(new HashMap());
        Map parameters = actionContext.getParameters();
        parameters.put(BooleanTweaker.PREFIX+"myProp1", "");
        parameters.put(BooleanTweaker.PREFIX+"myProp2", "");
        parameters.put(BooleanTweaker.PREFIX+"myProp3", "");
        parameters.put(BooleanTweaker.PREFIX+"myProp4", "");
        parameters.put("myProp3", "true");
        parameters.put("myProp2", "true");

        MockActionInvocation mockActionInvocation = new MockActionInvocation();
        mockActionInvocation.setInvocationContext(actionContext);


        BooleanTweaker tweaker = new BooleanTweaker();
        tweaker.intercept(mockActionInvocation);

        Map result = actionContext.getParameters();

        assertEquals(BooleanTweaker.FALSE, result.get("myProp1"));
        assertEquals("true", result.get("myProp2"));
        assertEquals("true", result.get("myProp3"));
        assertEquals(BooleanTweaker.FALSE, result.get("myProp4"));

    }
    
}
