/*
 * WebWork, Web Application Framework
 *
 * Distributable under Apache license.
 * See terms of license at opensource.org
 */
package webwork.config;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Text;
import org.w3c.dom.DOMException;
import org.apache.commons.logging.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.File;
import java.net.URL;
import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Map;

import webwork.util.ClassLoaderUtils;
import com.opensymphony.webwork.config.Configuration;

/**
 * Access view configuration from an XML file.
 *
 *	@author Rickard Ã–berg (rickard@middleware-company.com)
 *	@author Scott Farquhar (scott@atlassian.com)
 *	@version $Revision$
 *
 */
public class XMLActionConfiguration extends Configuration
{
   // Attributes ----------------------------------------------------
   /**
    * This stores the mapping from URL -> action.  It looks through the (xml) configuration file and
    * adds all the commands, aliases, and views.
    * <p>
    * One caveat - if you are using an extension aside from '.action', the views will be added with
    * '.action' here, and then replaced on retrieval.  This is to warn off a recursive dependency that
    * I haven't had a chance to look at yet.
    */
   Map actionMappings = null;
   Log log = LogFactory.getLog(getClass());
   private File file;
   private long lastModified;

   // Constructors --------------------------------------------------

   public XMLActionConfiguration(String aName)
   {
     URL fileUrl = ClassLoaderUtils.getResource(aName+".xml", XMLActionConfiguration.class);
     if (fileUrl == null)
        throw new IllegalArgumentException("No such XML file:"+aName+".xml");
     actionMappings = getMappingsFromResource(fileUrl);
     file = new File(fileUrl.getFile());
     if(!file.exists() || !file.canRead())
     {
       file = null;
     }
     if(file!=null)
       lastModified = file.lastModified();
   }

   private Map getMappingsFromResource(URL url)
   {
      Map actionMap = new HashMap();
      try
      {
         DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

         // Parse document
         Document document = factory.newDocumentBuilder().parse(url.toString());

         log.debug("Found XML view configuration "+url);

         // Get list of actions
         NodeList actions = document.getElementsByTagName("action");

         // Build list of views
         for (int i = 0; i < actions.getLength(); i++)
         {
            Element action = (Element)actions.item(i);
            String actionName = action.getAttribute("name");
            String actionAlias = action.getAttribute("alias");

            // Build views for this action
            {
               NodeList views = action.getElementsByTagName("view");
               for (int j = 0; j < views.getLength(); j++)
               {
                  Element view = (Element)views.item(j);

                  // This is to avoid listing "view" elements
                  // that are associated with the commands
                  // of this action
                  if (!view.getParentNode().equals(action))
                     break;

                  // View mappings for this action
                  NodeList viewMapping = view.getChildNodes();
                  StringBuffer mapping = new StringBuffer();
                  for (int k = 0; k < viewMapping.getLength(); k++)
                  {
                     Node mappingNode = viewMapping.item(k);
                     if (mappingNode instanceof Text)
                     {
                        mapping.append(mappingNode.getNodeValue());
                     }
                  }

                  String actionViewName;
                  if ("".equals(actionAlias))
                  {
                      if(!"".equals(actionName))
                      {
                          actionViewName = actionName+"."+view.getAttribute("name");
                      }
                      else
                      {
                          actionViewName = view.getAttribute("name");
                      }
                  }
                  else
                  {
                     actionViewName = actionAlias+"."+view.getAttribute("name");
                     log.debug("Adding action alias "+actionAlias+"="+actionName);
                     actionMap.put(actionAlias+".action",
                           actionName);
                  }

                  String actionViewMapping = mapping.toString().trim();
                  log.debug("Adding view mapping "+actionViewName+"="+actionViewMapping);
                  actionMap.put(actionViewName, actionViewMapping);
               }
            }

            // Commands
            NodeList commands = action.getElementsByTagName("command");
            for (int j = 0; j < commands.getLength(); j++)
            {
               Element command = (Element)commands.item(j);
               String commandName = command.getAttribute("name");
               String commandAlias = command.getAttribute("alias");

               if (!commandAlias.equals(""))
               {
                  log.debug("Adding command alias "+commandAlias+"="+actionName+"!"+commandName);
                  actionMap.put(commandAlias+".action",
                        actionName+"!"+commandName);
               }

               // Build views for this action
               NodeList views = command.getElementsByTagName("view");
               for (int k = 0; k < views.getLength(); k++)
               {
                  Element view = (Element)views.item(k);

                  // View mappings for this action
                  NodeList viewMapping = view.getChildNodes();
                  StringBuffer mapping = new StringBuffer();
                  for (int l = 0; l < viewMapping.getLength(); l++)
                  {
                     Node mappingNode = viewMapping.item(l);
                     if (mappingNode instanceof Text)
                     {
                        mapping.append(mappingNode.getNodeValue());
                     }
                  }

                  String commandViewName;
                  if (commandAlias.equals(""))
                  {
                     if (actionAlias.equals(""))
                        commandViewName = actionName+"!"+commandName+"."+view.getAttribute("name");
                     else
                        commandViewName = actionAlias+"!"+commandName+"."+view.getAttribute("name");
                  }
                  else
                  {
                     commandViewName = commandAlias+"."+view.getAttribute("name");
                  }

                  String commandViewMapping = mapping.toString().trim();
                  log.debug("Adding command view mapping "+commandViewName+"="+commandViewMapping);
                  actionMap.put(commandViewName, commandViewMapping);
               }
            }
         }
      } catch (SAXException e)
      {
         log.error("SAX exception", e);
         throw new IllegalArgumentException("Could not parse XML action configuration");
      } catch (IOException e)
      {
         log.error("IO exception", e);
         throw new IllegalArgumentException("Could not load XML action configuration");
      } catch (ParserConfigurationException e)
      {
         log.error("Parser conf exception", e);
         throw new IllegalArgumentException("Could not load XML action configuration");
      } catch (DOMException e)
      {
         log.error("DOM exception", e);
         throw new IllegalArgumentException("Could not load XML action configuration");
      }
     return actionMap;
   }

