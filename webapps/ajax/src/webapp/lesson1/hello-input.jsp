<%@ taglib prefix="ww" uri="/webwork" %>
<html>
<head>
    <title>Hello World</title>
    <jsp:include page="../commonInclude.jsp"/>
</head>

<body>

<ww:form action="hello" validate="true" >
    <ww:textfield label="Name" name="name" required="true"/>
    <ww:submit value="Say Hello"/>
</ww:form>

<form action="hello.jsp"><button type="submit">Huhu</button></form>

</body>
</html>