<%@ taglib prefix="ww" uri="/webwork" %>
<html>
<head>
    <title>Validation - Basic</title>
    <ww:head/>
</head>

<body>

<b>What is your favorite color?</b>
<p/>

<!-- START SNIPPET: basicValidation -->

<ww:form method="post">
    <ww:textfield label="Name" name="name"/>
    <ww:textfield label="Age" name="age"/>
    <ww:textfield label="Favorite color" name="answer"/>
    <ww:submit/>
</ww:form>

<!-- END SNIPPET: basicValidation -->

</body>
</html>