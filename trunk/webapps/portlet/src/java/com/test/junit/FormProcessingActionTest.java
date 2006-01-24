package com.test.junit;

import com.opensymphony.webwork.util.AttributeMap;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionProxy;
import com.opensymphony.xwork.ActionProxyFactory;
import com.test.FormProcessingAction;
import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Map;

public class FormProcessingActionTest extends TestCase {

    HashMap extraContext = new HashMap();

    protected void setUp() throws Exception {
        super.setUp();

        /*
        * If miss one of following parameters, execute() in FormProcessingAction
        * will be not executed and be by-passed, proxy.execute() return result is input.
        */

        Map parameterMap = new HashMap();
        parameterMap.put("file", "file");
        parameterMap.put("checkbox", "checkbox");
        parameterMap.put("radio", "checkbox");
        parameterMap.put("textarea", "checkbox");
        parameterMap.put("password", "password");
        parameterMap.put("select", "select");
        parameterMap.put("textfield", "textfield");
        extraContext.put(ActionContext.PARAMETERS, parameterMap);

        AttributeMap attrMap = new AttributeMap(extraContext);
        extraContext.put("attr", attrMap);
    }


    public void testSetFile() {
        try {
            FormProcessingAction action = execute();
            System.out.println(action.getFile());
            assertEquals("file", action.getFile());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testSetPassword() {
        try {
            FormProcessingAction action = execute();
            assertEquals("password", action.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testSetSelect() {
        try {
            FormProcessingAction action = execute();
            assertEquals("select", action.getSelect());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testSetTextfield() {
        try {
            FormProcessingAction action = execute();
            assertEquals("textfield", action.getTextfield());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Test Customer Method
     */
    public void testMyMethod() {

        try {
            ActionProxy proxy = ActionProxyFactory.getFactory().createActionProxy("", "formProcessing!myMethod", extraContext);
            proxy.setExecuteResult(false);
            System.out.println(proxy.execute());
            FormProcessingAction action = (FormProcessingAction) proxy.getAction();
            action.execute();
            assertEquals("fileHello", action.getFile());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private FormProcessingAction execute() throws Exception {
        ActionProxy proxy = ActionProxyFactory.getFactory().createActionProxy("", "formProcessing", extraContext);
        proxy.setExecuteResult(false);
        proxy.execute();
        return (FormProcessingAction) proxy.getAction();
    }

}
