package com.opensymphony.webwork.views.jsp.vui;

import com.opensymphony.webwork.util.ClassLoaderUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.InputStream;
import java.util.*;

/**
 * Utility class for handling multiple types of voice browsers
 *
 * @author Jeff Haynie (jhaynie@vocalocity.net)
 * @version $Revision$
 */
public final class BrowserSupport {
    static Log log = LogFactory.getLog(BrowserSupport.class);

    static Properties properties;
    static Map browserMap = Collections.synchronizedMap(new HashMap(2));
    static List browserMatch = Collections.synchronizedList(new ArrayList(2));

    static {
        InputStream in = null;
        Properties p = new Properties();

        try {
            in = ClassLoaderUtils.getResourceAsStream("/vxml.properties", BrowserSupport.class);
            if (in != null) {
                p.load(in);
                loadBrowserInfo(p);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception ignore) {
                }
                in = null;
            }
        }
    }

    static final class Match {
        String match;
        String userAgent;
        String templateDirectory;
    }

    /**
     * add a browser match pattern to use when trying to match a user-agent if the exact user agent string is specified.
     * The uaString can contain a wildcard, such as <tt>VocalOS/*</tt> which will match up to <tt>VocalOS/</tt>.
     */
    public static void addBrowserMatch(String uaString, String templateDir) {
        int w = uaString.indexOf("*");
        Match m = new Match();
        m.userAgent = uaString;
        if (w > 0) {
            m.match = uaString.substring(0, w);
        } else {
            m.match = null; // * - all/default
        }
        m.templateDirectory = templateDir;
        if (log.isDebugEnabled()) {
            log.debug("Loading useragent match: " + m.match + " [" + uaString + "] directory: " + templateDir);
        }
        browserMatch.add(m);
    }

    /**
     * load browser settings from properties file
     */
    public static void loadBrowserInfo(Properties p) {
        properties = p;

        String browsers = p.getProperty("browsers");
        if (browsers != null) {
            StringTokenizer tok = new StringTokenizer(browsers, ",");
            while (tok.hasMoreTokens()) {
                String token = tok.nextToken();
                String ua = p.getProperty(token + ".useragent");
                if (ua != null) {
                    String template = p.getProperty(token + ".templatedirectory");
                    if (template != null) {
                        if (log.isDebugEnabled()) {
                            log.debug("Loading useragent: " + token + " [" + ua + "] directory: " + template);
                        }
                        browserMap.put(ua, template);
                        if (ua.indexOf("*") > -1) {
                            addBrowserMatch(ua, template);
                        }
                    }
                }
            }
        }
    }

    /**
     * for a given user agent, return a near match if any
     */
    public static String getTemplateDirectoryFromMatch(String userAgent) {
        Iterator i = browserMatch.iterator();
        Match dm = null;
        while (i.hasNext()) {
            Match m = (Match) i.next();
            if (m.match == null) {
                dm = m; // default
                continue;
            }
            if (userAgent.startsWith(m.match)) {
                return m.templateDirectory;
            }
        }
        return (dm == null ? null : dm.templateDirectory);
    }

    /**
     * for a given user agent, return a vxml browser template directory
     */
    public static String getBrowserTemplateDirectory(String userAgent) {
        String tempDir = (String) browserMap.get(userAgent);
        if (tempDir == null) {
            tempDir = getTemplateDirectoryFromMatch(userAgent);
            if (tempDir == null) {
            }
        }
        return (tempDir == null ? "/template/vxml/" : tempDir);
    }
}