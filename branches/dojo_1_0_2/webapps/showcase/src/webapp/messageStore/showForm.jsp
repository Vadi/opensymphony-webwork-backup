<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="ww" uri="/webwork" %>    
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Showcase - Example of message store using 'store' interceptor</title>
<ww:head />
</head>
<body>

	<ww:fielderror />
	<ww:actionerror/>
	<ww:actionmessage /> 

	<ww:form action="submitForm" namespace="/messageStore">
		<ww:textfield name="name" label="Name" />
		<ww:textfield name="age" label="Age" />
		<ww:submit />
	</ww:form>

</body>
</html>

