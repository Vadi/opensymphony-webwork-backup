/*
 * WebWork, Web Application Framework
 *
 * Distributable under Apache license.
 * See terms of license at opensource.org
 */
package webwork.dispatcher;

import java.util.Map;

class ViewActionWrapper
{
   // Attributes ----------------------------------------------------
   private String actionName;
   private Map params;

   // Constructors --------------------------------------------------
   public ViewActionWrapper(String actionName)
   {
      this.actionName = actionName;
   }

   public ViewActionWrapper(String actionName, Map params)
   {
      this.actionName = actionName;
      this.params = params;
   }

	// Public --------------------------------------------------------
   public String getActionName()
   {
      return actionName;
   }

   public boolean hasParams()
   {
      return params != null;
   }
   
   public Map getParams()
   {
      return params;
   }
}
