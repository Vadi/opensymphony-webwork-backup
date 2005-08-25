<%@ taglib uri="webwork" prefix="ww" %>
<html>
<head>
    <title><ww:text name="heading"/></title>
    <meta name="help-path" content="/help/help7.html"/>
</head>

<body>
<p><ww:text name="greeting"/> @ <ww:property value="now"/></p>

<p>User Name: <ww:property value="userName"/></p>
<ww:form action="/jsphello.action" method="post">
    <!-- input type="text" name="userName" value="" / -->
    <ww:textfield label="User Name" name="userName"/>
    <br>
    <ww:submit value="Submit"/>
</ww:form>
<ww:a href="hello.jsp" id="hello">Back</ww:a>
</body>
</html>

