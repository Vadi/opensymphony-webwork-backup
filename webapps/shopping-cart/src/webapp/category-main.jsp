<%
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<%@ taglib uri="/webwork" prefix="ww" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>Category</title>

</head>

<body id="page-home">
<%--<ww:action name="activeCategory" namespace="/catalog/remote" executeResult="true" />--%>
<ww:div id="main" href="/catalog/remote/activeCategory.action" theme="ajax" listenTopics="categorySelected" loadingText="loading..." />
</body>
</html>