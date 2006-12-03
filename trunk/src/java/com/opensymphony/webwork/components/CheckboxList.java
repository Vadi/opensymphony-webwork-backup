package com.opensymphony.webwork.components;

import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <!-- START SNIPPET: javadoc -->
 *
 * Creates a series of checkboxes from a list. Setup is like &lt;ww:select /&gt; or &lt;ww:radio /&gt;, but creates checkbox tags.
 *
 * <!-- END SNIPPET: javadoc -->
 *
 * <p/> <b>Examples</b>
 *
 * <pre>
 * <!-- START SNIPPET: example -->
 * &lt;ww:checkboxlist name="foo" list="bar"/&gt;
 * <!-- END SNIPPET: example -->
 * </pre>
 * 
 * <!-- START SNIPPET: example2Description -->
 * It is possible to select multiple checkboxes, by handling a List  into the value attribute.
 * The List passed into the value attribute could be :-
 * <ul>
 *    <li>key if the list attribute is passed in is a Map</li>
 *    <li>listKey properties if listKey attribute is used</li>
 *    <li>entry/entries passed into the list attribute</li> 
 * </ul>
 * <!-- END SNIPPET: example2Description -->
 * 
 * <pre>
 * <!-- START SNIPPET: example2 -->
 * &lt;ww:checkboxlist name="options" 
 *                              list="%{#{'FOO':'foo','BAR':'bar','BAZ':'baz','BOO':'boo'}}" 
 *                              value="%{{'FOO','BAZ'}}" /&gt;
 *                              
 * &lt;ww:checkboxlist name="options"
 *                              list="%{{'Foo','Bar','Baz'}}"
 *                              value="%{{'Foo','Bar'}}" /&gt;
 *                              
 *  public class MyAction extends ActionSupport {
 *       public List&lt;Choice&gt; getChoices() {
 *          .... 
 *       }
 *       ....                       
 *       public List&lt;String&gt; getPreSelectedChoices() {
 *          // returns a list of Choice.getKey(), which is a String
 *          ....
 *       }
 *  }     
 *   
 *  public class Choice {
 *      public String getKey() { ...}
 *      public String getDisplayName() { ... }
 *      ....
 *  }
 *  
 *  &lt;ww:checkboxlist name="myChoice"
 *                               list="%{choices}"
 *                               listKey="%{'key'}"
 *                               listValue="%{'displayName'}"
 *                               value="%{preSelectedChoices}" /&gt;
 *  
 * <!-- END SNIPPET: example2 -->
 * </pre>
 *
 * @author Patrick Lightbody
 * @author Rene Gielen
 * @version $Revision$
 * @since 2.2
 *
 * @ww.tag name="checkboxlist" tld-body-content="JSP" tld-tag-class="com.opensymphony.webwork.views.jsp.ui.CheckboxListTag"
 * description="Render a list of checkboxes"
  */
public class CheckboxList extends ListUIBean {
    final public static String TEMPLATE = "checkboxlist";

    public CheckboxList(OgnlValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

    protected String getDefaultTemplate() {
        return TEMPLATE;
    }
}
