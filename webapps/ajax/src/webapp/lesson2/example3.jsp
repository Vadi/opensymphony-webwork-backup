<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="ww" uri="webwork" %>

<html>
<head>
    <title>Ajax Examples</title>
    <jsp:include page="../commonInclude.jsp"/>
</head>

<body>

<ww:div
        id="twoseconds"
        cssStyle="border: 1px solid yellow;"
        href="/AjaxTest.action"
        theme="ajax"
        delay="2000"
        updateFreq="#parameters.period"
        errorText="There was an error">Initial Content</ww:div>

</body>
</html>
