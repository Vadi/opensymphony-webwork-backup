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
		var advice = {
			advice: function() {
				var widget = dojo.widget.byId("bindDiv");
				widget.stop();
			}
		};
	
		dojo.addOnLoad(function() {
			dojo.event.kwConnect({
				srcObj: dojo.byId("stop"),
				srcFunc: 'onclick',
				adviceObj: advice,
				adviceFunc: 'advice'
			});
		});
	</script>

</head>
<body>

	<form id="myForm" action="<ww:url value='/dojo/currentTime.jsp' />" method="POST">
		<input type="text" name="name"  value="toby" />
		<input type="text" name="comment" value="webwork rocks !!!" />
	</form>
	<div dojoType="webwork:BindDiv"
			id="bindDiv"
			formId="myForm"
			delay="1000"
			refresh="3000"
	>
		Initial Value
	</div>
	<input id="stop" type="button" value="stop" />

</body>
</html>