package com.opensymphony.webwork.components;

import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <!-- START SNIPPET: javadoc -->
 * Render a submit button. The submit tag is used together with the form tag to provide asynchronous form submissions.
 * <!-- END SNIPPET: javadoc -->
 *
 * <p/> <b>Examples</b>
 *
 * <pre>
 * <!-- START SNIPPET: example -->
 * &lt;ww:submit value="%{'Submit'}" /&gt;
 * <!-- END SNIPPET: example -->
 * </pre>
 * 
 * 
 * <!-- START SNIPPET: ajaxJavadoc -->
 * <B>THE FOLLOWING IS ONLY VALID WHEN AJAX IS CONFIGURED</B>
 * <ul>
 * 		<li>resultDivId</li>
 * 		<li>notifyTopics</li>
 * 		<li>onLoadJS</li>
 * 		<li>preInvokeJS</li>
 * </ul>
 * The remote form has three basic modes of use, using the resultDivId, 
 * the notifyTopics, or the onLoadJS. You can mix and match any combination of 
 * them to get your desired result. All of these examples are contained in the 
 * Ajax example webapp. Lets go through some scenarios to see how you might use it:
 * <!-- END SNIPPET: ajaxJavadoc -->
 * 
 * <!-- START SNIPPET: ajxExDescription1 -->
 * Show the results in another div. If you want your results to be shown in 
 * a div, use the resultDivId where the id is the id of the div you want them 
 * shown in. This is an inner HTML approah. Your results get jammed into 
 * the div for you. Here is a sample of this approach:
 * <!-- END SNIPPET: ajxExDescription1 -->
 * 
 * <pre>
 * <!-- START SNIPPET: ajxExample1 -->
 * Remote form replacing another div:
 * &lt;div id='two' style="border: 1px solid yellow;"&gt;Initial content&lt;/div&gt;
 * &lt;ww:form
 *       id='theForm2'
 *       cssStyle="border: 1px solid green;"
 *       action='/AjaxRemoteForm.action'
 *       method='post'
 *       theme="ajax"&gt;
 *
 *   &lt;input type='text' name='data' value='WebWork User' /&gt;
 *   &lt;ww:submit value="GO2" theme="ajax" resultDivId="two" /&gt;
 *
 * &lt;/ww:form &gt;
 * <!-- END SNIPPET: ajxExample1 -->
 * </pre>
 * 
 * 
 * <!-- START SNIPPET: ajxExDescription2 -->
 * Notify other controls(divs) of a change. Using an pub-sub model you can 
 * notify others that your control changed and they can take the appropriate action. 
 * Most likely they will execute some action to refresh. The notifyTopics does this 
 * for you. You can have many topic names in a comma delimited list. 
 * eg: notifyTopics="newPerson, dataChanged" .
 * Here is an example of this approach:
 * <!-- END SNIPPET: ajxExDescription2 -->
 * 
 * <pre>
 * <!-- START SNIPPET: ajxExample2 -->
 * &lt;ww:form id="frm1" action="newPersonWithXMLResult" theme="ajax"  &gt;
 *     &lt;ww:textfield label="Name" name="person.name" value="person.name" size="20" required="true" /&gt;
 *     &lt;ww:submit id="submitBtn" value="Save" theme="ajax"  cssClass="primary"  notifyTopics="personUpdated, systemWorking" /&gt;
 * &lt;/ww:form &gt;
 * 
 * &lt;ww:div href="/listPeople.action" theme="ajax" errorText="error opps"
 *         loadingText="loading..." id="cart-body" &gt;
 *     &lt;ww:action namespace="" name="listPeople" executeResult="true" /&gt;
 * &lt;/ww:div&gt;
 * <!-- END SNIPPET: ajxExample2 -->
 * </pre>
 *
 * <!-- START SNIPPET: ajxExDescription3 -->
 * Massage the results with JavaScript. Say that your result returns some h
 * appy XML and you want to parse it and do lots of cool things with it. 
 * The way to do this is with a onLoadJS handler. Here you provide the name of
 * a JavaScript function to be called back with the result and the event type.
 * The only key is that you must use the variable names 'data' and 'type' when 
 * defining the callback. For example: onLoadJS="myFancyDancyFunction(data, type)".
 * While I talked about XML in this example, your not limited to XML, the data in 
 * the callback will be exactly whats returned as your result.
 * Here is an example of this approach:
 * <!-- END SNIPPET: ajxExDescription3 -->
 *
 * <pre>
 * <!-- START SNIPPET: ajxExample3 -->
 * &lt;script language="JavaScript" type="text/javascript"&gt;
 *     function doGreatThings(data, type) {
 *         //Do whatever with your returned fragment... 
 *         //Perhapps.... if xml...
 *               var xml = dojo.xml.domUtil.createDocumentFromText(data);
 *               var people = xml.getElementsByTagName("person");
 *               for(var i = 0;i < people.length; i ++){
 *                   var person = people[i];
 *                   var name = person.getAttribute("name")
 *                   var id = person.getAttribute("id")
 *                   alert('Thanks dude. Person: ' + name + ' saved great!!!');
 *               }
 *
 *     }
 * &lt;/script&gt;
 *
 * &lt;ww:form id="frm1" action="newPersonWithXMLResult" theme="ajax"  &gt;
 *     &lt;ww:textfield label="'Name'" name="'person.name'" value="person.name" size="20" required="true" /&gt;
 *     &lt;ww:submit id="submitBtn" value="Save" theme="ajax"  cssClass="primary"  onLoadJS="doGreatThings(data, type)" /&gt; 
 * &lt;/ww:form&gt;
 * <!-- END SNIPPET: ajxExample3 -->
 * </pre>
 *
 * @author Patrick Lightbody
 * @author Rene Gielen
 * @version $Revision$
 * @since 2.2
 *
 * @ww.tag name="submit" tld-body-content="JSP" tld-tag-class="com.opensymphony.webwork.views.jsp.ui.SubmitTag"
 * description="Render a submit button"
 */