   /**
    * Get a named setting. Note extension is stripped and replaced with extension defined
    * in property file. This is done here because of the recursive dependency if the constructor
    * called Configuration.getString("webwork.action.extension").
    */
   public Object getImpl(String aName)
      throws IllegalArgumentException
   {
     //Possible recursion in getString, so we make sure that we shortcircuit the call for the reload property to prevent
     //a stackoverflow
     boolean reloadEnabled = false;
     if(!"webwork.configuration.xml.reload".equals(aName))
     {
       try
       {
          reloadEnabled = "true".equalsIgnoreCase(Configuration.getString("webwork.configuration.xml.reload"));
       }
       catch(Exception ex)
       {}
     }
     if(reloadEnabled && file!=null && lastModified<file.lastModified())
     {
       lastModified = file.lastModified();
       log.debug("Reloading " + file);
       try
       {
         Map newMappings = getMappingsFromResource(file.toURL());
         actionMappings = newMappings;
       }
       catch(MalformedURLException e)
       {
         //can't really happen
         log.error("Something horrible happened", e);
       }
     }
      String mappingName = replaceExtension(aName);
      Object mapping = actionMappings.get(mappingName);
      if (mapping == null)
         throw new IllegalArgumentException("No such view mapping:"+mappingName);

      return mapping;
   }

    /**
     * As the actions are stored in the action mapping with a '.action' extension, we need to map the current
     * request to that '.action' mapping.
     * <p>
     * So an action 'ABC.jspa' would be lookup up in the map at 'ABC.action'.
     * <p>
     * The extension used is retrieved from the configuration using key 'webwork.action.extension'
     * @param   actionName  The original action (url) to be mapped, including an extension (if any).
     * @return  The action name used in actionMappings - ie with a '.action' extension.
     */
   private String replaceExtension(String actionName){
       String ext = "." + Configuration.getString("webwork.action.extension");

       //replace all custom extensions with .action to retrieve view mapping from cache
       if (actionName != null && !".action".equals(ext))
       {
           // try to find another extension
           if (actionName.endsWith(ext))
               actionName = actionName.substring(0, actionName.lastIndexOf(ext)) + ".action";

           // otherwise look for something like .jspa? in the view mapping and replace
           // with .action?
           int idx = actionName.indexOf(ext + "?");

           if (idx > 0)
           {
               actionName = actionName.substring(0, idx) + ".action?" + actionName.substring(idx + ext.length() + 1);
           }
       }

       return actionName;
   }

   public void setImpl(String aName, Object aValue)
   {
      throw new UnsupportedOperationException("May not update XML view mapping");
   }

   public Iterator listImpl()
   {
      return actionMappings.keySet().iterator();
   }
}
