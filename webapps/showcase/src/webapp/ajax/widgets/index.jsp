<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="ww" uri="/webwork" %>

<html>
<head>
    <title>Ajax Widgets</title>
    <jsp:include page="/ajax/commonInclude.jsp"/>
</head>

<body>

Default Editor configuration:<br/>
<ww:form action="AjaxRemoteForm" method="post">
    <ww:textarea name="data" theme="ajax" cols="10" rows="50"/>
    <ww:submit value="Submit"/>
</ww:form>
<br/>

Configured Editor configuration:<br/>
<ww:form action="AjaxRemoteForm" method="post">
    <ww:textarea name="data" theme="ajax" cols="10" rows="50">
        <ww:param name="editorControls">textGroup;|;justifyGroup;|;listGroup;indentGroup</ww:param>
    </ww:textarea>
    <ww:submit value="Submit"/>
</ww:form>
<br/>

<ww:include value="../footer.jsp"/>

</body>
</html>
