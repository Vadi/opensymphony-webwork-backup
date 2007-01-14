<%@ taglib prefix="ww" uri="/webwork" %>

<!-- START SNIPPET: ajaxValidation -->

<html>
<head>
    <title>Validation - Basic</title>
    <ww:head theme="ajax" debug="true"/>
</head>

<body>

<div id="r">test</div>

<ww:form id="f1" action="quizAjax" namespace="/validation" method="post" validate="false" theme="ajax">
    <ww:textfield label="Name" name="name" labelposition="left" />
    <ww:textfield label="Age" name="age" labelposition="left" />
    <ww:textfield label="Favorite color" name="answer" labelposition="left" />
    <ww:submit id="b1" />
</ww:form>

<ww:form id="f2" action="quizAjax" namespace="/validation" method="post" validate="true" theme="ajax">
    <ww:textfield label="Name" name="name" labelposition="top" />
    <ww:textfield label="Age" name="age" labelposition="top" />
    <ww:textfield label="Favorite color" name="answer" labelposition="top" />
    <ww:submit id="b2"/>
</ww:form> 

</body>
</html>

<!-- END SNIPPET: ajaxValidation -->
