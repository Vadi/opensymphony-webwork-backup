<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="ww" uri="webwork" %>

<html>
<head>
    <title>Ajax Examples</title>
    <jsp:include page="../commonInclude.jsp"/>

    <script language="JavaScript" type="text/javascript">
        function doSomething() {
            // stuff to manipulate form
            return true;
        }
    </script>

</head>

<body>


Remote form replacing another div:<br/>

<div id='two' style="border: 1px solid yellow;"><b>initial content</b></div>
<ww:form
        id='theForm2'
        cssStyle="border: 1px solid green;"
        action='/AjaxRemoteForm.action'
        method='post'
        theme="ajax">

    <input type='text' name='data' value='WebWork User'>

    <ww:submit value="GO2" theme="ajax" resultDivId="two"/>

</ww:form>


Remote form replacing the forms content:<br/>
<ww:form
        id='theForm3'
        cssStyle="border: 1px solid green;"
        action='/AjaxRemoteForm.action'
        method='post'
        theme="ajax">

    <input type='text' name='data' value='WebWork User'>

    <ww:submit value="GO3" theme="ajax" resultDivId="theForm3"/>

</ww:form>

Remote form evaluating suplied JS on completion:<br/>
<ww:form
        id='theForm4'
        cssStyle="border: 1px solid green;"
        action='/AjaxRemoteForm.action'
        method='post'
        theme="ajax">

    <input type='text' name='data' value='WebWork User'>

    <ww:submit value="GO4" theme="ajax" onLoadJS="alert('form submitted');"/>

</ww:form>

Remote form replacing the forms content after confirming results:<br/>
<ww:form
        id='theForm5'
        cssStyle="border: 1px solid green;"
        action='/AjaxRemoteForm.action'
        method='post'
        theme="ajax">

    <input type='text' name='data' value='WebWork User'>

    <ww:submit value="GO3" theme="ajax" resultDivId="theForm5" preInvokeJS="confirm('sure?');"/>

</ww:form>

Remote form replacing the forms content after running a function:<br/>
<ww:form
        id='theForm6'
        cssStyle="border: 1px solid green;"
        action='/AjaxRemoteForm.action'
        method='post'
        theme="ajax">

    <input type='text' name='data' value='WebWork User'>

    <ww:submit value="GO3" theme="ajax" resultDivId="theForm6" preInvokeJS="doSomething();"/>

</ww:form>

</body>
</html>
