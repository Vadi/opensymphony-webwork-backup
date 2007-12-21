<%--
	- test BindDIv
		- using delay
		- no refresh
		- use loadingHtml
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="ww" uri="/webwork" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

	<jsp:include flush="true" page="header.jsp"></jsp:include>

</head>
<body>
	
	<div dojoType="webwork:BindDiv" 
			href="<ww:url value='/dojo/currentTime.jsp' />" 
			delay="1000"
			loadingHtml="Loading ..."
			style="border: 2px solid red">Initial content</div>
		
</body>
</html>


