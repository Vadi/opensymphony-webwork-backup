/*
 * WebWork, Web Application Framework
 *
 * Distributable under Apache license.
 * See terms of license at opensource.org
 */
package webwork.action.factory;

import org.apache.commons.logging.*;
import webwork.action.Action;
import webwork.util.ClassLoaderUtils;
import com.opensymphony.webwork.config.Configuration;

/**
 * Base action factory implementation that initializes the action factory
 * delegation, establishes the ActionContext and provides access to the
 * action factory.  Upon instantiation, this class will initialize the
 * action factory specified by the configuration property
 * <code>webwork.action.factory.</code>  If this property is not configured
 * or the specified class cannot be instantiated, the
 * {@link MigrationActionFactory} class will be instantiated.
 *
 *	@author Rickard Ã–berg (rickard@middleware-company.com)
 *	@version $Revision$
 */
public abstract class ActionFactory
{
   static ActionFactory defaultActionFactory;
   static ActionFactory actionFactoryImplementation;

   static
   {
      // Create default implementation
      try
      {
         String className = Configuration.getString("webwork.action.factory");
         try
         {
            defaultActionFactory = (ActionFactory)ClassLoaderUtils.loadClass(className, ActionFactory.class).newInstance();
         } catch (Exception e)
         {
             LogFactory.getLog(ActionFactory.class).error("Could not instantiate action factory:"+e);
             defaultActionFactory = new MigrationActionFactory();
         }
      } catch (IllegalArgumentException ex)
      {
         LogFactory.getLog(ActionFactory.class).error("Could not instantiate configuration:"+ex);
         defaultActionFactory = new MigrationActionFactory();
      }

   }

   /**
    * Returns the matching action found as the result of traversing the action
    * factory delegation chain.
    *
    * @param aName name of action to return
    * @return action located through the factory delegation chain
    */
   public static Action getAction(String aName)
      throws Exception
   {
      return getActionFactory().getActionImpl(aName);
   }

   /**
    * Returns the action factory implementation or the default action factory
    * if not available.
    *
    * @return action factory implementation
    */
   public static ActionFactory getActionFactory()
   {
      return actionFactoryImplementation == null ? defaultActionFactory : actionFactoryImplementation;
   }

   /**
    * Set the action factory implementation. Can only be called once.
    */
   public static void setActionFactory(ActionFactory aFactory)
      throws IllegalStateException
   {
//      new Throwable().printStackTrace();
      if (actionFactoryImplementation != null)
         throw new IllegalStateException("May only set action factory implementation once");

      actionFactoryImplementation = aFactory;
   }

   /**
    * Returns the action object for the specified action or
    * a matching action on the action factory delegation chain.
    *
    * @param aName name of action to check for a match
    */
   public abstract Action getActionImpl(String aName)
      throws Exception;
}
