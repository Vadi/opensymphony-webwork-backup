<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="ww" uri="/webwork" %>

<html>
<head>
    <title>Ajax Examples</title>
</head>

<body>

<ul>
	<li>
		<ww:url id="url" value="example1.jsp" />
		<ww:a href="%{#url}">Remote form example with one submit button (webwork:BindButton)</ww:a>
	</li>
	<li>
		<ww:url id="url" value="example2.jsp" />
		<ww:a href="%{#url}">Remote form example with multiple submit button (webwork:BindButton)</ww:a>
	</li>
</ul>

<ww:include value="../footer.jsp"/>

</body>
</html>
