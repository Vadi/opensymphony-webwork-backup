<#include "common.ftl"/>
<html>
	<head>
		<@dojoRuntime 
			includes=[
				"webwork.widgets.RemoteDiv",
				"webwork.widgets.RemoteButton"
			]
			isDebug=true
		/>
		
		<style>
			.sampleBox {
				text-align : center;
				vertical-align: middle;
				width:250px;
			}
		</style>
	</head>
	
	<body>

		<@example heading='RemoteDiv : XHTML Widget Tag'>
			<dojo:remotediv 
				id='rd1'
				href="data/date.jsp?sleep=1000" 
				loadingHtml='Loading...'
				delay='1000'
				refresh='2000' 
				class='sampleBox'
				style='border: 1px solid red;'
				triggerTopics='allWidgets'
				>
				<b>initial content</b>
			</dojo:remotediv>
			<a href='javascript:rd1.start()'>start</a>
			<a href='javascript:rd1.stop()'>stop</a>
		</@>
		
		<@example heading='RemoteDiv : Standard HTML DIV Tag'>
			<div 
				id='rd2'
				dojoType="RemoteDiv" 
				href="data/date.jsp" 
				delay='1000'
				class='sampleBox'
				style='border: 1px solid orange;'
				triggerTopics='allWidgets'
				>
					<b>initial content</b>
			</div>
			<a href='javascript:rd2.bind()'>bind</a>
		</@>

		<@example heading='RemoteButton - using javascript to handle the results'>
			<form id='theForm' action='data/form.ftl' onSubmit='return false;' method='post'>
				<input type='text' name='name' value='WebWork User'>
			</form>
			
			<dojo:RemoteButton
				formId="theForm" 
				onLoad="document.getElementById('theFormOutput').innerHTML = 'received data :' + data;"
				value='Post'
				triggerTopics='allWidgets'
			/>
			<div id='theFormOutput'></div>
		</@><b>Note : RemoteButton has issues with IE .. some strange SystemError with http.responseText </b>

		<@example heading='RemoteButton rendering the results into a targetDiv'>
			<form id='theForm2' action='data/form.ftl' onSubmit='return false;' method='post'>
				<input type='text' name='name' value='WebWork User'>
			</form>
			
			<dojo:RemoteButton
				formId='theForm2'
				targetDiv='theForm2Output'
				value='Post'
				triggerTopics='allWidgets'
			/>
			<div id='theForm2Output'>form 2 will render to here</div>
		</@><b>Note : RemoteButtonAndDiv has issues with IE .. some strange SystemError with http.responseText </b>


		<hr>
			<a href='javascript:dojo.event.topic.publish("allWidgets", "trigger");'>send trigger event to 'allWidgets'</a>

	</body>
</html>

