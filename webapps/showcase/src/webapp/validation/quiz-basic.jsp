<%@ taglib prefix="ww" uri="/webwork" %>

<!-- START SNIPPET: basicValidation -->

<html>
<head>
    <title>Validation - Basic</title>
    <ww:head/>
</head>

<body>

<p/>

<p>
The following form uses labelposition="left"
<ww:form id="f0" method="post" labelposition="left">
    <ww:textfield label="Name" name="name" labelposition="left"/>
    <ww:textfield label="Age" name="age" labelposition="left"/>
    <ww:textfield label="Favorite color" name="answer" labelposition="left"/>
    <ww:submit/>
</ww:form>
</p>

<p>
The following form uses labelposition="top"
<ww:form id="f1" method="post" labelposition="top">
    <ww:textfield label="Name" name="name" labelposition="top"/>
    <ww:textfield label="Age" name="age" labelposition="top"/>
    <ww:textfield label="Favorite color" name="answer" labelposition="top"/>
    <ww:submit/>
</ww:form>
</p>

</body>
</html>

<!-- END SNIPPET: basicValidation -->

