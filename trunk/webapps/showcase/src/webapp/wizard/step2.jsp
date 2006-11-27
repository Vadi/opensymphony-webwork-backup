<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="ww" uri="/webwork" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>WebWork Showcase - Wizard (Step 2)</title>
</head>
<body>

	<ww:form method="post" namespace="/wizard">
		<ww:select label="Programming Language" name="programmingLanguage" list="%{#{'Java':'Java', 'C':'C Language', 'C++':'C++ Language', 'Smalltalk':'Smalltalk'}}"  />
		<ww:select label="Preferred Language" name="preferredLanguage" list="%{#{'English':'English', 'French':'French', 'German':'German', 'Spanish':'Spanish'}}" />
		<ww:textarea label="Interest" name="interest" />
		<ww:submit value="%{'Previous Step (Step1)'}" action="backToStep1" />
		<ww:submit value="%{'Next Step (Step 3)'}" action="forwardToStep3" />
	</ww:form>

</body>
</html>