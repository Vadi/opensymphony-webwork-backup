/*
 * WebWork, Web Application Framework
 *
 * Distributable under Apache license.
 * See terms of license at opensource.org
 */
package webwork.config;

import org.apache.commons.logging.*;

import java.util.Iterator;
import java.util.Locale;
import java.util.StringTokenizer;

import webwork.config.DefaultConfiguration;
import webwork.util.ClassLoaderUtils;

/**
 * Access to WebWork configuration. Use the constants to access defined configuration settings.
 *
 * The implementation is pluggable. The default implementation is to use the properties file
 * "webwork.properties", which must be in classpath. To install a new implementation subclass this
 * class and call setConfiguration() with it.
 *
 *	@author Rickard Öberg (rickard@middleware-company.com)
 *	@version $Revision$
 *
 */
public abstract class Configuration
{
   // Static --------------------------------------------------------
   static Configuration configurationImpl;
   static Configuration defaultImpl;
   static Locale locale; // Cached locale

   /**
    * Get a named setting.
    */
   public static Object get(String aName)
      throws IllegalArgumentException
   {
      Object val = getConfiguration().getImpl(aName);
      return val;
   }

   /**
    * Get a named setting as a string.
    */
   public static String getString(String aName)
      throws IllegalArgumentException
   {
      String val = get(aName).toString();
      return val;
   }

   /**
    * Set a named setting
    */
   public static void set(String aName, Object aValue)
      throws IllegalArgumentException, UnsupportedOperationException
   {
      getConfiguration().setImpl(aName, aValue);
   }

   /**
    * List setting names
    */
   public static Iterator list()
   {
      return getConfiguration().listImpl();
   }

   /**
    * WebWork locale accessor
    */
   public static Locale getLocale()
   {
      if (locale == null)
      {
         try
         {
            StringTokenizer localeTokens = new StringTokenizer(getString("webwork.locale"),"_");
            String lang = null;
            String country = null;
            if (localeTokens.hasMoreTokens())
               lang = localeTokens.nextToken();
            if (localeTokens.hasMoreTokens())
               country = localeTokens.nextToken();
            locale = new Locale(lang, country);
         } catch (IllegalArgumentException e)
         {
            // Default
            locale = Locale.getDefault();
         }
      }

      return locale;
   }

   /**
    * Get the current configuration implementation.
    */
   public static Configuration getConfiguration()
   {
      return configurationImpl == null ? getDefaultConfiguration() : configurationImpl;
   }

   /**
    * Set the current configuration implementation. Can only be called once.
    */
   public static void setConfiguration(Configuration aConfig)
      throws IllegalStateException
   {
      if (configurationImpl != null)
         throw new IllegalStateException("May only set configuration implementation once");

      configurationImpl = aConfig;
      locale = null; // Reset cached locale
   }

   /**
    * Get a named setting.
    *
    * @throws IllegalArgumentException if there is no configuration parameter with the given name.
    */
   public Object getImpl(String aName)
      throws IllegalArgumentException
   {
      return null;
   }

   /**
    * Set a named setting
    */
   public void setImpl(String aName, Object aValue)
      throws IllegalArgumentException, UnsupportedOperationException
   {
      throw new UnsupportedOperationException("This configuration does not support updating a setting");
   }

   /**
    * List setting names
    */
   public Iterator listImpl()
   {
      throw new UnsupportedOperationException("This configuration does not support listing the settings");
   }

   private static Configuration getDefaultConfiguration()
   {
      if (defaultImpl == null)
      {
         // Create bootstrap implementation
         defaultImpl = new DefaultConfiguration();

         // Create default implementation
         try
         {
            String className = getString("webwork.configuration");
            if (!className.equals(defaultImpl.getClass().getName()))
            {
               try
               {
                  defaultImpl = (Configuration)ClassLoaderUtils.loadClass(className, Configuration.class).newInstance();
               } catch (Exception e)
               {
                  LogFactory.getLog(Configuration.class).error("Could not instantiate configuration", e);
               }
            }
         } catch (IllegalArgumentException ex)
         {
            LogFactory.getLog(Configuration.class).error("No default configuration defined", ex);
         }
      }

      return defaultImpl;
   }
}
