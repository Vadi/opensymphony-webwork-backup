/*
 * WebWork, Web Application Framework
 *
 * Distributable under Apache license.
 * See terms of license at opensource.org
 */
package webwork.config;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Delegating implementation of configuration. Delegates to a list of other configurations.
 *
 *	@author Rickard Öberg (rickard@middleware-company.com)
 *	@version $Revision$
 *
 */
public class DelegatingConfiguration
   extends Configuration
{
   // Attributes ----------------------------------------------------
   Configuration[] configList;

   // Constructors --------------------------------------------------
   public DelegatingConfiguration(Configuration[] aConfigList)
   {
      configList = aConfigList;
   }

   /**
    * Get a named setting.
    */
   public Object getImpl(String aName)
      throws IllegalArgumentException
   {
      // Delegate to the other configurations
      IllegalArgumentException e = null;
      for (int i = 0; i < configList.length; i++)
      {
         try
         {
            return configList[i].getImpl(aName);
         } catch (IllegalArgumentException ex)
         {
            e = ex;
            // Try next config
         }
      }
      throw e;
   }

   /**
    * Set a named setting
    */
   public void setImpl(String aName, Object aValue)
      throws IllegalArgumentException, UnsupportedOperationException
   {
      // Determine which config to use by using get
      // Delegate to the other configurations
      IllegalArgumentException e = null;
      for (int i = 0; i < configList.length; i++)
      {
         try
         {
            configList[i].getImpl(aName);

            // Found it, now try setting
            configList[i].setImpl(aName, aValue);

            // Worked, now return
            return;
         } catch (IllegalArgumentException ex)
         {
            e = ex;
            // Try next config
         }
      }
      throw e;
   }

   /**
    * List setting names
    */
   public Iterator listImpl()
   {
      boolean workedAtAll = false;

      ArrayList settingList = new ArrayList();
      UnsupportedOperationException e = null;
      for (int i = 0; i < configList.length; i++)
      {
         try
         {
            Iterator list = configList[i].listImpl();
            while (list.hasNext())
            {
               settingList.add(list.next());
            }
            workedAtAll = true;
         } catch (UnsupportedOperationException ex)
         {
            e = ex;
            // Try next config
         }
      }

      if (!workedAtAll)
         throw e == null ? new UnsupportedOperationException() : e;
      else
         return settingList.iterator();
   }
}
