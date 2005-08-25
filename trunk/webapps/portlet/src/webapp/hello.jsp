<%@ taglib uri="webwork" prefix="ww" %>
<html>
<head><title>Please input your information</title>
    <meta name="help-path" content="/help/help7.html"/>
</head>

<body>
<p>Welcome to Use <ww:property value="now"/>
</p>

<ww:form action="'/jsphello.action'" method="'post'">
    <input type="text" name="userName" value=""/>
    <INPUT name="Submit" type="submit" value="Submit"/>
</ww:form>
<br/>
</body>
</html>

