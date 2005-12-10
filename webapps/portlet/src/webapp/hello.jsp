<%@ taglib uri="webwork" prefix="ww"%>
<html>
<head><title>Please input your information</title>
<meta name="help-path" content="/help/help7.html"/>  
</head>
<body>
<p>Welcome to Use <ww:property value="now"/>
</p>

<ww:form action="/jsphello.action" method="post">
		<ww:textfield label="User Name: " name="userName" value="%{userName}" />
		<ww:submit name="Submit" value="Submit"/>
</ww:form>
<br/>
</body>
</html>

