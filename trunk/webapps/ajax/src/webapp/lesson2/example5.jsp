<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="ww" uri="/webwork" %>

<html>
<head>
    <title>Ajax Examples</title>
    <jsp:include page="../commonInclude.jsp"/>
</head>

<body>

A simple DIV's that cannot contact the server:<br/>

<div class="code">
    <pre>
        &lt;ww:div
        id="error"
        cssStyle="border: 1px solid yellow;"
        href="/AjaxNoUrl.jsp"
        theme="ajax"
        delay="1000"
        errorText="Could not contact server"
        loadingText="reloading" &gt;loading now&lt;/ww:div&gt;
    </pre>
</div>

<ww:div
        id="error"
        cssStyle="border: 1px solid yellow;"
        href="/AjaxNoUrl.jsp"
        theme="ajax"
        delay="1000"
        errorText="Could not contact server"
        loadingText="reloading">loading now</ww:div>


</body>
</html>
