<html>
<head><title>Please input your information (Freemarker Version)</title>
<meta name="help-path" content="/help/help1_1.html"/>
</head>
<body>
<p>Welcome to Use <@ww.property value="now" />
</p>
<@ww.form action="/ftl_hello.action" method="post">
    	<@ww.textfield label="User Name" name="userName" value="%{userName}"/>
		<@ww.submit name="Submit" value="Submit"/>
</@ww.form>
<br/>
</body>
</html>

