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
			// pull in all the stuff we need to make the widget go
			dojo.hostenv.loadModule("webwork.widgets.RemoteDiv");
		</script>
		
	</script>
	</head>
	
	<body>
	
		<@example heading='XHTML Widget Tag'>
<dojo:remotediv 
	href="date.jsp?sleep=500" 
	loadingHtml='Loading...'
	delay='500'
	updateFreq='1000' 
	style='border:1px solid red; width:200px; height:50px'
	>
		<b>initial content</b>
</dojo:remotediv>
</@>
		
<@example heading='HTML DIV Tag'>
<div 
	dojoType="RemoteDiv" 
	href="date.jsp" 
	delay='1000'
	style='border:1px solid orange; width:200px;'
	>
		<b>initial content</b>
</div>
</@>

	</body>
</html>

