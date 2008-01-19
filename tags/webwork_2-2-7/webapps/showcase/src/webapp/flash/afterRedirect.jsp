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
	This a redirect, the information store by the "flash" interceptor should be 
	available<p/>
	<ww:property value="%{name}" /><br/>
	<ww:property value="%{address}" /><br/>

</body>
</html>