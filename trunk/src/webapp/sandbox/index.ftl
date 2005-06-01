<#include "common.ftl"/>
<#assign dojoBase = url('/webwork/dojo')/>
<html>
	<head>
		<script language="JavaScript" type="text/javascript">
			// Dojo configuration
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
	
		<h1>Dojo Widget RemoteDiv</h1>
		
		<@code><dojo:remotediv href="date.jsp" updateFreq='1000'/></@>
		
		<dojo:remotediv href="date.jsp" updateFreq='1000'/>

		<@code><div dojoType="RemoteDiv" href="date.jsp?sleep=1000" loadingHtml='Loading...' delay='1000' initialContent="&nbsp;"></div></@>

		<#-- the & in the URL is a temp workaround - there is a bug in dojo.io.bind that appends a ? to the url -->
		<div dojoType="RemoteDiv" href="date.jsp?sleep=1000&" loadingHtml='Loading...' delay='1000' initialContent="&nbsp;"></div>


	</body>
</html>

