<%@ taglib prefix="ww" uri="/webwork" %>
<html>
<head>
    <title>Hello World</title>
</head>

<body>

<ww:form action="hello" validate="true">
    <ww:textfield label="Name" name="name"/>
    <ww:submit value="Say Hello"/>
</ww:form>

</body>
</html>