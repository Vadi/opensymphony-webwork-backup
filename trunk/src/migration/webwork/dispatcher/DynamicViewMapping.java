/*
 * WebWork, Web Application Framework
 *
 * Distributable under Apache license.
 * See terms of license at opensource.org
 */
package webwork.dispatcher;

import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.util.OgnlValueStack;
import com.opensymphony.webwork.config.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

/**
 * This view mapping allows for dynamic values to be used in the view
 * specification. The dynamic expressions are specified as:
 * ${someWWExpression}
 * The expression will be looked up on the value stack.
 * When all dynamic expressions have been looked up the DynamicViewMapping also
 * checks if the view is an action, and wraps the view it in a ViewActionWrapper
 * in that case.
 * If the view did not contain dynamic data then it is cached, otherwise it is not.
 *
 *	@see webwork.dispatcher.ViewMapping
 *	@author Dick Zetterberg (dick@transitor.se)
 *	@version $Revision$
 */
public class DynamicViewMapping implements ViewMapping
{
   protected static Log log = LogFactory.getLog(DynamicViewMapping.class);

   protected ViewMapping delegate;   
   protected Map cache;
   protected String actionSuffix;
   protected String actionSuffixParam;
   protected final Object NULL_VIEW = new Object();
   
   public DynamicViewMapping(ViewMapping aDelegate)
   {
      delegate = aDelegate;
      cache = Collections.synchronizedMap(new HashMap());
		try
      {
         actionSuffix = "." + Configuration.getString("webwork.action.extension");
      }
      catch(IllegalArgumentException iae)
      {
         actionSuffix = ".action";	
         log.warn("Unable to find \'webwork.action.extension\' property setting. Defaulting to \'action\'");
      }
      actionSuffixParam = actionSuffix + "?";
   }
   
   /**
    * Get view corresponding to given action and view name
    */
   public Object getView(String anActionName, String aViewName)
   {
      // First check if the mapping exists in the cache.
      // Only non-dynamic view mappings are put there
      String cacheKey = anActionName+"."+aViewName;
      Object view = cache.get(cacheKey);
      if (view == NULL_VIEW)
         return null;
      else if (view != null)
         return view;

      // No view found in cache. Either this is the first time the view is
      // requested or it is dynamic and cannot be cached.
      // Get the view object from the delegate view mapping first
      view = delegate.getView(anActionName, aViewName);
      if (view == null)
      {
         cache.put(cacheKey, NULL_VIEW);
         return null;
      }
      String viewString = view.toString();
      // If the viewString is null, just cache the view object and return      
      if (viewString == null)
      {
         cache.put(cacheKey, view);
         return view;
      }
      
      // Check if the view contains any dynamic values and in that case look them up
      // dynamicView is null if it was not a dynamic view. Otherwise it is the view
      // after dynamic lookups has beem made
      String dynamicView = checkDynamicView(viewString);
            
      if (dynamicView != null)
      {
         viewString = dynamicView;
         view = dynamicView;
      }

      // Now check if the view maps to an action and should be wrapped
      // with a ViewActionWrapper, and if it has parameters or not
      int actionIndex = viewString.indexOf(actionSuffixParam);
      // If the view maps to an action with parameters
      if (actionIndex != -1)
      {
        	String newActionName = viewString.substring(0, actionIndex); 
         // The action has parameters, get the parameters as a Map
         Map parameters = getParams(viewString.substring(actionIndex + actionSuffixParam.length()));
         view = new ViewActionWrapper(newActionName, parameters);
      }
      // Else check if view ends with an action suffix
      else if (viewString.endsWith(actionSuffix))
      {
        	String newActionName = viewString.substring(0, viewString.length() - actionSuffix.length()); 
         // The action has no parameters, just create the wrapper
         view = new ViewActionWrapper(newActionName);
      }

      // If dynamicView is null it means that this view was not dynamic
      // In that case store the result in the cache
      if (dynamicView == null)
         cache.put(cacheKey, view);

      // Return the found view
      return view;
   }

   /**
   * Check if the supplied string specifies a dynamic view. In that
   * case lookup the dynamic elements on the stack. If not, return null.
   * The parameter view should not be null
   */
   protected String checkDynamicView(String view)
   {
      int dynStart = view.indexOf("${");
      // If no dynamic parts return null
      if (dynStart == -1)
         return null;
      StringBuffer dynView = new StringBuffer(view.length() + 40);
      OgnlValueStack stack = ActionContext.getContext().getValueStack();
      int dynEnd = -1;
      int last = 0;
      do
      {
         // We call the method getMatchingBrace to find the correct matching
         // brace. This necessary since the EL allows braces in the expression
         dynEnd = getMatchingBrace(view, dynStart+2);
         // If no closing brace found then break out
         if (dynEnd == -1)
            break;
         String findExp = view.substring(dynStart+2, dynEnd);
         String value = (String) stack.findValue(findExp,String.class);
         // Now append this to stringbuffer,
         // Also append any previously skipped data.
         if (dynStart > last)
            dynView.append(view.substring(last, dynStart));
         dynView.append(value);
         last = dynEnd + 1;
         dynStart = view.indexOf("${", last);   		
      }
      while(dynStart != -1);
      // If nothing appended there was no end brace and thus no dynamic expression
      if (last == 0)
         return null;
      // Append the characters after the last closing brace
      if (view.length()-1 > last)
         dynView.append(view.substring(last));
      return dynView.toString();
   }
   
   protected int getMatchingBrace(String view, int dynStart)
   {
      int depth = 0;
      for(int i=dynStart; i < view.length(); i++)
      {
         char c = view.charAt(i);
         // If we find a new opening brace then increase the depth
         if (c == '{')
            depth++;
         else if (c == '}')
         {
            // We found a closing brace, if depth is 0 it is the matching one
            if (depth == 0)
               return i;
            else
               depth--;
         }
      }
      log.warn("No matching brace found in view: " + view + ", starting at index: " + dynStart);
      return -1;
   }

   /**
    * Tokenize the parameterString and create a Map of its parameters
    * The map is made of name value pairs i.e., name1=value&name2=value
    */
   private Map getParams(String parameterString) {
     if (parameterString == null)
        return Collections.EMPTY_MAP;
     try {
         StringTokenizer stParams = new StringTokenizer(parameterString, "&");
         Map params = new HashMap();
         while (stParams.hasMoreTokens()) {
             String nameValue = stParams.nextToken();
             StringTokenizer stNameValuePairs = new StringTokenizer(nameValue, "=");
             while (stNameValuePairs.hasMoreTokens()) {
                 String name = stNameValuePairs.nextToken();
                 String value = stNameValuePairs.nextToken();
                 params.put(name, value);
             }
         }
         return params;
     } catch (NoSuchElementException nse) {
   		log.warn("getParams: " + parameterString + ", caught NoSuchElementException: ", nse);
         return Collections.EMPTY_MAP;
     } catch (Exception e) {
   		log.warn("getParams: " + parameterString + ", caught Exception: ", e);
         return Collections.EMPTY_MAP;
     }
   }
}
