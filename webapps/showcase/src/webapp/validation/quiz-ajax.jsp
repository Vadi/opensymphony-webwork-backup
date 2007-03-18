<%@ taglib prefix="ww" uri="/webwork" %>
<%@page import="com.opensymphony.xwork.*" %>
<%@page import="java.util.*" %>

<!-- START SNIPPET: ajaxValidation -->

<html>
<head>
    <title>Validation - Basic</title>
    <ww:head theme="ajax" debug="true"/>
</head>

<body>

This quiz (ajax) example is customized to use 2 locale, namely en_US and cn_ZH, as I don't know 
how to write / read chinese, the chinese resource bundle is just like the english but prefixed with (cn)
<ul>
	<li>
		<ww:url id="url" namespace="/validation" action="quizAjax" method="input">
			<ww:param name="request_locale" value="%{'zh_CN'}" />
		</ww:url>
		To swich to use the chinese resource bundle click <ww:a href="%{#url}">here</ww:a>.
	</li>
	<li>
		<ww:url id="url" namespace="/validation" action="quizAjax" method="input">
			<ww:param name="request_locale" value="%{'en_US'}" />
		</ww:url>
		To swich to use the english resource bundle click <ww:a href="%{#url}">here</ww:a>.
	</li>
</ul>

The following form uses the labelposition="left"
<ww:form id="f1" action="quizAjax" namespace="/validation" method="post" validate="true" theme="ajax">
    <ww:textfield label="Name" name="name" labelposition="left" />
    <ww:textfield label="Age" name="age" labelposition="left" />
    <ww:textfield label="Favorite color" name="answer" labelposition="left" />
    <ww:submit id="b1" />
</ww:form>

The following form uses the labelposition="top"
<ww:form id="f2" action="quizAjax" namespace="/validation" method="post" validate="true" theme="ajax">
    <ww:textfield label="Name" name="name" labelposition="top" />
    <ww:textfield label="Age" name="age" labelposition="top" />
    <ww:textfield label="Favorite color" name="answer" labelposition="top" />
    <ww:submit id="b2"/>
</ww:form> 

</body>
</html>

<!-- END SNIPPET: ajaxValidation -->
