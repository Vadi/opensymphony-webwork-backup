<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="webwork" prefix="ww" %>

<html>
    <head>
        <title>Ajax Examples</title>
            <jsp:include page="../commonInclude.jsp" />
    </head>
  <body>

                    A simple DIV's that cannot contact the server and displays the transport error message:<br/>

                    <div class="source">
<pre>
        One Component:
    &lt;ww:div
        id="one"
        cssStyle="border: 1px solid yellow;"
        href="/AjaxTest.action"
        theme="ajax"
        listenTopics="mylink1_click"
        delay="1000" &gt;Initial Content&lt;/ww:div&gt;

        &lt;br/&gt;&lt;br/&gt;

        Two Component:
    &lt;ww:div
        id="two"
        cssStyle="border: 1px solid yellow;"
        href="/AjaxTest.action"
        theme="ajax"
        listenTopics="mylink1_click,mylink2_click"
        delay="1000" &gt;Initial Content&lt;/ww:div&gt;
        &lt;br/&gt;&lt;br/&gt;

        Three Component:
    &lt;ww:div
        id="three"
        cssStyle="border: 1px solid yellow;"
        href="/AjaxTest.action"
        theme="ajax"
        listenTopics="mylink2_click"
        delay="1000" &gt;Initial Content&lt;/ww:div&gt;
        &lt;br/&gt;&lt;br/&gt;

        Remote link 1 updating "One Component" and "Two Component"&lt;br/&gt;
        &lt;ww:a
            id="link1"
            theme="ajax"
            href="/AjaxRemoteLink.action"
            notifyTopics="mylink1_click"
            showErrorTransportText="true"
            errorText="An Error ocurred" &gt;Update&lt;/ww:a&gt;
        &lt;br/&gt;&lt;br/&gt;

        Remote link 2 updating "Two Component" and "Three Component"&lt;br/&gt;
        &lt;ww:a
             id="link2"
             theme="ajax"
             href="/AjaxRemoteLink.action"
             notifyTopics="mylink2_click"
             showErrorTransportText="true"
             errorText="An Error ocurred" &gt;Update&lt;/ww:a&gt;
        &lt;br/&gt;&lt;br/&gt;

        Remote DIV that is not connected to any remote links:
    &lt;ww:div
        id="four"
        cssStyle="border: 1px solid yellow;"
        href="/AjaxTest.action"
        theme="ajax"
        delay="1000" &gt;Initial Content&lt;/ww:div&gt;
        &lt;br/&gt;&lt;br/&gt;

        A Remote link that doesn't trigger any remote DIV updates&lt;br/&gt;
        &lt;ww:a
             id="link1"
             theme="ajax"
             href="/AjaxRemoteLink.action"
             showErrorTransportText="true"
             errorText="An Error ocurred" &gt;Update&lt;/ww:a&gt;
        &lt;br/&gt;&lt;br/&gt;
</pre>
                    </div>

        One Component:
    <ww:div
        id="one"
        cssStyle="border: 1px solid yellow;"
        href="/AjaxTest.action"
        theme="ajax"
        listenTopics="mylink1_click"
        delay="1000" >Initial Content</ww:div>

        <br/><br/>

        Two Component:
    <ww:div
        id="two"
        cssStyle="border: 1px solid yellow;"
        href="/AjaxTest.action"
        theme="ajax"
        listenTopics="mylink1_click,mylink2_click"
        delay="1000" >Initial Content</ww:div>
        <br/><br/>

        Three Component:
    <ww:div
        id="three"
        cssStyle="border: 1px solid yellow;"
        href="/AjaxTest.action"
        theme="ajax"
        listenTopics="mylink2_click"
        delay="1000" >Initial Content</ww:div>
        <br/><br/>

        Remote link 1 updating "One Component" and "Two Component"<br/>
        <ww:a
            id="link1"
            theme="ajax"
            href="/AjaxRemoteLink.action"
            notifyTopics="mylink1_click"
            showErrorTransportText="true"
            errorText="An Error ocurred" >Update</ww:a>
        <br/><br/>

        Remote link 2 updating "Two Component" and "Three Component"<br/>
        <ww:a
             id="link2"
             theme="ajax"
             href="/AjaxRemoteLink.action"
             notifyTopics="mylink2_click"
             showErrorTransportText="true"
             errorText="An Error ocurred" >Update</ww:a>
        <br/><br/>

        Remote DIV that is not connected to any remote links:
    <ww:div
        id="four"
        cssStyle="border: 1px solid yellow;"
        href="/AjaxTest.action"
        theme="ajax"
        delay="1000" >Initial Content</ww:div>
        <br/><br/>

        A Remote link that doesn't trigger any remote DIV updates<br/>
        <ww:a
             id="link1"
             theme="ajax"
             href="/AjaxRemoteLink.action"
             showErrorTransportText="true"
             errorText="An Error ocurred" >Update</ww:a>
        <br/><br/>

  </body>
</html>
