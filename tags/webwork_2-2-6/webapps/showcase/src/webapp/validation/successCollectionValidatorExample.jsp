<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="ww" uri="/webwork" %>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<ww:iterator id="person" value="%{persons}" status="stat">
		<ww:label label="Agent" value="%{#stat.index}" /><br/>
		<ww:label label="Name" value="%{#person.name}" /><br/>
		<ww:label label="Age" value="%{#person.age}" /><br/>
	</ww:iterator>
</body>
</html>