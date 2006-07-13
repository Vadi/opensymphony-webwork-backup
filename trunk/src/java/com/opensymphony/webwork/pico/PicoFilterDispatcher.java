package com.opensymphony.webwork.pico;

import com.opensymphony.webwork.dispatcher.FilterDispatcher;
import com.opensymphony.webwork.WebWorkException;
import com.opensymphony.xwork.ObjectFactory;
import org.nanocontainer.nanowar.ServletRequestContainerLauncher;
import org.picocontainer.defaults.ObjectReference;
import org.picocontainer.gems.adapters.ThreadLocalReference;

import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 * User: patrick Date: Dec 22, 2005 Time: 4:14:10 PM
 */
public class PicoFilterDispatcher extends FilterDispatcher {
    private ObjectReference objectReference;

    public void init(FilterConfig config) throws ServletException {
        objectReference = new ThreadLocalReference();
        ObjectFactory.setObjectFactory(new PicoObjectFactory(objectReference));
        super.init(filterConfig);
    }

    protected Object beforeActionInvocation(HttpServletRequest request, ServletContext servletContext) {
        objectReference.set(request);

        ServletRequestContainerLauncher containerLauncher = new ServletRequestContainerLauncher(servletContext, request);
        try {
            containerLauncher.startContainer();
        } catch (ServletException e) {
            throw new WebWorkException("Could not start pico container", e);
        }

        return containerLauncher;
    }

    protected void afterActionInvocation(HttpServletRequest request, Object o, Object o1) {
        ServletRequestContainerLauncher containerLauncher = (ServletRequestContainerLauncher) o;
        containerLauncher.killContainer();

        objectReference.set(null);
    }
}
