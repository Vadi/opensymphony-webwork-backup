/*
 * WebWork, Web Application Framework
 *
 * Distributable under Apache license.
 * See terms of license at opensource.org
 */
package webwork.config;

import webwork.util.ClassLoaderUtils;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;

/**
 * Access configuration from a properties file.
 *
 *	@author Rickard Öberg (rickard@middleware-company.com)
 *	@version $Revision$
 *
 */
public class PropertiesConfiguration
   extends Configuration
{
   // Attributes ----------------------------------------------------
   Properties settings;

   // Constructors --------------------------------------------------
   public PropertiesConfiguration(String aName)
   {
      settings = new Properties();

      URL settingsUrl = ClassLoaderUtils.getResource(aName+".properties", PropertiesConfiguration.class);

      if (settingsUrl == null)
      {
         throw new IllegalStateException(aName+".properties missing");
      }

      // Load settings
      try
      {
         settings.load(settingsUrl.openStream());
      } catch (IOException e)
      {
         throw new RuntimeException("Could not load "+aName+".properties:"+e);
      }
   }

   /**
    * Get a named setting.
    */
   public Object getImpl(String aName)
      throws IllegalArgumentException
   {
      Object setting = settings.get(aName);
      if (setting == null)
         throw new IllegalArgumentException("No such setting:"+aName);

      return setting;
   }

   public void setImpl(String aName, Object aValue)
   {
      settings.put(aName, aValue);
   }

   public Iterator listImpl()
   {
      return settings.keySet().iterator();
   }
}
