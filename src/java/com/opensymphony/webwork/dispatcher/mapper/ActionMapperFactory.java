package com.opensymphony.webwork.dispatcher.mapper;

import com.opensymphony.webwork.config.Configuration;
import com.opensymphony.xwork.ObjectFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;

/**
 * User: plightbo
 * Date: Jun 20, 2005
 * Time: 6:56:45 PM
 */
public class ActionMapperFactory {
    protected static final Log LOG = LogFactory.getLog(ActionMapperFactory.class);

    private static final HashMap classMap = new HashMap();

    public static ActionMapper getMapper() {
        synchronized (classMap) {
            String clazz = (String) Configuration.get("webwork.mapper.class");
            try {
                ActionMapper mapper = (ActionMapper) classMap.get(clazz);
                if (mapper == null) {
                    mapper = (ActionMapper) ObjectFactory.getObjectFactory().buildBean(clazz);
                    classMap.put(clazz, mapper);
                }

                return mapper;
            } catch (Exception e) {
                String msg = "Could not create ActionMapper: WebWork will *not* work!";
                LOG.fatal(msg, e);
                throw new RuntimeException(msg, e);
            }
        }
    }
}
