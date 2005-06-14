<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="webwork" prefix="ww" %>

<html>
    <head>
        <title>Ajax Examples</title>
        <jsp:include page="../commonInclude.jsp" />
	</head>

	<body>



<div id='two' style="border: 1px solid yellow;"><b>initial content</b></div>
Remote form replacing another div:<br/>
<ww:form
    id='theForm2'
    cssStyle="border: 1px solid green;"
    action='/tutorial/ajax/AjaxRemoteForm.action'
    useAjax="true"
    method='post'
    resultDivId="two"
    theme="simple" >

    <input type='text' name='data' value='WebWork User'>

    <ww:submit value="GO2" theme="ajax" />

</ww:form>


Remote form replacing the forms content:<br/>
<ww:form
    id='theForm3'
    cssStyle="border: 1px solid green;"
    action='/tutorial/ajax/AjaxRemoteForm.action'
    useAjax="true"
    method='post'
    resultDivId="theForm3"
    theme="simple" >

    <input type='text' name='data' value='WebWork User'>

    <ww:submit value="GO3" theme="ajax" />

</ww:form>


  </body>
</html>
