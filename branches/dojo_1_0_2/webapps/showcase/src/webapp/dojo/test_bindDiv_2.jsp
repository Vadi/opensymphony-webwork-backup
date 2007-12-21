<%--
	- use delay
	- use refresh
	- didn't use loadingHtml
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

	<script type="text/javascript">
		dojo.addOnLoad(function() {
			var advice = {
				advice: function() {
					var button = dojo.widget.byId('bindDiv');
					button.stop();
				}
			};
			
			dojo.event.kwConnect({
				srcObj: dojo.byId('stop'),
				srcFunc: 'onclick',
				adviceObj: advice,
				adviceFunc: 'advice'
			});
		});
	</script>

</head>
<body>
	<div dojoType="webwork:BindDiv" 
			id="bindDiv"
			href="<ww:url value='/dojo/currentTime.jsp' />" 
			delay="1000"
			refresh="3000"
			style="border: 2px solid red">Initial content</div>
	<input id="stop" type="button" value="stop" />	
</body>
</html>


