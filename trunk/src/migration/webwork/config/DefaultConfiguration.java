/*
 * WebWork, Web Application Framework
 *
 * Distributable under Apache license.
 * See terms of license at opensource.org
 */
package webwork.config;

import org.apache.commons.logging.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

import com.opensymphony.webwork.config.Configuration;
import com.opensymphony.webwork.config.PropertiesConfiguration;
import com.opensymphony.webwork.config.DelegatingConfiguration;

/**
 * Default implementation of configuration. Creates and delegates to other configurations.
 *
 *	@author Rickard Öberg (rickard@middleware-company.com)
 *	@version $Revision$
 *
 */
public class DefaultConfiguration
   extends Configuration
{
   // Attributes ----------------------------------------------------
   Configuration config;

   // Constructors --------------------------------------------------
   public DefaultConfiguration()
   {
      // Create default implementations
      // Use default properties and webwork.properties
      ArrayList list = new ArrayList();
      try
      {
         list.add(new PropertiesConfiguration("webwork"));
      } catch (Exception e)
      {
         LogFactory.getLog(this.getClass()).warn("Could not find webwork.properties");
      }
      try
      {
         list.add(new PropertiesConfiguration("webwork/default"));
      } catch (Exception e)
      {
         LogFactory.getLog(this.getClass()).error("Could not find webwork/default.properties", e);
      }

      Configuration[] configList = new Configuration[list.size()];
      config = new DelegatingConfiguration((Configuration[])list.toArray(configList));

      // List of configurations to delegate to
      list = new ArrayList();

      // Add list of properties configurations
      StringTokenizer configFiles = new StringTokenizer((String)config.getImpl("webwork.configuration.properties"), ",");
      while (configFiles.hasMoreTokens())
      {
         String name = configFiles.nextToken();
         try
         {
            list.add(new PropertiesConfiguration(name));
         } catch (Exception e)
         {
            LogFactory.getLog(this.getClass()).error("Could not find "+name+".properties. Skipping");
         }
      }

      // Add list of XML action configurations
      configFiles = new StringTokenizer((String)config.getImpl("webwork.configuration.xml"), ",");
      while (configFiles.hasMoreTokens())
      {
         String name = configFiles.nextToken();
         try
         {
            list.add(new XMLActionConfiguration(name));
         } catch (IllegalArgumentException e)
         {
            LogFactory.getLog(this.getClass()).warn("Skipping XML action configuration for "+name+".xml");
         }catch (Exception e)
         {
            LogFactory.getLog(this.getClass()).error("Could not create XML action configuration", e);
         }
      }

      configList = new Configuration[list.size()];
      config = new DelegatingConfiguration((Configuration[])list.toArray(configList));
   }

   /**
    * Get a named setting.
    */
   public Object getImpl(String aName)
      throws IllegalArgumentException
   {
      // Delegate
      return config.getImpl(aName);
   }

   /**
    * Set a named setting
    */
   public void setImpl(String aName, Object aValue)
      throws IllegalArgumentException, UnsupportedOperationException
   {
      config.setImpl(aName, aValue);
   }

   /**
    * List setting names
    */
   public Iterator listImpl()
   {
      return config.listImpl();
   }
}
