<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="ww" uri="/webwork" %>

<html>
<head>
    <title>Ajax Examples - Example 2</title>
    <jsp:include page="/ajax/commonInclude.jsp"/>
</head>
<body>
	
	<div id="result">
		Initial content ...
	</div>
	
	<ww:form action="GetGreetingAndCurrentTime" theme="ajax" namespace="/nodecorate">
		<ww:textfield label="Name" name="name" value="Toby" />
		
		<ww:submit action="button1" id="buttonId1" theme="ajax" value="Button 1" resultDivId="result" />
		<ww:submit action="button2" id="buttonId2" theme="ajax" value="Button 2" resultDivId="result" />
		<ww:submit action="button3" id="buttonId3" theme="ajax" value="Button 3" resultDivId="result" />
	</ww:form>
	
	
</body>
</html>

