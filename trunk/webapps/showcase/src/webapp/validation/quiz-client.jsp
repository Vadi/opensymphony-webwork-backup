<%@ taglib prefix="ww" uri="/webwork" %>
<html>
<head>
    <title>Validation - Basic</title>
    <ww:head/>
</head>

<body>

<!-- START SNIPPET: clientValidation -->

<ww:form method="post" validate="true">
    <ww:textfield label="Name" name="name"/>
    <ww:textfield label="Age" name="age"/>
    <ww:textfield label="Favorite color" name="answer"/>
    <ww:submit/>
</ww:form>

<!--  END SNIPPET: clientValidation -->


</body>
</html>