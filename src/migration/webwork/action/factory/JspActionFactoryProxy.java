/*
 * WebWork, Web Application Framework
 *
 * Distributable under Apache license.
 * See terms of license at opensource.org
 */
package webwork.action.factory;

import webwork.action.Action;
import webwork.action.standard.JSP;

/**
 * Obtains the JSP action if the action suffix is <code>".jsp"</code>.
 *
 * @author Rickard Öberg (rickard@middleware-company.com)
 * @version $Revision$
 */
public class JspActionFactoryProxy
   extends ActionFactoryProxy
{
   // Attributes ----------------------------------------------------

   // Constructors --------------------------------------------------
   public JspActionFactoryProxy(ActionFactory aFactory)
   {
      super(aFactory);
   }

   // ActionFactory overrides ---------------------------------------
  /**
   * If the suffix of the action is <code>".jsp"</code>, return the JSP action.
   *
   * @param   aName
   * @return   the JSP-action or action corresponding to the given name
   * @exception   Exception
   */
   public Action getActionImpl(String aName)
     throws Exception
   {
      // Check for scripting extension
      if (aName.endsWith(".jsp"))
      {
         String jspName = aName.substring(0,aName.length()-4).replace('.','/')+".jsp";
         JSP jsp = (JSP)ActionFactory.getAction("JSP");
         jsp.setPage(jspName);

         if (jsp.getPage() == null)
            throw new IllegalArgumentException("JSP '"+aName+"' does not exist");

         return jsp;
      } else
      {
         return getNextFactory().getActionImpl(aName);
      }
   }
}
