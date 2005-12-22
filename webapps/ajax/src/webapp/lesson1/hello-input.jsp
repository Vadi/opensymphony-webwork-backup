<%@ taglib prefix="ww" uri="/webwork" %>
<html>
<head>
    <title>Hello World</title>
    <jsp:include page="../commonInclude.jsp"/>
</head>

<body>

<ww:form action="hello" validate="true" theme="ajax">
    <ww:textfield label="Name" name="name" required="true" theme="ajax"/>
    <ww:submit value="Say Hello" theme="ajax"/>
</ww:form>

</body>
</html>