/*
 * WebWork, Web Application Framework
 *
 * Distributable under Apache license.
 * See terms of license at opensource.org
 */
package webwork.action.standard;

import webwork.action.ActionSupport;
import webwork.action.ServletRequestAware;

import javax.servlet.http.HttpServletRequest;
import java.util.StringTokenizer;

/**
 *  A utility class for extracting browser information from the client.
 *
 *  @author Henrik Mattsson (henrik.mattsson@sublime.se)
 *  @author Rickard Öberg (rickard@middleware-company.com)
 *  @version $Revision$
 */
public class ClientInfo
  extends ActionSupport
  implements java.io.Serializable, ServletRequestAware
{
   // Attributes ----------------------------------------------------
   // Properties
   private String browser = "Unknown";
   private float version;

   // HTTP_* data
   private String httpUserAgent = "";
   private String httpAccept = "";

   // Public --------------------------------------------------------
   public void setUserAgent(String val)
   {
      httpUserAgent = val;
   }

   public void setAccept(String val)
   {
      httpAccept = val;
   }

   public String getBrowser()
   {
      return browser;
   }

   public float getVersion()
   {
      return version;
   }

   /**
   *  Check whether or not the browser supports a certain type
   *  of data. (jpeg/wbmp/etc)
   */
   public boolean supportsType(String key)
   {
      return httpAccept.indexOf(key) != -1 || httpAccept.indexOf("*/*") != -1;
   }

   // ServletRequestAware implementation -----------------------------------
   public void setServletRequest(HttpServletRequest request)
   {
      if (request.getHeader("User-Agent") != null)
          setUserAgent(request.getHeader("User-Agent"));
      if (request.getHeader("Accept") != null)
          setAccept(request.getHeader("Accept"));
   }

   /**
   *  Set up properties
   *
   *  Currently supports the version format of the following browsers:
   *      - Microsoft Internet Explorer
   *      - Netscape
   *      - Opera
   *      - Lynx
   */
   protected String doExecute() throws Exception
   {
      StringTokenizer st = new StringTokenizer(httpUserAgent.toLowerCase());

      // Set browser name
      if(httpUserAgent.indexOf("Mozilla") != -1)
      {
         this.browser = "mozilla";
      }
      if(httpUserAgent.indexOf("MSIE") != -1)
      {
         this.browser = "msie";
      }
      if(httpUserAgent.indexOf("Opera") != -1)
      {
         this.browser = "opera";
      }
      if(httpUserAgent.indexOf("Lynx") != -1)
      {
         this.browser = "lynx";
      }

      /*
      * Get browser version, the browser version strings varies depending
      * on the browser.
      */
      String versionString = "0.0"; // String representation of version no
      for(int tokenCount = 0; st.hasMoreTokens(); tokenCount++)
      {
         String token = st.nextToken();

         switch (tokenCount)
         {
            // mozilla and lynx use the first token for storing the version
            case 0:
            {
               int start = token.indexOf("/") + 1;
               if(this.browser.equals("mozilla"))
               {
                  versionString = token.substring(start);
               } else if(this.browser.equals("lynx"))
               {
                  // end pos on the other hand..
                  int end  = token.indexOf(".",start + 3);
                  if(token.indexOf("r",start) < end || end == -1)
                  {
                     end = token.indexOf("r",start);
                  }

                  if(end == -1)
                  {
                     end = start + 3;
                  }

                  versionString = token.substring(start,end);
               }
               break;
            }

            // msie uses the fourth token for storing version no.
            case 3:
            {
               if(this.browser.equals("msie"))
               {
                  int end = token.length() - 1;
                  versionString = token.substring(0, end);
               }
               break;
            }

            // opera uses the ninth token for storing version no.
            case 8:
            {
               if(this.browser.equals("opera"))
               {
                  versionString = token;
               }
               break;
            }
         }
      }

      // convert versionString to a float
      Float versionFloat;
      try
      {
         versionFloat = new Float(versionString);
      }catch(NumberFormatException nfe)
      {
         versionFloat = new Float(-10);
      }

      if(!versionFloat.isNaN() && !versionFloat.isInfinite())
      {
         this.version = versionFloat.floatValue();
      }
      else
      {
         this.version = (float) -11.0;
      }

      return SUCCESS;
   }
}
