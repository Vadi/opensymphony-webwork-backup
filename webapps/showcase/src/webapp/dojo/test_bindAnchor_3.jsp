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

	<div id="result" style="border: 1px solid red">Initial Content</div>
	<form id="myForm" action="<ww:url value='/dojo/currentTime.jsp' />" method="POST">
		<input type="text" name="name"  value="" />
		<input type="text" name="comment" value="" />
	</form>
	<div dojoType="webwork:BindAnchor"
			formId="myForm"
			targetDiv="result"
	>
	test
	</div>
	
</body>
</html>