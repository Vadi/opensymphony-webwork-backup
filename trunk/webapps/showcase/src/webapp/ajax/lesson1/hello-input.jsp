<%@ taglib prefix="ww" uri="/webwork" %>
<html>
<head>
    <title>Hello World</title>
    <jsp:include page="/ajax/commonInclude.jsp"/>
</head>

<body>

<ww:form action="hello" validate="true" theme="ajax" name="hello" id="helloFormId">
    <ww:textfield label="Name" name="name" id="name" required="true" theme="ajax"/>
    <ww:submit value="Say Hello" theme="ajax" resultDivId="helloFormId"/>
</ww:form>

</body>
</html>