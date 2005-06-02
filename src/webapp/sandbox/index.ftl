<#include "common.ftl"/>
<html>
	<head>
		<script type="text/javascript" src="/webwork/shared/jscalendar-1.0/calendar.js"></script>
		<script type="text/javascript" src="/webwork/shared/jscalendar-1.0/calendar-setup.js"></script>
		<script type="text/javascript" src="/webwork/shared/jscalendar-1.0/lang/calendar-en.js"></script>
		<link rel="stylesheet" type="text/css" media="all" href="/webwork/shared/jscalendar-1.0/skins/aqua/theme.css" title="Aqua" />
		<link rel="alternate stylesheet" type="text/css" media="all" href="/webwork/shared/jscalendar-1.0/calendar-blue.css" title="winter" />
		<link rel="alternate stylesheet" type="text/css" media="all" href="/webwork/shared/jscalendar-1.0/calendar-blue2.css" title="blue" />
		<link rel="alternate stylesheet" type="text/css" media="all" href="/webwork/shared/jscalendar-1.0/calendar-brown.css" title="summer" />
		<link rel="alternate stylesheet" type="text/css" media="all" href="/webwork/shared/jscalendar-1.0/calendar-green.css" title="green" />
		<link rel="alternate stylesheet" type="text/css" media="all" href="/webwork/shared/jscalendar-1.0/calendar-win2k-1.css" title="win2k-1" />
		<link rel="alternate stylesheet" type="text/css" media="all" href="/webwork/shared/jscalendar-1.0/calendar-win2k-2.css" title="win2k-2" />
		<link rel="alternate stylesheet" type="text/css" media="all" href="/webwork/shared/jscalendar-1.0/calendar-win2k-cold-1.css" title="win2k-cold-1" />
		
		<link rel="alternate stylesheet" type="text/css" media="all" href="/webwork/shared/jscalendar-1.0/calendar-win2k-cold-2.css" title="win2k-cold-2" />
		<link rel="alternate stylesheet" type="text/css" media="all" href="/webwork/shared/jscalendar-1.0/calendar-system.css" title="system" />

		<@dojoRuntime 
			includes=[
				"webwork.widgets.DynArchCalendar"
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

		<@example heading='Calendar'>
			<label for='calendar'>Date Select</label>
			<dojo:dynarchcalendar 
				id='myCal' 
				ifFormat='%A, %B %e, %Y'
				inputFieldStyle='width:200px'
			/>
			<input type='button' onclick='myCal.show()' value='Show Calendar'>
		</@>
		
	</body>
	
</html>

