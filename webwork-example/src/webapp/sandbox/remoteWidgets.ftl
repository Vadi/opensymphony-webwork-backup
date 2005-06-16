<#include "common.ftl"/>
<html>
	<head>
		<@dojoRuntime 
			includes=[
				"webwork.widgets.Bind",
				"webwork.widgets.BindDiv",
				"webwork.widgets.BindButton",
				"webwork.widgets.BindAnchor"
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

		<@example heading='binddiv : XHTML Widget Tag'>
			<dojo:binddiv 
				id='rd1'
				href="data/date.jsp?sleep=1000" 
				loadingHtml='Loading...'
				delay='1000'
				refresh='2000' 
				class='sampleBox'
				style='border: 1px solid red;'
				listenTopics='allWidgets'
				>
				<b>initial content</b>
			</dojo:binddiv>
			<a href='javascript:rd1.start()'>start</a>
			<a href='javascript:rd1.stop()'>stop</a>
		</@>
		
		<@example heading='binddiv : Standard HTML DIV Tag'>
			<div 
				id='rd2'
				dojoType="binddiv" 
				href="data/date.jsp" 
				delay='1000'
				class='sampleBox'
				style='border: 1px solid orange;'
				listenTopics='allWidgets'
				>
					<b>initial content</b>
			</div>
			<a href='javascript:rd2.bind()'>bind</a>
		</@>

		<@example heading='BindButton - using javascript to handle the results'>
			<form id='theForm' action='data/form.ftl' onSubmit='return false;' method='post'>
				<input type='text' name='name' value='WebWork User'>
			</form>
			
			<dojo:bindbutton
				formId="theForm" 
				onLoad="document.getElementById('theFormOutput').innerHTML = 'received data :' + data;"
				value='Post'
				listenTopics='allWidgets'>
			</dojo:bindbutton>

			<div id='theFormOutput'></div>
		</@><b>Note : BindButton has issues with IE .. some strange SystemError with http.responseText </b>

		<@example heading='bindbutton rendering the results into a targetDiv'>
			<form id='theForm2' action='data/form.ftl' onSubmit='return false;' method='post'>
				<input type='text' name='name' value='WebWork User'>
			</form>
			
			<dojo:bindbutton
				formId='theForm2'
				targetDiv='theForm2Output'
				value='Post'
				listenTopics='allWidgets'>
			</dojo:bindbutton>
			<div id='theForm2Output'>form 2 will render to here</div>
		</@><b>Note : BindButtonAndDiv has issues with IE .. some strange SystemError with http.responseText </b>

		<@example heading='bind executing the results as javascript'>
			<dojo:bind
				id='jsAlert'
				href='data/alert.js'
				listenTopics='allWidgets'
				evalResult='true'
			>
			</dojo:bind>
			<a href='javascript:jsAlert.bind()'>go</a>
		</@>

		<@example heading='bind executing the results as javascript'>
			<dojo:bindanchor
				id='bindAnchor'
				href='data/alert.js'
				listenTopics='allWidgets'
				evalResult='true'
			>click here to get an alert</dojo:bindanchor>
		</@>

		<@example heading='binding to a 404 page'>
			<dojo:binddiv 
				href="data/nonExistant.jsp" 
				loadingHtml='Loading...'
				showTransportError=true
				class='sampleBox'
				style='border: 1px solid red;'
				listenTopics='allWidgets'
				>
				<b>initial content</b>
			</dojo:binddiv>
		</@>


		<hr>
			<a href='javascript:dojo.event.topic.publish("allWidgets", "trigger");'>send trigger event to 'allWidgets'</a>

	</body>
</html>

