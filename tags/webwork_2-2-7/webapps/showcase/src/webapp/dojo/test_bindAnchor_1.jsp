<%--
	- use targetDiv
	- use href
	- no use of preInvokeJs
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="ww" uri="/webwork" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Showcase - Dojo - Test BindAnchor - 1</title>
	<jsp:include flush="true" page="header.jsp"></jsp:include>
</head>
<body>
	<div id="result" style="border: 2px solid red">Initial Content</div>
	<div dojoType="webwork:BindAnchor"
			targetDiv="result"
			href="<ww:url value='/dojo/currentTime.jsp' />"
			style="color: green"
	>asd
	</div>

</body>
</html>