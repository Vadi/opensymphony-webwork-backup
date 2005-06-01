<#include "common.ftl"/>
<#assign dojoBase = url('/webwork/dojo')/>
<html>
	<head>
		<script language="JavaScript" type="text/javascript">
			djConfig = { 
				baseRelativePath: "${dojoBase}/",
				isDebug: true
			};
		</script>

		<script src="${dojoBase}/__package__.js" language="JavaScript" type="text/javascript" ></script>

		<script language="JavaScript" type="text/javascript">
			dojo.hostenv.loadModule("webwork.widgets.RemoteDiv");
			dojo.hostenv.loadModule("webwork.widgets.RemoteSubmitButton");
		</script>
		
		<style>
			.sampleBox {
				text-align : center;
				vertical-align: middle;
				border : 1px solid;
				width:250px;
			}
		</style>
	</head>
	
	<body>
	
		<@example heading='RemoteDiv : XHTML Widget Tag'>
<dojo:remotediv 
	href="data/date.jsp?sleep=1000" 
	loadingHtml='Loading...'
	delay='1000'
	updateFreq='2000' 
	class='sampleBox'
	style='border-color:red;'
	>
		<b>initial content</b>
</dojo:remotediv>
</@>
		
<@example heading='RemoteDiv : Standard HTML DIV Tag'>
<div 
	dojoType="RemoteDiv" 
	href="data/date.jsp" 
	delay='1000'
	class='sampleBox'
	style='border-color:orange;'
	>
		<b>initial content</b>
</div>
</@>

<@example heading='RemoteSubmitButton'>
<form id='theForm' action='data/form.ftl' oncubmit='return false;'>
	<input type='text' name='name' value='WebWork User'>
</form>

<dojo:RemoteSubmitButton
	formId="theForm" 
	onLoad="alert(data)"
/>
</@>

	</body>
</html>

