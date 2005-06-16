<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="webwork" prefix="ww" %>

<html>
    <head>
        <title>Ajax Examples</title>
        <jsp:include page="../commonInclude.jsp" />
	</head>

	<body>



Remote form replacing another div:<br/>
<div id='two' style="border: 1px solid yellow;"><b>initial content</b></div>
<ww:form
    id='theForm2'
    cssStyle="border: 1px solid green;"
    action='/tutorial/ajax/AjaxRemoteForm.action'
    method='post'
    theme="ajax" >

    <input type='text' name='data' value='WebWork User'>

    <ww:submit value="GO2" theme="ajax" >
        <ww:param name="resultDivId" value="'two'" />
    </ww:submit>

</ww:form>


Remote form replacing the forms content:<br/>
<ww:form
    id='theForm3'
    cssStyle="border: 1px solid green;"
    action='/tutorial/ajax/AjaxRemoteForm.action'
    method='post'
    theme="ajax" >

    <input type='text' name='data' value='WebWork User'>

    <ww:submit value="GO3" theme="ajax" >
        <ww:param name="resultDivId" value="'theForm3'" />
    </ww:submit>

</ww:form>

Remote form evaluating suplied JS on completion:<br/>
<ww:form
    id='theForm4'
    cssStyle="border: 1px solid green;"
    action='/tutorial/ajax/AjaxRemoteForm.action'
    method='post'
    theme="ajax" >

    <input type='text' name='data' value='WebWork User'>

    <ww:submit value="GO4" theme="ajax" >
        <ww:param name="onLoadJS" value="'alert('form submitted');'" />
    </ww:submit>

</ww:form>

  </body>
</html>
