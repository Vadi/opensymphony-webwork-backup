<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="ww" uri="/webwork" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>WebWork Showcase - Wizard (Step 3)</title>
</head>
<body>

	<ww:form method="POST" namespace="/wizard">
		<ww:textfield label="hobby name" name="hobby.name" value="%{hobby.name}" />
		<ww:textarea label="hobby description" name="hobby.description" value="%{hobby.description}" />
		<ww:submit value="%{'Previous (Step 2)'}" action="backToStep2" />
		<ww:submit value="%{'Next (Step 4)'}" action="forwardToStep4" />
	</ww:form>

</body>
</html>