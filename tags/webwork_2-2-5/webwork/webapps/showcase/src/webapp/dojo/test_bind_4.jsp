<%-- 
	- use formId
	- bind when topic is received by submitting form
	- fire topic when bind done
	- use onLoad
	- use evalResult
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
	function fireTopic() {
		dojo.event.topic.publish("/topic", {});
	};

	dojo.addOnLoad(function() {
		var obj = {
			notifyingTopicReceived: function() {
				alert('notifying topic received');
			}
		};
		dojo.event.topic.subscribe("/notifyingTopic", obj, "notifyingTopicReceived");
	});
</script>

</head>
<body>
	<div id="result" style="border: 2px solid red">Javascript should be executed</div>
	<div dojoType="webwork:Bind"
			listenTopics="/topic"
			notifyTopics="/notifyingTopic"
			formId="myForm"
			onLoad="alert('ok data loaded');"
			evalResult="true"
	>
	</div>
	<form id="myForm" action="<ww:url value='/dojo/javascript.jsp' />" method="POST">
		<input type="text" name="name" />
		<input type="text" name="comment" />
		<input type="button" value="Fire Topic" onclick="fireTopic();" />	
	</form>
</body>
</html>