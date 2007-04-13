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

	<ww:label name="name" label="Name" /><br/>
	<ww:label name="age" label="Age" /><br/>
	<ww:label name="telephone" label="Telephone" /><br/>
	<ww:label name="gender" label="Gender" /><br/>
	<ww:label name="programmingLanguage" label="Programming Language" /><br/>
	<ww:label name="preferredLanguage" label="Preferred Language" /><br/>
	<ww:label name="interest" label="Interest" /><br/>
	<ww:label name="hobby.name" label="hobby name" /><br/>
	<ww:label name="hobby.description" label="hobby description" /><br/>
	
	<ww:form method="POST" namespace="/wizard">
		<ww:submit value="%{'Previous (Step 3)'}" action="backToStep3" />
		<ww:submit value="%{'Confirm'}" action="confirm" />
	</ww:form>

</body>
</html>