<%@ taglib prefix="ww" uri="/webwork" %>
<html>
<head>
    <title>Validation - Basic</title>
    <link rel="stylesheet" href="<ww:url value="/webwork/xhtml/styles.css"/>" type="text/css"/>
</head>

<body>

<b>What is your favorite color?</b>
<p/>

<ww:form method="post">
    <ww:textfield label="Name" name="name"/>
    <ww:textfield label="Age" name="age"/>
    <ww:textfield label="Favorite color" name="answer"/>
    <ww:submit/>
</ww:form>

</body>
</html>