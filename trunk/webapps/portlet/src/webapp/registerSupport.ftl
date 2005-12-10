<html>
<head>
<title>WebWork Register - Example 6 (Freemarker Version)</title>
<meta name="help-path" content="/help/help6_1.html"/>
</head>
<body>

<table border=0 width=97%>
<tr><td align="left">
	<@ww.form name="test" action="/ftl_registerSupport.action" method="POST" >
            <@ww.textfield label="Username" name="user.username" required="true" />
            <@ww.textfield label="Password" name="user.password" required="true" />
            <@ww.textfield label="VerifyPassword" name="verifyPassword" required="true" />
            <@ww.textfield label="Email" name="user.email" required="true" />
            <@ww.textfield label="Age" name="user.age" required="true" />
            <@ww.submit value="Submit" />
    </@ww.form>

</td></tr>
</table>

<br/>
</body>
</html>
