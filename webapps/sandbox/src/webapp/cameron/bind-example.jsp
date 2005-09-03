<%@ taglib uri="webwork" prefix="ww" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
	<head>
		<script type="text/javascript">
			djConfig = { isDebug: true };
		</script>
	
	    <script language="JavaScript" type="text/javascript" src="<ww:url value='/webwork/dojo/dojo.js'/>"></script>
	
		<script type="text/javascript">
			dojo.require("webwork.widgets.Bind");
			dojo.require("webwork.widgets.BindDiv");
			dojo.require("webwork.widgets.BindButton");
			dojo.require("webwork.widgets.BindAnchor");
			dojo.require("webwork.widgets.ToggleBindDiv");
		</script>
		
		<style>
			.sampleBox {
				text-align : center;
				vertical-align: middle;
				width:250px;
			}
		</style>
	</head>
	
	<body>
		
		<a href='javascript:dojo.event.topic.publish("allWidgets", "")'>send event to allWidgets</a>
		
		<hr/>
	
		<div dojoType='BindDiv'
			id='rd1'
			href="data/date.jsp?sleep=1000" 
			loadingHtml='Loading...'
			delay='1000'
			refresh='2000' 
			autoStart='false'
			style='border: 1px solid red;'
			listenTopics='allWidgets'
			>
			<b>initial content</b>
		</div>
		<a href='javascript:rd1.start()'>start</a>
		<a href='javascript:rd1.stop()'>stop</a>

		<hr/>
		
		<div dojoType='BindDiv'
			id='rd2'
			getHref="'data/date.jsp?sleep=2000&__date__=' + new Date().getTime()" 
			loadingHtml='Loading...'
			delay='0'
			refresh='0' 
			style='border: 1px solid red;'
			listenTopics='allWidgets'
			>
			<b>initial content</b>
		</div>
		
		<hr/>
		
		<a id='anchor' dojoType='bindanchor' href='data/date.jsp' targetDiv='anchor' listenTopics='allWidgets'>
			click me to see the current server datetime
		</a>				

		<hr/>
		
		<a dojoType='bindanchor' href='data/alert.js' evalResult='true' listenTopics='allWidgets'>
			click me to eval some server side javascript
		</a>				

		<hr/>

		<h2>Togglable div</h2>		
		<p>
			<ul>
				<li>will display below here, when toggled
				<li>initially hidden
				<li><a href="javascript:dojo.event.topic.publish('toggles', '')">toggle</a>
			</ul>
		</p>
		<div dojoType='BindDiv'
			id='toggle1'
			getHref="'data/date.jsp?sleep=2000&__date__=' + new Date().getTime()" 
			loadingHtml='Loading...'
			style='border: 1px solid red; display:none;'
			listenTopics='toggles,allWidgets'
			toggle='true'
			>
			<b>initial content</b>
		</div>

		<hr/>

		<h2>Togglable div (using subclass - an example for Pat)</h2>		
		<p>
			<ul>
				<li>will display below here, when toggled
				<li>initially hidden
				<li><a href="javascript:dojo.event.topic.publish('toggles', '')">toggle</a>
			</ul>
		</p>
		<div dojoType='ToggleBindDiv'
			id='toggle2'
			getHref="'data/date.jsp?sleep=2000&__date__=' + new Date().getTime()" 
			loadingHtml='Loading...'
			style='border: 1px solid red; display:none;'
			listenTopics='toggles,allWidgets'
			toggle2='true'
			>
			<b>initial content</b>
		</div>

		<hr/>

	</body>
</html>