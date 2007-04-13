<%--
	- use targetDiv
	- use href
	- use preInvokeJs
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

<script type="text/javascript">
	function preInvoke() {
		var text = dojo.byId("text");
		if (text.value == "true") {
			return true;
		}
		return false;
	}
</script>
<div id="result">Initial Value</div>
<input id="text" type="text" value="true" />
<div dojoType="webwork:BindButton"
		targetDiv="result"
		href="<ww:url value='/dojo/currentTime.jsp' />"
		preInvokeJs="preInvoke();"
>
</div>

</body>
</html>