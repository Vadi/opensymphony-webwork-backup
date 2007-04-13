<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="ww" uri="/webwork" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Showcase - Flash</title>
</head>
<body>
	
	This information will be saved by "flash" interceptor. It will be available 
	even after a redirect.<p/>
	
	"Flash" interceptor actually saves current action into http session and pushes
	it into the stack in the new request, such that the information stored in
	the this action will be available in the  next request even after a
	redirect. <p/>
	
	<ww:form namespace="/flash">
		<ww:textfield name="name" label="Name" />
		<ww:textfield name="address" label="Address" />
		<ww:submit action="flashUsingInterceptor"  value="Flash (using Flash interceptor)" />
		<ww:submit action="flashUsingResult" value="Flash (using Flash Result)" />
	</ww:form>

</body>
</html>

