/*
 * WebWork, Web Application Framework
 *
 * Distributable under Apache license.
 * See terms of license at opensource.org
 */
package webwork.action.standard;

import com.ibm.bsf.BSFEngine;
import com.ibm.bsf.BSFException;
import com.ibm.bsf.BSFManager;
import com.ibm.bsf.util.IOUtils;
import webwork.action.ActionSupport;
import webwork.action.ParameterAware;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.AbstractMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 *	Script execution wrapper.
 *
 *	@see <related>
 *	@author Rickard Öberg (rickard@middleware-company.com)
 *	@version $Revision$
 */
public class Script
   extends ActionSupport
   implements ParameterAware
{
   // Static --------------------------------------------------------

   // Attributes ----------------------------------------------------
   boolean exists = true;
   String scriptName;
   URL scriptUrl;
   Map parameters;
   BSFManager mgr = new BSFManager();
   BSFEngine engine;
   Map resultMap = new ScriptMap();

   // ParameterAware implementation ---------------------------------
   public void setParameters(Map parameters)
   {
      this.parameters = parameters;
   }

   // Action implementation -----------------------------------------
   public String execute()
      throws Exception
   {
      try
      {
         URL scriptUrl = getScriptURL();

         String script = getScriptContent(scriptUrl);
         registerVariables();
         engine = getScriptingEngine();

         // Evaluate script
         engine.exec(scriptUrl.getFile(), -1, -1, script);
         return engine.eval(null, -1, -1, "result").toString();
      } catch (Exception e)
      {
         return handleException(e);
      }
   }

   // Public --------------------------------------------------------
   public void setScript(String scriptName)
   {
      this.scriptName = scriptName;
   }

   public URL getScriptURL()
   {
      if (scriptUrl == null)
      {
//         System.out.println("Script:"+scriptName);
         String ext = scriptName.substring(scriptName.lastIndexOf("."));
         String realScriptName = scriptName.substring(0,scriptName.length()-ext.length());
         realScriptName = realScriptName.replace('.','/');
         realScriptName += ext;
//         System.out.println("Script:"+realScriptName);

         scriptUrl = getClass().getClassLoader().getResource(realScriptName);
      }
      return scriptUrl;
   }

   public Map getResults()
   {
      return resultMap;
   }

   // Protected -----------------------------------------------------
   protected String getScriptContent(URL scriptUrl)
      throws IOException
   {
//      System.out.println("URL:"+scriptUrl);
      Reader in = new InputStreamReader(scriptUrl.openStream());
      String script = IOUtils.getStringFromReader(in);
      return script;
   }

   protected BSFEngine getScriptingEngine()
      throws BSFException
   {
      String language = BSFManager.getLangFromFilename(scriptName);
      return mgr.loadScriptingEngine(language);
   }

   protected void registerVariables()
      throws BSFException
   {
      // Register parameters as scripting variables
      Iterator params = parameters.entrySet().iterator();
      while (params.hasNext())
      {
         Map.Entry entry = (Map.Entry)params.next();
         String key = (String)entry.getKey();
         String value = ((String[])entry.getValue())[0];
         mgr.declareBean(key,value, String.class);
      }

      // Register additional info
      mgr.declareBean("action", this, getClass());
   }

   protected String handleException(Exception e)
   {
      e.printStackTrace();
      return ERROR;
   }

   // Inner classes -------------------------------------------------
   class ScriptMap
      extends AbstractMap
   {
      public Set entrySet()
      {
         // Not allowed
         new Throwable().printStackTrace();
         throw new UnsupportedOperationException("Can't get scripting variable set");
      }

      public boolean containsKey(Object key)
      {
         try
         {
            engine.eval(null, -1, -1, key);
            return true;
         } catch (BSFException e)
         {
            return false;
         }
      }

      public Object put(Object key, Object value)
      {
         // Not allowed
         throw new UnsupportedOperationException("Can't set scripting variable");
      }

      public Object get(Object key)
      {
         try
         {
            return engine.eval(null, -1, -1, key);
         } catch (BSFException e)
         {
            throw new IllegalArgumentException("Could not evaluate variable name "+key);
         }
      }

      public Object remove(Object key)
      {
         // Not allowed
         throw new UnsupportedOperationException("Can't remove scripting variable");
      }

      public void clear()
      {
         // Not allowed
         throw new UnsupportedOperationException("Can't remove scripting variables");
      }
   }
}
