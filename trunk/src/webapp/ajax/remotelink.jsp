<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="webwork" prefix="ww" %>

<html>
    <head>
        <title>Ajax Examples</title>
            <jsp:include page="commonInclude.jsp" />
    </head>
  <body>

    <ww:topicScope>

        One Component:
        <ww:remotediv id="one" url="'/AjaxTest.action'" topicName="mylink1_click" />
        <br/><br/>

        Two Component:
        <ww:remotediv id="two" url="'/AjaxTest.action'" topicName="mylink1_click,mylink2_click" />
        <br/><br/>

        Three Component:
        <ww:remotediv id="three" url="'/AjaxTest.action'" topicName="mylink2_click" />
        <br/><br/>

        Remote link 1 updating the above<br/>
        <ww:remotelink id="link1" url="'/AjaxRemoteLink.action'" topicName="mylink1_click" showErrorTransportText="true" errorText="An Error ocurred" >Update</ww:remotelink>
        <br/><br/>

        Remote link 2 updating the above<br/>
        <ww:remotelink id="link2" url="'/AjaxRemoteLink.action'" topicName="mylink2_click" showErrorTransportText="true" errorText="An Error ocurred" >Update</ww:remotelink>
        <br/><br/>

        Non-topic Component:
        <ww:remotediv id="four" url="'/AjaxTest.action'" />
        <br/><br/>

        Non-topic Remote link <br/>
        <ww:remotelink id="link1" url="'/AjaxRemoteLink.action'" showErrorTransportText="true" errorText="An Error ocurred" >Update</ww:remotelink>
        <br/><br/>

    </ww:topicScope>
  </body>
</html>