<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="ww" uri="/webwork" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Showcase - Session Invalidation - Another Request</title>
</head>
<body>
	Session attributes :-
	<table>
		<tr>
			<td>___invalidateSession (marker)</td>
			<td><ww:property value="%{#session['___invalidateSession']}" /></td>
		</tr>
		<tr>
			<td>someKey1</td>
			<td><ww:property value="%{#session.someKey1}" /></td>
		</tr>	
		<tr>
			<td>someKey2</td>
			<td><ww:property value="%{#session.someKey2}" /></td>
		</tr>
	</table>
	<ww:url id="startaction" action="start" namespace="/sessionInvalidation" />
	<ww:a href="%{#startaction}">Start over again</ww:a>
</body>
</html>