/*
 * WebWork, Web Application Framework
 *
 * Distributable under Apache license.
 * See terms of license at opensource.org
 */
package webwork.action.standard;

import webwork.action.ActionSupport;
import webwork.action.ServletResponseAware;

import javax.servlet.http.HttpServletResponse;

/**
 *	Redirect to a given URL. Do not map any view to this action.
 *
 *	@see <related>
 *	@author Rickard Öberg (rickard@middleware-company.com)
 *	@version $Revision$
 */
public class Redirect
   extends ActionSupport
   implements ServletResponseAware
{
   // Attributes ----------------------------------------------------
   String url;
   HttpServletResponse response;

   // Static --------------------------------------------------------

   // Implements ServletResponseAware -------------------------------
   public void setServletResponse(HttpServletResponse aResponse)
   {
      response = aResponse;
   }

   // Public --------------------------------------------------------
   /**
    * URL to redirect to
    */
   public void setUrl(String aUrl)
   {
      url = aUrl;
   }

   public String getUrl()
   {
      return url;
   }

   // Action implementation -----------------------------------------
   /**
    * Redirect to URL
    */
   protected String doExecute()
      throws Exception
   {
      if (url == null)
        return ERROR;

      url = response.encodeUrl(url);
      response.sendRedirect(url);
      return NONE;
   }
}

