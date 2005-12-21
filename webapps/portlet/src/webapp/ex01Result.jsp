<%@ taglib uri="/webwork" prefix="ww"%>
<html>
<head>
<title><ww:text name="heading_jsp" /></title>
<meta name="help-path" content="/help/help1_2.html"/>  
</head>
<body>
<p><ww:text name="greeting_jsp" /> @ <ww:property value="now" /></p>
<p>User Name: <ww:property value="userName" /></p>
<ww:form action="/jsp_hello.action" method="post">
	<ww:textfield label="User Name" name="userName"/>
	<ww:submit value="Submit" />
</ww:form>
<ww:a href="ex01.jsp" id="hello">Back</ww:a>
</body>
</html>

