/*
 * WebWork, Web Application Framework
 *
 * Distributable under Apache license.
 * See terms of license at opensource.org
 */
package webwork.dispatcher;

import webwork.action.Action;
import webwork.config.Configuration;

import java.util.Map;
import java.util.Collections;
import java.util.HashMap;

/**
 * Caching view mapping. This mapping only delegates to other mapping implementations, and will cache
 * the result so that subsequent mappings for the same actionName/viewName tuple is retrieved from this
 * cache instead. Do not use this if you want to be able to update the mappings at runtime.
 *
 * @author Rickard Öberg (rickard@middleware-company.com)
 * @version $Revision$
 */
public class CachingViewMapping implements ViewMapping
{
   protected ViewMapping delegate;
   protected Map cache;
   protected final Object NULL_VIEW = new Object();

   // Constructors --------------------------------------------------
   public CachingViewMapping(ViewMapping aDelegate)
   {
      delegate = aDelegate;
      cache = Collections.synchronizedMap(new HashMap());
   }

   // ViewMapping implementation ------------------------------------
   /**
    * Get view corresponding to given action and view names
    */
   public Object getView(String anActionName, String aViewName)
   {
      Object view = cache.get(anActionName+"."+aViewName);      
      if (view == null)
      {
         view = delegate.getView(anActionName, aViewName);
         // If the view was null we cache it but use the NULL_VIEW object to be able to
         // distinguish it from a non-cached view
         if (view == null)
            view = NULL_VIEW;
         cache.put(anActionName+"."+aViewName,view);
      }
      if (view == NULL_VIEW)
         return null;
      else
         return view;
   }
}
