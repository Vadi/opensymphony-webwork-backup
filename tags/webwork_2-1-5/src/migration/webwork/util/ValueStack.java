package webwork.util;

import com.opensymphony.xwork.util.OgnlValueStack;

public class ValueStack {
    OgnlValueStack stack = new OgnlValueStack();

    public ValueStack() {
    }

    public void pushValue(Object value) {
        stack.push(value);
    }


    public Object popValue() {
        return stack.pop();
    }

    public int size() {
        return stack.size();
    }

    public boolean isEmpty() {
        return (stack.size() == 0);
    }

    public boolean test(String expression) {
        Boolean b = (Boolean) stack.findValue(expression, Boolean.class);
        if (b != null) {
            return ((Boolean) b).booleanValue();
        }

        return false;
    }

    public Object findValue(String query) {
        return stack.findValue(query);
    }
}
