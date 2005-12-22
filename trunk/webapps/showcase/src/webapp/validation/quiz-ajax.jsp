<%@ taglib prefix="ww" uri="/webwork" %>

<!-- START SNIPPET: ajaxValidation -->

<html>
<head>
    <title>Validation - Basic</title>
    <ww:head theme="ajax"/>
</head>

<body>

<div id='two' style="border: 1px solid yellow;"><b>initial content</b></div>
<ww:form method="post" validate="true" theme="ajax">
    <ww:textfield label="Name" name="name"/>
    <ww:textfield label="Age" name="age"/>
    <ww:textfield label="Favorite color" name="answer"/>
    <ww:submit resultDivId="two"/>
</ww:form>

</body>
</html>

<!-- END SNIPPET: ajaxValidation -->
