<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="ww" uri="/webwork" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Showcase - validation - Conversion Validation</title>
</head>
<body>

<ww:form name="submitConversionvalidationExample" namespace="/validation">
	<ww:textfield label="Age" name="age" />
	<ww:textfield label="Bean Age" name="bean.age" />
	<ww:submit />
</ww:form>

</body>
</html>