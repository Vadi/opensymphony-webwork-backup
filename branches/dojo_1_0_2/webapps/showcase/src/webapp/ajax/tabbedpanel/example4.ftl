<html>
<head>
	<title>Example 4</title>
	<@ww.head theme="ajax" debug="false" />
	<link rel="stylesheet" type="text/css" href="<@ww.url value="/webwork/tabs.css"/>" />
</head>
<body>
	<@ww.url id="panel1url" action="panel1" namespace="/nodecorate" includeContext="false" />
	<@ww.url id="panel2url" action="panel2" namespace="/nodecorate" includeContext="false"/>
	<@ww.url id="panel3url" action="panel3" namespace="/nodecorate" includeContext="false"/>
	<@ww.tabbedPanel id="tabbedpanel" >
		<@ww.panel id="panel1" tabName="Panel1" remote="true" href="%{#panel1url}" theme="ajax" /> 
		<@ww.panel id="panel2" tabName="Panel2" remote="true" href="%{#panel2url}" theme="ajax"  />
		<@ww.panel id="panel3" tabName="Panel3" remote="true" href="%{#panel3url}" theme="ajax" />
	</@ww.tabbedPanel>
</body>
</html>

