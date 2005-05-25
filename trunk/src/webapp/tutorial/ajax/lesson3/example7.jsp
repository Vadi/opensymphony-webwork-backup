<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="webwork" prefix="ww" %>

<html>
    <head>
        <title>Ajax Examples</title>
            <jsp:include page="../commonInclude.jsp" />
    </head>
  <body>

                    A simple DIV's that cannot contact the server and displays the transport error message:<br/>

                    <div class="code">
                        <pre>
    &lt;ww:topicScope&gt;

        One Component:
        &lt;ww:remotediv id="one" url="/tutorial/ajax/AjaxTest.action" topicName="mylink1_click" /&gt;
        &lt;br/&gt;&lt;br/&gt;

        Two Component:
        &lt;ww:remotediv id="two" url="/tutorial/ajax/AjaxTest.action" topicName="mylink1_click,mylink2_click" /&gt;
        &lt;br/&gt;&lt;br/&gt;

        Three Component:
        &lt;ww:remotediv id="three" url="/tutorial/ajax/AjaxTest.action" topicName="mylink2_click" /&gt;
        &lt;br/&gt;&lt;br/&gt;

        Remote link 1 updating "One Component" and "Two Component"&lt;br/&gt;
        &lt;ww:remotelink id="link1" url="%{'/tutorial/ajax/AjaxRemoteLink.action'}" topicName="mylink1_click" showErrorTransportText="true" errorText="%{'An Error ocurred'}" &gt;Update&lt;/ww:remotelink&gt;
        &lt;br/&gt;&lt;br/&gt;

        Remote link 2 updating "Two Component" and "Three Component"&lt;br/&gt;
        &lt;ww:remotelink id="link2" url="%{'/tutorial/ajax/AjaxRemoteLink.action'}" topicName="mylink2_click" showErrorTransportText="true" errorText="%{'An Error ocurred'}" &gt;Update&lt;/ww:remotelink&gt;
        &lt;br/&gt;&lt;br/&gt;

        Remote DIV that is not connected to any remote links:
        &lt;ww:remotediv id="four" url="/tutorial/ajax/AjaxTest.action" /&gt;
        &lt;br/&gt;&lt;br/&gt;

        A Remote link that doesn't trigger any remote DIV updates &lt;br/&gt;
        &lt;ww:remotelink id="link1" url="/tutorial/ajax/AjaxRemoteLink.action" showErrorTransportText="true" errorText="An Error ocurred" &gt;Update&lt;/ww:remotelink&gt;
        &lt;br/&gt;&lt;br/&gt;

    &lt;/ww:topicScope&gt;

                        </pre>
                    </div>

    <ww:topicScope>

        One Component:
        <ww:remotediv id="one" url="/tutorial/ajax/AjaxTest.action" topicName="mylink1_click" />
        <br/><br/>

        Two Component:
        <ww:remotediv id="two" url="/tutorial/ajax/AjaxTest.action" topicName="mylink1_click,mylink2_click" />
        <br/><br/>

        Three Component:
        <ww:remotediv id="three" url="/tutorial/ajax/AjaxTest.action" topicName="mylink2_click" />
        <br/><br/>

        Remote link 1 updating "One Component" and "Two Component"<br/>
        <ww:remotelink id="link1" url="%{'/tutorial/ajax/AjaxRemoteLink.action'}" topicName="mylink1_click" showErrorTransportText="true" errorText="%{'An Error ocurred'}" >Update</ww:remotelink>
        <br/><br/>

        Remote link 2 updating "Two Component" and "Three Component"<br/>
        <ww:remotelink id="link2" url="%{'/tutorial/ajax/AjaxRemoteLink.action'}" topicName="mylink2_click" showErrorTransportText="true" errorText="%{'An Error ocurred'}" >Update</ww:remotelink>
        <br/><br/>

        Remote DIV that is not connected to any remote links:
        <ww:remotediv id="four" url="/tutorial/ajax/AjaxTest.action" />
        <br/><br/>

        A Remote link that doesn't trigger any remote DIV updates<br/>
        <ww:remotelink id="link1" url="/tutorial/ajax/AjaxRemoteLink.action" showErrorTransportText="true" errorText="An Error ocurred" >Update</ww:remotelink>
        <br/><br/>

    </ww:topicScope>
  </body>
</html>
