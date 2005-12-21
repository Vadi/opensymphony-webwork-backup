
package org.hibernate.auction.web.filters;


import org.hibernate.auction.model.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;



public class AuthenticationFilter implements Filter {
    public static final String USER = "user";
    
   
    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        
    	HttpSession httpSession = ((HttpServletRequest) request).getSession(true);
    	User u = null; 
    			
    	u = (User)httpSession.getAttribute(USER);
    			
    	if(u != null)   	
    	    chain.doFilter(request, response);           	
    }
       


    /**
     * Does nothing.
     */
    public void init(FilterConfig filterConfig) throws ServletException {
    }

   
}
