<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="ww" uri="/webwork" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Showcase - Session Invalidation</title>
</head>
<body>
	This are the values from http session :-
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
	
	<ww:url id="invalidateNowAction" action="invalidateNow" namespace="/sessionInvalidation" />
	<ww:url id="invalidateOnNextRequestAction" action="invalidateOnNextRequest" namespace="/sessionInvalidation" />
	
	<ww:a href="%{#invalidateNowAction}">Invalidate Now</ww:a>
	<ww:a href="%{#invalidateOnNextRequestAction}">Invalidate On Next Request</ww:a>
</body>
</html>