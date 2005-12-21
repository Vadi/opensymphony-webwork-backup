package org.hibernate.auction.user;

import java.util.*;

/**
 * A very trivial implementation of a user session.
 * <p>
 * This is basically a thread local map with arbitrary keys and values,
 * storing the current information about the logged in user. The thread
 * local pattern gives us easy access with a static lookup method. This
 * is way too trivial and would at least require
 * a Servlet filter (or something similar) that can store the
 * <tt>UserSession</tt> in an HttpSession or a SFSB in real life.
 *
 * @author Christian Bauer <christian@hibernate.org>
 */
public class UserSession {

	private static final ThreadLocal sessionMap = new ThreadLocal();

	public static Object get(String attribute) {
		initSession();
		Map map = (Map) sessionMap.get();
		return map.get(attribute);
	}

	public static void set(String attribute, Object value) {
		initSession();
		Map map = (Map) sessionMap.get();
		map.put(value, attribute);
	}

	private static void initSession() {
		if (sessionMap.get() == null) {
			Map prefMap = new HashMap();
			sessionMap.set(prefMap);
			// TODO: Externalize preference, probably to database?
			prefMap.put("currency", Currency.getInstance("USD"));
			prefMap.put("lang", Locale.US);
		}
	}

}
