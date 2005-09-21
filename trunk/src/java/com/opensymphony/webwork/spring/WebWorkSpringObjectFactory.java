package com.opensymphony.webwork.spring;

import com.opensymphony.webwork.config.Configuration;
import com.opensymphony.webwork.util.ObjectFactoryInitializable;
import com.opensymphony.xwork.spring.SpringObjectFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;

/**
 * User: plightbo
 * Date: Sep 20, 2005
 * Time: 6:41:14 PM
 */
public class WebWorkSpringObjectFactory extends SpringObjectFactory implements ObjectFactoryInitializable {
    private static final Log log = LogFactory.getLog(WebWorkSpringObjectFactory.class);

    public void init(ServletContext servletContext) {
        ApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        if (appContext == null) {
            // uh oh! looks like the lifecycle listener wasn't installed. Let's inform the user
            String message = "********** FATAL ERROR STARTING UP SPRING-WEBWORK INTEGRATION **********\n" +
                    "Looks like the Spring listener was not configured in for your web app! \n" +
                    "Nothing will work until WebApplicationContextUtils returns a valid ApplicationContext.\n" +
                    "You might need to add the following to web.xml: \n" +
                    "    <listener>\n" +
                    "        <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>\n" +
                    "    </listener>";
            log.fatal(message);
            return;
        }

        this.setApplicationContext(appContext);

        String autoWire = Configuration.getString("webwork.spring.objectFactory.autoWire");
        int type = -1;
        if ("name".equals(autoWire)) {
            type = AutowireCapableBeanFactory.AUTOWIRE_BY_NAME;
        } else if ("type".equals(autoWire)) {
            type = AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE;
        } else if ("auto".equals(autoWire)) {
            type = AutowireCapableBeanFactory.AUTOWIRE_AUTODETECT;
        } else if ("constructor".equals(autoWire)) {
            type = AutowireCapableBeanFactory.AUTOWIRE_CONSTRUCTOR;
        }
        this.setAutowireStrategy(type);
    }
}
