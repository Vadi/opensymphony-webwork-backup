package com.test.junit;

import com.opensymphony.webwork.util.AttributeMap;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionProxy;
import com.opensymphony.xwork.ActionProxyFactory;
import com.test.HelloAction;
import junit.framework.TestCase;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HelloActionTest extends TestCase {

    HashMap extraContext = new HashMap();

    protected void setUp() throws Exception {
        super.setUp();

        Map parameterMap = new HashMap();
        parameterMap.put("userName", "HenryHu");
        extraContext.put(ActionContext.PARAMETERS, parameterMap);

        AttributeMap attrMap = new AttributeMap(extraContext);
        extraContext.put("attr", attrMap);
    }

    public void testGetUserName() {
        try {
            HelloAction action = execute();

            String userName = action.getUserName();
            assertEquals(userName, "HenryHu");
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void testGetNow() {
        try {
            HelloAction action = execute();

            Date now = action.getNow();
            System.out.println(now);
            assertNotNull(now);

        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void testExecute() {
        try {

            ActionProxy proxy = ActionProxyFactory.getFactory().createActionProxy("", "hello", extraContext);
            proxy.setExecuteResult(false);
            String result = proxy.execute();

            String userName = ((HelloAction) proxy.getAction()).getUserName();
            assertEquals(result, "success");
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }


    private HelloAction execute() throws Exception {
        ActionProxy proxy = ActionProxyFactory.getFactory().createActionProxy("", "hello", extraContext);
        proxy.setExecuteResult(false);
        proxy.execute();
        return (HelloAction) proxy.getAction();
    }

}