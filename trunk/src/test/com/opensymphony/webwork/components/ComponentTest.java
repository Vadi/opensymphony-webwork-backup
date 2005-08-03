package com.opensymphony.webwork.components;

import com.opensymphony.webwork.WebWorkTestCase;
import com.opensymphony.xwork.util.OgnlValueStack;

/**
 * User: plightbo
 * Date: Aug 3, 2005
 * Time: 5:21:47 AM
 */
public class ComponentTest extends WebWorkTestCase {
    public void testTranslateVariables() {
        OgnlValueStack stack = new OgnlValueStack();

        String s = Component.translateVariables("foo: %{{1, 2, 3}}, bar: %{1}", stack);
        assertEquals("foo: [1, 2, 3], bar: 1", s);

        s = Component.translateVariables("foo: %{#{1 : 2, 3 : 4}}, bar: %{1}", stack);
        assertEquals("foo: {1=2, 3=4}, bar: 1", s);

        s = Component.translateVariables("foo: 1}", stack);
        assertEquals("foo: 1}", s);

        s = Component.translateVariables("foo: {1}", stack);
        assertEquals("foo: {1}", s);

        s = Component.translateVariables("foo: %{1", stack);
        assertEquals("foo: %{1", s);
    }
}
