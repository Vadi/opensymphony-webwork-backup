<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="ww" uri="/webwork" %>

<html>
<head>
    <title>Ajax Examples</title>
    <jsp:include page="../commonInclude.jsp"/>
</head>

<body>

<ww:url id='url' value="/tutorial/ajax/AjaxTest.action"/>
<ww:div
        id="twoseconds"
        cssStyle="border: 1px solid yellow;"
        href="%{#parameters.url}"
        theme="ajax"
        delay="2000"
        updateFreq="2000"
        errorText="There was an error"
        loadingText="loading...">Initial Content</ww:div>

</body>
</html>
