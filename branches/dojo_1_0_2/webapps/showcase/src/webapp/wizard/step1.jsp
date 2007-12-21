<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="ww" uri="/webwork"  %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>WebWork Showcase - Wizard (Step 1)</title>
</head>
<body>

	<ww:form method="POST" namespace="/wizard">
		<ww:textfield label="Name" name="name" />
		<ww:textfield label="Age" name="age" />
		<ww:textfield label="Telephone" name="telephone" />
		<ww:radio label="Gender" list="%{#{'Male':'Male', 'Female':'Female'}}"  name="gender" />
		<ww:submit value="%{'Next Step (Step2)'}" action="forwardToStep2" />
	</ww:form>

</body>
</html>

