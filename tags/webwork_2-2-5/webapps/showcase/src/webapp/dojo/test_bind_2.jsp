<%-- 
	- use formId
	- bind when topic is received with formId specified causes the form to be submitted
	- fire topics when bind is done
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
	}	
	
	dojo.addOnLoad(function() {
		var obj = {
			notifyingTopic1Received: function() {
				alert('Notifying Topic 1 Received');
			},
			notifyingTopic2Received: function() {
				alert('Notifying Topic 2 Received');
			}
		}
		dojo.event.topic.subscribe('/notifyingTopic1', obj, 'notifyingTopic1Received');
		dojo.event.topic.subscribe('/notifyingTopic2', obj, 'notifyingTopic2Received');
	});
</script>
	
</head>
<body>
	<div id="result" style="border: 2px solid red">Greetings and Current Time will be displayed here</div>
	<div dojoType="webwork:Bind"
			formId="myForm"
			listenTopics="/topic"
			notifyTopics="/notifyingTopic1, /notifyingTopic2"
			targetDiv="result"
			useCache="false"
	>
	</div>
	<form id="myForm" action="<ww:url value='/dojo/currentTime.jsp' />" method="POST">
		<input type="text" name="name" />
		<input type="text" name="comment" />
		<input type="button" onclick="fireTopic()"  value="Fire Topic" />
	</form> 

</body>
</html>


