<%--

	- use href
	- bind when topic are received
	- fire topic when bind is done

--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="ww" uri="/webwork" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Showcase - Dojo</title>

<jsp:include flush="true" page="header.jsp"></jsp:include>

<script type="text/javascript">
	function fireTopic1() {
		dojo.event.topic.publish("/topic1", {});
	};
	
	function fireTopic2() {
		dojo.event.topic.publish("/topic2", {});
	};

	var obj = {
		notifyingTopic1Received: function(evt) {
			alert('Notifying Topic 1 Received');
		},
		notifyingTopic2Received: function(evt) {
			alert('Notifying Topic 2 Received');
		}
	};	
	
	dojo.addOnLoad(function() {
		dojo.event.topic.subscribe('/notifyingTopic1', obj, 'notifyingTopic1Received');
		dojo.event.topic.subscribe('/notifyingTopic2', obj, 'notifyingTopic2Received');
	});
</script>

</head>
<body>

<div id="result" style="border: 2px solid red;">
	The current time will show here.
</div>
<div dojoType="webwork:Bind" 
		href="<ww:url value='/dojo/currentTime.jsp' />" 
		listenTopics="/topic1, /topic2" 
		notifyTopics="/notifyingTopic1, /notifyingTopic2" 
		targetDiv="result" 
		showTransportError="true">
</div>
<input type="button" onclick="fireTopic1();" value="Fire Topic 1" />
<input type="button" onclick="fireTopic2();"  value="Fire Topic 2" />
</body>
</html>