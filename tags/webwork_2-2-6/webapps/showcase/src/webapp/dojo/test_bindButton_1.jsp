<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%--
	- use targetDiv
	- use href
	- didn't use preInvokeJs
 --%>

<%@taglib prefix="ww" uri="/webwork" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
	<jsp:include flush="true" page="header.jsp"></jsp:include>
</head>
<body>
	
	<div id="result" style="border: 2px solid red">Initial Content</div>
	<div dojoType="webwork:BindButton"
			 targetDiv="result"
			 href="<ww:url value='/dojo/currentTime.jsp' />"
			 style="border: 2px solid red"
	>
	</div>

</body>
</html>