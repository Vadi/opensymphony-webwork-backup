package webwork.util;

import java.util.Iterator;

public class MakeIterator extends com.opensymphony.webwork.util.MakeIterator {

    public static Iterator convert(Object value) {
        return com.opensymphony.webwork.util.MakeIterator.convert(value);
    }

    public static boolean isIterable(Object object) {
        return com.opensymphony.webwork.util.MakeIterator.isIterable(object);
    }
}
