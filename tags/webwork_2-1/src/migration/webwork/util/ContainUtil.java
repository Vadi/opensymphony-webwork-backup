package webwork.util;

public class ContainUtil extends com.opensymphony.webwork.util.ContainUtil {
    public static boolean contains(Object obj1, Object obj2) {
        return com.opensymphony.webwork.util.ContainUtil.contains(obj1, obj1);
    }
}
