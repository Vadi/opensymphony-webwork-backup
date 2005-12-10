<html>
<head>
<title><@ww.text name="heading_ftl" /></title>
<meta name="help-path" content="/help/help1_1.html"/>
</head>
<body>
<p><@ww.text name="greeting_ftl" /> @ <@ww.property value="now" /></p>
<p>User Name: <@ww.property value="userName" /> <br>
Repeat:  ${userName}</p>
<@ww.form action="/ftl_hello.action" method="post">
	<@ww.textfield label="User Name" name="userName"/>
	<@ww.submit value="Submit" />
</@ww.form>
<@ww.a href="ex01.ftl" id="hello">Back</@ww.a>

</body>
</html>

