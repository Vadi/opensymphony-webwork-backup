package com.opensymphony.webwork.dispatcher;

import com.mockobjects.constraint.IsEqual;
import com.mockobjects.constraint.Constraint;
import com.mockobjects.constraint.IsAnything;
import com.mockobjects.dynamic.Mock;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

/**
 * @author Robert Dawson (robert@rojotek.com)
 */
public class SessionMapTest extends TestCase {
    private Mock sessionMock;
    private Mock requestMock;

    protected void setUp() throws Exception {
        sessionMock = new Mock(HttpSession.class);
        requestMock = new Mock(HttpServletRequest.class);
        requestMock.matchAndReturn("getSession", sessionMock.proxy());
    }

    public void testGetOnSessionMapUsesWrappedSessionsGetAttribute() throws Exception {
        Object value = new Object();
        sessionMock.expectAndReturn("getAttribute", new Constraint[]{new IsEqual("KEY")}, value);
        SessionMap sessionMap = new SessionMap((HttpServletRequest) requestMock.proxy());
        assertEquals("Expected the get using KEY to return the value object setup in the mockSession", value, sessionMap.get("KEY"));
        sessionMock.verify();
    }
    public void testPutOnSessionMapUsesWrappedSessionsSetsAttribute() throws Exception {
        Object value = new Object();
        sessionMock.expect("getAttribute",new Constraint[]{new IsAnything()});
        sessionMock.expect("setAttribute", new Constraint[]{new IsEqual("KEY"),new IsEqual(value)});
        SessionMap sessionMap = new SessionMap((HttpServletRequest) requestMock.proxy());
        sessionMap.put("KEY", value);
        sessionMock.verify();
    }

    /**
     * class that extends session map, making the values available in a local map -- useful
     * for confirming put and get calls in the superclass. ie useful for testing that the get is done before
     * putting new data into the map.
     */
    private class MockSessionMap extends SessionMap{
        public MockSessionMap(HttpServletRequest request) {
            super(request);
        }

        private Map map = new HashMap();
        public Object get(Object key) {
            return map.get(key);
        }

        public Object put(Object key, Object value) {
            Object originalValue = super.put(key, value);
            map.put(key,value); //put the value into our map after putting it in the superclass map to avoid polluting the get call.
            return originalValue;
        }
    }
    public void testPuttingObjectInMapReturnsNullForPreviouslyUnusedKey() throws Exception {
        Object value = new Object();
        sessionMock.expectAndReturn("getAttribute", new Constraint[]{new IsEqual("KEY")}, null);
        sessionMock.expect("setAttribute", new Constraint[]{new IsEqual("KEY"),new IsEqual(value)});
        SessionMap sessionMap = new SessionMap((HttpServletRequest) requestMock.proxy());
        assertNull("should be null, as the contract for Map says that put returns the previous value in the map for the key",sessionMap.put("KEY", value));
        sessionMock.verify();
    }

    public void testPuttingObjectInMapReturnsPreviousValueForKey() throws Exception {
        Object originalValue = new Object();
        Object value = new Object();
        sessionMock.expectAndReturn("getAttribute", new Constraint[]{new IsEqual("KEY")}, null);
        sessionMock.expect("setAttribute", new Constraint[]{new IsEqual("KEY"),new IsEqual(originalValue)});
        sessionMock.expectAndReturn("getAttribute", new Constraint[]{new IsEqual("KEY")}, originalValue);
        sessionMock.expect("setAttribute", new Constraint[]{new IsEqual("KEY"),new IsEqual(value)});
        SessionMap sessionMap = new SessionMap((HttpServletRequest) requestMock.proxy());
        sessionMap.put("KEY", originalValue);
        assertEquals("should be the OriginalValue, as the contract for Map says that put returns the previous value in the map for the key",originalValue, sessionMap.put("KEY", value));
        sessionMock.verify();
    }

    public void testRemovePassThroughCallToRemoveAttribute() throws Exception {
        Object value = new Object();
        sessionMock.expectAndReturn("getAttribute", new Constraint[]{new IsEqual("KEY")}, value);
        sessionMock.expect("removeAttribute", new Constraint[]{new IsEqual("KEY")});
        SessionMap sessionMap = new SessionMap((HttpServletRequest) requestMock.proxy());
        assertEquals(value, sessionMap.remove("KEY"));
        sessionMock.verify();
    }
    public void testClearInvalidatesTheSession() throws Exception {
        sessionMock.expect("invalidate");
        MockSessionMap sessionMap = new MockSessionMap((HttpServletRequest) requestMock.proxy());
        sessionMap.clear();
        sessionMock.verify();
    }
}
