/*
 * WebWork, Web Application Framework
 *
 * Distributable under Apache license.
 * See terms of license at opensource.org
 */
package webwork.action.factory;

import webwork.action.Action;
import webwork.util.ClassLoaderUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Returns a loaded and instantiated action class instance.
 *
 * @author Rickard Öberg (rickard@middleware-company.com)
 * @version $Revision$
 */
public class JavaActionFactory
   extends ActionFactory
{
   // Attributes ----------------------------------------------------
   ClassLoader actionLoader;
   Map actionMapping = new HashMap();

   // HttpServlet overrides -----------------------------------------
   public JavaActionFactory()
   {
      // Choose classloader
      actionLoader = Thread.currentThread().getContextClassLoader();
   }

  /**
   * Returns a loaded and instantiated action class instance using a fully
   * qualified classname.
   *
   * @param   name classname of the action to be created
   * @return   get the action corresponding to the given Java class name
   * @exception   Exception
   */
   public Action getActionImpl(String name)
     throws Exception
   {
      // Check cache
      Class actionClass = (Class)actionMapping.get(name);

      // Find class using cached actionLoader, or else try Class.forName() as a backup
      if (actionClass == null)
      {
         try
         {
            actionClass = actionLoader.loadClass(name);
         } catch (Exception e)
         {
             try
             {
                 ClassLoaderUtils.loadClass(name, JavaActionFactory.class);
             } catch (Exception e2)
             {
                // Not found or could not be instantiated
                throw new IllegalArgumentException("Action '"+name+"' not found or could not be initialized: "+e2);
             }
         }

         // Put action class in cache
         actionMapping.put(name, actionClass);
      }

      try
      {
         return (Action)actionClass.newInstance();
      } catch (Exception e)
      {
         throw new IllegalArgumentException("Action '"+name+"' could not be instantiated:"+e);
      }
   }
}
