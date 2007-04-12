<%--
	- use href
	- bind when topic is received
	- fire topic when bind completed
	- use getHref
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
			dojo.event.topic.publish('/topic', {});
		}
		
		dojo.addOnLoad(function() {
			var obj = {
				notifyingTopicReceived: function() {
					alert('Notifying Topic Received');
				}
			};
			dojo.event.topic.subscribe('/notifyingTopic', obj, 'notifyingTopicReceived');
		});
	</script>

</head>
<body>
	<div id="result" style="border: 2px solid red">Javascript should be executed when topic is fired</div>
	<div dojoType="webwork:Bind"
			href="<ww:url value='/dojo/javascript.jsp' />"
			listenTopics="/topic"
			notifyTopics="/notifyingTopic"
			onLoad="alert('ok data already loaded')"
			evalResult="true"
			showTransportError="true"
	>
	</div>
	<input type="button" value="Fire Topic" onclick="fireTopic();" />
</body>
</html>