public class Submit extends UIBean {
    final public static String TEMPLATE = "submit";

    protected String action;
    protected String method;
    protected String align;
    protected String resultDivId;
    protected String onLoadJS;
    protected String notifyTopics;
    protected String listenTopics;
    protected String preInvokeJS;

    public Submit(OgnlValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

    protected String getDefaultTemplate() {
        return TEMPLATE;
    }

    public void evaluateParams() {
        if (align == null) {
            align = "right";
        }

        if (value == null) {
            value = "Submit";
        }

        super.evaluateParams();

        if (action != null || method != null) {
            String name;

            if (action != null) {
                name = "action:" + findString(action);

                if (method != null) {
                    name += findString(method);
                }
            } else {
                name = "method:" + findString(method);
            }
            
            addParameter("name", name);
        }

        addParameter("align", findString(align));

        if (null != resultDivId) {
            addParameter("resultDivId", findString(resultDivId));
        }

        if (null != onLoadJS) {
            addParameter("onLoadJS", findString(onLoadJS));
        }

        if (null != notifyTopics) {
            addParameter("notifyTopics", findString(notifyTopics));
        }

        if (null != listenTopics) {
            addParameter("listenTopics", findString(listenTopics));
        }

        if (preInvokeJS != null) {
            addParameter("preInvokeJS", findString(preInvokeJS));
        }
    }

    /**
     * @ww.tagattribute required="false" type="String"
     * description="Set action attribute"
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * @ww.tagattribute required="false" type="String"
     * description="Set method attribute"
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * @ww.tagattribute required="false" type="String"
     * description="HTML align attribute"
     */
    public void setAlign(String align) {
        this.align = align;
    }

    /**
     * @ww.tagattribute required="false"  type="String"
     * description="The id of the HTML element to place the result (this can the the form's id or any id on the page"
     */
    public void setResultDivId(String resultDivId) {
        this.resultDivId = resultDivId;
    }

    /**
     * @ww.tagattribute required="false" type="String"
     * description="Javascript code that will be executed after the form has been submitted. The format is onLoadJS='yourMethodName(data,type)'. NOTE: the words data and type must be left like that if you want the event type and the returned data."
     */
    public void setOnLoadJS(String onLoadJS) {
        this.onLoadJS = onLoadJS;
    }

    /**
     * @ww.tagattribute required="false" type="String"
     * description=" Topic names to post an event to after the form has been submitted"
     */
    public void setNotifyTopics(String notifyTopics) {
        this.notifyTopics = notifyTopics;
    }

    /**
     * @ww.tagattribute required="false" type="String"
     * description="Set listenTopics attribute"
     */
    public void setListenTopics(String listenTopics) {
        this.listenTopics = listenTopics;
    }

    /**
     * @ww.tagattribute required="false" type="String"
     * description="Javascript code that will be executed before invokation. The format is preInvokeJS='yourMethodName(data,type)'."
     */
    public void setPreInvokeJS(String preInvokeJS) {
        this.preInvokeJS = preInvokeJS;
    }
}
