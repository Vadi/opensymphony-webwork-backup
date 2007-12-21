<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="ww" uri="/webwork"  %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Showcase - Session Invalidation - Invalidate Now</title>
</head>
<body>
	The Session is Invalidated at the end of this request
	<table>
		<tr>
			<td>___invalidateSession</td>
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
	<ww:url id="issueAnotherRequestAction" action="issueAnotherRequest" namespace="/sessionInvalidation" />
	<ww:a href="%{#issueAnotherRequestAction}">Issue another request to see that the session is already invalidated</ww:a>
</body>
</html>